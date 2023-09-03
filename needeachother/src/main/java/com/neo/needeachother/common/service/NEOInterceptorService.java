package com.neo.needeachother.common.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.enums.NEOResponseCode;
import com.neo.needeachother.common.response.NEOErrorResponse;
import com.neo.needeachother.common.response.NEOFinalErrorResponse;
import com.neo.needeachother.users.enums.NEOUserApiOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NEOInterceptorService {

    private final ObjectMapper objectMapper;

    public HashSet<String> getJsonPropertyNames(Class<?> clazz) {
        HashSet<String> jsonProperties = new HashSet<>();

        if (clazz == null) {
            return jsonProperties;
        }

        Field[] annotatedFields = objectMapper.getSerializationConfig()
                .introspect(objectMapper.constructType(clazz))
                .findProperties()
                .stream()
                .map(property -> property.getField().getAnnotated())
                .toArray(Field[]::new);

        for (Field field : annotatedFields) {
            JsonProperty jsonPropertyAnnotation = field.getAnnotation(JsonProperty.class);
            if (jsonPropertyAnnotation != null) {
                jsonProperties.add(jsonPropertyAnnotation.value());
            }
        }

        return jsonProperties;
    }

    public String convertDtoToWriterValue(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

    public Map getRequestBodyMap(String requestBody) throws IOException {
        return objectMapper.readValue(requestBody, Map.class);
    }

    public NEOFinalErrorResponse getFinalErrorResponseWithJsonFieldError(List<String> errorJsonFieldNameList, NEOUserApiOrder userOrder) {

        // 문제가 있는 경우, 문제 대상 리스트를 기반으로 NEOErrorResponse 리스트 생성
        List<NEOErrorResponse> errorResponseList = errorJsonFieldNameList.stream()
                .map(errorJsonFieldName -> NEOErrorResponse.fromErrorCodeWithDetail(NEOErrorCode.INVALID_JSON_FIELD_NAME, "에러 대상 : " + errorJsonFieldName))
                .toList();

        // 최종 에러 응답 생성
        return NEOFinalErrorResponse.builder()
                .responseCode(NEOResponseCode.FAIL)
                .msg(userOrder.getFailMessage())
                .requestedMethodAndURI(userOrder.getRequestedMethodAndURI())
                .errors(errorResponseList).build();
    }

    public NEOFinalErrorResponse getFinalErrorResponseWithJsonFormatError(NEOUserApiOrder userOrder) {
        List<NEOErrorResponse> errorResponseList = List.of(
                NEOErrorResponse.fromErrorCodeWithDetail(NEOErrorCode.INVALID_JSON_FORMAT_REQUEST,
                        "요청을 Map 형식으로 변환할 수 없습니다. 다시 확인해주세요."));

        // 최종 에러 응답 생성
        return NEOFinalErrorResponse.builder()
                .responseCode(NEOResponseCode.FAIL)
                .msg(userOrder.getFailMessage())
                .requestedMethodAndURI(userOrder.getRequestedMethodAndURI())
                .errors(errorResponseList).build();
    }
}

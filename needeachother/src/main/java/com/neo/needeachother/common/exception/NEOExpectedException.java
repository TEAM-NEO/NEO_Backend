package com.neo.needeachother.common.exception;

import com.neo.needeachother.common.enums.NEODomainType;
import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.response.NEOErrorResponse;
import lombok.Getter;

import java.util.*;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * NEO의 예측 가능한 예외를 정의합니다.<br>
 */
@Getter
public class NEOExpectedException extends RuntimeException {

    private final NEODomainType domainType;
    private final List<NEOErrorResponse> errorResponseList;

    /**
     * @param domainType 에러 발생 도메인 지점
     * @param errorCode NEO 에러코드
     * @param message 클라이언트 요청에 대한 전체 메시지
     */
    public NEOExpectedException(NEODomainType domainType, NEOErrorCode errorCode, String message){
        this(domainType, errorCode, null, message);
    }

    /**
     * @param domainType 에러 발생 도메인 지점
     * @param errorCode NEO 에러코드
     * @param errorDetailMessage 해당 에러에 대한 상세 메시지
     * @param message 클라이언트 요청에 대한 전체 메시지
     */
    public NEOExpectedException(NEODomainType domainType, NEOErrorCode errorCode, String errorDetailMessage, String message) {
        this(domainType,
                NEOErrorResponse.builder()
                        .neoErrorCode(errorCode)
                        .errorCode(errorCode.getErrorCode())
                        .errorDescription(errorCode.getErrorDescription())
                        .errorDetail(errorDetailMessage).build(),
                message);
    }

    /**
     * @param domainType 에러 발생 도메인 지정
     * @param errorResponse 최종 응답에 포함되는 에러 응답
     * @param message 클라이언트 요청에 대한 전체 메시지
     */
    public NEOExpectedException(NEODomainType domainType, NEOErrorResponse errorResponse, String message) {
        this(domainType, List.of(errorResponse), message);
    }

    /**
     * @param domainType 에러 발생 도메인 지정
     * @param errorResponseList 최종 응답에 포함되는 에러 응답 리스트
     * @param message 클라이언트 요청에 대한 전체 메시지
     */
    public NEOExpectedException(NEODomainType domainType, List<NEOErrorResponse> errorResponseList, String message) {
        super(message);
        if(errorResponseList == null || errorResponseList.size() == 0){
            throw new NEOUnexpectedException("NEOExpectedException을 생성하기 위한 List<NEOErrorResponse> 타입의 값이 null이거나 비어있음.");
        }
        this.domainType = domainType;
        this.errorResponseList = errorResponseList;
    }

}

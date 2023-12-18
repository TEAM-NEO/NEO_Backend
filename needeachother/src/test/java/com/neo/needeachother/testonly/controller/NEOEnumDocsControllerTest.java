package com.neo.needeachother.testonly.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.needeachother.auth.filter.NEOJwtAuthenticationFilter;
import com.neo.needeachother.common.config.NEOTestConfiguration;
import com.neo.needeachother.common.docsutil.NEOCustomResponseFieldsSnippet;
import com.neo.needeachother.testonly.dto.NEOEqualCodeEnumDocsDTO;
import com.neo.needeachother.testonly.dto.NEOTestApiResponseDTO;
import com.neo.needeachother.users.filter.NEOUserDomainBadRequestFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.neo.needeachother.common.config.NEOTestConfiguration.field;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Import(value = {NEOTestConfiguration.class})
@WebMvcTest(controllers = NEOEnumDocsController.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
class NEOEnumDocsControllerTest{

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    @BeforeEach
    public void initMockMvc(WebApplicationContext wac, RestDocumentationContextProvider restDocumentationContextProvider){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .addFilter(new NEOUserDomainBadRequestFilter())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    public void equalCodeEnums() throws Exception {
        // 요청
        ResultActions result = mockMvc.perform(
                get("/test/equal-code-enums")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // 결과값
        MvcResult mvcResult = result.andReturn();

        // 데이터 파싱
        NEOEqualCodeEnumDocsDTO enumDocs = getData(mvcResult);

        // 문서화 진행
        result.andExpect(status().isOk())
                .andDo(restDocs.document(
                        customResponseFields("custom-equalenum-response", beneathPath("data.genderCode").withSubsectionId("genderCode"),
                                attributes(field("title", "genderCode")),
                                enumConvertFieldDescriptor((enumDocs.getGenderCode()))
                        ),
                        customResponseFields("custom-equalenum-response", beneathPath("data.starClassificationCode").withSubsectionId("starClassificationCode"),
                                attributes(field("title", "starClassificationCode")),
                                enumConvertFieldDescriptor((enumDocs.getStarClassificationCode()))
                        )
                ));
    }

    // 커스텀 템플릿 사용을 위한 함수
    public static NEOCustomResponseFieldsSnippet customResponseFields
    (String type, PayloadSubsectionExtractor<?> subsectionExtractor,
     Map<String, Object> attributes, FieldDescriptor... descriptors) {
        return new NEOCustomResponseFieldsSnippet(type, subsectionExtractor, Arrays.asList(descriptors), attributes
                , true);
    }

    // Map으로 넘어온 enumValue를 fieldWithPath로 변경하여 리턴
    private static FieldDescriptor[] enumConvertFieldDescriptor(Map<String, List<String>> enumValues) {
        return enumValues.entrySet().stream()
                .map(x -> fieldWithPath(x.getKey()).description(x.getValue().get(1)).attributes(field("equalcode", x.getValue().get(0))))
                .toArray(FieldDescriptor[]::new);
    }

    // mvc result 데이터 파싱
    private NEOEqualCodeEnumDocsDTO getData(MvcResult result) throws IOException {
        NEOTestApiResponseDTO<NEOEqualCodeEnumDocsDTO> apiResponseDto = objectMapper
                .readValue(result.getResponse().getContentAsByteArray(),
                        new TypeReference<NEOTestApiResponseDTO<NEOEqualCodeEnumDocsDTO>>() {}
                );
        return apiResponseDto.getData();
    }
}

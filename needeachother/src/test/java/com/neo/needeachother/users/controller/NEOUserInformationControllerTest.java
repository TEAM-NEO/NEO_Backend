package com.neo.needeachother.users.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.needeachother.auth.dto.NEORefreshToken;
import com.neo.needeachother.auth.filter.NEOJwtAuthenticationFilter;
import com.neo.needeachother.auth.repository.NEORefreshTokenRepository;
import com.neo.needeachother.auth.service.NEOTokenService;
import com.neo.needeachother.common.config.NEOTestConfiguration;
import com.neo.needeachother.common.util.NEOServletResponseWriter;
import com.neo.needeachother.users.dto.NEOAdditionalFanInfoRequest;
import com.neo.needeachother.users.dto.NEOAdditionalStarInfoRequest;
import com.neo.needeachother.users.dto.NEOChangeableInfoDTO;
import com.neo.needeachother.users.dto.NEOUserInformationDTO;
import com.neo.needeachother.users.filter.NEOUserDomainBadRequestFilter;
import com.neo.needeachother.users.mother.NEOFanTestObjectMother;
import com.neo.needeachother.users.mother.NEOStarTestObjectMother;
import com.neo.needeachother.users.repository.NEOUserRepository;
import com.neo.needeachother.users.service.NEOUserInformationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.neo.needeachother.common.config.NEOTestConfiguration.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Import(value = {NEOTestConfiguration.class})
@WebMvcTest(controllers = NEOUserInformationController.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
class NEOUserInformationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NEOUserInformationService userInformationService;

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    @SpyBean
    private NEOTokenService tokenService;

    @MockBean
    private NEORefreshTokenRepository refreshTokenRepository;

    @MockBean
    private NEOUserRepository userRepository;

    @MockBean
    private NEOServletResponseWriter servletResponseWriter;

    @BeforeEach
    public void initMockMvc(WebApplicationContext wac, RestDocumentationContextProvider restDocumentationContextProvider){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .addFilter(new NEOUserDomainBadRequestFilter())
                .addFilter(new NEOJwtAuthenticationFilter(tokenService, userRepository, servletResponseWriter))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("🔧 POST /api/v1/users/stars : 신규 스타 회원 추가 정보 생성 테스트")
    void createNewStarInformationOrder() throws Exception {
        // given
        NEOAdditionalStarInfoRequest request = NEOStarTestObjectMother.STAR_CASE_1.getCreateRequestFixture();
        NEOUserInformationDTO finalResponse = NEOStarTestObjectMother.STAR_CASE_1.getCreateResponseFixture();

        String accessToken = tokenService.createAccessToken(NEOStarTestObjectMother.STAR_CASE_1.getUserEmail());

        given(userInformationService.doCreateNewStarInformationOrder(any(), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                RestDocumentationRequestBuilders.post("/api/v1/users/stars")
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Bearer " + accessToken)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/users/" + NEOStarTestObjectMother.STAR_CASE_1.getUserID()))
                .andExpect(content().string(objectMapper.writeValueAsString(finalResponse)))
                .andDo(print())
                /* write docsutil */
                .andDo(restDocs.document(
                                requestFields(
                                        fieldWithPath("user_id").type(JsonFieldType.STRING).description("사용자 아이디").attributes(field("constraints", "4글자 이상, 알파벳 대소문자, 숫자, 언더바 포함가능")),
                                        fieldWithPath("user_pw").type(JsonFieldType.STRING).description("사용자 비밀번호").attributes(field("constraints", "4글자 이상")),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일").attributes(field("constraints", "email의 형식 준수")),
                                        fieldWithPath("user_name").type(JsonFieldType.STRING).description("사용자 본명"),
                                        fieldWithPath("phone_number").type(JsonFieldType.STRING).description("핸드폰 번호").attributes(field("constraints", "'-'를 포함한 핸드폰 번호 (ex. 010-1111-1111)")),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("네오 닉네임"),
                                        fieldWithPath("star_nickname").type(JsonFieldType.STRING).description("스타 활동명"),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별").attributes(field("constraints", "gender 코드 참조")),
                                        fieldWithPath("star_classification_list").type(JsonFieldType.ARRAY).description("스타 유형").attributes(field("constraints", "starClassification 코드 참조")),
                                        fieldWithPath("submitted_url").type(JsonFieldType.ARRAY).description("대표 url").optional(),
                                        fieldWithPath("introduction").type(JsonFieldType.STRING).description("한 줄 소개글").optional(),
                                        fieldWithPath("custom_wiki_list").type(JsonFieldType.ARRAY).description("스타 자기소개 위키").optional(),
                                        fieldWithPath("custom_wiki_list[].custom_title").type(JsonFieldType.STRING).description("위키 제목").optional(),
                                        fieldWithPath("custom_wiki_list[].custom_context").type(JsonFieldType.STRING).description("위키 내용").optional()
                                ),
                                responseFields(
                                        fieldWithPath("user_type").type(JsonFieldType.STRING).description("사용자 유형"),
                                        fieldWithPath("has_wiki").type(JsonFieldType.BOOLEAN).description("위키 포함 여부"),
                                        fieldWithPath("is_private").type(JsonFieldType.BOOLEAN).description("개인정보 포함 여부"),
                                        fieldWithPath("user_id").type(JsonFieldType.STRING).description("사용자 아이디"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                                        fieldWithPath("user_name").type(JsonFieldType.STRING).description("사용자 본명"),
                                        fieldWithPath("phone_number").type(JsonFieldType.STRING).description("핸드폰 번호"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("네오 닉네임"),
                                        fieldWithPath("star_nickname").type(JsonFieldType.STRING).description("스타 활동명"),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("star_classification_list").type(JsonFieldType.ARRAY).description("스타 유형"),
                                        fieldWithPath("submitted_url").type(JsonFieldType.ARRAY).description("대표 url"),
                                        fieldWithPath("introduction").type(JsonFieldType.STRING).description("한 줄 소개글"),
                                        fieldWithPath("custom_wiki_list").type(JsonFieldType.ARRAY).description("스타 자기소개 위키"),
                                        fieldWithPath("custom_wiki_list[].custom_title").type(JsonFieldType.STRING).description("위키 제목"),
                                        fieldWithPath("custom_wiki_list[].custom_context").type(JsonFieldType.STRING).description("위키 내용")
                                )
                        )
                );
    }

    @Test
    @DisplayName("🔧 POST /api/v1/users/fans : 신규 팬 회원 추가 정보 생성 테스트")
    void createNewFanInformationOrder() throws Exception {
        // given
        NEOAdditionalFanInfoRequest request = NEOFanTestObjectMother.FAN_CASE_1.getCreateRequestFixture();
        NEOUserInformationDTO finalResponse = NEOFanTestObjectMother.FAN_CASE_1.getCreateResponseFixture();

        String accessToken = tokenService.createAccessToken(NEOFanTestObjectMother.FAN_CASE_1.getUserEmail());

        given(userInformationService.doCreateNewFanInformationOrder(any(), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                RestDocumentationRequestBuilders.post("/api/v1/users/fans")
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Bearer " + accessToken)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/users/" + NEOFanTestObjectMother.FAN_CASE_1.getUserID()))
                .andExpect(content().string(objectMapper.writeValueAsString(finalResponse)))
                .andDo(print())
                /* write docsutil */
                .andDo(restDocs.document(
                                requestFields(
                                        fieldWithPath("user_id").type(JsonFieldType.STRING).description("사용자 아이디").attributes(field("constraints", "4글자 이상, 알파벳 대소문자, 숫자, 언더바 포함가능")),
                                        fieldWithPath("user_pw").type(JsonFieldType.STRING).description("사용자 비밀번호").attributes(field("constraints", "4글자 이상")),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일").attributes(field("constraints", "email의 형식 준수")),
                                        fieldWithPath("user_name").type(JsonFieldType.STRING).description("사용자 본명"),
                                        fieldWithPath("phone_number").type(JsonFieldType.STRING).description("핸드폰 번호").attributes(field("constraints", "'-'를 포함한 핸드폰 번호 (ex. 010-1111-1111)")),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("네오 닉네임"),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별").attributes(field("constraints", "gender 코드 참조")),
                                        fieldWithPath("favorite_star_id").type(JsonFieldType.STRING).description("최애 스타 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("user_type").type(JsonFieldType.STRING).description("사용자 유형"),
                                        fieldWithPath("has_wiki").type(JsonFieldType.BOOLEAN).description("위키 포함 여부"),
                                        fieldWithPath("is_private").type(JsonFieldType.BOOLEAN).description("개인정보 포함 여부"),
                                        fieldWithPath("user_id").type(JsonFieldType.STRING).description("사용자 아이디"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                                        fieldWithPath("user_name").type(JsonFieldType.STRING).description("사용자 본명"),
                                        fieldWithPath("phone_number").type(JsonFieldType.STRING).description("핸드폰 번호"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("네오 닉네임"),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별")
                                )
                        )
                );
    }

    @Test
    @DisplayName("🔧 GET /api/v1/users/stars/{user_id} : 스타 정보 열람 테스트 (모두 열람)")
    void getStarInformationOrder() throws Exception {
        // given
        String userID = NEOStarTestObjectMother.STAR_CASE_1.getUserID();
        NEOUserInformationDTO finalResponse = NEOStarTestObjectMother.STAR_CASE_1.getUserInfoResponseFixture();

        String accessToken = tokenService.createAccessToken(NEOStarTestObjectMother.STAR_CASE_1.getUserEmail());
        given(userInformationService.doGetStarInformationOrder(eq(userID), eq(true), eq(true), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/users/stars/{user_id}", userID)
                        .header("Authorization", "Bearer " + accessToken)
                        .param("privacy", String.valueOf(true))
                        .param("detail", String.valueOf(true))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(finalResponse)))
                .andExpect(jsonPath("$.user_pw").doesNotExist())
                .andDo(print())
                /* write docsutil */
                .andDo(restDocs.document(
                                pathParameters(
                                        parameterWithName("user_id").description("스타 아이디")
                                ),
                                queryParameters(
                                        parameterWithName("privacy").description("개인정보 포함 여부"),
                                        parameterWithName("detail").description("상세내용(위키) 포함 여부")
                                ),
                                responseFields(
                                        fieldWithPath("user_type").type(JsonFieldType.STRING).description("사용자 유형"),
                                        fieldWithPath("has_wiki").type(JsonFieldType.BOOLEAN).description("위키 포함 여부"),
                                        fieldWithPath("is_private").type(JsonFieldType.BOOLEAN).description("개인정보 포함 여부"),
                                        fieldWithPath("user_id").type(JsonFieldType.STRING).description("사용자 아이디"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                                        fieldWithPath("user_name").type(JsonFieldType.STRING).description("사용자 본명"),
                                        fieldWithPath("phone_number").type(JsonFieldType.STRING).description("핸드폰 번호"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("네오 닉네임"),
                                        fieldWithPath("star_nickname").type(JsonFieldType.STRING).description("스타 활동명"),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("star_classification_list").type(JsonFieldType.ARRAY).description("스타 유형"),
                                        fieldWithPath("submitted_url").type(JsonFieldType.ARRAY).description("대표 url"),
                                        fieldWithPath("introduction").type(JsonFieldType.STRING).description("한 줄 소개글"),
                                        fieldWithPath("custom_wiki_list").type(JsonFieldType.ARRAY).description("스타 자기소개 위키"),
                                        fieldWithPath("custom_wiki_list[].custom_title").type(JsonFieldType.STRING).description("위키 제목"),
                                        fieldWithPath("custom_wiki_list[].custom_context").type(JsonFieldType.STRING).description("위키 내용")
                                )
                        )
                );
    }

    @Test
    @DisplayName("🔧 GET /api/v1/users/stars/{user_id} : 스타 정보 열람 테스트 (개인정보 제외)")
    void getStarInformationOrderWithoutPrivacy() throws Exception {
        // given
        String userID = NEOStarTestObjectMother.STAR_CASE_1.getUserID();
        NEOUserInformationDTO finalResponse = NEOStarTestObjectMother.STAR_CASE_1.getUserInfoResponseFixtureWithoutPrivacy();

        String accessToken = tokenService.createAccessToken(NEOStarTestObjectMother.STAR_CASE_1.getUserEmail());
        given(userInformationService.doGetStarInformationOrder(eq(userID), eq(false), eq(true), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/users/stars/{user_id}", userID)
                        .header("Authorization", "Bearer " + accessToken)
                        .param("privacy", String.valueOf(false))
                        .param("detail", String.valueOf(true))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(finalResponse)))
                .andExpectAll(
                        jsonPath("$.user_pw").doesNotExist(),
                        jsonPath("$.email").doesNotExist(),
                        jsonPath("$.user_name").doesNotExist(),
                        jsonPath("$.phone_number").doesNotExist()
                )
                .andDo(print())
                /* write docsutil */
                .andDo(restDocs.document(
                                pathParameters(
                                        parameterWithName("user_id").description("스타 아이디")
                                ),
                                queryParameters(
                                        parameterWithName("privacy").description("개인정보 포함 여부"),
                                        parameterWithName("detail").description("상세내용(위키) 포함 여부")
                                ),
                                responseFields(
                                        fieldWithPath("user_type").type(JsonFieldType.STRING).description("사용자 유형"),
                                        fieldWithPath("has_wiki").type(JsonFieldType.BOOLEAN).description("위키 포함 여부"),
                                        fieldWithPath("is_private").type(JsonFieldType.BOOLEAN).description("개인정보 포함 여부"),
                                        fieldWithPath("user_id").type(JsonFieldType.STRING).description("사용자 아이디"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("네오 닉네임"),
                                        fieldWithPath("star_nickname").type(JsonFieldType.STRING).description("스타 활동명"),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("star_classification_list").type(JsonFieldType.ARRAY).description("스타 유형"),
                                        fieldWithPath("submitted_url").type(JsonFieldType.ARRAY).description("대표 url"),
                                        fieldWithPath("introduction").type(JsonFieldType.STRING).description("한 줄 소개글"),
                                        fieldWithPath("custom_wiki_list").type(JsonFieldType.ARRAY).description("스타 자기소개 위키"),
                                        fieldWithPath("custom_wiki_list[].custom_title").type(JsonFieldType.STRING).description("위키 제목"),
                                        fieldWithPath("custom_wiki_list[].custom_context").type(JsonFieldType.STRING).description("위키 내용")
                                )
                        )
                );
    }

    @Test
    @DisplayName("🔧 GET /api/v1/users/stars/{user_id} : 스타 정보 열람 테스트 (위키정보 제외)")
    void getStarInformationOrderWithoutWiki() throws Exception {
        // given
        String userID = NEOStarTestObjectMother.STAR_CASE_1.getUserID();
        NEOUserInformationDTO finalResponse = NEOStarTestObjectMother.STAR_CASE_1.getUserInfoResponseFixtureWithoutWiki();

        String accessToken = tokenService.createAccessToken(NEOStarTestObjectMother.STAR_CASE_1.getUserEmail());

        given(userInformationService.doGetStarInformationOrder(eq(userID), eq(true), eq(false), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/users/stars/{user_id}", userID)
                        .header("Authorization", "Bearer " + accessToken)
                        .param("privacy", String.valueOf(true))
                        .param("detail", String.valueOf(false))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(finalResponse)))
                .andExpectAll(
                        jsonPath("$.user_pw").doesNotExist(),
                        jsonPath("$.custom_wiki_list").doesNotExist())
                .andDo(print())
                /* write docsutil */
                .andDo(restDocs.document(
                                pathParameters(
                                        parameterWithName("user_id").description("스타 아이디")
                                ),
                                queryParameters(
                                        parameterWithName("privacy").description("개인정보 포함 여부"),
                                        parameterWithName("detail").description("상세내용(위키) 포함 여부")
                                ),
                                responseFields(
                                        fieldWithPath("user_type").type(JsonFieldType.STRING).description("사용자 유형"),
                                        fieldWithPath("has_wiki").type(JsonFieldType.BOOLEAN).description("위키 포함 여부"),
                                        fieldWithPath("is_private").type(JsonFieldType.BOOLEAN).description("개인정보 포함 여부"),
                                        fieldWithPath("user_id").type(JsonFieldType.STRING).description("사용자 아이디"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                                        fieldWithPath("user_name").type(JsonFieldType.STRING).description("사용자 본명"),
                                        fieldWithPath("phone_number").type(JsonFieldType.STRING).description("핸드폰 번호"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("네오 닉네임"),
                                        fieldWithPath("star_nickname").type(JsonFieldType.STRING).description("스타 활동명"),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("star_classification_list").type(JsonFieldType.ARRAY).description("스타 유형"),
                                        fieldWithPath("submitted_url").type(JsonFieldType.ARRAY).description("대표 url"),
                                        fieldWithPath("introduction").type(JsonFieldType.STRING).description("한 줄 소개글")
                                )
                        )
                );

    }

    @Test
    @DisplayName("🔧 GET /api/v1/users/stars/{user_id} : 스타 정보 열람 테스트 (개인정보, 위키정보 제외)")
    void getStarInformationOrderWithoutPrivacyAndWiki() throws Exception {
        // given
        String userID = NEOStarTestObjectMother.STAR_CASE_1.getUserID();
        NEOUserInformationDTO finalResponse = NEOStarTestObjectMother.STAR_CASE_1.getUserInfoResponseFixtureWithoutPrivacyAndWiki();

        String accessToken = tokenService.createAccessToken(NEOStarTestObjectMother.STAR_CASE_1.getUserEmail());

        given(userInformationService.doGetStarInformationOrder(eq(userID), eq(false), eq(false), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/users/stars/{user_id}", userID)
                        .header("Authorization", "Bearer " + accessToken)
                        .param("privacy", String.valueOf(false))
                        .param("detail", String.valueOf(false))
                        .accept(MediaType.APPLICATION_JSON)
        );

        ResultActions withoutParamResult = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/users/stars/{user_id}", userID)
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        MvcResult finalResultWithoutRequestParam = withoutParamResult.andReturn();
        MvcResult finalResult = result.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(finalResponse)))
                .andExpectAll(
                        jsonPath("$.user_pw").doesNotExist(),
                        jsonPath("$.email").doesNotExist(),
                        jsonPath("$.user_name").doesNotExist(),
                        jsonPath("$.phone_number").doesNotExist(),
                        jsonPath("$.custom_wiki_list").doesNotExist()
                )
                .andDo(print())
                /* write docsutil */
                .andDo(restDocs.document(
                                pathParameters(
                                        parameterWithName("user_id").description("스타 아이디")
                                ),
                                queryParameters(
                                        parameterWithName("privacy").description("개인정보 포함 여부"),
                                        parameterWithName("detail").description("상세내용(위키) 포함 여부")
                                ),
                                responseFields(
                                        fieldWithPath("user_type").type(JsonFieldType.STRING).description("사용자 유형"),
                                        fieldWithPath("has_wiki").type(JsonFieldType.BOOLEAN).description("위키 포함 여부"),
                                        fieldWithPath("is_private").type(JsonFieldType.BOOLEAN).description("개인정보 포함 여부"),
                                        fieldWithPath("user_id").type(JsonFieldType.STRING).description("사용자 아이디"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("네오 닉네임"),
                                        fieldWithPath("star_nickname").type(JsonFieldType.STRING).description("스타 활동명"),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("star_classification_list").type(JsonFieldType.ARRAY).description("스타 유형"),
                                        fieldWithPath("submitted_url").type(JsonFieldType.ARRAY).description("대표 url"),
                                        fieldWithPath("introduction").type(JsonFieldType.STRING).description("한 줄 소개글")
                                )
                        )
                ).andReturn();

        Assertions.assertEquals(finalResult.getResponse().getContentAsString(), finalResultWithoutRequestParam.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("🔧 GET /api/v1/users/fans/{user_id} : 팬 정보 열람 테스트 (모두 열람)")
    void getFanInformationOrder() throws Exception {
        // given
        String userID = NEOFanTestObjectMother.FAN_CASE_1.getUserID();
        NEOUserInformationDTO finalResponse = NEOFanTestObjectMother.FAN_CASE_1.getUserInfoResponseFixture();

        String accessToken = tokenService.createAccessToken(NEOFanTestObjectMother.FAN_CASE_1.getUserEmail());

        given(userInformationService.doGetFanInformationOrder(eq(userID), eq(true), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/users/fans/{user_id}", userID)
                        .header("Authorization", "Bearer " + accessToken)
                        .param("privacy", String.valueOf(true))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(finalResponse)))
                .andExpectAll(
                        jsonPath("$.user_pw").doesNotExist(),
                        jsonPath("$.custom_wiki_list").doesNotExist(),
                        jsonPath("$.star_nickname").doesNotExist(),
                        jsonPath("$.star_classification_list").doesNotExist(),
                        jsonPath("$.submitted_url").doesNotExist(),
                        jsonPath("$.introduction").doesNotExist()
                )
                .andDo(print())
                /* write docsutil */
                .andDo(restDocs.document(
                                pathParameters(
                                        parameterWithName("user_id").description("스타 아이디")
                                ),
                                queryParameters(
                                        parameterWithName("privacy").description("개인정보 포함 여부")
                                ),
                                responseFields(
                                        fieldWithPath("user_type").type(JsonFieldType.STRING).description("사용자 유형"),
                                        fieldWithPath("has_wiki").type(JsonFieldType.BOOLEAN).description("위키 포함 여부"),
                                        fieldWithPath("is_private").type(JsonFieldType.BOOLEAN).description("개인정보 포함 여부"),
                                        fieldWithPath("user_id").type(JsonFieldType.STRING).description("사용자 아이디"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                                        fieldWithPath("user_name").type(JsonFieldType.STRING).description("사용자 본명"),
                                        fieldWithPath("phone_number").type(JsonFieldType.STRING).description("핸드폰 번호"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("네오 닉네임"),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별")
                                )
                        )
                );
    }

    @Test
    @DisplayName("🔧 GET /api/v1/users/fans/{user_id} : 팬 정보 열람 테스트 (개인정보 제외)")
    void getFanInformationOrderWithoutPrivacy() throws Exception {
        // given
        String userID = NEOFanTestObjectMother.FAN_CASE_1.getUserID();
        NEOUserInformationDTO finalResponse = NEOFanTestObjectMother.FAN_CASE_1.getUserInfoResponseFixtureWithoutPrivacy();

        String accessToken = tokenService.createAccessToken(NEOFanTestObjectMother.FAN_CASE_1.getUserEmail());

        given(userInformationService.doGetFanInformationOrder(eq(userID), eq(false), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/users/fans/{user_id}", userID)
                        .header("Authorization", "Bearer " + accessToken)
                        .param("privacy", String.valueOf(false))
                        .accept(MediaType.APPLICATION_JSON)
        );

        ResultActions withoutParamResult = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/users/fans/{user_id}", userID)
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        MvcResult finalResultWithoutRequestParam = withoutParamResult.andReturn();
        MvcResult finalResult = result.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(finalResponse)))
                .andExpectAll(
                        jsonPath("$.user_pw").doesNotExist(),
                        jsonPath("$.custom_wiki_list").doesNotExist(),
                        jsonPath("$.star_nickname").doesNotExist(),
                        jsonPath("$.star_classification_list").doesNotExist(),
                        jsonPath("$.submitted_url").doesNotExist(),
                        jsonPath("$.introduction").doesNotExist(),
                        jsonPath("$.email").doesNotExist(),
                        jsonPath("$.user_name").doesNotExist(),
                        jsonPath("$.phone_number").doesNotExist()
                )
                .andDo(print())
                /* write docsutil */
                .andDo(restDocs.document(
                                pathParameters(
                                        parameterWithName("user_id").description("스타 아이디")
                                ),
                                queryParameters(
                                        parameterWithName("privacy").description("개인정보 포함 여부")
                                ),
                                responseFields(
                                        fieldWithPath("user_type").type(JsonFieldType.STRING).description("사용자 유형"),
                                        fieldWithPath("has_wiki").type(JsonFieldType.BOOLEAN).description("위키 포함 여부"),
                                        fieldWithPath("is_private").type(JsonFieldType.BOOLEAN).description("개인정보 포함 여부"),
                                        fieldWithPath("user_id").type(JsonFieldType.STRING).description("사용자 아이디"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("네오 닉네임"),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별")
                                )
                        )
                ).andReturn();

        Assertions.assertEquals(finalResult.getResponse().getContentAsString(), finalResultWithoutRequestParam.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("🔧 PATCH /api/v1/users/stars/{user_id} : 스타 정보 변경 테스트")
    void changePartialStarInformationOrder() throws Exception {
        // given
        String userID = NEOStarTestObjectMother.STAR_CASE_1.getUserID();
        NEOChangeableInfoDTO request = new NEOChangeableInfoDTO("박보영뽀블리", null, null, null, null, null);
        NEOUserInformationDTO finalResponse = NEOStarTestObjectMother.STAR_CASE_1.getChangeInfoResponseFixture(request);

        String accessToken = tokenService.createAccessToken(NEOStarTestObjectMother.STAR_CASE_1.getUserEmail());

        given(userInformationService.doChangePartialInformationOrder(eq(userID), any(), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                RestDocumentationRequestBuilders.patch("/api/v1/users/stars/{user_id}", userID)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(finalResponse)))
                .andExpectAll(
                        jsonPath("$.user_pw").doesNotExist(),
                        jsonPath("$.star_nickname").value("박보영뽀블리")
                )
                .andDo(print())
                /* write docsutil */
                .andDo(restDocs.document(
                                pathParameters(
                                        parameterWithName("user_id").description("스타 아이디")
                                ),
                                requestFields(
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("네오 닉네임").optional(),
                                        fieldWithPath("star_nickname").type(JsonFieldType.STRING).description("스타 활동명").optional(),
                                        fieldWithPath("star_classification_list").type(JsonFieldType.ARRAY).description("스타 유형").attributes(field("constraints", "starClassification 코드 참조")).optional(),
                                        fieldWithPath("submitted_url").type(JsonFieldType.ARRAY).description("대표 url").optional(),
                                        fieldWithPath("introduction").type(JsonFieldType.STRING).description("한 줄 소개글").optional(),
                                        fieldWithPath("custom_wiki_list").type(JsonFieldType.ARRAY).description("스타 자기소개 위키").optional(),
                                        fieldWithPath("custom_wiki_list[].custom_title").type(JsonFieldType.STRING).description("위키 제목").optional(),
                                        fieldWithPath("custom_wiki_list[].custom_context").type(JsonFieldType.STRING).description("위키 내용").optional()
                                ),
                                responseFields(
                                        fieldWithPath("user_type").type(JsonFieldType.STRING).description("사용자 유형"),
                                        fieldWithPath("has_wiki").type(JsonFieldType.BOOLEAN).description("위키 포함 여부"),
                                        fieldWithPath("is_private").type(JsonFieldType.BOOLEAN).description("개인정보 포함 여부"),
                                        fieldWithPath("user_id").type(JsonFieldType.STRING).description("사용자 아이디"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                                        fieldWithPath("user_name").type(JsonFieldType.STRING).description("사용자 본명"),
                                        fieldWithPath("phone_number").type(JsonFieldType.STRING).description("핸드폰 번호"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("네오 닉네임"),
                                        fieldWithPath("star_nickname").type(JsonFieldType.STRING).description("스타 활동명"),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("star_classification_list").type(JsonFieldType.ARRAY).description("스타 유형"),
                                        fieldWithPath("submitted_url").type(JsonFieldType.ARRAY).description("대표 url"),
                                        fieldWithPath("introduction").type(JsonFieldType.STRING).description("한 줄 소개글"),
                                        fieldWithPath("custom_wiki_list").type(JsonFieldType.ARRAY).description("스타 자기소개 위키"),
                                        fieldWithPath("custom_wiki_list[].custom_title").type(JsonFieldType.STRING).description("위키 제목"),
                                        fieldWithPath("custom_wiki_list[].custom_context").type(JsonFieldType.STRING).description("위키 내용")
                                )
                        )
                );
    }

    @Test
    @DisplayName("🔧 PATCH /api/v1/users/fans/{user_id} : 팬 정보 변경 테스트")
    void changePartialFanInformationOrder() throws Exception {
        // given
        String userID = NEOFanTestObjectMother.FAN_CASE_1.getUserID();
        Map<String, String> request = new HashMap<>();
        request.put("nickname", "박보영최고야");

        NEOChangeableInfoDTO requestDeserializedDto = objectMapper.convertValue(request, NEOChangeableInfoDTO.class);
        NEOUserInformationDTO finalResponse = NEOFanTestObjectMother.FAN_CASE_1.getChangeInfoResponseFixture(requestDeserializedDto);

        String accessToken = tokenService.createAccessToken(NEOStarTestObjectMother.STAR_CASE_1.getUserEmail());

        given(userInformationService.doChangePartialInformationOrder(eq(userID), any(), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                RestDocumentationRequestBuilders.patch("/api/v1/users/fans/{user_id}", userID)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(finalResponse)))
                .andExpectAll(
                        jsonPath("$.user_pw").doesNotExist(),
                        jsonPath("$.custom_wiki_list").doesNotExist(),
                        jsonPath("$.star_nickname").doesNotExist(),
                        jsonPath("$.star_classification_list").doesNotExist(),
                        jsonPath("$.submitted_url").doesNotExist(),
                        jsonPath("$.introduction").doesNotExist(),
                        jsonPath("$.nickname").value("박보영최고야")
                )
                .andDo(print())
                /* write docsutil */
                .andDo(restDocs.document(
                                pathParameters(
                                        parameterWithName("user_id").description("스타 아이디")
                                ),
                                requestFields(
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("네오 닉네임").optional()
                                ),
                                responseFields(
                                        fieldWithPath("user_type").type(JsonFieldType.STRING).description("사용자 유형"),
                                        fieldWithPath("has_wiki").type(JsonFieldType.BOOLEAN).description("위키 포함 여부"),
                                        fieldWithPath("is_private").type(JsonFieldType.BOOLEAN).description("개인정보 포함 여부"),
                                        fieldWithPath("user_id").type(JsonFieldType.STRING).description("사용자 아이디"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                                        fieldWithPath("user_name").type(JsonFieldType.STRING).description("사용자 본명"),
                                        fieldWithPath("phone_number").type(JsonFieldType.STRING).description("핸드폰 번호"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("네오 닉네임"),
                                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별")
                                )
                        )
                );

    }

    @Test
    @DisplayName("🔧 DELETE /api/v1/users/stars/{user_id} : 스타 정보 삭제 테스트")
    void deleteStarInformationOrder() throws Exception {
        // given
        String userID = NEOStarTestObjectMother.STAR_CASE_1.getUserID();

        String accessToken = tokenService.createAccessToken(NEOStarTestObjectMother.STAR_CASE_1.getUserEmail());

        // when
        ResultActions result = mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/api/v1/users/stars/{user_id}", userID)
                        .header("Authorization", "Bearer " + accessToken)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                /* write docsutil */
                .andDo(restDocs.document(
                                pathParameters(
                                        parameterWithName("user_id").description("스타 아이디")
                                )
                        )
                );
    }

    @Test
    @DisplayName("🔧 DELETE /api/v1/users/fans/{user_id} : 팬 정보 삭제 테스트")
    void deleteFanInformationOrder() throws Exception {
        // given
        String userID = NEOFanTestObjectMother.FAN_CASE_1.getUserID();

        String accessToken = tokenService.createAccessToken(NEOFanTestObjectMother.FAN_CASE_1.getUserEmail());

        // when
        ResultActions result = mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/api/v1/users/fans/{user_id}", userID)
                        .header("Authorization", "Bearer " + accessToken)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                /* write docsutil */
                .andDo(restDocs.document(
                                pathParameters(
                                        parameterWithName("user_id").description("스타 아이디")
                                )
                        )
                );
    }
}
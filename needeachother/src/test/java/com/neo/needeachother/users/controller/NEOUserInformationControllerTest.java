package com.neo.needeachother.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.needeachother.common.config.NEOTestConfiguration;
import com.neo.needeachother.users.dto.NEOAdditionalFanInfoRequest;
import com.neo.needeachother.users.dto.NEOAdditionalStarInfoRequest;
import com.neo.needeachother.users.dto.NEOChangeableInfoDTO;
import com.neo.needeachother.users.dto.NEOUserInformationDTO;
import com.neo.needeachother.users.mother.NEOFanTestObjectMother;
import com.neo.needeachother.users.mother.NEOStarTestObjectMother;
import com.neo.needeachother.users.service.NEOUserInformationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.junit.jupiter.api.Assertions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Import(value = {NEOTestConfiguration.class})
@WebMvcTest(controllers = NEOUserInformationController.class)
class NEOUserInformationControllerTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NEOUserInformationService userInformationService;

    @Test
    @DisplayName("🔧 POST /api/v1/users/stars : 신규 스타 회원 추가 정보 생성 테스트")
    void createNewStarInformationOrder() throws Exception {
        // given
        NEOAdditionalStarInfoRequest request = NEOStarTestObjectMother.STAR_CASE_1.getCreateRequestFixture();
        NEOUserInformationDTO finalResponse = NEOStarTestObjectMother.STAR_CASE_1.getCreateResponseFixture();

        given(userInformationService.doCreateNewStarInformationOrder(any(), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                post("/api/v1/users/stars")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/users/" + NEOStarTestObjectMother.STAR_CASE_1.getUserID()))
                .andExpect(content().string(objectMapper.writeValueAsString(finalResponse)))
                .andDo(print());
    }

    @Test
    @DisplayName("🔧 POST /api/v1/users/fans : 신규 팬 회원 추가 정보 생성 테스트")
    void createNewFanInformationOrder() throws Exception {
        // given
        NEOAdditionalFanInfoRequest request = NEOFanTestObjectMother.FAN_CASE_1.getCreateRequestFixture();
        NEOUserInformationDTO finalResponse = NEOFanTestObjectMother.FAN_CASE_1.getCreateResponseFixture();

        given(userInformationService.doCreateNewFanInformationOrder(any(), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                post("/api/v1/users/fans")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/users/" + NEOFanTestObjectMother.FAN_CASE_1.getUserID()))
                .andExpect(content().string(objectMapper.writeValueAsString(finalResponse)))
                .andDo(print());
    }

    @Test
    @DisplayName("🔧 GET /api/v1/users/stars/{user_id} : 스타 정보 열람 테스트 (모두 열람)")
    void getStarInformationOrder() throws Exception {
        // given
        String userID = NEOStarTestObjectMother.STAR_CASE_1.getUserID();
        NEOUserInformationDTO finalResponse = NEOStarTestObjectMother.STAR_CASE_1.getUserInfoResponseFixture();

        given(userInformationService.doGetStarInformationOrder(eq(userID), eq(true), eq(true), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/users/stars/" + userID)
                        .param("privacy", String.valueOf(true))
                        .param("detail", String.valueOf(true))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(finalResponse)))
                .andExpect(jsonPath("$.user_pw").doesNotExist())
                .andDo(print());
    }

    @Test
    @DisplayName("🔧 GET /api/v1/users/stars/{user_id} : 스타 정보 열람 테스트 (개인정보 제외)")
    void getStarInformationOrderWithoutPrivacy() throws Exception {
        // given
        String userID = NEOStarTestObjectMother.STAR_CASE_1.getUserID();
        NEOUserInformationDTO finalResponse = NEOStarTestObjectMother.STAR_CASE_1.getUserInfoResponseFixtureWithoutPrivacy();

        given(userInformationService.doGetStarInformationOrder(eq(userID), eq(false), eq(true), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/users/stars/" + userID)
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
                .andDo(print());
    }

    @Test
    @DisplayName("🔧 GET /api/v1/users/stars/{user_id} : 스타 정보 열람 테스트 (위키정보 제외)")
    void getStarInformationOrderWithoutWiki() throws Exception {
        // given
        String userID = NEOStarTestObjectMother.STAR_CASE_1.getUserID();
        NEOUserInformationDTO finalResponse = NEOStarTestObjectMother.STAR_CASE_1.getUserInfoResponseFixtureWithoutWiki();

        given(userInformationService.doGetStarInformationOrder(eq(userID), eq(true), eq(false), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/users/stars/" + userID)
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
                .andDo(print());

    }

    @Test
    @DisplayName("🔧 GET /api/v1/users/stars/{user_id} : 스타 정보 열람 테스트 (개인정보, 위키정보 제외)")
    void getStarInformationOrderWithoutPrivacyAndWiki() throws Exception {
        // given
        String userID = NEOStarTestObjectMother.STAR_CASE_1.getUserID();
        NEOUserInformationDTO finalResponse = NEOStarTestObjectMother.STAR_CASE_1.getUserInfoResponseFixtureWithoutPrivacyAndWiki();

        given(userInformationService.doGetStarInformationOrder(eq(userID), eq(false), eq(false), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/users/stars/" + userID)
                        .param("privacy", String.valueOf(false))
                        .param("detail", String.valueOf(false))
                        .accept(MediaType.APPLICATION_JSON)
        );

        ResultActions withoutParamResult = mockMvc.perform(
                get("/api/v1/users/stars/" + userID)
                        .param("privacy", String.valueOf(false))
                        .param("detail", String.valueOf(false))
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
                .andDo(print()).andReturn();

        Assertions.assertEquals(finalResult.getResponse().getContentAsString(), finalResultWithoutRequestParam.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("🔧 GET /api/v1/users/fans/{user_id} : 팬 정보 열람 테스트 (모두 열람)")
    void getFanInformationOrder() throws Exception {
        // given
        String userID = NEOFanTestObjectMother.FAN_CASE_1.getUserID();
        NEOUserInformationDTO finalResponse = NEOFanTestObjectMother.FAN_CASE_1.getUserInfoResponseFixture();

        given(userInformationService.doGetFanInformationOrder(eq(userID), eq(true), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/users/fans/" + userID)
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
                .andDo(print());
    }

    @Test
    @DisplayName("🔧 GET /api/v1/users/fans/{user_id} : 팬 정보 열람 테스트 (개인정보 제외)")
    void getFanInformationOrderWithoutPrivacy() throws Exception {
        // given
        String userID = NEOFanTestObjectMother.FAN_CASE_1.getUserID();
        NEOUserInformationDTO finalResponse = NEOFanTestObjectMother.FAN_CASE_1.getUserInfoResponseFixtureWithoutPrivacy();

        given(userInformationService.doGetFanInformationOrder(eq(userID), eq(false), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/users/fans/" + userID)
                        .param("privacy", String.valueOf(false))
                        .accept(MediaType.APPLICATION_JSON)
        );

        ResultActions withoutParamResult = mockMvc.perform(
                get("/api/v1/users/fans/" + userID)
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
                .andDo(print()).andReturn();

        Assertions.assertEquals(finalResult.getResponse().getContentAsString(), finalResultWithoutRequestParam.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("🔧 PATCH /api/v1/users/stars/{user_id} : 스타 정보 변경 테스트")
    void changePartialStarInformationOrder() throws Exception {
        // given
        String userID = NEOStarTestObjectMother.STAR_CASE_1.getUserID();
        NEOChangeableInfoDTO request = new NEOChangeableInfoDTO("박보영뽀블리", null, null, null, null, null);
        NEOUserInformationDTO finalResponse = NEOStarTestObjectMother.STAR_CASE_1.getChangeInfoResponseFixture(request);

        given(userInformationService.doChangePartialInformationOrder(eq(userID), any(), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                patch("/api/v1/users/stars/" + userID)
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
                .andDo(print());
    }

    @Test
    @DisplayName("🔧 PATCH /api/v1/users/fans/{user_id} : 팬 정보 변경 테스트")
    void changePartialFanInformationOrder() throws Exception {
        // given
        String userID = NEOFanTestObjectMother.FAN_CASE_1.getUserID();
        NEOChangeableInfoDTO request = new NEOChangeableInfoDTO(null, "박보영최고야", null, null, null, null);
        NEOUserInformationDTO finalResponse = NEOFanTestObjectMother.FAN_CASE_1.getChangeInfoResponseFixture(request);

        given(userInformationService.doChangePartialInformationOrder(eq(userID), any(), any()))
                .willReturn(finalResponse);

        // when
        ResultActions result = mockMvc.perform(
                patch("/api/v1/users/fans/" + userID)
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
                .andDo(print());

    }

    @Test
    @DisplayName("🔧 DELETE /api/v1/users/stars : 스타 정보 삭제 테스트")
    void deleteStarInformationOrder() throws Exception{
        // given
        String userID = NEOStarTestObjectMother.STAR_CASE_1.getUserID();

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/v1/users/stars/" + userID)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("🔧 DELETE /api/v1/users/fans : 팬 정보 삭제 테스트")
    void deleteFanInformationOrder() throws Exception {
        // given
        String userID = NEOFanTestObjectMother.FAN_CASE_1.getUserID();

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/v1/users/fans/" + userID)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print());
    }
}
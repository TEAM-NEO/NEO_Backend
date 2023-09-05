package com.neo.needeachother.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.needeachother.config.NEOJacksonConfig;
import com.neo.needeachother.users.dto.NEOAdditionalStarInfoRequest;
import com.neo.needeachother.users.dto.NEOStarWikiInformationDTO;
import com.neo.needeachother.users.dto.NEOUserInformationDTO;
import com.neo.needeachother.users.enums.NEOGenderType;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import com.neo.needeachother.users.enums.NEOUserType;
import com.neo.needeachother.users.interceptor.NEOUserDomainBadRequestInterceptor;
import com.neo.needeachother.users.service.NEOUserInformationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(controllers = NEOUserInformationController.class)
@ContextConfiguration(classes = NEOJacksonConfig.class)
class NEOUserInformationControllerTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NEOUserInformationService userInformationService;

    @MockBean
    private BindingResult bindingResult;

    @MockBean
    private NEOUserDomainBadRequestInterceptor userDomainBadRequestInterceptor;

    @Test
    @DisplayName("POST /api/v1/users/stars : 신규 스타 회원 추가 정보 생성 테스트")
    void createNewStarInformationOrder() throws Exception{
        // given
        NEOAdditionalStarInfoRequest request = NEOAdditionalStarInfoRequest.builder()
                .userID("free_minkya")
                .userPW("1234")
                .userName("이승훈")
                .email("free_minkya@naver.com")
                .phoneNumber("010-4397-3598")
                .neoNickName("네오네오")
                .starNickName("네오스타")
                .gender(NEOGenderType.MALE)
                .starClassificationSet(new HashSet<>(List.of(NEOStarDetailClassification.ACTOR, NEOStarDetailClassification.COMEDIAN)))
                .submittedUrl(List.of("www.youtube.com/@free_minkya"))
                .introduction("안녕하세요")
                .customWikiList(List.of(NEOStarWikiInformationDTO.builder().customTitle("MBTI").customContext("ISTJ").build()))
                .build();

        NEOUserInformationDTO finalResponse = NEOUserInformationDTO.builder()
                .userType(NEOUserType.STAR)
                .hasWiki(true)
                .isPrivate(true)
                .userID("free_minkya")
                .userPW("1234")
                .userName("이승훈")
                .email("free_minkya@naver.com")
                .phoneNumber("010-4397-3598")
                .neoNickName("네오네오")
                .starNickName("네오스타")
                .gender(NEOGenderType.MALE)
                .starClassificationSet(new HashSet<>(List.of(NEOStarDetailClassification.ACTOR, NEOStarDetailClassification.COMEDIAN)))
                .submittedUrl(List.of("www.youtube.com/@free_minkya"))
                .introduction("안녕하세요")
                .customWikiList(List.of(NEOStarWikiInformationDTO.builder().customTitle("MBTI").customContext("ISTJ").build()))
                .build();

        given(userDomainBadRequestInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any(Object.class)))
                .willReturn(true);
        given(bindingResult.hasErrors()).willReturn(false);
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
                .andExpect(header().string("Location", "/api/v1/users/free_minkya"))
                .andExpect(content().string(objectMapper.writeValueAsString(finalResponse)))
                .andDo(print());
    }

    @Test
    @DisplayName("POST /api/v1/users/fans : 신규 팬 회원 추가 정보 생성 테스트")
    void createNewFanInformationOrder() {
    }

    @Test
    @DisplayName("GET /api/v1/users/stars/{user_id} : 스타 정보 열람 테스트")
    void getStarInformationOrder() {
    }

    @Test
    @DisplayName("GET /api/v1/users/fans/{user_id} : 팬 정보 열람 테스트")
    void getFanInformationOrder() {
    }

    @Test
    @DisplayName("PATCH /api/v1/users/stars, /api/v1/users/fans : 스타, 팬 정보 변경 테스트")
    void changePartialUserInformationOrder() {
    }

    @Test
    @DisplayName("DELETE /api/v1/users/stars, /api/v1/users/fans : 스타, 팬 정보 삭제 테스트")
    void deleteUserInformationOrder() {
    }
}
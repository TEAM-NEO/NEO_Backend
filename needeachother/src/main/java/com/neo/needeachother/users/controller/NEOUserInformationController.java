package com.neo.needeachother.users.controller;

import com.neo.needeachother.common.response.NEOErrorResponse;
import com.neo.needeachother.common.response.NEOResponseBody;
import com.neo.needeachother.users.docs.*;
import com.neo.needeachother.users.dto.*;
import com.neo.needeachother.users.exception.NEOUserExpectedException;
import com.neo.needeachother.users.service.NEOUserInformationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * 유저 정보와 관련된 엔드포인트 API입니다.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User : Information", description = "NEO의 회원 관련 API입니다. 그 중 회원 정보와 관련된 API 리스트입니다.")
public class NEOUserInformationController {

    private final NEOUserInformationService userInformationService;

    /**
     * 유저의 정보를 가져옵니다.<br>
     * {@code isPublic}의 값에 따라 공개 정보 / 전체 정보를 얻어올 수 있습니다. <br>
     * 팬 혹은 스타 관계 없이 해당 API를 사용해 유저 정보를 얻을 수 있습니다.<br>
     *
     * @param userID   유저의 아이디
     * @param isPublic 공개 정보 / 전체 정보 여부
     * @return {@code ResponseEntity<NEOFanInfoDto>}, {@code ResponseEntity<NEOStarInfoDto>},
     * {@code ResponseEntity<NEOPublicFanInfoDto>}, {@code ResponseEntity<NEOPublicStarInfoDto>}
     */
    @GetMapping("/{user_id}")
    @NEOGetUserInfoOrderDocs
    public ResponseEntity<?> getUserInformationOrder(
            @PathVariable("user_id") String userID,
            @RequestParam(value = "public", required = false, defaultValue = "false") boolean isPublic) {
        if (isPublic) {
            return userInformationService.doGetPublicUserInformationOrder(userID, NEOUserOrder.GET_USER_PUBLIC_INFO);
        }
        return userInformationService.doGetUserInformationOrder(userID, NEOUserOrder.GET_USER_INFO);
    }

    /**
     * 기존 유저 정보를 변경합니다.<br>
     * 스타 및 팬 구별없이 사용 가능하며 내부 로직에 의해 판단합니다. 요청의 일부 로직은 스타에게만 해당되며, 팬이 요청에 해당 필드를 포함하더라도 적용되지 않습니다.<br>
     *
     * @param userID                       사용자 아이디
     * @param changePartialStarInfoRequest 정보 변경에 필요한 요청
     * @return {@code ResponseEntity<NEOResponseBody<NEOStarInfoDto>>}, {@code ResponseEntity<NEOResponseBody<NEOFanInfoDto>>}
     */
    @PatchMapping("/{user_id}")
    @NEOChangeUserInfoOrderDocs
    public ResponseEntity<?> changePartialUserInformationOrder(@PathVariable("user_id") final String userID,
                                                               @RequestBody final NEOChangeableInfoDto changePartialStarInfoRequest) {
        return userInformationService.doChangePartialInformationOrder(userID, NEOUserOrder.CHANGE_USER_INFO, changePartialStarInfoRequest);
    }

    /**
     * OAuth2.0을 통한 회원가입 이후, 스타로서 NEO에 가입하기 위해 추가정보를 입력합니다.<br>
     * 추가 정보에 관한 내용은 {@code createStarInfoRequest}에서 확인할 수 있습니다.<br>
     *
     * @param createStarInfoRequest 새로운 스타 정보 생성에 필요한 요청
     * @param bindingResult         요청 객체 유효성 검사 결과
     * @return {@code ResponseEntity<NEOResponseBody>}
     */
    @PostMapping("/stars")
    @NEOCreateStarInfoOrderDocs
    public ResponseEntity<NEOResponseBody> createNewStarInformationOrder(@RequestBody @Validated final NEOStarInfoDto createStarInfoRequest, BindingResult bindingResult) {
        NEOUserOrder userOrder = NEOUserOrder.CREATE_STAR_INFO;
        checkRequestValidationPassed(bindingResult, userOrder);
        return userInformationService.doCreateNewStarInformationOrder(createStarInfoRequest, userOrder);
    }

    /**
     * OAuth2.0을 통한 회원가입 이후, 팬으로서 NEO에 가입하기 위해 추가정보를 입력합니다.<br>
     * 추가 정보에 관한 내용은 {@code createFanInfoRequest}에서 확인할 수 있습니다.
     *
     * @param createFanInfoRequest 새로운 팬 정보 생성에 필요한 요청
     * @param bindingResult        요청 객체 유효성 검사 결과
     * @return {@code ResponseEntity<NEOResponseBody>}
     */
    @PostMapping("/fans")
    @NEOCreateFanInfoOrderDocs
    public ResponseEntity<NEOResponseBody> createNewFanInformationOrder(@RequestBody @Validated final NEOFanInfoDto createFanInfoRequest, BindingResult bindingResult) {
        NEOUserOrder userOrder = NEOUserOrder.CREATE_FAN_INFO;
        checkRequestValidationPassed(bindingResult, userOrder);
        return userInformationService.doCreateNewFanInformationOrder(createFanInfoRequest, userOrder);
    }


    @DeleteMapping("/{user_id}")
    @NEODeleteUserInfoOrderDocs
    public void deleteUserInformationOrder(@PathVariable("user_id") String userID) {
    }

    /**
     * Jackson을 통해 request를 역직렬화 하는 과정에서, 유효성 검사에서 실패했는지 체크하는 메소드입니다. <br>
     * 통과했다면, 그대로 서비스를 진행하고, 실패한다면 {@code NEOUserExpectedException}을 발생시켜<br>
     * {@code NEOUserExceptionAdvisor}에서 후처리를 진행하게끔 유도합니다.
     *
     * @param bindingResult 유효성 검사 결과
     */
    public void checkRequestValidationPassed(BindingResult bindingResult, NEOUserOrder userOrder) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 처리
            List<NEOErrorResponse> renderedErrorResponseList = renderErrorResponseListByRequestValidationFieldError(bindingResult.getFieldErrors());
            throw new NEOUserExpectedException(renderedErrorResponseList, userOrder);
        }
    }

    /**
     * request 유효성 검사에서 발생한 필드 에러에 대해서 로깅 및 exception 통합 메시지를 생성합니다.
     *
     * @param errors 유효성 검사 발생 필드 에러
     * @return exception 통합 메시지
     */
    public List<NEOErrorResponse> renderErrorResponseListByRequestValidationFieldError(List<FieldError> errors) {
        return errors.stream()
                .map(NEOErrorResponse::fromFieldError)
                .collect(Collectors.toList());
    }

    /**
     * 각 컨트롤러의 엔드포인트 메서드가 고유하게 갖는 값입니다.<br>
     * 해당 enum 값을 통해 어떤 요청인지, 성공시 최종 메시지 및 실패시 최종 메시지를 획득할 수 있습니다.
     */
    @Getter
    @RequiredArgsConstructor
    public enum NEOUserOrder {
        CREATE_STAR_INFO("새로운 스타 정보 생성에 성공했습니다.", "새로운 스타 정보 생성에 실패했습니다.", "POST api/v1/users/stars"),
        CREATE_FAN_INFO("새로운 팬 정보 생성에 성공했습니다.", "새로운 팬 정보 생성에 실패했습니다.", "POST api/v1/users/fans"),
        GET_USER_INFO("사용자 전체 정보를 얻어오는데 성공했습니다.", "사용자 전체 정보를 얻어오는데 실패했습니다.", "GET api/v1/users/{user_id}"),
        GET_USER_PUBLIC_INFO("사용자 공개 정보를 얻어오는데 성공했습니다.", "사용자 공개 정보를 얻어오는데 실패했습니다.", "GET api/v1/users/{user_id}"),
        CHANGE_USER_INFO("사용자 정보 변경에 성공했습니다", "사용자 정보 변경에 실패했습니다.", "PATCH api/v1/users/{user_id}"),
        COMMON(null, null, null);

        private final String successMessage;
        private final String failMessage;
        private final String requestedMethodAndURI;
    }

}

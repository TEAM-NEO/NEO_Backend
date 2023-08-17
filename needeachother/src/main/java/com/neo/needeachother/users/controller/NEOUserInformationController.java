package com.neo.needeachother.users.controller;

import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.response.NEOErrorResponse;
import com.neo.needeachother.common.response.NEOResponseBody;
import com.neo.needeachother.users.exception.NEOUserExpectedException;
import com.neo.needeachother.users.request.NEOCreateFanInfoRequest;
import com.neo.needeachother.users.request.NEOCreateStarInfoRequest;
import com.neo.needeachother.users.service.NEOUserInformationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User : Information", description = "NEO의 회원 관련 API입니다. 그 중 회원 정보와 관련된 API 리스트입니다.")
public class NEOUserInformationController {

    private final NEOUserInformationService userInformationService;

    @GetMapping("/{user_id}")
    public void getUserInformationOrder(
            @PathVariable("user_id") String userID,
            @RequestParam(value = "public", required = false, defaultValue = "false") boolean isPublic){

    }

    @PutMapping("/{user_id}")
    public void changeUserInformationOrder(@PathVariable("user_id") String userID){}

    @PatchMapping("/{user_id}")
    public void changePartialUserInformationOrder(@PathVariable("user_id") String userID){}

    /**
     * OAuth2.0을 통한 회원가입 이후, 스타로서 NEO에 가입하기 위해 추가정보를 입력합니다.<br>
     * 추가 정보에 관한 내용은 {@code createStarInfoRequest}에서 확인할 수 있습니다.<br>
     * @param createStarInfoRequest 새로운 스타 정보 생성에 필요한 요청
     * @return ResponseEntity<NEOResponseBody>
     */
    @Operation(summary = "새로운 스타 정보 생성",
            description = "OAuth2.0을 통해 회원가입을 한 후, 스타에 대한 추가정보를 입력받을 수 있습니다. OAuth2.0가 적용되기 이전에는 " +
                    "회원의 나머지 정보도 받아오는 것을 대체합니다.",
            parameters = {@Parameter(name = "NEOCreateStarInfoRequest", description = "새로운 스타 정보생성에 필요한 요청")})
    @PostMapping("/stars")
    public ResponseEntity<NEOResponseBody> createNewStarInformationOrder(@RequestBody @Validated final NEOCreateStarInfoRequest createStarInfoRequest, BindingResult bindingResult){
        NEOUserOrder userOrder = NEOUserOrder.CREATE_STAR_INFO;
        checkRequestValidationPassed(bindingResult, userOrder);
        return userInformationService.handleCreateNewStarInformationOrder(createStarInfoRequest, userOrder);
    }

    @Operation(summary = "새로운 팬 정보 생성",
            description = "OAuth2.0을 통해 회원가입을 한 후, 팬에 대한 추가정보를 입력받을 수 있습니다. OAuth2.0가 적용되기 이전에는 " +
                    "회원의 나머지 정보도 받아오는 것을 대체합니다.",
            parameters = {@Parameter(name = "NEOCreateFanInfoRequest", description = "새로운 팬 정보생성에 필요한 요청")})
    @PostMapping("/fans")
    public void createNewFanInformationOrder(@RequestBody @Validated final NEOCreateFanInfoRequest createFanInfoRequest, BindingResult bindingResult){
        NEOUserOrder userOrder = NEOUserOrder.CREATE_FAN_INFO;
        checkRequestValidationPassed(bindingResult, userOrder);
        userInformationService.handleCreateNewFanInformationOrder(createFanInfoRequest, userOrder);
    }

    @DeleteMapping("/{user_id}")
    public void deleteUserInformationOrder(@PathVariable("user_id") String userID){}

    /**
     * Jackson을 통해 request를 직렬화 하는 과정에서, 유효성 검사에서 실패했는지 체크하는 메소드입니다. <br>
     * 통과했다면, 그대로 서비스를 진행하고, 실패한다면 {@code NEOUserExpectedException}을 발생시켜<br>
     * {@code NEOUserExceptionAdvisor}에서 후처리를 진행하게끔 유도합니다.
     * @param bindingResult 유효성 검사 결과
     */
    public void checkRequestValidationPassed(BindingResult bindingResult, NEOUserOrder userOrder){
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 처리
            List<NEOErrorResponse> renderedErrorResponseList = renderErrorResponseListByRequestValidationFieldError(bindingResult.getFieldErrors());
            throw new NEOUserExpectedException(renderedErrorResponseList, userOrder);
        }
    }

    /**
     * request 유효성 검사에서 발생한 필드 에러에 대해서 로깅 및 exception 통합 메시지를 생성합니다.
     * @param errors 유효성 검사 발생 필드 에러
     * @return exception 통합 메시지
     */
    public List<NEOErrorResponse> renderErrorResponseListByRequestValidationFieldError(List<FieldError> errors){
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
    public enum NEOUserOrder{
        CREATE_STAR_INFO("새로운 스타 정보 생성에 성공했습니다.","새로운 스타 정보 생성에 실패했습니다.", "api/v1/users/stars"),
        CREATE_FAN_INFO("새로운 팬 정보 생성에 성공했습니다.", "새로운 팬 정보 생성에 실패했습니다.", "api/v1/users/fans");

        private final String successMessage;
        private final String failMessage;
        private final String requestedPath;
    }

}

package com.neo.needeachother.users.controller;

import com.neo.needeachother.users.request.NEOCreateStarInfoRequest;
import com.neo.needeachother.users.service.NEOUserInformationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @param createStarInfoRequest
     */
    @Operation(summary = "새로운 스타 정보 생성",
            description = "OAuth2.0을 통해 회원가입을 한 후, 스타에 대한 추가정보를 입력받을 수 있습니다. OAuth2.0가 적용되기 이전에는 " +
                    "회원의 나머지 정보도 받아오는 것을 대체합니다.",
            parameters = {@Parameter(name = "NEOCreateStarInfoRequest", description = "새로운 스타 정보생성에 필요한 요청")})
    @PostMapping("/stars")
    public void createNewStarInformationOrder(@RequestBody @Validated NEOCreateStarInfoRequest createStarInfoRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 처리
            loggingRequestValidationFieldError(bindingResult.getFieldErrors());
        }
        userInformationService.handleCreateNewStarInformationOrder(createStarInfoRequest);
    }

    @PostMapping("/fans")
    public void createNewFanInformationOrder(){}

    @DeleteMapping("/{user_id}")
    public void deleteUserInformationOrder(@PathVariable("user_id") String userID){}

    public void loggingRequestValidationFieldError(List<FieldError> errors){
        StringBuilder logBuilder = new StringBuilder();
        for (FieldError error : errors) {
            log.warn("=========== validation error! ===========");
            log.warn("Field: " + error.getField());
            log.warn("Rejected Value: " + error.getRejectedValue());
            log.warn("Message: " + error.getDefaultMessage());
            logBuilder.append(error.getDefaultMessage());
        }
    }

}

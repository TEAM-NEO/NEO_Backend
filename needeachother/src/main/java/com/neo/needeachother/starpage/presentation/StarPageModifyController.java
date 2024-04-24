package com.neo.needeachother.starpage.presentation;

import com.neo.needeachother.starpage.application.ModifyStarPageInformationService;
import com.neo.needeachother.starpage.presentation.dto.ModifyProfileImageRequest;
import com.neo.needeachother.starpage.presentation.dto.ModifyStarIntroductionRequest;
import com.neo.needeachother.starpage.presentation.dto.ModifyStarNickNameRequest;
import com.neo.needeachother.starpage.presentation.dto.ModifyTopRepresentativeImageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/starpage")
@RequiredArgsConstructor
public class StarPageModifyController {

    private final ModifyStarPageInformationService modifyStarPageInformationService;

    @PutMapping("")
    public void demandChangeProfileImage(@RequestBody ModifyProfileImageRequest modifyProfileImageRequest){
        modifyStarPageInformationService.modifyProfileImage(
                modifyProfileImageRequest.getStarPageId(),
                modifyProfileImageRequest.getEmail(),
                modifyProfileImageRequest.getImageUrl()
        );
    }

    @PutMapping("")
    public void demandChangeTopRepresentativeImage(@RequestBody ModifyTopRepresentativeImageRequest modifyTopRepresentativeImageRequest){
        modifyStarPageInformationService.modifyTopRepresentativeImage(
                modifyTopRepresentativeImageRequest.getStarPageId(),
                modifyTopRepresentativeImageRequest.getEmail(),
                modifyTopRepresentativeImageRequest.getImageUrl()
        );
    }

    @PutMapping("")
    public void demandChangeStarNickName(@RequestBody ModifyStarNickNameRequest modifyStarNickNameRequest){
        modifyStarPageInformationService.modifyStarNickName(
                modifyStarNickNameRequest.getStarPageId(),
                modifyStarNickNameRequest.getEmail(),
                modifyStarNickNameRequest.getNickName()
        );
    }

    @PutMapping("")
    public void demandChangeStarIntroduction(@RequestBody ModifyStarIntroductionRequest modifyStarIntroductionRequest){
        modifyStarPageInformationService.modifyStarIntroduction(
                modifyStarIntroductionRequest.getStarPageId(),
                modifyStarIntroductionRequest.getEmail(),
                modifyStarIntroductionRequest.getIntroduction()
        );
    }
}

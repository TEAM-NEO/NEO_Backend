package com.neo.needeachother.starpage.presentation;

import com.neo.needeachother.starpage.application.ModifyStarPageInformationService;
import com.neo.needeachother.starpage.application.SNSLineService;
import com.neo.needeachother.starpage.presentation.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/starpage")
@RequiredArgsConstructor
public class StarPageModifyController {

    private final ModifyStarPageInformationService modifyStarPageInformationService;
    private final SNSLineService snsLineService;

    @PutMapping("/{star_page_id}/profile_image")
    public void demandChangeProfileImage(
            @PathVariable("star_page_id") String starPageId,
            @Valid @RequestBody ModifyProfileImageRequest modifyProfileImageRequest) {
        modifyStarPageInformationService.modifyProfileImage(
                starPageId,
                modifyProfileImageRequest.getEmail(),
                modifyProfileImageRequest.getImageUrl()
        );
    }

    @PutMapping("/{star_page_id}/representative_image")
    public void demandChangeTopRepresentativeImage(
            @PathVariable("star_page_id") String starPageId,
            @Valid @RequestBody ModifyTopRepresentativeImageRequest modifyTopRepresentativeImageRequest) {
        modifyStarPageInformationService.modifyTopRepresentativeImage(
                starPageId,
                modifyTopRepresentativeImageRequest.getEmail(),
                modifyTopRepresentativeImageRequest.getImageUrl()
        );
    }

    @PutMapping("/{star_page_id}/info/nickname")
    public void demandChangeStarNickName(
            @PathVariable("star_page_id") String starPageId,
            @Valid @RequestBody ModifyStarNickNameRequest modifyStarNickNameRequest) {
        modifyStarPageInformationService.modifyStarNickName(
                starPageId,
                modifyStarNickNameRequest.getEmail(),
                modifyStarNickNameRequest.getNickName()
        );
    }

    @PutMapping("/{star_page_id}/info/introduction")
    public void demandChangeStarIntroduction(
            @PathVariable("star_page_id") String starPageId,
            @Valid @RequestBody ModifyStarIntroductionRequest modifyStarIntroductionRequest) {
        modifyStarPageInformationService.modifyStarIntroduction(
                starPageId,
                modifyStarIntroductionRequest.getEmail(),
                modifyStarIntroductionRequest.getIntroduction()
        );
    }

    @PutMapping("/{star_page_id}/info/sns_line")
    public void demandAddSNSLine(
            @PathVariable("star_page_id") String starPageId,
            @Valid @RequestBody AddSNSLineRequest addSNSLineRequest) {
        snsLineService.appendSNSLine(
                starPageId,
                addSNSLineRequest.getEmail(),
                addSNSLineRequest.getSnsName(),
                addSNSLineRequest.getSnsUrl()
        );
    }

    @DeleteMapping("/{star_page_id}/info/sns_line")
    public void demandDeleteSNSLine(
            @PathVariable("star_page_id") String starPageId,
            @Valid @RequestBody DeleteSNSLineRequest deleteSNSLineRequest) {
        snsLineService.deleteSNSLine(
                starPageId,
                deleteSNSLineRequest.getEmail(),
                deleteSNSLineRequest.getSnsName(),
                deleteSNSLineRequest.getSnsUrl()
        );
    }
}

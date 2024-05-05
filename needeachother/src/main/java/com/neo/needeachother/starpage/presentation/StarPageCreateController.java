package com.neo.needeachother.starpage.presentation;

import com.neo.needeachother.starpage.application.CreateStarPageService;
import com.neo.needeachother.starpage.application.dto.CreatedStarPageResult;
import com.neo.needeachother.starpage.domain.SNSLine;
import com.neo.needeachother.starpage.presentation.dto.CreateStarPageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/starpage")
public class StarPageCreateController {

    private final CreateStarPageService createStarPageService;

    @PostMapping
    public ResponseEntity<CreatedStarPageResult> demandCreateNewStarPage(@RequestBody CreateStarPageRequest createStarPageRequest) {

        CreatedStarPageResult createdStarPageResult = createStarPageService.createStarPage(
                createStarPageRequest.getStarNickName(),
                createStarPageRequest.getEmail(),
                createStarPageRequest.getStarTypeSet(),
                createStarPageRequest.getSnsProfiles().stream()
                        .map(snsProfile -> SNSLine.of(snsProfile.getSnsTypeName(), snsProfile.getUrl()))
                        .toList(),
                createStarPageRequest.getStarPageIntroduce()
        );

        return ResponseEntity.created(URI.create("/api/v1/starpage/" + createdStarPageResult.getCreatedStarPageId()))
                .body(createdStarPageResult);
    }
}

package com.neo.needeachother.starpage.presentation;

import com.neo.needeachother.starpage.application.LayoutManagementService;
import com.neo.needeachother.starpage.application.dto.StarPageLayoutResult;
import com.neo.needeachother.starpage.presentation.dto.AddCategoricalLayoutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/starpage")
public class StarPageLayoutController {

    private final LayoutManagementService layoutManagementService;

    @PostMapping("/layout")
    public ResponseEntity demandAddLayout(@RequestBody AddCategoricalLayoutRequest addCategoricalLayoutRequest){
        List<StarPageLayoutResult> result = layoutManagementService.appendLayoutInStarPage(
                addCategoricalLayoutRequest.getStarPageId(),
                addCategoricalLayoutRequest.getEmail(),
                addCategoricalLayoutRequest.getCategoryId()
        );

        Long appendedLayoutId = result.get(result.size() - 1).getLayoutId();
        return ResponseEntity.created(URI.create("/api/v1/starpage/layout?id=" + appendedLayoutId))
                .body(result);
    }
}

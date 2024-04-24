package com.neo.needeachother.starpage.presentation;

import com.neo.needeachother.starpage.application.StarPageViewDataService;
import com.neo.needeachother.starpage.application.dto.StarPageTopViewData;
import com.neo.needeachother.starpage.application.dto.StarPageViewData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/starpage")
public class StarPageViewController {

    private final StarPageViewDataService viewDataService;

    @GetMapping("/top_view")
    public ResponseEntity<StarPageTopViewData> demandStarPageTopView(@RequestParam String starPageId){
        StarPageTopViewData topViewData = viewDataService.getStarPageTopViewData(starPageId);
        return ResponseEntity.ok(topViewData);
    }

    @GetMapping("/layout_view")
    public ResponseEntity<StarPageViewData> demandStarPageLayoutView(@RequestParam String starPageId){
        StarPageViewData layoutViewData = viewDataService.getStarPageLayoutViewData(starPageId);
        return ResponseEntity.ok(layoutViewData);
    }

}

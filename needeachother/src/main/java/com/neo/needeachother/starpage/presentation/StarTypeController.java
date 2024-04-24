package com.neo.needeachother.starpage.presentation;

import com.neo.needeachother.starpage.application.StarTypeService;
import com.neo.needeachother.starpage.application.dto.ModifiedStarTypeResult;
import com.neo.needeachother.starpage.presentation.dto.AddStarTypeRequest;
import com.neo.needeachother.starpage.presentation.dto.RemoveStarTypeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/starpage")
public class StarTypeController {

    private final StarTypeService starTypeService;

    @PostMapping("/{star_page_id}/star-type")
    public ResponseEntity<ModifiedStarTypeResult> demandAddStarType(
            @PathVariable("star_page_id") String starPageId,
            @Valid  @RequestBody AddStarTypeRequest addStarTypeRequest) {
        return ResponseEntity.ok(starTypeService.addStarType(starPageId, addStarTypeRequest.getEmail(), addStarTypeRequest.getStarTypeName()));
    }

    @DeleteMapping("/{star_page_id}/star-type")
    public ResponseEntity<ModifiedStarTypeResult> demandRemoveStarType(
            @PathVariable("star_page_id") String starPageId,
            @Valid @RequestBody RemoveStarTypeRequest removeStarTypeRequest) {
        return ResponseEntity.ok(starTypeService.removeStarType(starPageId, removeStarTypeRequest.getEmail(), removeStarTypeRequest.getStarTypeName()));
    }
}

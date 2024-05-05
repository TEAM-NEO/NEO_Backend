package com.neo.needeachother.starpage.presentation;

import com.neo.needeachother.starpage.application.StarPageAdminManagementService;
import com.neo.needeachother.starpage.presentation.dto.AppointStarPageAdminRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/starpage")
@RequiredArgsConstructor
public class StarPageAdminManageController {

    private final StarPageAdminManagementService starPageAdminManagementService;

    @PostMapping("{star_page_id}/admin")
    public void demandAppointStarPageAdmin(
            @PathVariable("star_page_id") String starPageId,
            @Valid @RequestBody AppointStarPageAdminRequest appointStarPageAdminRequest){
        starPageAdminManagementService.appointAsStarPageAdmin(
                starPageId,
                appointStarPageAdminRequest.getRequesterEmail(),
                appointStarPageAdminRequest.getBeingAdminEmail()
        );
    }
}

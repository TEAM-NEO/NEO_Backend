package com.neo.needeachother.starpage.presentation.dto;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointStarPageAdminRequest {
    private String requesterEmail;
    private String beingAdminEmail;
}

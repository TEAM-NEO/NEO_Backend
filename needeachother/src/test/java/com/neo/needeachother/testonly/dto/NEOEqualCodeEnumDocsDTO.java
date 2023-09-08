package com.neo.needeachother.testonly.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NEOEqualCodeEnumDocsDTO {
    Map<String, List<String>> genderCode;
    Map<String, List<String>> starClassificationCode;
}

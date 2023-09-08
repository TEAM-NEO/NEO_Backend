package com.neo.needeachother.testonly.controller;

import com.neo.needeachother.testonly.dto.NEOEqualCodeEnumDocsDTO;
import com.neo.needeachother.testonly.dto.NEOTestApiResponseDTO;
import com.neo.needeachother.common.enums.NEOEqualCodeEnum;
import com.neo.needeachother.users.enums.NEOGenderType;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/test")
public class NEOEnumDocsController {

    @GetMapping("/equal-code-enums")
    public NEOTestApiResponseDTO<NEOEqualCodeEnumDocsDTO> findEqualCodeEnums() {

        // 문서화 하고 싶은 -> EnumDocs 클래스에 담긴 모든 Enum 값 생성
        Map<String, List<String>> genders = getDocs(NEOGenderType.values());
        Map<String, List<String>> starDetailClassifications = getDocs(NEOStarDetailClassification.values());

        // 전부 담아서 반환 -> 테스트에서는 이걸 꺼내 해석하여 조각을 만들면 된다.
        return NEOTestApiResponseDTO.of(NEOEqualCodeEnumDocsDTO.builder()
                .genderCode(genders)
                .starClassificationCode(starDetailClassifications)
                .build());

    }

    private Map<String, List<String>> getDocs(NEOEqualCodeEnum[] equalCodeEnums) {
        Map<String, List<String>> tempDocs = new HashMap<>();
        Arrays.stream(equalCodeEnums)
                .filter(neoEqualCodeEnum -> !neoEqualCodeEnum.getName().equals("NONE"))
                .forEach(neoEqualCodeEnum -> {
                    tempDocs.put(neoEqualCodeEnum.getName(), List.of(neoEqualCodeEnum.getEqualCode(), neoEqualCodeEnum.getDescription()));
                });
        return tempDocs;
    }
}

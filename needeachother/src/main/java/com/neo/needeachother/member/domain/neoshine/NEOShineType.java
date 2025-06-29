package com.neo.needeachother.member.domain.neoshine;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NEOShineType {
    INDIVIDUAL("IND", "개인"),
    GROUP("GRP", "그룹");

    private final String typeCode;
    private final String description;
}
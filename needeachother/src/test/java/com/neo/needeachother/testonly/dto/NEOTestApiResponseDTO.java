package com.neo.needeachother.testonly.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
public class NEOTestApiResponseDTO<T> {

    private T data;

    private NEOTestApiResponseDTO(T data){
        this.data = data;
    }
    public static <T> NEOTestApiResponseDTO<T> of(T data) {
        return new NEOTestApiResponseDTO<>(data);
    }
}
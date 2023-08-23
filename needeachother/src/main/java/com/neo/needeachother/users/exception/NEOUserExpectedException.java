package com.neo.needeachother.users.exception;

import com.neo.needeachother.common.enums.NEODomainType;
import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.exception.NEOExpectedException;
import com.neo.needeachother.common.response.NEOErrorResponse;
import com.neo.needeachother.users.enums.NEOUserOrder;
import lombok.Getter;

import java.util.List;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * NEO의 예측 가능한 예외 중, 유저 도메인에서 발생한 예외를 정의합니다.<br>
 */
@Getter
public class NEOUserExpectedException extends NEOExpectedException {

    private final NEOUserOrder userOrder;

    /**
     * @param errorCode NEO 에러코드
     * @param userOrder 클라이언트 요청 구분 enum 객체
     */
    public NEOUserExpectedException(NEOErrorCode errorCode, NEOUserOrder userOrder) {
        super(NEODomainType.USERS, errorCode, userOrder.getFailMessage());
        this.userOrder = userOrder;
    }

    /**
     * @param errorCode NEO 에러코드
     * @param errorDetailMessage 해당 에러에 대한 상세 메시지
     * @param userOrder 클라이언트 요청 구분 enum 객체
     */
    public NEOUserExpectedException(NEOErrorCode errorCode, String errorDetailMessage, NEOUserOrder userOrder){
        super(NEODomainType.USERS, errorCode, errorDetailMessage, userOrder.getFailMessage());
        this.userOrder = userOrder;
    }

    /**
     * @param errorResponse 최종 응답에 포함되는 에러 응답
     * @param userOrder 클라이언트 요청 구분 enum 객체
     */
    public NEOUserExpectedException(NEOErrorResponse errorResponse, NEOUserOrder userOrder){
        super(NEODomainType.USERS, errorResponse, userOrder.getFailMessage());
        this.userOrder = userOrder;
    }

    /**
     * @param errorResponseList 최종 응답에 포함되는 에러 응답 리스트
     * @param userOrder 클라이언트 요청 구분 enum 객체
     */
    public NEOUserExpectedException(List<NEOErrorResponse> errorResponseList, NEOUserOrder userOrder){
        super(NEODomainType.USERS, errorResponseList, userOrder.getFailMessage());
        this.userOrder = userOrder;
    }

}

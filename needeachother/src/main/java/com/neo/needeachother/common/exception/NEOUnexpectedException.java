package com.neo.needeachother.common.exception;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * 예측 불가능한, 발생하면 안되는 예외에 대해서 정의합니다.<br>
 */
public class NEOUnexpectedException extends RuntimeException{
    public NEOUnexpectedException(String msg){
        super(msg);
    }
}

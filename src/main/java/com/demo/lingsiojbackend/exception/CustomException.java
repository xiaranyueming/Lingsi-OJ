package com.demo.lingsiojbackend.exception;

import com.demo.lingsiojbackend.constant.ErrorCodeEnum;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private Integer code;
    private String message;

    public CustomException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
    }

}

package com.demo.lingsiojbackend.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private Integer code;
    private String message;

    public CustomException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}

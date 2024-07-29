package com.demo.lingsiojbackend.utils;

import com.demo.lingsiojbackend.constant.ErrorCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private int code;
    private String message;
    private Object data;

    public static Result success() {
        return new Result(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMessage(), null);
    }

    public static Result success(Object data) {
        return new Result(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMessage(), data);
    }

    public static Result fail(int code, String message) {
        return new Result(code, message, null);
    }

    public static Result fail(ErrorCodeEnum errorCodeEnum) {
        return new Result(errorCodeEnum.getCode(), errorCodeEnum.getMessage(), null);
    }
}

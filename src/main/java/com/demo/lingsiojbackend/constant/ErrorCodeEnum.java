package com.demo.lingsiojbackend.constant;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    SUCCESS(200, "success"),
    SYSTEM_ERROR(500, "系统异常"),
    PARAM_ERROR(400, "参数错误"),
    USER_NOT_EXIST(405, "用户不存在"),
    INFO_HAS_EXIST(406, "信息已存在"),
    ACCOUNT_PASSWORD_ERROR(407, "账号或密码错误"),
    USER_HAS_EXIST(408, "用户已存在"),
    UPDATE_PASSWORD_ERROR(409, "修改密码失败");

    private final int code;
    private final String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

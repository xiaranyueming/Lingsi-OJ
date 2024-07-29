package com.demo.lingsiojbackend.exception;

import com.demo.lingsiojbackend.utils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(CustomException.class)
    public Result handleException(CustomException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }
}

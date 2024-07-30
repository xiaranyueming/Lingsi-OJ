package com.demo.lingsiojbackend.exception;

import com.demo.lingsiojbackend.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalException {

    @ExceptionHandler(CustomException.class)
    public Result handleException(CustomException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }
}

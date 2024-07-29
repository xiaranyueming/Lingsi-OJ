package com.demo.lingsiojbackend.controller;

import com.demo.lingsiojbackend.constant.ErrorCodeEnum;
import com.demo.lingsiojbackend.entity.dto.UserDTO;
import com.demo.lingsiojbackend.entity.vo.UserVO;
import com.demo.lingsiojbackend.service.UserService;
import com.demo.lingsiojbackend.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "登录注册接口")
@RequiredArgsConstructor
public class LoginAndRegisterController {

    private final UserService userService;


    /**
     * 用户登录
     * @return Result
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public Result register(@RequestBody UserDTO userDTO) {
        if (userDTO == null) {
            return Result.fail(ErrorCodeEnum.PARAM_ERROR);
        }

        userService.register(userDTO);

        return Result.success();
    }


    /**
     * 用户登录
     * @return Result
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result login(@RequestBody UserDTO userDTO, HttpSession session) {
        if (userDTO == null) {
            return Result.fail(ErrorCodeEnum.PARAM_ERROR);
        }

        UserVO userVO = userService.login(userDTO, session);

        return Result.success(userVO);
    }
}

package com.demo.lingsiojbackend.controller;

import com.demo.lingsiojbackend.constant.ErrorCodeEnum;
import com.demo.lingsiojbackend.entity.user.UpdatePassword;
import com.demo.lingsiojbackend.entity.vo.UserVO;
import com.demo.lingsiojbackend.service.UserService;
import com.demo.lingsiojbackend.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "用户接口")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    /**
     * 根据用户id获取用户信息
     * @param id 用户id
     * @return 用户信息
     */
    @GetMapping("/info/{id}")
    @Operation(summary = "根据用户id获取用户信息")
    public Result getUserInfo(@PathVariable("id") Integer id) {
        if (id == null) {
            return Result.fail(ErrorCodeEnum.PARAM_ERROR);
        }

        UserVO userVO = userService.getUserInfoById(id);
        return Result.success(userVO);
    }



    @PutMapping("/change")
    @Operation(summary = "修改密码")
    public Result uerUpdatePassword(@RequestBody UpdatePassword updatePassword, HttpServletRequest request) {
        if (updatePassword == null) {
            return Result.fail(ErrorCodeEnum.PARAM_ERROR);
        }
        boolean result = userService.updatePassword(updatePassword, request);
        if (result) {
            return Result.success();
        } else {
            return Result.fail(ErrorCodeEnum.UPDATE_PASSWORD_ERROR);
        }
    }


}

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
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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



    @PutMapping("/update")
    @Operation(summary = "修改用户信息")
    public Result updateUserInfo(@RequestBody UserVO userVO) {
        if (userVO == null) {
            return Result.fail(ErrorCodeEnum.PARAM_ERROR);
        }
        boolean result = userService.updateUserInfo(userVO);
        if (result) {
            return Result.success();
        } else {
            return Result.fail(ErrorCodeEnum.SYSTEM_ERROR);
        }
    }


    /**
     * 上传文件
     * @param file 文件
     * @return Result
     */
    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    public Result upload(MultipartFile file) throws FileNotFoundException {
        if (file == null) {
            return Result.fail(ErrorCodeEnum.PARAM_ERROR);
        }
        String fileName = file.getOriginalFilename();
        String suffix = null;
        if (fileName != null) {
            suffix = fileName.substring(fileName.lastIndexOf("."));
        }
        if (!".jpg".equals(suffix) && !".png".equals(suffix) && !".jpeg".equals(suffix)) {
            return Result.fail(ErrorCodeEnum.FILE_FORMAT_ERROR);
        }
        String currentPath = ResourceUtils.getURL("classpath:").getPath();
        File uploadPath = new File(currentPath, "/static/upload/");
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        String newName = System.currentTimeMillis() + suffix;
        File dest = new File(uploadPath, newName);
        try {
            file.transferTo(dest);
            String resultPath = "http://localhost:8080/upload/" + newName;
            return Result.success(resultPath);
        } catch (IOException e) {
            return Result.fail(ErrorCodeEnum.SYSTEM_ERROR);
        }
    }


}

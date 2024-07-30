package com.demo.lingsiojbackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.lingsiojbackend.constant.ErrorCodeEnum;
import com.demo.lingsiojbackend.constant.RoleEnum;
import com.demo.lingsiojbackend.entity.domain.User;
import com.demo.lingsiojbackend.entity.dto.UserDTO;
import com.demo.lingsiojbackend.entity.user.UpdatePassword;
import com.demo.lingsiojbackend.entity.vo.UserVO;
import com.demo.lingsiojbackend.exception.CustomException;
import com.demo.lingsiojbackend.service.UserService;
import com.demo.lingsiojbackend.mapper.UserMapper;
import com.demo.lingsiojbackend.utils.EncryptionUtil;
import com.demo.lingsiojbackend.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
* @author sh
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-07-29 08:55:33
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    /**
     * 用户注册
     * @param userDTO 用户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserDTO userDTO) {
        if (userDTO == null) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR.getCode(), ErrorCodeEnum.PARAM_ERROR.getMessage());
        }
        User user = BeanUtil.copyProperties(userDTO, User.class);
        // 判断用户是否已存在
        User one = this.lambdaQuery()
                .eq(User::getUserName, user.getUserName())
                .one();
        if (one != null) {
            throw new CustomException(ErrorCodeEnum.USER_HAS_EXIST.getCode(), ErrorCodeEnum.USER_HAS_EXIST.getMessage());
        }

        user.setRole(RoleEnum.USER.getRole());
        user.setPassword(EncryptionUtil.encryption(userDTO.getPassword()));
        this.save(user);
    }


    /**
     * 用户登录
     * @param userDTO 用户信息
     * @param request 请求
     * @return UserVO 登录用户信息
     */
    @Override
    public UserVO login(UserDTO userDTO, HttpServletRequest request) {
        if (userDTO == null) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR.getCode(), ErrorCodeEnum.PARAM_ERROR.getMessage());
        }
        // 判断用户是否存在
        User user = this.lambdaQuery()
                .eq(User::getUserName, userDTO.getUserName())
                .one();
        if (user == null) {
            throw new CustomException(ErrorCodeEnum.USER_NOT_EXIST.getCode(), ErrorCodeEnum.USER_NOT_EXIST.getMessage());
        }
        // 判断密码是否正确
        if (!EncryptionUtil.encryption(userDTO.getPassword()).equals(user.getPassword())) {
            throw new CustomException(ErrorCodeEnum.ACCOUNT_PASSWORD_ERROR.getCode(), ErrorCodeEnum.ACCOUNT_PASSWORD_ERROR.getMessage());
        }

        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        HttpSession session = request.getSession();
        session.setAttribute("userId", user.getId());
        return userVO;
    }


    /**
     * 根据用户id获取用户信息
     * @param id 用户id
     * @return UserVO 用户信息
     */
    @Override
    public UserVO getUserInfoById(Integer id) {
        if (id == null) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR.getCode(), ErrorCodeEnum.PARAM_ERROR.getMessage());
        }

        User user = this.getById(id);
        if (user == null) {
            throw new CustomException(ErrorCodeEnum.USER_NOT_EXIST.getCode(), ErrorCodeEnum.USER_NOT_EXIST.getMessage());
        }
        return BeanUtil.copyProperties(user, UserVO.class);
    }


    /**
     * 修改密码
     *
     * @param updatePassword 修改密码信息
     * @param request 请求
     * @return boolean 是否修改成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePassword(UpdatePassword updatePassword, HttpServletRequest request) {
        if (updatePassword == null) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR.getCode(), ErrorCodeEnum.PARAM_ERROR.getMessage());
        }

        // 判断用户是否存在
        User user = this.getById(updatePassword.getUserId());
        if (user == null) {
            throw new CustomException(ErrorCodeEnum.USER_NOT_EXIST.getCode(), ErrorCodeEnum.USER_NOT_EXIST.getMessage());
        }
        // 判断原密码是否正确
        if (!EncryptionUtil.encryption(updatePassword.getOldPassword()).equals(user.getPassword())) {
            throw new CustomException(ErrorCodeEnum.UPDATE_PASSWORD_ERROR.getCode(), ErrorCodeEnum.UPDATE_PASSWORD_ERROR.getMessage());
        }
        user.setPassword(EncryptionUtil.encryption(updatePassword.getNewPassword()));
        request.getSession().removeAttribute("userId");
        return this.updateById(user);
    }


    /**
     * 修改用户信息
     * @param userVO   用户信息
     * @return boolean 是否修改成功
     */
    @Override
    public boolean updateUserInfo(UserVO userVO) {
        if (userVO == null || userVO.getId() == null) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR.getCode(), ErrorCodeEnum.PARAM_ERROR.getMessage());
        }
        // 判断用户是否存在
        User user = this.getById(userVO.getId());
        if (user == null) {
            throw new CustomException(ErrorCodeEnum.USER_NOT_EXIST.getCode(), ErrorCodeEnum.USER_NOT_EXIST.getMessage());
        }

        return this.updateById(BeanUtil.copyProperties(userVO, User.class));
    }




}





package com.demo.lingsiojbackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.lingsiojbackend.constant.ErrorCodeEnum;
import com.demo.lingsiojbackend.constant.RoleEnum;
import com.demo.lingsiojbackend.entity.domain.User;
import com.demo.lingsiojbackend.entity.dto.UserDTO;
import com.demo.lingsiojbackend.entity.vo.UserVO;
import com.demo.lingsiojbackend.exception.CustomException;
import com.demo.lingsiojbackend.service.UserService;
import com.demo.lingsiojbackend.mapper.UserMapper;
import com.demo.lingsiojbackend.utils.EncryptionUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author sh
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-07-29 08:55:33
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserDTO userDTO) {
        if (userDTO == null) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR);
        }
        User user = BeanUtil.copyProperties(userDTO, User.class);
        // 判断用户是否已存在
        User one = this.lambdaQuery()
                .eq(User::getUserName, user.getUserName())
                .one();
        if (one != null) {
            throw new CustomException(ErrorCodeEnum.USER_HAS_EXIST);
        }

        user.setRole(RoleEnum.USER.getRole());
        user.setPassword(EncryptionUtil.encryption(userDTO.getPassword()));
        this.save(user);
    }



    @Override
    public UserVO login(UserDTO userDTO, HttpSession session) {
        if (userDTO == null) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR);
        }
        // 判断用户是否存在
        User user = this.lambdaQuery()
                .eq(User::getUserName, userDTO.getUserName())
                .one();
        if (user == null) {
            throw new CustomException(ErrorCodeEnum.USER_NOT_EXIST);
        }
        // 判断密码是否正确
        if (!EncryptionUtil.encryption(userDTO.getPassword()).equals(user.getPassword())) {
            throw new CustomException(ErrorCodeEnum.ACCOUNT_PASSWORD_ERROR);
        }

        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        session.setAttribute("userId", user.getId());
        return userVO;
    }
}





package com.demo.lingsiojbackend.service;

import com.demo.lingsiojbackend.entity.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.lingsiojbackend.entity.dto.UserDTO;
import com.demo.lingsiojbackend.entity.vo.UserVO;
import jakarta.servlet.http.HttpSession;

/**
* @author sh
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-07-29 08:55:33
*/
public interface UserService extends IService<User> {

    /**
     *  用户注册
     * @param userDTO 用户信息
     */
    void register(UserDTO userDTO);

    /**
     * 用户登录
     *
     * @param userDTO 用户信息
     * @param session
     * @return UserVO 登录用户信息
     */
    UserVO login(UserDTO userDTO, HttpSession session);

}

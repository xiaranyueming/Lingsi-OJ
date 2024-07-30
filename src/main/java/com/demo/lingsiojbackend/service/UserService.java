package com.demo.lingsiojbackend.service;

import com.demo.lingsiojbackend.entity.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.lingsiojbackend.entity.dto.UserDTO;
import com.demo.lingsiojbackend.entity.user.UpdatePassword;
import com.demo.lingsiojbackend.entity.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

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
     * @param request 请求
     * @return UserVO 登录用户信息
     */
    UserVO login(UserDTO userDTO, HttpServletRequest request);


    /**
     * 根据用户id获取用户信息
     * @param id 用户id
     * @return UserVO 用户信息
     */
    UserVO getUserInfoById(Integer id);


    /**
     * 修改密码
     *
     * @param updatePassword 修改密码信息
     * @param request 请求
     * @return boolean 是否修改成功
     */
    boolean updatePassword(UpdatePassword updatePassword, HttpServletRequest request);


    /**
     * 修改用户信息
     * @param userVO   用户信息
     * @return boolean 是否修改成功
     */
    boolean updateUserInfo(UserVO userVO);
}

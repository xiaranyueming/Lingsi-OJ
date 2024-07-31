package com.demo.lingsiojbackend.entity.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserVO implements Serializable {
    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户描述
     */
    private String description;

    /**
     * 角色：user，admin
     */
    private String role;
}

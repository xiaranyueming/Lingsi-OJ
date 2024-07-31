package com.demo.lingsiojbackend.entity.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePassword implements Serializable {
    private Integer userId;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}

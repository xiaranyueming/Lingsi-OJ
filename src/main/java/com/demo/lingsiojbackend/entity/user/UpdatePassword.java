package com.demo.lingsiojbackend.entity.user;

import lombok.Data;

@Data
public class UpdatePassword {
    private Integer userId;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}

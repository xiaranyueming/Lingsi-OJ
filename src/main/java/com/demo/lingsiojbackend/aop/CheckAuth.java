package com.demo.lingsiojbackend.aop;

import com.demo.lingsiojbackend.constant.ErrorCodeEnum;
import com.demo.lingsiojbackend.constant.RoleEnum;
import com.demo.lingsiojbackend.entity.vo.UserVO;
import com.demo.lingsiojbackend.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class CheckAuth {

    @Pointcut("@annotation(com.demo.lingsiojbackend.annotation.Auth)")
    public void auth() {

    }


    /**
     * 检查权限，判断是否为管理员
     */
    @Before("auth()")
    public void checkAuth() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new CustomException(ErrorCodeEnum.SYSTEM_ERROR.getCode(), ErrorCodeEnum.SYSTEM_ERROR.getMessage());
        }
        HttpServletRequest request = requestAttributes.getRequest();
        UserVO user = (UserVO) request.getSession().getAttribute("userVO");
        if (user == null || !RoleEnum.ADMIN.getRole().equals(user.getRole())) {
            throw new CustomException(ErrorCodeEnum.NO_AUTH.getCode(), ErrorCodeEnum.NO_AUTH.getMessage());
        }
    }
}

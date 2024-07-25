import {RoleEnum} from "@/utils/RoleEnum.js";

export const checkAccess = (user, needRole) => {
    // 不需要登录就可以访问的页面
    if (needRole === RoleEnum.NOT_LOGIN) {
        return true;
    }
    // 管理员页面
    if (RoleEnum.ADMIN === needRole) {
        return user && user.role === RoleEnum.ADMIN;
    }
    // 需要登录才能访问的页面
    if (RoleEnum.USER === needRole) {
        return user && (user.role === RoleEnum.USER || user.role === RoleEnum.ADMIN);
    }
}
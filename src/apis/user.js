import Request  from "@/utils/Request.js";

// 用户登录
export const loginApi = (data) => {
    return Request({
        url: '/login',
        method: 'post',
        data: data
    })
}


// 用户注册
export const registerApi = (data) => {
    return Request({
        url: '/register',
        method: 'post',
        data: data
    })
}


// 获取用户信息
export const getUserInfoApi = (id) => {
    return Request({
        url: `/user/info/${id}`,
        method: 'get',
    })
}


// 修改密码
export const changePasswordApi = (data) => {
    return Request({
        url: '/user/change',
        method: 'put',
        data: data
    })
}


// 退出登录
export const logoutApi = () => {
    return Request({
        url: '/logout',
        method: 'get',
    })
}


// 修改用户信息
export const updateUserInfoApi = (data) => {
    return Request({
        url: '/user/update',
        method: 'put',
        data: data
    })
}
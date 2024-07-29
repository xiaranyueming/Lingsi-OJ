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
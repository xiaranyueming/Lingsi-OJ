import axios from 'axios'
import router from "@/router/index.js";
import { notification } from "ant-design-vue";

const Request = axios.create({
    baseURL: "http://localhost:8080",
    timeout: 30000,
    withCredentials: true
})

Request.interceptors.request.use( config => {
    return config
})

Request.interceptors.response.use( response => {
        return response.data
}, error => {
    if (error.response.status === 401) {
        router.push('/login')
        notification.error({
            message: '未登录',
            description: '请先登录'
        })
    }
    return Promise.reject(error)
})

export default Request
import Request from "@/utils/Request.js";


// 分页获取问题列表
export const getQuestionListApi = (data) => {
    return Request({
        url: '/question/page/list',
        method: 'post',
        data: data
    })
}


// 发布题目
export const addQuestionApi = (data) => {
    return Request({
        url: '/question/add',
        method: 'post',
        data: data
    })
}


// 获取题目详情
export const getQuestionDetailApi = (id) => {
    return Request({
        url: `/question/detail/${id}`,
        method: 'get',
    })
}


// 删除题目
export const deleteQuestionApi = (id) => {
    return Request({
        url: `/question/delete/${id}`,
        method: 'delete',
    })
}
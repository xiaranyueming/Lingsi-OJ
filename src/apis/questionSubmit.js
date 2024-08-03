import Request from "@/utils/Request.js";


// 获取语言列表
export const getLanguageListApi = () => {
    return Request({
        url: '/submit/language',
        method: 'get',
    })
}


// 获取题目提交列表
export const getQuestionSubmitListApi = (data) => {
    return Request({
        url: '/submit/list',
        method: 'post',
        data: data
    })
}


// 提交题目
export const questionSubmitApi = (data) => {
    return Request({
        url: '/submit/question',
        method: 'post',
        data: data
    })
}


// 获取题目提交详情
export const getQuestionSubmitDetailApi = (id) => {
    return Request({
        url: `/submit/question/${id}`,
        method: 'get',
    })
}


export const getQuestionSubmitApi = (id) => {
    return Request({
        url: `/submit/question/vo/${id}`,
        method: 'get',
    })
}

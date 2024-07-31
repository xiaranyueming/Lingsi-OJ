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
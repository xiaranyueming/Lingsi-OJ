import Request from "@/utils/Request.js";


// 分页获取问题列表
export const getQuestionListApi = (data) => {
    return Request({
        url: '/question/page/list',
        method: 'post',
        data: data
    })
}
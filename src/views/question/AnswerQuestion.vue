<script setup>
import {onMounted, ref} from "vue";
import CodeEditor from "@/components/CodeEditor.vue";
import {useRoute} from "vue-router";
import {getQuestionDetailToUserApi} from "@/apis/question.js";
import {questionSubmitApi, getQuestionSubmitDetailApi} from "@/apis/questionSubmit.js";
import MDViewer from "@/components/MDViewer.vue";
import {useUserStore} from "@/stores/user.js";
import {notification} from "ant-design-vue";

const userStore = useUserStore()
const route = useRoute()

const value = ref('')
const handleChange = (v) => {
  value.value = v
  questionSubmit.value.code = v
}

const activeKey = ref('question')

// 获取题目详情
const id = route.params.id
const questionDetail = ref({})
const getQuestionDetail = async () => {
  const res = await getQuestionDetailToUserApi(id)
  if (res.code === 200) {
    questionDetail.value = res.data
  }
}


// 题目提交的参数
const questionSubmit = ref({
  code: '',
  questionId: id,
  userId: userStore.getUser.id,
  language: ''
})
const getLanguage = (v) => {
  questionSubmit.value.language = v
}
// 提交
const submit = async () => {
  const res = await questionSubmitApi(questionSubmit.value)
  if (res.code === 200) {
    notification.success({
      message: '提交成功',
      description: '请等待判题结果'
    })
    questionSubmit.value.code = ''
  } else {
    notification.error({
      message: '提交失败',
      description: res.message
    })
  }
}


const type = route.query.type

// 获取题目提交详情
const getQuestionSubmitDetail = async () => {
  const res = await getQuestionSubmitDetailApi(id)
  if (res.code === 200) {
    questionDetail.value = res.data
  } else {
    notification.error({
      message: '获取题目提交详情失败',
      description: res.message
    })
  }
}


onMounted(() => {
  if (type === 'answer') {
    getQuestionDetail()
  } else if (type === 'submit') {
    getQuestionSubmitDetail()
  }
})
</script>

<template>
  <div class="answerQuestion">
    <a-row>
      <a-col :span="12">
        <div class="info">
          <a-tabs v-model:activeKey="activeKey">
            <a-tab-pane key="question" tab="题目">
              <a-card :title="questionDetail.title" v-if="type === 'answer'">
                <template #extra>
                  <a-tag color="orange" v-for="item in questionDetail.tags">{{item}}</a-tag>
                </template>
                <a-descriptions title="判题配置" :column="{xs: 1, sm: 2, md: 3}">
                  <a-descriptions-item label="时间消耗">{{questionDetail.judgeConfig?.timeLimit}}</a-descriptions-item>
                  <a-descriptions-item label="内存消耗">{{questionDetail.judgeConfig?.memoryLimit}}</a-descriptions-item>
                  <a-descriptions-item label="堆栈消耗">{{questionDetail.judgeConfig?.stackLimit}}</a-descriptions-item>
                </a-descriptions>
                <m-d-viewer :value="questionDetail.content" />
              </a-card>
              <a-card :title="questionDetail.adminQuestionDetail?.title" v-else>
                <template #extra>
                  <a-tag color="orange" v-for="item in questionDetail.adminQuestionDetail?.tags">{{item}}</a-tag>
                </template>
                <a-descriptions title="判题配置" :column="{xs: 1, sm: 2, md: 3}">
                  <a-descriptions-item label="时间消耗">{{questionDetail.adminQuestionDetail?.judgeConfig?.timeLimit}}</a-descriptions-item>
                  <a-descriptions-item label="内存消耗">{{questionDetail.adminQuestionDetail?.judgeConfig?.memoryLimit}}</a-descriptions-item>
                  <a-descriptions-item label="堆栈消耗">{{questionDetail.adminQuestionDetail?.judgeConfig?.stackLimit}}</a-descriptions-item>
                </a-descriptions>
                <div style="margin-bottom: 20px">
                  <m-d-viewer :value="questionDetail.adminQuestionDetail?.content" />
                </div>
                <div style="margin-bottom: 20px">
                  <a-tag v-if="questionDetail.status === 0" color="orange">未提交</a-tag>
                </div>
                <a-descriptions title="判题信息" :column="{xs: 1, sm: 2, md: 3}">
                  <a-descriptions-item label="时间消耗">{{questionDetail.judgeInfo?.time}}</a-descriptions-item>
                  <a-descriptions-item label="内存消耗">{{questionDetail.judgeInfo?.memory}}</a-descriptions-item>
                  <a-descriptions-item label="信息">{{questionDetail.judgeInfo?.message}}</a-descriptions-item>
                </a-descriptions>
                <div v-if="questionDetail.userVO" style="margin-top: 45px">
                  <img :src="questionDetail.userVO?.avatar" alt="头像" style="width: 60px;border-radius: 15%" >
                  <span style="margin-left: 15px">{{questionDetail.userVO?.userName}}</span>
                </div>
              </a-card>
            </a-tab-pane>
            <a-tab-pane key="answer" tab="答案" force-render></a-tab-pane>
            <a-tab-pane key="comment" tab="评论"></a-tab-pane>
          </a-tabs>
        </div>
      </a-col>
      <a-col :span="12">
        <div class="code">
          <code-editor v-if="type === 'answer'"
                       :value="value" :language="''"
                       @handleChange="handleChange"
                       @getLanguage="getLanguage"
                       :onlyRead="false"
          />
          <code-editor v-else :language="questionDetail.language"
                       :value="questionDetail.code"
                       @handleChange="handleChange"
                       @getLanguage="getLanguage"
                       :onlyRead="true"
          />
          <a-button ghost type="primary" style="margin-top: 20px; margin-left: 300px; width: 120px" @click="submit">提交</a-button>
        </div>
      </a-col>
    </a-row>
  </div>
</template>

<style scoped>
</style>
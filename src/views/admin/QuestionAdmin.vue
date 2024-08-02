<script setup>
import {onMounted, ref} from "vue";
import { getQuestionListApi } from "@/apis/question.js";
import {getQuestionDetailApi, deleteQuestionApi} from "@/apis/question.js";
import {notification} from "ant-design-vue";
import MDViewer from "@/components/MDViewer.vue";


const columns = [
  {
    title: '序号',
    dataIndex: 'id',
    key: 'id',
    width: 80,
    align: 'center'
  },
  {
    title: '题目',
    dataIndex: 'title',
    key: 'title',
    width: 1000,
    align: 'center'
  },
  {
    title: '分类',
    dataIndex: 'tags',
    key: 'tags',
    align: 'center'
  },
  {
    title: '通过数',
    dataIndex: 'acceptNum',
    key: 'acceptNum',
    width: 100,
    align: 'center'
  },
  {
    title: '提交数',
    dataIndex: 'submitNum',
    key: 'submitNum',
    width: 100,
    align: 'center'
  },
  {
    title: "操作",
    key: "action",
    align: 'center'
  }
];
const data = ref([])
// 分页信息
const page = ref({
  pageNum: 1,
  pageSize: 10,
  keyword: '',
  questionIndex: null
})
// 获取题目数据
const getQuestionList = async () => {
  const res = await getQuestionListApi(page.value)
  if (res.code === 200) {
    data.value = res.data
  }
}
// 重置
const reset = async () => {
  page.value = {
    pageNum: 1,
    pageSize: 10,
    keyword: '',
    questionIndex: null
  }
  await getQuestionList()
}
// 搜索
const search = async () => {
  await getQuestionList()
}

// 遮罩层
const open = ref(false)
const lookQuestionDetail = async (id) => {
  open.value = true
  await getQuestionDetail(id)
}
// 获取题目详情
const questionDetail = ref({})
const getQuestionDetail = async (id) => {
  const res = await getQuestionDetailApi(id)
  if (res.code === 200) {
    questionDetail.value = res.data
  } else {
    notification.error({
      message: '获取题目详情失败',
      description: res.message,
    });
  }
}

// 删除题目
const confirm = async (e, id) => {
  const res = await deleteQuestionApi(id)
  if (res.code === 200) {
    notification.success({
      message: '删除成功',
      description: res.message,
    });
    await getQuestionList()
  } else {
    notification.error({
      message: '删除失败',
      description: res.message,
    });
  }
}


onMounted(() => {
  getQuestionList()
})
</script>

<template>
  <div class="search">
    <a-input-number class="inputNumber" v-model:value="page.questionIndex" placeholder="请输入题目序号" :min="1" />
    <a-input class="keywordInput" v-model:value="page.keyword" placeholder="请输入搜索词" allow-clear />
    <a-button ghost class="search-btn" type="primary" @click="search">搜索</a-button>
    <a-button ghost class="reset-btn" type="primary" @click="reset">重置</a-button>
  </div>
  <a-table :columns="columns" :data-source="data">
    <template #bodyCell="{ column, record }">
      <template v-if="column.key === 'tags'">
        <a-tag color="orange" v-for="item in record.tags">
          {{ item }}
        </a-tag>
      </template>
      <template v-else-if="column.key === 'action'">
        <a-space>
          <a-button ghost type="primary" @click="lookQuestionDetail(record.id)">查看</a-button>
          <a-popconfirm title="确定删除该题目吗？"
              ok-text="确定" cancel-text="取消"
              @confirm="confirm(event, record.id)"
          >
            <a-button ghost type="primary">删除</a-button>
          </a-popconfirm>
        </a-space>
      </template>
    </template>
  </a-table>

<!--  查看题目详情遮罩层-->
  <a-drawer width="600px"
      v-model:open="open"
      class="custom-class"
      root-class-name="root-class-name"
      :root-style="{ color: '#0078D4' }"
      title="题目详情"
      placement="right"
  >
    <div style="font-size: 25px; font-weight: bold; margin-bottom: 20px;text-align: center">{{ questionDetail.title }}</div>
    <div style="margin-left: 100px">
      <a-space direction="vertical">
        <MDViewer :value="questionDetail.content" />
        <MDViewer :value="questionDetail.answer" />
      </a-space>
    </div>
    <div style="margin-top: 20px; margin-left: 100px">
      <a-tag v-for="item in questionDetail.tags" color="orange">{{ item }}</a-tag>
    </div>
    <div style="margin-top: 20px; margin-left: 100px">
      <a-space :size="30" v-for="item in questionDetail.judgeCase">
        <span>输入用例：{{ item.input }}</span>
        <span>输出用例：{{ item.output }}</span>
      </a-space>
    </div>
    <div style="margin-top: 20px; margin-left: 100px">
      <a-space direction="vertical">
        <div>时间限制：{{questionDetail.judgeConfig?.timeLimit}}ms</div>
        <div>内存限制：{{questionDetail.judgeConfig?.memoryLimit}}ms</div>
        <div>堆栈限制：{{questionDetail.judgeConfig?.stackLimit}}ms</div>
      </a-space>
    </div>

  </a-drawer>
</template>

<style scoped>
.search {
  display: flex;
  align-content: center;
  margin-bottom: 15px;
  .inputNumber {
    width: 150px;
    margin-right: 30px;
  }
  .keywordInput {
    width: 150px;
    margin-right: 50px;
  }
  .search-btn {
    margin-right: 30px;
  }
}
</style>
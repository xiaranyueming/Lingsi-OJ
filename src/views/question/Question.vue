<script setup>
import {onMounted, ref} from "vue";
import { getQuestionListApi } from "@/apis/question.js";
import {useUserStore} from "@/stores/user.js";
import {RoleEnum} from "@/utils/RoleEnum.js";

const userStore = useUserStore()

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

// 发布题目
const publishQuestion = () => {
  
}

onMounted(() => {
  getQuestionList()
})
</script>

<template>
  <div class="search">
    <a-input-number class="inputNumber" v-model:value="page.questionIndex" placeholder="请输入题目序号" :min="1" />
    <a-input class="keywordInput" v-model:value="page.keyword" placeholder="请输入搜索词" />
    <a-button ghost class="search-btn" type="primary" @click="search">搜索</a-button>
    <a-button ghost class="reset-btn" type="primary" @click="reset">重置</a-button>
    <a-button ghost class="pub-btn" type="primary" @click="publishQuestion" :disabled="userStore.getUser.role !== RoleEnum.ADMIN">发布题目</a-button>
  </div>
  <a-table :columns="columns" :data-source="data">
    <template #bodyCell="{ column, record }">
      <template v-if="column.key === 'tags'">
        <a-tag color="orange" v-for="item in record.tags">
          {{ item }}
        </a-tag>
      </template>
      <template v-else-if="column.key === 'action'">
        <a-button ghost type="primary">答题</a-button>
      </template>
    </template>
  </a-table>
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
  .pub-btn {
    position: absolute;
    top: 97px;
    right: 200px;
  }
}
</style>
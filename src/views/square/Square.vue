<script setup>
import {onMounted, ref} from "vue";
import {getLanguageListApi, getQuestionSubmitListApi} from "@/apis/questionSubmit.js";

const page = ref({
  questionIndex: null,
  pageNum: 1,
  pageSize: 10,
  language: 'java'
})

// 获取语言列表
const languageList = ref([])
const getLanguageList = async () => {
  const res = await getLanguageListApi()
  if (res.code === 200) {
    languageList.value = res.data
  }
}
const languageChange = (value) => {
  page.value.language = value
}

const search = async () => {
  await getQuestionSubmitList()
}
// 重置
const reset = async () => {
  page.value = {
    questionIndex: null,
    pageNum: 1,
    pageSize: 10,
    language: 'java'
  }
  await getQuestionSubmitList()
}


const columns = [
  {
    title: '题目序号',
    customRender: ({ record }) => {
      return record.questionVO?.id
    },
    key: 'id',
    width: 100,
    align: 'center'
  },
  {
    title: '题目',
    customRender: ({ record }) => {
      return record.questionVO?.title
    },
    key: 'title',
    width: 600,
    align: 'center'
  },
  {
    title: '标签',
    key: 'tags',
    width: 200,
    align: 'center'
  },
  {
    title: '语言',
    dataIndex: 'language',
    key: 'language',
    width: 80,
    align: 'center'
  },
  {
    title: '通过数',
    customRender: ({ record }) => {
      return record.questionVO?.acceptNum
    },
    key: 'acceptNum',
    width: 100,
    align: 'center'
  },
  {
    title: '提交数',
    customRender: ({ record }) => {
      return record.questionVO?.submitNum
    },
    key: 'submitNum',
    width: 100,
    align: 'center'
  },
  {
    title: "操作",
    key: "action",
    align: 'center'
  },
  {
    title: '提交者',
    key: 'info',
    width: 150
  }
];
// 获取题目提交数据
const data = ref([])
const getQuestionSubmitList = async () => {
  const res = await getQuestionSubmitListApi(page.value)
  if (res.code === 200) {
    data.value = res.data
  }
}



onMounted(() => {
  getLanguageList()
  getQuestionSubmitList()
})
</script>

<template>
  <div class="square">
    <div class="search">
      <a-input-number class="inputNumber" v-model:value="page.questionIndex" placeholder="请输入题目序号" :min="1" />
      <a-select
          ref="select"
          v-model:value="page.language"
          style="width: 120px"
          @change="languageChange"
      >
        <a-select-option v-for="item in languageList" :value="item">{{ item }}</a-select-option>
      </a-select>
      <a-button ghost class="search-btn" type="primary" @click="search">搜索</a-button>
      <a-button ghost class="reset-btn" type="primary" @click="reset">重置</a-button>
    </div>

    <a-table :columns="columns" :data-source="data">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'tags'">
          <a-tag color="orange" v-for="item in record.questionVO?.tags">
            {{ item }}
          </a-tag>
        </template>
        <template v-else-if="column.key === 'action'">
          <a-button ghost type="primary">查看</a-button>
        </template>
        <template v-else-if="column.key === 'info'">
          <img :src="record.userVO.avatar" alt="头像" style="width: 35px; margin-right: 10px; border-radius: 8px">
          <span>{{ record.userVO.userName }}</span>
        </template>
      </template>
    </a-table>
  </div>
</template>

<style scoped>
.square {
  padding: 5px 10px;
  .search {
    display: flex;
    align-content: center;
    margin-bottom: 20px;
    .inputNumber {
      width: 150px;
      margin-right: 25px;
    }
    .search-btn {
      margin-left: 20px;
    }
    .reset-btn {
      margin-left: 20px;
    }
  }
}
</style>
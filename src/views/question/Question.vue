<script setup>
import {onMounted, ref} from "vue";
import { getQuestionListApi } from "@/apis/question.js";

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
    title: "操作",
    key: "action",
    align: 'center'
  }
];
const data = ref([])
const page = ref({
  pageNum: 1,
  pageSize: 10,
  keyword: ''
})
const getQuestionList = async () => {
  const res = await getQuestionListApi(page.value)
  if (res.code === 200) {
    data.value = res.data
  }
}

onMounted(() => {
  getQuestionList()
})
</script>

<template>
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

</style>
<script setup>
import {ref} from "vue";
import CodeEditor from "@/components/CodeEditor.vue";

const value = ref('')
const handleChange = (v) => {
  value.value = v
}

// 提交
const submit = () => {
  console.log(value.value)
}
</script>

<template>
  <div>
    <code-editor :value="value" @handleChange="handleChange" />
    <a-button ghost type="primary" style="margin-top: 20px; margin-left: 300px; width: 120px" @click="submit">提交</a-button>
  </div>

</template>

<style scoped>

</style>
<script setup>
import * as monaco from 'monaco-editor';
import {onMounted, ref, watch} from "vue";
import {getLanguageListApi} from "@/apis/questionSubmit.js";

self.MonacoEnvironment = {
  getWorker: function (workerId, label) {
    const getWorkerModule = (moduleUrl, label) => {
      return new Worker(self.MonacoEnvironment.getWorkerUrl(moduleUrl), {
        name: label,
        type: 'module'
      });
    };

    switch (label) {
      case 'json':
        return getWorkerModule('/monaco-editor/esm/vs/language/json/json.worker?worker', label);
      case 'css':
      case 'scss':
      case 'less':
        return getWorkerModule('/monaco-editor/esm/vs/language/css/css.worker?worker', label);
      case 'html':
      case 'handlebars':
      case 'razor':
        return getWorkerModule('/monaco-editor/esm/vs/language/html/html.worker?worker', label);
      case 'typescript':
      case 'javascript':
        return getWorkerModule('/monaco-editor/esm/vs/language/typescript/ts.worker?worker', label);
      default:
        return getWorkerModule('/monaco-editor/esm/vs/editor/editor.worker?worker', label);
    }
  }
};

const props = defineProps({
  value: String
})
const emit = defineEmits(['handleChange'])

// 获取语言列表
const languageList = ref([])
const getLanguageList = async () => {
  const res = await getLanguageListApi()
  if (res.code === 200) {
    languageList.value = res.data
  }
}
const languageHandleChange = (value) => {
  options.value.language = value
}
// 主题列表
const theme = ref('vs-dark')
const themeList = ref([
  'vs',
  'vs-dark',
  'hc-black'
])
const themeHandleChange = (value) => {
  options.value.theme = value
}

// 代码编辑器配置
const language = ref('java')
const options = ref({
  value: props.value,
  language: language.value,
  theme: theme.value,
  selectOnLineNumbers: true,
  minimap: {
    enabled: true
  }
})
// 监听配置变化，更新编辑器
watch(options, (value) => {
  monaco.editor.getModels()[0].setValue(value.value)
  monaco.editor.setModelLanguage(monaco.editor.getModels()[0], value.language)
  monaco.editor.setTheme(value.theme)
}, {deep: true})

onMounted(() => {
  getLanguageList()
  monaco.editor.create(document.getElementById('container'), {
    ...options.value
  });
  monaco.editor.getModels()[0].onDidChangeContent(() => {
    emit('handleChange', monaco.editor.getModels()[0].getValue())
  })
})
</script>

<template>
  <div style="margin-bottom: 15px">
    <a-select
        ref="select"
        v-model:value="language"
        style="width: 120px;margin-right: 50px; margin-left: 200px"
        @change="languageHandleChange"
    >
      <a-select-option v-for="item in languageList" :value="item">{{item}}</a-select-option>
    </a-select>
    <a-select
        ref="select"
        v-model:value="theme"
        style="width: 120px"
        @change="themeHandleChange"
    >
      <a-select-option v-for="item in themeList" :value="item">{{item}}</a-select-option>
    </a-select>
  </div>
  <div id="container" style="width: 800px; height: 600px;"></div>
</template>

<style scoped>

</style>
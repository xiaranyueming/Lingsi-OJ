<script setup>
import {ref} from "vue";
import MDEditor from "@/components/MDEditor.vue";
import { notification } from "ant-design-vue";
import { addQuestionApi } from "@/apis/question.js";
import {useUserStore} from "@/stores/user.js";
import { MinusCircleOutlined, PlusOutlined } from "@ant-design/icons-vue";

const userStore = useUserStore()

const form = ref({
  title: '',
  content: '',
  tags: [],
  answer: '',
  judgeCase: [],
  judgeConfig: {
    timeLimit: 1000,
    memoryLimit: 1024,
    stackLimit: 1024,
  },
  userId: userStore.getUser.id
})
const contentHandleChange = (v) => {
  form.value.content = v
}
const answerHandleChange = (v) => {
  form.value.answer = v
}

// 标签样式
const formItemLayout = {
  labelCol: {
    xs: {
      span: 20,
    },
    sm: {
      span: 4,
    },
  },
  wrapperCol: {
    xs: {
      span: 12,
    },
    sm: {
      span: 20,
    },
  },
};
const formItemLayoutWithOutLabel = {
  wrapperCol: {
    xs: {
      span: 20,
      offset: 4,
    },
    sm: {
      span: 20,
      offset: 0,
    },
  },
};
// 从tags数组中删除一个对象
const removeTag = item => {
  const index = form.value.tags.indexOf(item);
  if (index !== -1) {
    form.value.tags.splice(index, 1);
  }
};
// 向tags数组中添加一个对象
const addTag = () => {
  form.value.tags.push({
    value: '',
    key: Date.now(),
  });
};


// 判题用例
const removeCase = item => {
  const index = form.value.judgeCase.indexOf(item);
  if (index !== -1) {
    form.value.judgeCase.splice(index, 1);
  }
};
const addCase = () => {
  form.value.judgeCase.push({
    input: '',
    output: '',
  });
};


// 提交
const onFinish = async () => {
  if (form.value.tags.length === 0) {
    notification.error({
      message: '标签不能为空',
      description: '请至少输入一个标签',
    });
    return
  }
  if (form.value.judgeCase.length === 0) {
    notification.error({
      message: '判题用例不能为空',
      description: '请至少输入一个判题用例',
    });
    return
  }
  form.value.tags = form.value.tags.map(item => {
    return item.value
  })
  const res = await addQuestionApi(form.value)
  if (res.code === 200) {
    notification.success({
      message: '发布成功',
      description: '题目发布成功',
    });
  } else {
    notification.error({
      message: '发布失败',
      description: res.message,
    });
  }
  // 重置表单
  form.value = {
    title: '',
    content: '',
    tags: [],
    answer: '',
    judgeCase: [],
    judgeConfig: {
      timeLimit: 1000,
      memoryLimit: 1024,
      stackLimit: 1024,
    },
    userId: userStore.getUser.id
  }
}
</script>

<template>
  <div class="publishQuestion">
    <a-form
        :model="form"
        name="basic"
        :label-col="{ span: 2 }"
        :wrapper-col="{ span: 16 }"
        autocomplete="off"
        @finish="onFinish"
    >
<!--      题目-->
      <a-form-item
          label="题目"
          name="title"
          :rules="[{ required: true, message: '请输入题目' }]"
      >
        <a-input v-model:value="form.title" />
      </a-form-item>

<!--      内容-->
      <a-form-item
          label="内容"
          name="content"
          :rules="[{ required: true, message: '请输入内容' }]"
      >
        <m-d-editor :value="form.content" :handle-change="contentHandleChange"/>
      </a-form-item>

<!--      答案-->
      <a-form-item
          label="答案"
          name="answer"
          :rules="[{ required: true, message: '请输入答案' }]"
      >
        <m-d-editor :value="form.answer" :handle-change="answerHandleChange"/>
      </a-form-item>

<!--      标签-->
      <a-form-item style="margin-left: -100px"
          v-for="(item, index) in form.tags"
          :key="item.key"
          v-bind="index === 0 ? formItemLayout : formItemLayoutWithOutLabel"
          :label="index === 0 ? '标签' : ''"
          :name="['tags', index, 'value']"
          :rules="{ required: true, message: '请输入标签', trigger: 'change' }"
      >
        <a-input
            v-model:value="item.value"
            placeholder="请输入标签"
            style="width: 60%; margin-right: 8px"
        />
        <MinusCircleOutlined
            v-if="form.tags.length > 1"
            class="dynamic-delete-button"
            @click="removeTag(item)"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayoutWithOutLabel" style="margin-left: -55px">
        <a-button type="dashed" style="width: 57%" @click="addTag">
          <PlusOutlined />
          添加标签
        </a-button>
      </a-form-item>

<!--      判题用例-->
      <a-space v-for="(item, index) in form.judgeCase"
          :key="item.id"
          style="display: flex; width: 600px;margin-left: 110px"
          align="baseline"
      >
        <span v-if="form.judgeCase.length > 0" style="margin-left: -82px">判题用例-{{ index + 1 }}：</span>
        <a-form-item style="width: 330px"
            :name="['judgeCase', index, 'input']"
            :rules="{required: true,message: '请输入输入用例'}"
        >
          <a-input v-model:value="item.input" placeholder="输入用例" />
        </a-form-item>
        <a-form-item style="width: 330px; margin-left: -60px"
            :name="['judgeCase', index, 'output']"
            :rules="{required: true,message: '请输入输出用例'}"
        >
          <a-input v-model:value="item.output" placeholder="输出用例" />
        </a-form-item>
        <MinusCircleOutlined style="margin-left: -80px" @click="removeCase(item)" />
      </a-space>
      <a-form-item style="width: 760px; margin-left: 120px">
        <a-button type="dashed" block @click="addCase">
          <PlusOutlined />
          添加用例
        </a-button>
      </a-form-item>

<!--      判题配置-耗时-->
      <a-form-item
          style="margin-left: 20px"
          label="时间限制"
      >
        <a-input-number style="width: 200px" v-model:value="form.judgeConfig.timeLimit" :min="1" />
      </a-form-item>

<!--      判题配置-内存-->
      <a-form-item
          style="margin-left: 20px"
          label="内存限制"
      >
        <a-input-number style="width: 200px" v-model:value="form.judgeConfig.memoryLimit" :min="1" />
      </a-form-item>

<!--      判题配置-堆栈-->
      <a-form-item
          style="margin-left: 20px"
          label="堆栈限制"
      >
        <a-input-number style="width: 200px" v-model:value="form.judgeConfig.stackLimit" :min="1" />
      </a-form-item>

      <a-form-item :wrapper-col="{ offset: 5, span: 16 }">
        <a-button style="width: 400px; height: 50px" type="primary" html-type="submit">发布</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<style scoped>
.publishQuestion {
  width: 1000px;
  margin: 0 auto;
}
.dynamic-delete-button {
  cursor: pointer;
  position: relative;
  top: 4px;
  font-size: 24px;
  color: #999;
  transition: all 0.3s;
}
.dynamic-delete-button:hover {
  color: #777;
}
.dynamic-delete-button[disabled] {
  cursor: not-allowed;
  opacity: 0.5;
}
</style>
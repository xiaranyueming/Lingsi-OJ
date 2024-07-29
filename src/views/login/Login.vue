<script setup>
import {ref} from "vue";
import { loginApi } from "@/apis/user.js";
import { useRouter } from "vue-router";
import {useUserStore} from "@/stores/user.js";
import { notification } from "ant-design-vue";

const router = useRouter();
const userStore = useUserStore();

const formState = ref({
  userName: '',
  password: '',
});

// 登录
const onFinish = async () => {
  const res = await loginApi(formState.value);
  if (res.code === 200) {
    notification.success({
      message: '登录成功',
      description: '欢迎回来！',
    });
    userStore.setUser(res.data);
    userStore.setLogin();
    await router.push('/');
  } else {
    notification.error({
      message: '登录失败',
      description: res.message,
    });
  }
};
</script>

<template>
  <div class="login">
    <div>
      <img src="@/images/logo.jpg" class="login-img" alt="灵思-OJ">
    </div>
    <div>
      <a-form :style="{width: '400px'}"
          :model="formState"
          name="basic"
          :label-col="{ span: 8 }"
          :wrapper-col="{ span: 25 }"
          autocomplete="off"
          @finish="onFinish"
      >
        <a-form-item
            label="用户名"
            name="userName"
            :rules="[{ required: true, message: '请输入用户名！' }]"
        >
          <a-input v-model:value="formState.userName" />
        </a-form-item>

        <a-form-item
            label="密码"
            name="password"
            :rules="[{ required: true, message: '请输出密码!' }]"
        >
          <a-input-password v-model:value="formState.password" />
        </a-form-item>

        <div class="to-register">
          <RouterLink to="/register">还没有账号？立即注册</RouterLink>
        </div>

        <a-form-item :wrapper-col="{ offset: 12, span: 20 }">
          <a-button type="primary" html-type="submit" :style="{width: '100px'}">登录</a-button>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>

<style scoped>
.login {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 150px;
  .login-img {
    width: 300px;
    margin-left: 100px;
  }
  .to-register {
    margin-left: 255px;
    margin-bottom: 10px;
  }
}
</style>
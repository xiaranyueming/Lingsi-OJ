<script setup>
import { ref, onMounted } from "vue";
import {useUserStore} from "@/stores/user.js";
import { DownOutlined } from "@ant-design/icons-vue";
import { notification } from "ant-design-vue";
import {changePasswordApi, getUserInfoApi, logoutApi} from "@/apis/user.js";
import { useRouter } from "vue-router";

const userStore = useUserStore();
const userInfo = ref({});
const router = useRouter();

// 获取用户信息
const getUserInfo = async () => {
  userInfo.value = userStore.getUser
};


// 修改密码
const password_open = ref(false);
const passwordForm = ref({
  userId: userStore.getUser.id,
  oldPassword: "",
  newPassword: "",
  confirmPassword: "",
});
const updatePassword = () => {
  password_open.value = true;
};
// 数据重置
const reset = () => {
  passwordForm.value = {
    ...passwordForm,
    oldPassword: "",
    newPassword: "",
    confirmPassword: "",
  };
}
// 保存
const onFinish = async () => {
  if (passwordForm.value.newPassword !== passwordForm.value.confirmPassword) {
    notification.error({
      message: "错误",
      description: "两次输入的密码不一致",
    });
    return
  }
  const res = await changePasswordApi(passwordForm.value);
  // const res = await getUserInfoApi(userStore.getUser.id)
  if (res.code === 200) {
    notification.success({
      message: "成功",
      description: "修改密码成功",
    });
    password_open.value = false;
    userStore.clear()
    await router.push("/login");
  } else {
    notification.error({
      message: "错误",
      description: res.message,
    });
  }
}


// 退出登录
const logout = async () => {
  const res = await logoutApi();
  if (res.code === 200) {
    notification.success({
      message: "成功",
      description: "请重新登录",
    });
  } else {
    notification.error({
      message: "错误",
      description: res.message,
    });
  }
  userStore.clear()
  await router.push("/login");
}

onMounted(() => {
  getUserInfo();
});
</script>

<template>
  <div class="my">
    <div class="my-info">
      <img :src="userInfo.avatar" alt="头像" />
      <span class="name">{{ userInfo.userName }}</span>
      <a-dropdown>
        <span class="dropdown" @click.prevent>
          click me <DownOutlined style="font-size: 15px; margin-left: 5px"/>
        </span>
        <template #overlay>
          <a-menu>
            <a-menu-item>
              <span @click="editUserInfo">编辑信息</span>
            </a-menu-item>
            <a-menu-item>
              <span @click="updatePassword">修改密码</span>
            </a-menu-item>
            <a-menu-item>
              <span @click="logout">退出登录</span>
            </a-menu-item>
          </a-menu>
        </template>
      </a-dropdown>
    </div>
    <div class="description">
      <a-textarea v-model:value="userInfo.description" placeholder="个人简介" :disabled="true" />
    </div>
  </div>


<!--  修改密码遮罩层-->
  <a-drawer width="500px"
      v-model:open="password_open"
      class="custom-class"
      root-class-name="root-class-name"
      :root-style="{ color: 'blue' }"
      title="修改密码"
      placement="right"
  >
    <a-form
        :model="passwordForm"
        name="basic"
        :label-col="{ span: 8 }"
        :wrapper-col="{ span: 16 }"
        autocomplete="off"
        @finish="onFinish"
    >
      <a-form-item
          label="原始密码"
          name="oldPassword"
          :rules="[{ required: true, message: '请输入原始密码' }]"
      >
        <a-input-password v-model:value="passwordForm.oldPassword" allow-clear />
      </a-form-item>
      <a-form-item
          label="新密码"
          name="newPassword"
          :rules="[{ required: true, message: '请输入新密码' }]"
      >
        <a-input-password v-model:value="passwordForm.newPassword" allow-clear />
      </a-form-item>
      <a-form-item
          label="确认密码"
          name="confirmPassword"
          :rules="[{ required: true, message: '请再次输入新密码' }]"
      >
        <a-input-password v-model:value="passwordForm.confirmPassword" allow-clear />
      </a-form-item>
      <a-form-item :wrapper-col="{ offset: 11, span: 20 }">
        <a-space size="middle">
          <a-button type="primary" html-type="submit">保存</a-button>
          <a-button type="primary" @click="reset">重置</a-button>
        </a-space>
      </a-form-item>
    </a-form>
  </a-drawer>
</template>

<style scoped>
.my {
  padding: 20px;
  text-align: center;
  .my-info {
    height: 100px;
    img {
      width: 60px;
      height: 60px;
    }
    .name {
      display: inline-block;
      height: 100%;
      line-height: 100px;
      margin-left: 50px;
      font-size: 20px;
      font-weight: bold;
    }
    .dropdown {
      margin-left: 15px;
      color: #6e76ff;
    }
  }
  .description {
    display: inline-block;
    width: 800px;
    margin-top: 25px;
  }
}
</style>
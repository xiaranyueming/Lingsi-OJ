<script setup>
import {computed, ref, watch} from "vue";
import { useRouter, useRoute } from "vue-router";
import router from "@/router";
import {useUserStore} from "@/stores/user.js";
import {checkAccess} from "@/utils/CheckUtil.js";
import {logoutApi} from "@/apis/user.js";
import {notification} from "ant-design-vue";

const rou = useRouter();
const route = useRoute();
const userStore = useUserStore();

// 选中的菜单
const selectedKeys = ref([]);
watch(() => route.path, (val) => {
  selectedKeys.value.push(val);
});
// 点击菜单跳转
const menuSelect = (path) => {
  rou.push(path);
};

// 过滤出路由中的菜单
const showMenu = computed(() => {
  return router.options.routes.filter(item => {
    // 需要隐藏的
    if (item.meta?.hidden) {
      return false;
    }
    // 需要权限的
    if (checkAccess(userStore.user, item.meta.role)) {
      return true;
    }
  });
});


// 跳转登录
const login = () => {
  rou.push("/login");
};

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
      description: "退出登录失败",
    });
  }
  userStore.clear();
  await rou.push("/login");
};

</script>

<template>
  <a-layout class="layout">
    <a-layout-header>
      <div class="logo">
        <img src="@/images/logo.jpg" alt="灵思OJ" style="width: 50px; height: 50px; border-radius: 15px">
      </div>
      <a-menu
          v-model:selectedKeys="selectedKeys"
          theme="dark"
          mode="horizontal"
          :style="{ lineHeight: '64px', marginLeft: '35px' }"
      >
        <a-menu-item v-for="item in showMenu" :key="item.path" @click="menuSelect(item.path)">{{ item.name }}</a-menu-item>
      </a-menu>
      <div class="info">
        <template v-if="userStore.isLogin">
          <div class="img">
            <img :src="userStore.getUser.avatar" alt="头像">
          </div>
        </template>
        <div class="name">
          {{ userStore.isLogin ? userStore.user.userName : "未登录" }}
          <template v-if="!userStore.isLogin">
            <a-button @click="login" style="margin-left: 10px" type="primary" ghost>登录</a-button>
          </template>
          <template v-else>
            <a-button class="btn" @click="logout" style="margin-left: 10px" type="primary" ghost>退出登录</a-button>
          </template>
        </div>
      </div>
    </a-layout-header>
    <a-layout-content style="padding: 10px 20px 0">
      <div :style="{ background: '#fff', padding: '24px', minHeight: '825px' }">
        <router-view />
      </div>
    </a-layout-content>
    <a-layout-footer style="text-align: center">
      ©2024 Created by Little Monster
    </a-layout-footer>
  </a-layout>
</template>

<style scoped>
.logo {
  position: absolute;
  top: 0;
  left: 30px;
}
.info {
  display: flex;
  align-items: center;
  position: relative;
  .img {
    position: absolute;
    top: -65px;
    right: 150px;
    width: 50px;
    img {
      width: 100%;
      border-radius: 50%;
    }
  }
  .name {
    position: absolute;
    top: -65px;
    right: 85px;
    color: #fff;
    font-size: 16px;
    font-weight: bold;
  }
  .btn {
    position: absolute;
    top: 17px;
  }
}
</style>
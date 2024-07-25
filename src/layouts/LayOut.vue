<script setup>
import {computed, ref} from "vue";
import { useRouter } from "vue-router";
import router from "@/router";
import {useUserStore} from "@/stores/user.js";
import {checkAccess} from "@/utils/CheckUtil.js";

const rou = useRouter();
const userStore = useUserStore();

const selectedKeys = ref([rou.currentRoute.value.path]);
// 点击菜单跳转
const menuSelect = (key) => {
  rou.push(key);
};

// 过滤出路由中的菜单
const showMenu = computed(() => {
  return router.options.routes.filter(item => {
    if (checkAccess(userStore.user, item.meta.role)) {
      return true;
    }
  });
});


// setTimeout(() => {
//   userStore.setLogin();
// }, 3000);

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
        {{ userStore.isLogin ? userStore.user.userName : "未登录" }}
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
  position: absolute;
  top: 0;
  right: 100px;
  color: #fff;
  font-size: 16px;
  font-weight: bold;
}
</style>
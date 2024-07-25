import { createRouter, createWebHistory } from 'vue-router'
import Topic from "@/views/topic/Topic.vue";
import My from "@/views/my/My.vue";
import Admin from "@/views/admin/Admin.vue";
import {RoleEnum} from "@/utils/RoleEnum.js";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: '题目',
      component: Topic,
      meta: {
        role: RoleEnum.NOT_LOGIN
      }
    },
    {
      path: '/my',
      name: '我的',
      component: My,
      meta: {
        role: RoleEnum.USER
      }
    },
    {
      path: '/admin',
      name: '管理',
      component: Admin,
      meta: {
        role: RoleEnum.ADMIN
      }
    }
  ]
})

export default router

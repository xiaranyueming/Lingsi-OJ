import { createRouter, createWebHistory } from 'vue-router'
import Topic from "@/views/topic/Topic.vue";
import My from "@/views/my/My.vue";
import Admin from "@/views/admin/Admin.vue";
import NoAuth from "@/components/NoAuth.vue";
import Login from "@/views/login/Login.vue";
import Register from "@/views/login/Register.vue";
import {RoleEnum} from "@/utils/RoleEnum.js";
import {useUserStore} from "@/stores/user.js";

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
      path: '/login',
      component: Login,
      meta: {
        hidden: true,
        role: RoleEnum.NOT_LOGIN
      }
    },
    {
      path: '/register',
      component: Register,
      meta: {
          hidden: true,
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
    },
    {
      path: '/NoAuth',
      name: '无权限',
      component: NoAuth,
      meta: {
        hidden: true,
        role: RoleEnum.NOT_LOGIN
      }
    }
  ]
})


router.beforeEach((to, from, next) => {
    const userStore = useUserStore()

    if (to.meta.role === RoleEnum.ADMIN) {
        if (userStore.getUser.role === RoleEnum.ADMIN) {
            next()
        } else {
          next('/NoAuth')
        }
    }
    if (to.meta.role === RoleEnum.USER) {
        if (userStore.getUser.role === RoleEnum.USER) {
            next()
        } else {
            next('/NoAuth')
        }
    }
    if (to.meta.role === RoleEnum.NOT_LOGIN) {
        next()
    }
})

export default router

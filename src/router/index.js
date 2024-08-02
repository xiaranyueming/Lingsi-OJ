import { createRouter, createWebHistory } from 'vue-router'
import Topic from "@/views/question/Question.vue";
import My from "@/views/my/My.vue";
import QuestionAdmin from "@/views/admin/QuestionAdmin.vue";
import NoAuth from "@/components/NoAuth.vue";
import Login from "@/views/login/Login.vue";
import Register from "@/views/login/Register.vue";
import Square from "@/views/square/Square.vue";
import AnswerQuestion from "@/views/question/AnswerQuestion.vue";
import PublishQuestion from "@/views/question/PublishQuestion.vue";
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
      path: '/answerQuestion',
      component: AnswerQuestion,
      meta: {
        hidden: true,
        role: RoleEnum.NOT_LOGIN
      }
    },
    {
      path: '/square',
      name: '广场',
      component: Square,
      meta: {
        role: RoleEnum.USER
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
      path: '/publishQuestion',
      name: '发布题目',
        component: PublishQuestion,
        meta: {
            role: RoleEnum.ADMIN
        }
    },
    {
      path: '/questionAdmin',
      name: '题目管理',
      component: QuestionAdmin,
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
        if (userStore.getUser.role === RoleEnum.USER || userStore.getUser.role === RoleEnum.ADMIN) {
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

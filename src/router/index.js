import { createRouter, createWebHistory } from 'vue-router'
import Topic from "@/views/topic/Topic.vue";
import My from "@/views/my/My.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: '题目',
      component: Topic
    },
    {
      path: '/my',
      name: '我的',
      component: My
    }
  ]
})

export default router

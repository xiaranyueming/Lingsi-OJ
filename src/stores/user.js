import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import {RoleEnum} from "@/utils/RoleEnum.js";

export const useUserStore = defineStore('user', () => {
  const user = ref({
    userName: "张三",
    role: RoleEnum.ADMIN
  })
  const isLogin = ref(false)

  const setUser = (newUser) => {
    user.value = newUser
  }

  const getUser = computed(() => user.value)

  const setLogin = () => {
    isLogin.value = true
  }

  return {
    user,
    setUser,
    getUser,
    isLogin,
    setLogin
  }
})

import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import {RoleEnum} from "@/utils/RoleEnum.js";

export const useUserStore = defineStore('user', () => {
  const user = ref({
    id: null,
    userName: "",
    avatar: "",
    description: "",
    role: RoleEnum.NOT_LOGIN
  })
  const isLogin = ref(false)

  const setUser = (newUser) => {
    user.value = newUser
  }

  const getUser = computed(() => user.value)

  const setLogin = () => {
    isLogin.value = true
  }

  const clear = () => {
    user.value = {
      id: null,
      userName: "",
      avatar: "",
      description: "",
      role: RoleEnum.NOT_LOGIN
    }
    isLogin.value = false
  }

  return {
    user,
    setUser,
    getUser,
    isLogin,
    setLogin,
    clear
  }
}, {
  persist: true
})

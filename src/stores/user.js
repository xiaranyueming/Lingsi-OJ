import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', () => {
  const user = ref({
    userName: "张三",
    role: "admin"
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

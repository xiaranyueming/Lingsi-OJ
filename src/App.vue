<script setup>
import LayOut from "@/layouts/LayOut.vue";
import { useRouter} from "vue-router";

const router = useRouter();
</script>

<template>
  {{    }}
  <div>
    <template v-if="router.currentRoute.value.path === '/login' ||
                    router.currentRoute.value.path === '/register'
">
      <router-view />
    </template>
    <template v-else>
      <lay-out />
    </template>
  </div>
</template>

<style scoped>
</style>

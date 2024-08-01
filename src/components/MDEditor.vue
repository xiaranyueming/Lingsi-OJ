<script setup>
import gfm from '@bytemd/plugin-gfm'
import highlight from "@bytemd/plugin-highlight";
import { Editor, Viewer } from '@bytemd/vue-next'

const plugins = [
  gfm(),
  highlight()
]

const props = defineProps({
  value: String,
  handleChange: Function
})
</script>

<template>
  <div class="md">
    <Editor class="edit" :value="props.value" :plugins="plugins" @change="props.handleChange" />
  </div>
</template>

<style scoped>

</style>
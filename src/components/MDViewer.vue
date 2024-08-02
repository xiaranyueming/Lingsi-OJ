<script setup>
import gfm from '@bytemd/plugin-gfm'
import highlight from "@bytemd/plugin-highlight";
import {Viewer} from '@bytemd/vue-next'

const plugins = [
  gfm(),
  highlight()
]

const props = defineProps({
  value: String,
})
</script>

<template>
  <div class="md">
    <Viewer class="edit" :value="props.value" :plugins="plugins" />
  </div>
</template>

<style scoped>

</style>
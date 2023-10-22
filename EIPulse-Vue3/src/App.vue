<template>
  <router-view />
</template>

<script setup>
import useSettingsStore from '@/store/modules/settings'
import { handleThemeStyle } from '@/utils/theme'

onMounted(() => {
  nextTick(() => {
       // 初始化主題樣式
    handleThemeStyle(useSettingsStore().theme)
  })
})
</script>

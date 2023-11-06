
<template>
  <div class="card  h-auto">
    <div class="card-header">
      <p>{{title}}</p>
      <slot name="findSearch"></slot>
    </div>
    <div class="card-body">
      <slot name="body"></slot>
    </div>
    <div class="card-footer text-body-secondary">
      <slot name="page"></slot>
    </div>
  </div>
</template>

<script setup>
import FindSearch from "./clocktime/FindSearch.vue";

const props=defineProps({
  title:{
    type:String,
    required:true,
    default:'功能表'
  },
})
</script>

<style scoped>

</style>
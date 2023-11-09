
<template>

  <div class="row">
    <div class="row w-25">
      <div class="form-floating ">
        <input v-model="startDate"   type="date"  class="form-control " id="startDate" >
        <label  for="startDate">起始時間</label>
      </div>
    </div>
    <div class="row w-25">
      <div class="form-floating  ">
        <input v-model="endDate" type="date" class="form-control " id="endDate" >
        <label for="endDate">結束時間</label>
      </div>
    </div>
    <button class="btn btn-secondary  w-25" @click="getSearchDate">查詢
    </button>
  </div>

</template>


<script setup>
import {ref} from "vue";
let date = new Date();
let formattedDate = date.toISOString().split('T')[0]; // 這將給你 "2023-10-20" 這樣的格式
const startDate = ref(formattedDate);
const endDate = ref(formattedDate);
const emit = defineEmits(['sendSearchDate'])
const getSearchDate=()=>{
  console.log(startDate.value)
  emit('sendSearchDate',{
    startDate:startDate.value,
    endDate:endDate.value,
  })
}
</script>

<style scoped>

</style>
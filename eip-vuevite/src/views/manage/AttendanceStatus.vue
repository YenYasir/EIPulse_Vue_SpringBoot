<script setup>

import Card from "../../components/Card.vue";
import FindSearch from "../../components/clocktime/FindSearch.vue";
import axios from "axios";
import {computed, reactive, ref} from "vue";
import Page from "../../components/Page.vue";
const URL = import.meta.env.VITE_API_JAVAURL;
const empAttendances = ref([]);
const totalPages = ref(0);
const currentPage = ref(1);
const currentSearchDate = ref(null); // 用於保存當前的搜索條件
const getDate = (date) => {
  currentSearchDate.value =date;
  let url =`${URL}attendance/${date.startDate}/${date.endDate}/${currentPage.value}`
  if(date.empId){
    url = `${URL}attendance/${date.empId}/${date.startDate}/${date.endDate}/${currentPage.value}`
  }
  axios.get(url).then((res) => {
    empAttendances.value = res.data.content;
    totalPages.value=res.data.totalPages;

  }).catch((e)=>{

  })
}
const getLightColor = (item) => {
  if (item.isLate || item.isEarlyLeave || item.isHoursNotMet || item.isOverTime ||item.totalHours==0) {
    return 'red';
  }
  return 'green';
};
const updateCurrentPage = (newPage) => {
  currentPage.value = newPage;
  getDate(currentSearchDate.value)
  // 這裡加載新頁面的數據
};

</script>

<template>
  <card title="出勤查詢" >
    <template #findSearch>
      <find-search @sendSearchDate="getDate"></find-search>
    </template>
    <template #body>
      <table class="table table-hover" >
        <thead>
        <tr>
          <th>員工編號</th>
          <th>員工姓名</th>
          <th>日期</th>
          <th>總時數</th>
          <th>狀態</th>
        </tr>
        </thead>
        <tbody  >
        <tr v-for="item in empAttendances" >
          <td>{{ item.empId }}</td>
          <td>{{ item.empName }}</td>
          <td>{{ item.date }}</td>
          <td>{{ item.totalHours }}</td>
          <td :style="{ backgroundColor: getLightColor(item), width: '20px', height: '20px' }"></td>
        </tr>
        </tbody>
      </table>
    </template>
    <template #page>
      <page :total-pages="totalPages" :current-page="currentPage" @select-page="updateCurrentPage"></page>
    </template>

  </card>

</template>
<style scoped>

</style>
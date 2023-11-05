
<template>
  <card title="打卡查詢">
    <template #findSearch>
      <find-search @sendSearchDate="getDate"></find-search>
    </template>
    <template #body>
      <table class="table table-hover">
        <thead>
        <tr>
          <th>員工編號</th>
          <th>員工姓名</th>
          <th>紀錄</th>
          <th>類別</th>
          <th>辦公室</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="item in getTime">
          <td>{{ item.empId }}</td>
          <td>{{ item.empName }}</td>
          <td>{{ item.time }}</td>
          <td>{{item.type}}</td>
          <td>{{ item.officeRegions }}</td>
        </tr>
        </tbody>
      </table>
    </template>
    <template #page>
      <page :total-pages="totalPages" :current-page="currentPage" @select-page="updateCurrentPage"></page>
    </template>
  </card>

</template>

<script setup>

import Card from "../../components/Card.vue";
import FindSearch from "../../components/clocktime/FindSearch.vue";
import axios from "axios";
import {ref, watch} from "vue";
import Page from "../../components/Page.vue";

const totalPages = ref(0);
const currentPage = ref(1);
const getTime = ref([]);
const currentSearchDate = ref(null); // 用於保存當前的搜索條件
const getDate = (date) => {
  currentSearchDate.value = date;
  let url = `http://localhost:8090/eipulse/clockTimes/${date.startDate}/${date.endDate}/${currentPage.value}`;
  if (date.empId) {
    url = `http://localhost:8090/eipulse/clockTimes/${date.empId}/${date.startDate}/${date.endDate}/${currentPage.value}`;
  }
  axios.get(url).then((res) => {
    getTime.value = res.data.content;
    totalPages.value = res.data.totalPages;

  }).catch((e) => {
  });
};

// watch(currentPage, (newPage) => {
//   if (currentSearchDate.value) {
//     getDate(currentSearchDate.value)
//   }
// })
const updateCurrentPage = (newPage) => {
  currentPage.value = newPage;
  getDate(currentSearchDate.value)
  // 這裡加載新頁面的數據
};

</script>

<style scoped>

</style>
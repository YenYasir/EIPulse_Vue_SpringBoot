<script setup>

import Card from "../../components/Card.vue";
import axios from "axios";
import {ref, watch} from "vue";
import Page from "../../components/Page.vue";
import UserFindSearch from "../../components/clocktime/UserFindSearch.vue";
import {empStore} from "../../stores/employee.js";
const URL = import.meta.env.VITE_API_JAVAURL;

const totalPages = ref(0);
const currentPage = ref(1);
const getTime = ref([]);
const emp = empStore()
const currentSearchDate = ref({
  startDate: null,
  endDate: null,
  empId: emp.empId // 從你的 empStore 獲取
});
const getDate = (date) => {
  currentSearchDate.value = date;
  axios.get(`${URL}clockTimes/${emp.empId}/${date.startDate}/${date.endDate}/${currentPage.value}`).then((res) => {
    getTime.value = res.data.content;
    totalPages.value = res.data.totalPages;
  }).catch((e) => {
  });
};

const updateCurrentPage = (newPage) => {
  currentPage.value = newPage;
  getDate(currentSearchDate.value)
  // 這裡加載新頁面的數據
};

</script>

<template>
  <card title="打卡查詢">
    <template #findSearch>
      <user-find-search  @sendSearchDate="getDate"></user-find-search>
    </template>
    <template #body>
      <table class="table table-hover">
        <thead>
        <tr>
          <th>員工編號</th>
          <th>員工姓名</th>
          <th>紀錄</th>
          <th>辦公室</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="item in getTime">
          <td>{{ item.empId }}</td>
          <td>{{ item.empName }}</td>
          <td>{{ item.time }}</td>
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
<style scoped>

</style>
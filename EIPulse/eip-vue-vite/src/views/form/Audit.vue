<template>
  <section class="card border-0 shadow-sm ">
    <div class="card-header text-center mb-2">待審核表單

    <table class="table table-light" style="height: 300px">
      <thead>
      <tr>
        <th scope="col" data-sortable="true">表單編號</th>
        <th scope="col">申請者</th>
        <th scope="col">表單類型</th>
        <th scope="col">申請時間</th>
        <th scope="col">狀態</th>
        <th scope="col">操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(data, index) in uncompletedDatas">
        <td>{{ data.formId }}</td>
        <td>{{ data.empId }}</td>
        <td>{{ data.typeName }}</td>
        <td>{{ formatStartDate(data.startDate) }}</td>
        <td>{{ data.statusName }}</td>
        <td>
          <button @click="audit(data.auditEventId)" v-if="data.statusId == 1">
            審核
          </button>
          <ShowFormObj :datas="data" :formType="data.typeId" />
        </td>
      </tr>
      </tbody>
    </table>

    <FormPage
        :total-pages="page.unTotalPages"
        :current-page="page.unCurrentPage"
        @page-change="loadUncompletedProducts"
    /></div>
    <div class="card-header text-center mb-2"> 未完成表單
    <table class="table table-light" style="height: 300px">
      <thead>
      <tr>
        <th scope="col" data-sortable="true">表單編號</th>
        <th scope="col">申請者</th>
        <th scope="col">表單類型</th>
        <th scope="col">申請時間</th>
        <th scope="col">結束時間</th>
        <th scope="col">狀態</th>
        <th scope="col">操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(data, index) in completedDatas">
        <td>{{ data.formId }}</td>
        <td>{{ data.empId }}</td>
        <td>{{ data.typeName }}</td>
        <td>{{ formatStartDate(data.startDate) }}</td>
        <td>{{ formatStartDate(data.endDate) }}</td>
        <td>{{ data.statusName }}</td>
        <td>
          <ShowFormObj :datas="data" :formType="data.typeId" />
        </td>
      </tr>
      </tbody>
    </table>

  <FormPage
      :total-pages="page.totalPages"
      :current-page="page.currentPage"
      @page-change="loadCompletedProducts"
  /> </div> </section>
</template>
<script setup>
import {ref, reactive, onMounted} from "vue";
import axios from "axios";
import Swal from "sweetalert2";
import FormPage from "../../components/form/FormPage.vue";
import ShowFormObj from "../../components/form/ShowFormObj.vue";
import {empStore} from "../../stores/employee.js";
const uncompletedDatas = ref([]);
const completedDatas = ref([]);
const emp = empStore();
const user = ref(emp.empId);
const page = reactive({
  totalPages: 0,
  currentPage: 0,
  unTotalPages: 0,
  unCurrentPage: 0,
});

const URL = import.meta.env.VITE_API_JAVAURL;
const loadProducts = () => {
  loadUncompletedProducts();
  loadCompletedProducts();
};
const loadUncompletedProducts = async (nowpage) => {
  if (nowpage == null) {
    nowpage = 1;
  }
  let URLAPI = `${URL}form/uncompletedAuditForm?id=${user.value}&pageNumber=${
    nowpage - 1
  }&pageSize=5`;
  let response = await axios.get(URLAPI);
  uncompletedDatas.value = response.data.content;
  page.unTotalPages = parseInt(response.data.totalPages);
  page.unCurrentPage = parseInt(response.data.pageable.pageNumber) + 1;
};
const loadCompletedProducts = async (nowpage) => {
  if (nowpage == null) {
    nowpage = 1;
  }
  let URLAPI = `${URL}form/completedAuditForm?id=${user.value}&pageNumber=${
    nowpage - 1
  }&pageSize=5`;
  let response = await axios.get(URLAPI);
  completedDatas.value = response.data.content;
  page.totalPages = parseInt(response.data.totalPages);
  page.currentPage = parseInt(response.data.pageable.pageNumber) + 1;
};

const audit = (audit) => {
  Swal.fire({
    title: "要給申請者什麼樣的訊息嗎?",
    input: "text",
    inputValue: "",
    inputPlaceholder: "在這裡輸入",
    showCancelButton: true, // 顯示 "取消" 按鈕
    showDenyButton: true, // 顯示 "拒絕" 按鈕
    confirmButtonText: "允許",
    denyButtonText: "拒絕",
    cancelButtonText: "取消",
    returnInputValueOnDeny: true,
  }).then((result) => {
    if (result.isConfirmed) {
      review(1, audit, result.value);
    } else if (result.isDenied) {
      // 用戶點擊了 "拒絕" 按鈕
      review(0, audit, result.value);
    } else {
      // 用戶點擊了 "取消" 按鈕
      // console.log('取消');
    }
  });
};
const review = async (yn, auditEventId, message) => {
  const aduit = reactive({
    eventId: auditEventId,
    message: message,
  });
  console.log(auditEventId);
  const URLAPI = `${URL}form/check?yn=${yn}`;
  const response = await axios.put(URLAPI, aduit);
  if (response.status == 200) {
    Swal.fire({
      title: "審核成功",
      timer: 1000,
      timerProgressBar: true,
      icon: "success",
    });
    loadProducts();
  }
};
// const viewContent = async (formId) =>{
//   const URLAPI = `${URL}form/findCheckEmpForm?formId=${formId}`;
//   const response = await axios.get(URLAPI);
//   return response.data;
// }
const formatStartDate = (dateString) => {
  const options = {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
  };
  const date = new Date(dateString);
  return date.toLocaleDateString("zh-TW", options);
};
onMounted(()=>{
  loadProducts();
})
</script>


<style scoped></style>
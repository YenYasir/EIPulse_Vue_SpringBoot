
<template>
  <div class="card text-center div1">
    現在使用者:{{emp.empName}}
    <select v-model="formSelect.formType">
      <option v-for="(type, index) in types" :value="index">{{ type }}</option>
    </select>
    <select
      v-model="formSelect.type"
      v-if="(formSelect.formType != 0) & (formSelect.formType != 3)"
    >
      <option
        v-for="(type, index) in leave"
        :value="index"
        v-if="formSelect.formType == 1"
      >
        {{ type }}
      </option>
      <option
        v-for="(type, index) in overtime"
        :value="index"
        v-if="formSelect.formType == 2"
      >
        {{ type }}
      </option>
    </select>
    <select v-model="formSelect.stustId">
      <option v-for="(stust, index) in stusts" :value="index">
        {{ stust }}
      </option>
    </select>
    <label>
      <input type="checkbox" v-model="startDate" value="option1" />
      日期搜尋
    </label>
    <template v-if="startDate == true">
      開始日期
      <input type="date" v-model="formSelect.startTime" />
      結束日期
      <input type="date" v-model="formSelect.endTime" />
    </template>
    <button @click="savevalue">進階搜尋</button>
    <div class="card-header"></div>
    <div class="card-body">
      <table class="table">
        <tr>
          <th data-sortable="true">表單ID</th>
          <th>表單類型</th>
          <th>創建時間</th>
          <th>更新日期</th>
          <th>審核人</th>
          <th>審核狀態</th>
          <th>操作</th>
        </tr>
        <tr v-for="(form, index) in forms" :key="form.formId">
          <td>{{ form.formId }}</td>
          <td>{{ form.typeName }}</td>
          <td>{{ formatStartDate(form.startDate) }}</td>
          <td>{{ formatStartDate(form.endDate) }}</td>
          <td>{{ form.auditor }}</td>
          <td>{{ form.statusName }}</td>
          <td>
            <ShowFormObj :datas="form" :formType="form.typeId" />
            <button
              @click="revokeForm(form.empId, form.formId)"
              v-if="form.statusId == 1"
            >
              撤回
            </button>
          </td>
        </tr>
      </table>
      <FormPage
        :total-pages="page.totalPages"
        :current-page="page.currentPage"
        @page-change="selectForm"
      />
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import { computed, reactive, ref } from "vue";
import ShowFormObj from "../../components/form/ShowFormObj.vue";
import Swal from "sweetalert2";
import FormPage from "../../components/form/FormPage.vue";
import {empStore} from "../../stores/employee.js";
const emp =empStore();
const URL = import.meta.env.VITE_API_JAVAURL;
let toDay = new Date();
let formattedDate = toDay.toISOString().split('T')[0]; // 這將給你 "2023-10-20" 這樣的格式
const startDate = ref(false);

const page = reactive({
  totalPages: 0,
  currentPage: 0,
});
const forms = ref([]);

// 下面是進階查詢
const types = reactive(["全部", "請假", "加班", "離職"]);
const leave = reactive([
  "全部",
  "半年特休",
  "一年特休",
  "半薪病假",
  "生理假",
  "事假",
  "婚假",
  "喪假",
  "產假",
]);
const overtime = reactive([
  "全部",
  "平日",
  "休息日",
  "國定假日或特別休假",
  "例假",
]);
const stusts = reactive([
  "全部",
  "審核中",
  "已批准",
  "已拒絕",
  "待處理",
  "過期",
  "撤回",
]);
const formSelect = reactive({
  formType: 0,
  type: 0,
  empId: emp.empId,
  stustId: 0,
  startTime: '',
  endTime: '',
});
let dataSelect = reactive({
  formType: 0,
  type: 0,
  empId: "",
  stustId: 0,
  startTime: "",
  endTime: "",
});
const savevalue = () => {
  dataSelect = JSON.parse(JSON.stringify(formSelect));
  selectForm(1);
};
const selectForm = async (nowpage) => {
  if (nowpage === null) {
    nowpage = 1;
  }
  let tempFormSelect = { ...dataSelect };
  if (tempFormSelect.formtype == 0 || tempFormSelect.formtype == 3) {
    tempFormSelect.type = "";
  }
  if (tempFormSelect.stustId == 0) {
    tempFormSelect.stustId = "";
  }
  if (tempFormSelect.startTime != "") {
    tempFormSelect.startTime = tempFormSelect.startTime + "T00:00:00";
    console.log(tempFormSelect.startTime);
  }
  if (tempFormSelect.endTime != "") {
    tempFormSelect.endTime = tempFormSelect.endTime + "T00:00:00";
  }
  console.log(tempFormSelect);
  const URLAPI = `${URL}form/selectForms?pageNumber=${nowpage - 1}&pageSize=5`;
  const response = await axios.post(URLAPI, tempFormSelect);
  forms.value = response.data.content;
  page.totalPages = parseInt(response.data.totalPages);
  page.currentPage = parseInt(response.data.pageable.pageNumber) + 1;
};

const revokeForm = async (empId, formId) => {
  Swal.fire({
    title: "確定要執行此操作嗎？",
    text: "這個操作將無法撤銷。",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "確定",
    cancelButtonText: "取消",
  }).then((result) => {
    if (result.isConfirmed) {
      const URLAPI = `${URL}form/revoke?empId=${empId}&formId=${formId}`;
      const response = axios.put(URLAPI);
      Swal.fire("操作已執行", "", "success");
      selectForm();
    } else {
      Swal.fire("操作已取消", "", "info");
    }
  });
};

const formatStartDate = (dateString) => {
  if (dateString == null) {
    return "尚未批改";
  }
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
</script>
<style>
.currentPage {
  background-color: red;
}
</style>

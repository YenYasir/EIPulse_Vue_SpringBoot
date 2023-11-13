<script setup>
import axios from "axios";
import { computed, reactive, ref } from "vue";
import ShowFormObj from "../../components/form/ShowFormObj.vue";
import Swal from "sweetalert2";
import FormPage from "../../components/form/FormPage.vue";
import { empStore } from "../../stores/employee.js";
const emp = empStore();
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
const pg = ref();
const selectForm = async (nowpage) => {
  if (nowpage === null) {
    nowpage = 1;
  }
  let tempFormSelect = { ...dataSelect };
  if (tempFormSelect.formType == 0 || tempFormSelect.formType == 3) {
    tempFormSelect.type = null;
  } else if (tempFormSelect.type == 0) {
    tempFormSelect.type = null;
  }
  if (tempFormSelect.stustId == 0) {
    tempFormSelect.stustId = "";
  }
  if (tempFormSelect.startTime != "") {
    tempFormSelect.startTime = tempFormSelect.startTime + "T00:00:00";
  }
  if (tempFormSelect.endTime != "") {
    tempFormSelect.endTime = tempFormSelect.endTime + "T00:00:00";
  }
  const URLAPI = `${URL}form/selectForms?pageNumber=${nowpage - 1}&pageSize=5`;
  const response = await axios.post(URLAPI, tempFormSelect);
  forms.value = response.data.content;
  page.totalPages = parseInt(response.data.totalPages);
  page.currentPage = parseInt(response.data.pageable.pageNumber) + 1;
  pg.value = nowpage;
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
      selectForm(pg.value);
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

const dateReset = () => {
  formSelect.startTime = "";
  formSelect.endTime = "";
}

const selectReset = () => {
  formSelect.type = 0;
}

const assessAge = () => {
  const now = new Date();
  const age = new Date(emp.hireDate);
  return ((now.getFullYear()-age.getFullYear())*12 + now.getMonth() - age.getMonth())
}
</script>
<template>
  <div class="card div1">

    <h2 class="text-center" style="margin: 20px 0">查詢自己送出的表單</h2>
<span>
    <div class="select-div">
      <span style="margin-right: 5%">選擇表單類型:
        <select v-model="formSelect.formType" @change="selectReset">
          <option v-for="(type, index) in types" :value="index">{{ type }}</option>
        </select>
      </span>

      <span v-if="(formSelect.formType != 0) & (formSelect.formType != 3)">
        <template v-if="formSelect.formType == 1">選擇請假類別:</template>
        <template v-if="formSelect.formType == 2">選擇加班時段:</template>
        <select v-model="formSelect.type">
          <template v-if="formSelect.formType == 1">
            <option value="0" >全部</option>
            <option value="2" v-if="assessAge()>=12">一年特休</option>
            <option value="1" v-else-if="assessAge()>=6">半年特休</option>
            <option value="3">半薪病假</option>
            <option value="4" v-if="emp.gender == '女'">生理假</option>
            <option value="5">事假</option>
            <option value="6">婚假</option>
            <option value="7">喪假</option>
            <option value="8" v-if="emp.gender == '女'">產假</option>
          </template>

          <option v-for="(type, index) in overtime" :value="index" v-if="formSelect.formType == 2">
            {{ type }}
          </option>
        </select>
      </span>
    </div>

    <div class="select-div">
      <span>請選擇表單狀態:
        <select v-model="formSelect.stustId">
          <option v-for="(stust, index) in stusts" :value="index">
            {{ stust }}
          </option>
        </select>
      </span>
    </div>

    <div class="select-div">
      <label>
        <input type="checkbox" v-model="startDate" value="option1" @change="dateReset" />
        日期搜尋
      </label>
      <div>
        <template v-if="startDate == true">
          開始日期
          <input type="date" v-model="formSelect.startTime" />
          結束日期
          <input type="date" v-model="formSelect.endTime" />
        </template>
      </div>
    </div>
</span>


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
            <button class="btn btn-warning" @click="revokeForm(form.empId, form.formId)" v-if="form.statusId == 1">
              撤回
            </button>
          </td>
        </tr>
      </table>
      <FormPage :total-pages="page.totalPages" :current-page="page.currentPage" @page-change="selectForm" />
    </div>
  </div>
</template>

<style>
.select-div {
  margin: 0 0 10px 5%  ;
}
</style>
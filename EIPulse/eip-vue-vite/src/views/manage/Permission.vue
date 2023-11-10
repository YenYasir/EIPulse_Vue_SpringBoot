<template>
  <edit-permission :editEmp="editEmp" ref="showModal" @close-modal="closeModal"></edit-permission>
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item"><a href="#" @click="changeGetConditionEmp(3)">同部門權限</a></li>
      <li class="breadcrumb-item"><a href="#" @click="changeGetConditionEmp(2)">相同權限</a></li>
      <li class="breadcrumb-item"><a href="#" @click="changeGetConditionEmp(1)">所有員工權限</a></li>
    </ol>
  </nav>

  <card title="權限管理">
    <template #body>
      <table class="table table-hover">
        <thead>
          <tr>
            <th>員工編號</th>
            <th>員工姓名</th>
            <th>權限</th>
            <th>可用功能</th>
            <th>權限異動</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="emp in emps" :key="emp[idex]">
            <td>{{ emp.empId }}</td>
            <td>{{ emp.empName }}</td>
            <td>{{ emp.permissionName }}</td>
            <td>{{ emp.permissionStatement }}</td>
            <td>
              <button class="btn btn-secondary mx-1" @click="startEdit(emp)">
                <i class="bi bi-pencil-square"></i>
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </template>

    <template #page>
      <page :total-pages="totalPages" :current-page="currentPage" @select-page="updatePageData"></page>
    </template>
  </card>
</template>
<script setup>
import axios from "axios";
import Card from "@/components/Card.vue";
import Page from "@/components/Page.vue";
import EditPermission from "../../components/permission/EditPermission.vue";
import { reactive, ref, onMounted } from "vue";
import { empStore } from "../../stores/employee";
const store = empStore();
const URL = import.meta.env.VITE_API_JAVAURL;
const emps = ref({});
const editEmp = reactive({});
const currentPage = ref(1);
const totalPages = ref(null);
const showModal = ref(null);
let bootstrapModal = null;
let reqUrl = `${URL}permissionInfo/findAll/${currentPage.value}`

function startEdit(emp) {
  Object.assign(editEmp, emp);
  console.log(editEmp);
  if (bootstrapModal) {
    bootstrapModal.show();
  }
}

function getEmps() {
  console.log(reqUrl);
  axios.get(reqUrl).then((res) => {
    totalPages.value = res.data.totalPages;
    console.log(res.data.content);
    emps.value = res.data.content;
    // Object.assign(emps, res.data.content);
  });
}

function closeModal() {
  getEmps();
  bootstrapModal.hide();
}
const page = ref(1);
function updatePageData(newPage) {
  console.log("下一頁" + newPage);
  currentPage.value = newPage;
  changeGetConditionEmp(page.value)
  getEmps();
}

function changeGetConditionEmp(number) {
  if (number != page.value) {
    page.value = number;
    currentPage.value = 1;
  }

  switch (number) {
    case 1: // 所有權限
      reqUrl = `${URL}permissionInfo/findAll/${currentPage.value}`
      console.log("所有權限");
      break
    case 2:// 查與帳號相同權限
      reqUrl = `${URL}permissionInfo/permission/${store.empId}/${currentPage.value}`
      console.log("查與帳號相同權限");
      break
    case 3:// 查同部門權限
      reqUrl = `${URL}permissionInfo/${store.empId}/${currentPage.value}`
      console.log("查同部門權限");
      break
  }
  getEmps();
}


onMounted(() => {
  getEmps();
  let modelEl = showModal.value.$el;
  bootstrapModal = new bootstrap.Modal(modelEl, {});
});
</script>

<style scoped></style>

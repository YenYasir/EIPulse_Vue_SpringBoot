<template>
  <div class="card rounded ">
    <div class="card-header text-center">部門/職位異動紀錄表</div>
    <div class="card-body ">
      <div class="mb-3" style="text-align:left; position:left;">
        <button type="button" class="btn btn-outline-success w-40 mb-2" @click="addDept">新增紀錄</button>
        <form @submit.prevent="loadDept" style="text-align: left">
          <div class="input-group">
            <input type="text" class="form-control custom-width" placeholder="輸入員工姓名" aria-label="輸入員工姓名"
              aria-describedby="button-addon2" v-model="eMPname">
            <button class="btn btn-outline-secondary" type="button" id="button-addon2">查詢</button>
          </div>
        </form>
      </div>
      <table class="table table-bordered text-center">
        <thead>
          <tr>
            <th>編號</th>
            <th>員工編號</th>
            <th>員工姓名</th>
            <th>更動前職位</th>
            <th>更動後職位</th>
            <th>更動原因</th>
            <th>起效日</th>
            <th>簽核長官</th>
            <th>編輯日期</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="move in moveData" :key="move.id">
            <td>{{ move.id }}</td>
            <td>{{ move.empId }}</td>
            <td>{{ move.empName }}</td>
            <td>{{ move.beforeDeptInfo }}</td>
            <td>{{ move.afterDeptInfo }}</td>
            <td>{{ move.reason }}</td>
            <td>{{ move.effectDate }}</td>
            <td>{{ move.approver }}</td>
            <td>{{ move.editDate }}</td>
            <td>
              <button class="btn btn-secondary mx-1"><i class="bi bi-pencil-square" @click="updateDept(dept.deptId)"></i>
              </button>
              <button class="btn btn-secondary mx-1"><i class="bi bi-bucket" @click="deleteDept(dept.deptId)"></i>
              </button>
            </td>
          </tr>
        </tbody>
      </table>
      <Page :total-pages="totalPages" :current-page="currentPage" @select-page="updateCurrentPage"></Page>
    </div>
    <div class="card-footer text-body-secondary">
      ©2023 EIPulse
    </div>
    <window-modal class="modal-xl" titleName="產品編輯" ref="editModal">
      <form>
        <edit-input></edit-input>
      </form>
    </window-modal>
  </div>
</template>


<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';
import Swal from 'sweetalert2';
import Page from "../../../components/Page.vue";
import { useRouter } from 'vue-router';
import { debounce } from 'lodash';

const moveData = ref([]);
const currentSearch = ref({});
const eMPname = ref('');
const totalPages = ref(0);
const currentPage = ref(1);
const router = useRouter();
const oldEmpName = '';

const loadDept = async () => {
  let url = `http://localhost:8090/eipulse/TitleMove/paged/${currentPage.value}`;

  if (eMPname.value) {
    url = `http://localhost:8090/eipulse/TitleMove/paged/${eMPname.value}/${currentPage.value}`

  }
  // 創建一個防抖函數
  const debouncedLoadDept = debounce(() => {
    loadDept();
  }, 500); // 500ms 防抖時間

  watch(eMPname, (newValue, oldValue) => {
    // 當eMPname變化時，調用防抖函數
    debouncedLoadDept();
  });

  try {
    const res = await axios.get(url);
    console.log(res.data.content);
    moveData.value = res.data.content;
    totalPages.value = res.data.totalPages;
  } catch (e) {
    console.log(e);
  }
};

const updateCurrentPage = (newPage) => {
  currentPage.value = newPage;
  loadDept();
};

const addDept = () => {
  router.push(`/xukai/add-dept`);
};

const updateDept = (deptId) => {
  router.push(`/xukai/updateDept/${deptId}`);
};

const deleteDept = async (deptId) => {
  if (confirm("確定要刪除嗎?")) {
    const url = `http://localhost:8090/eipulse/dept/delete/${deptId}`
    const data = await axios.delete(url);
    if (data.status == 200) {
      Swal.fire({
        icon: 'success',
        title: '已刪除',
        text: data.message
      });
      loadDept();
    }
  }
};

onMounted(loadDept);
</script>

<style scoped>
.card {
  margin: 20px;
  padding: 20px;

}

.card-header {

  background-color: #a3c4e8;
}

img {
  width: 50px;
}

.table {
  margin-top: 20px;
}

th,
td {
  padding: 12px;
}

.btn-secondary {
  margin: 0 5px;
}

/* 新增顏色區分按鈕 */
.btn-edit {
  background-color: #007bff;
  color: rgb(38, 61, 97);
  width: 50px;
}

.btn-delete {
  background-color: #dc3545;
  color: white;
}

.sidebar {
  position: -webkit-sticky;
  /* 兼容Safari瀏覽器 */
  position: sticky;
  top: 0;
  /* 從視窗頂部開始 */
  height: 100vh;
  /* 使其占滿整個視窗的高度 */
  overflow-y: auto;
  /* 如果內容超出視窗高度，讓其可以滾動 */
}

.input-group {
  width: 40%;
}

.card-body>div {
  text-align: left;
}
</style>
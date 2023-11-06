<template>
  <div class="card text-center  rounded ">
    <div class="card-header">所有離職紀錄</div>
    <div class="card-body ">
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>編號</th>
            <th>員工編號</th>
            <th>離職原因</th>
            <th>審核人</th>
            <th>離職日期</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="resignRecord in resignRecords" :key="resignRecord.id">
            <td>{{ resignRecord.id }}</td>
            <td>{{ resignRecord.empId }}</td>
            <td>{{ resignRecord.approver }}</td>
            <td>{{ resignRecord.reason }}</td>
            <td>{{ resignRecord.editDate }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="card-footer text-body-secondary">
      Copyright ©  EIPulse科技 All Rights Reserved.
    </div>
    <window-modal @submit="editSubmit" class="modal-xl" titleName="產品編輯" ref="editModal">
      <form>
        <edit-input :editProduct="editProduct"></edit-input>
      </form>
    </window-modal>
  </div>
</template>


<script>
import axios from "axios";
export default {
  data() {
    return {
      resignRecords: [],
    }
  },
  methods: {
    loadRecord() {
      axios.get('http://localhost:8090/eipulse/resign/findAll').then(res => {
        console.log(res.data)
        this.resignRecords = res.data;
      }).catch((e) => {
        console.log(e)
      })
    }
  },
  mounted() {
    this.loadRecord();
  }
}
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
</style>
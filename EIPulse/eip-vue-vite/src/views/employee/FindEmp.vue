
<template>
  <div class="card mx-2 p-2 mb-2">
    <div class="card-header text-center  text-bg-dark fs-5">員工列表</div>
    <div class="card-body ">
      <div class="mb-3" style="text-align:left; position:left;">
        <button type="button" class="btn btn-outline-success  mb-2" @click="addEmp">新增員工</button>
        <form @submit.prevent="loadEmployee" style="text-align: left">
          <div class="input-group w-25">
            <input type="text" class="form-control custom-width" placeholder="輸入員工姓名" aria-label="Recipient's username"
              aria-describedby="button-addon2" v-model="EMPname">
            <button class="btn btn-outline-secondary " type="button" id="button-addon2">查詢</button>
          </div>
        </form>
      </div>
      <table class="table table-bordered text-center">
        <thead>
          <tr style="background-color: rgb(255, 204, 0);; border-color: rgb(0, 0, 0);">
            <th>員工編號</th>
            <th>員工姓名</th>
            <th>生日</th>
            <th>電子信箱</th>
            <th>身分證</th>
            <th>性別</th>
            <th>手機號碼</th>
            <th>室內電話</th>
            <th>照片</th>
            <th>聯絡地址</th>
            <th>職位</th>
            <th>入職日</th>
            <th>在職狀態</th>
            <th>建檔日期</th>
            <th>編輯</th>
          </tr>
        </thead>
        <tbody style="border-color: black; ">
          <tr v-for="emp in employees" :key="emp.id">
            <td style="width: 10px;">{{ emp.empId }}</td>
            <td>{{ emp.empName }}</td>
            <td>{{ emp.birth }}</td>
            <td>{{ emp.email }}</td>
            <td>{{ emp.idNumber }}</td>
            <td>{{ emp.gender }}</td>
            <td>{{ emp.phone }}</td>
            <td>{{ emp.tel }}</td>
            <td v-if="emp.photoUrl == null || emp.photoUrl == ''">N/A</td>
            <td v-else>
              <img :src="emp.photoUrl" style="width: 100px;height: 80px;">
            </td>
            <td>{{ emp.address }}</td>
            <td>
              <button type="button" class="btn btn-secondary btn-font-color-black text-center"
                @click="showEditModal(emp)">
                {{ emp.titleName }}</button>
            </td>
            <td>{{ emp.hireDate }}</td>
            <td>{{ emp.empState }}</td>
            <td>{{ emp.editDate }}</td>
            <td>
              <button class="btn btn-secondary mx-1"><i class="bi bi-pencil-square" @click="updateEmp(emp.empId)"></i>
              </button>
            </td>
          </tr>
        </tbody>
      </table>
      <Page :total-pages="totalPages" :current-page="currentPage" @select-page="updateCurrentPage"></Page>
    </div>
    <div class="card-footer text-body-secondary">
      Copyright © EIPulse科技 All Rights Reserved.
    </div>
    <window-modal class="modal-lg" titleName="職位變更" ref="editModal" @submit="saveTitle">
      <form>
        <update-title :emp-edit="empEdit" @update-send-title="getEditedData"></update-title>
      </form>
    </window-modal>

  </div>
</template>

<script>
import axios from "axios";
import Page from "../../../src/components/Page.vue";
import Swal from 'sweetalert2';
import updateTitle from '../../components/employee/UpdateTitle.vue'
import WindowModal from '../../components/mall/WindowModal.vue'
import { empStore } from "../../stores/employee.js";

export default {
  data() {
    return {
      employees: [],
      currentSearch: {},
      EMPname: '',
      totalPages: 0,
      currentPage: 1,
      empEdit: {},
      getData: {},
    };
  },
  // setup() {
  //   const updateEmpTitle = useEmpInfoStore();
  //   return {
  //     updateEmpTitle
  //   }
  // },
  components: {
    Page, updateTitle, WindowModal
  },
  methods: {
    async loadEmployee() {
      let url = `http://localhost:8090/eipulse/employee/paged/${this.currentPage}`;

      // 模糊收尋
      if (this.EMPname !== null && this.EMPname !== '') {
        url = `http://localhost:8090/eipulse/employee/paged/${this.EMPname}/${this.currentPage}`;
      }
      try {
        const res = await axios.get(url);
        console.log(res.data.content);
        this.employees = res.data.content;
        this.totalPages = res.data.totalPages;
      } catch (e) {
        console.log(e);
      }
    },
    updateCurrentPage(newPage) {
      console.log("測試測試");
      console.log(this.currentPage);

      this.currentPage = newPage;
      // 這裡加載新頁面的數據
      console.log(this.currentSearch.value);
      this.loadEmployee();
    },
    getEditedData(data) {
      this.getData = data;
      console.log(this.getData);
    },

    // 轉跳至新增畫面
    addEmp() {
      this.$router.push(`/employee/add-emp`);
    },

    updateEmp(empId) {
      this.$router.push(`/employee/updateEmp/${empId}`);
    },

    // 跳出編輯職位的畫面
    showEditModal(emp) {
      this.empEdit = emp;
      this.$nextTick(() => {
        let modalElemnt = this.$refs.editModal.$el;
        let modal = new bootstrap.Modal(modalElemnt, {});
        modal.show();
      })
    },
    // 儲存編輯職位的API
    async saveTitle() {
      try {
        this.getData.approver = empStore().empName;
        const api = `http://localhost:8090/eipulse/employee/updateTitle`;
        const response = await axios.put(api, this.getData);
        console.log(this.getData.reason);

        const result = await Swal.fire({
          title: 'Are you sure?',
          text: "確定要更動嗎?",
          icon: 'question',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: '確定'
        });

        if (result.isConfirmed && this.getData.reason) {
          Swal.fire(
            '儲存成功',
            '',
            'success'
          )
          let modalElemnt = this.$refs.editModal.$el;
          let modal = bootstrap.Modal.getInstance(modalElemnt);
          if (modal) {
            modal.hide();
          }
        }
        else {
          // 處理非200的狀態碼
          throw new Error(`HTTP error! status: ${response.status}`);
        }
      } catch (error) {
        // 處理異步請求過程中的錯誤
        console.error("Error occurred:", error);
        Swal.fire(
          '發生錯誤',
          '請確認所有欄位是否空白。',
          'error'
        );
      }
    }
  },
  mounted() {
    this.loadEmployee();
  }
};

</script>



<style scoped>
.card-body {
  max-height: 500px;
  overflow-y: auto;
  /* overflow-x: auto; */
}
</style>
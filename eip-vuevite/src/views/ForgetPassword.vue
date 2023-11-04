<script>
import axios from "axios";
import Swal from "sweetalert2";
import {empStore} from "../stores/employee.js";
const URL = import.meta.env.VITE_API_JAVAURL;

export default {
  setup() {
    const store = empStore();
    const updateEmpId =store.updateEmpId;
    return {
      empId: store.empId,
      email: store.email,
      updateEmpId
    }
  },
  methods:{
    async sendEmail() {
      axios.post(`${URL}login/forgetPassword`, {
        email: this.email
      }, {
        withCredentials: true
      }).then((res) => {
            console.log(res)
            Swal.fire({
              title: '驗證碼已寄送至信箱',
              timer: 1000,
              timerProgressBar: true,
              icon: 'success'
            })
        this.updateEmpId(res.data)
            this.$router.push({name:'new-password'})
          }
      ).catch((e) => {
        console.log(e)
        Swal.fire({
          title: '信箱不存在請確認',
          icon: 'error'
        })
      });
    }
  },
}

</script>

<template>
  <div class="card text-center div1" >
    <div class="card-header">
      忘記密碼
    </div>
    <div class="card-body">
      <form @submit.prevent="sendEmail">
        <div class="form-floating mb-3">
          <input v-model="email" type="email" class="form-control" id="email" placeholder="email">
          <label for="email">email</label>
        </div>
        <div class="form-floating">
          <input class="btn btn-secondary " type="submit" value="送出">
        </div>
      </form>
    </div>
    <div class="card-footer text-body-secondary">
      ©2023 EIPulse
    </div>
  </div>
</template>

<style scoped>
.div1 {
  margin: 50px auto;
  width: 400px;
}

</style>
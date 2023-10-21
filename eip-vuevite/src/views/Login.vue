<script>
import axios from "axios";
import Swal from "sweetalert2";
import index from "./Index.vue";
import {empStore} from "../stores/employee.js";

export default {
  setup(){
    const store = empStore();
    return{
      empId:store.empId,
      password:store.password,
      isLogin: store.isLogin,
      setLoginStatus:store.setLoginStatus
    }
  },
  methods: {
    async handleSubmit() {
      try {
        const response = await axios.post('http://localhost:8090/eipulse/login', {
          empId: this.empId,
          password: this.password,
        }, {
          withCredentials: true
        });
        await Swal.fire({
          title: '登入成功',
          timer: 1000,
          timerProgressBar: true,
          icon:'success'
        })
        this.setLoginStatus(true,response.data);
        this.$router.push({name:'index'})
      } catch (e) {
        await Swal.fire({
          title: '登入失敗',
          text: '請確認工號與密碼',
          icon: 'error',
          theme:'dark'
        })
        console.error('error:' + e);
      }
    },
    toggleForgetPassword(){
      this.$router.push({name:'forget'})
    }
  },
  watch:{
  },
  name: "Login"
}
</script>

<template>
  <div class="card text-center div1" >
    <div class="card-header">
      EIPulse
    </div>
    <div class="card-body">
      <form @submit.prevent="handleSubmit">
        <div class="form-floating mb-3">
          <input v-model="empId" type="text" class="form-control" id="empId" placeholder="Employee">
          <label for="empId">Employee</label>
        </div>
        <div class="form-floating">
          <input v-model="password" type="password" class="form-control mb-2" id="floatingPassword"
                 placeholder="Password">
          <label for="floatingPassword">Password</label>
          <input class="btn btn-secondary mb-4" type="submit" value="Log in">
        </div>
      </form>
      <button @click="toggleForgetPassword" class="btn btn-secondary btn-sm">忘記密碼</button>
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
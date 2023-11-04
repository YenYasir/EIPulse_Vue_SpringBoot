<script>
import axios from "axios";
import Swal from "sweetalert2";
import index from "./manage/Index.vue";
import {empStore} from "../stores/employee.js";
const URL = import.meta.env.VITE_API_JAVAURL;

export default {
  // 全局員工資料
  setup(){
    const store = empStore();
    return{
      empId:store.empId,
      password:store.password,
      isLogin: store.isLogin,
      setLoginStatus:store.setLoginStatus,
      permission: store.permission
    }
  },
  methods: {
    //登入 function
    async handleSubmit() {
      try {
        const response = await axios.post(`${URL}login`, {
          empId: this.empId,
          password: this.password,
        }, {
          withCredentials: true
        });
        await this.setLoginStatus(true,response.data);
        await Swal.fire({
          title: '登入成功',
          timer: 1000,
          timerProgressBar: true,
          icon:'success'
        })
        //判別權限導向不同畫面
        if(sessionStorage.getItem('permission') ==='6' || this.permission==='6'){
          this.$router.push({name:'manage-index',params:{empId:this.empId}})
        }else {
          this.$router.push({name:'user-index',params:{empId:this.empId}})
        }
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
    //導向忘記密碼
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
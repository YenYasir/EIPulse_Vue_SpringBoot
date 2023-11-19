
<template >
  <div class="newpassword">
  <div class="card text-center div1">
    <div class="card-header">
      新密碼
    </div>
    <div class="card-body">
      <form @submit.prevent="sendOtp">
        <div class="form-floating mb-3">
          <input v-model="otpCheck" type="text" class="form-control" id="otp" placeholder="驗證碼" >
          <label for="otp">驗證碼</label>
        </div>
        <div class="form-floating mb-3">
          <input v-model="newPassword" type="password" class="form-control" id="newPassword" placeholder="新密碼" >
          <label for="newPassword">新密碼</label>
        </div>
        <div class="form-floating mb-3">
          <input v-model="newPasswordCheck" type="password" class="form-control" id="newPasswordCheck" placeholder="確認新密碼" >
          <label for="newPasswordCheck">確認新密碼</label>
        </div>
        <div v-if="!passwordMatch" class="waring">請確認新密碼是否相同</div>
        <div class="form-floating">
          <input :disabled="!passwordMatch" class="btn btn-warning" type="submit" value="送出">
        </div>
      </form>
      <button @click="goToLogin" class="btn btn-secondary mt-3">返回登入</button>
    </div>
    <div class="card-footer text-body-secondary">
      Copyright ©  EIPulse科技 All Rights Reserved.
    </div>
  </div>
</div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
import {empStore} from "../stores/employee.js";

export default {
  setup() {
    const store = empStore();
    return {
      empId: store.empId,
      email: store.email,
    }
  },
  data(){
    return{
      otpCheck:'',
      newPassword:'',
      newPasswordCheck:'',
    }
  },
  methods:{
    async sendOtp(){
      try {
        const response = await axios.post("http://localhost:8090/eipulse/login/newPassword",{
          empId:this.empId,
          newPassword:this.newPassword,
          otpCheck:this.otpCheck,
        },{
          withCredentials:true
        })
        await Swal.fire({
          title:'密碼已重設，將返回登入頁面',
          timer:1000,
          timerProgressBar:true,
          icon:'success'
        })
        this.$router.push({name:'login'})
        this.$emit('new-password-success')
      }catch (e){
        await Swal.fire({
          title:'驗證碼錯誤',
          icon:'error'
        })
      }
    },
  goToLogin() {
      this.$router.push({ name: 'login' });
    }
  },
  computed:{
    passwordMatch(){
      return this.newPassword === this.newPasswordCheck &&this.newPassword!="";
    }
  },
  name: "NewPassword"
}
</script>

<style scoped>
.newpassword{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/chpassword.jpg");
  background-size: cover;
}
.div1{
  margin: 50px auto;
  width: 400px;
}
.waring{
  color: red;
}
</style>
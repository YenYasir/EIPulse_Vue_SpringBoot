<script>
import axios from "axios";
import Swal from "sweetalert2";

export default {
  props:{
    empId:{
      type:[String,Number],
      default:null
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
          timer:1500,
          timerProgressBar:true,
          icon:'success'
        })
        console.log(response.status)
        this.$emit('new-password-success')
        console.log("new-password-success有觸發")
      }catch (e){
        console.log("error:"+ e)
        await Swal.fire({
          title:'驗證碼錯誤',
          icon:'error'
        })
      }
    },
  },
  computed:{
    passwordMatch(){
      return this.newPassword === this.newPasswordCheck &&this.newPassword!="";
    }
  },
  name: "NewPassword"
}
</script>

<template>
  <div class="card text-center div1">
    <div class="card-header">
      EIPulse
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
          <input :disabled="!passwordMatch" class="btn btn-secondary " type="submit" value="送出">
        </div>
      </form>
    </div>
    <div class="card-footer text-body-secondary">
      ©2023 EIPulse
    </div>
  </div>
</template>

<style scoped>
.div1{
  margin: 50px auto;
  width: 400px;
}
.waring{
  color: red;
}
</style>
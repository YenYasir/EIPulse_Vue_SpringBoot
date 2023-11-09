

<template>
  <div class="forgetpassword">
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
    const updateEmpId =store.updateEmpId;
    return {
      empId: store.empId,
      email: store.email,
      updateEmpId
    }
  },
  methods:{
    async sendEmail() {
      axios.post('http://localhost:8090/eipulse/login/forgetPassword', {
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
      // try {
      //   const response = await axios.post('http://localhost:8090/eipulse/login/forgetPassword',{
      //     email:this.email
      //   },{
      //     withCredentials:true
      //   });
      //   console.log(response.status)
      //   console.log(response.data)
      //   this.empId = response.data
      //   alert('已寄送驗證碼至信箱請確認');
      //   this.showIsForget=false;
      //   this.$emit('email-verified',this.empId)
      // }catch (e){
      //   console.log('errMessage'+e)
      //   alert('信箱不存在請重新確認')
      // }
    }
  },
}

</script>
<style scoped>
.forgetpassword{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/chpassword.jpg");
  background-size: cover;
}
.div1 {
  margin: 50px auto;
  width: 400px;
}

</style>
<template>
  <section class="vh-100">
  <div class="container-fluid">
    <div class="row">
      <div class="col-sm-6 text-black">

  
        <div class="d-flex align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">

          <form style="width: 40rem;" @submit.prevent="handleSubmit">

            <h3 class="fw-normal mb-3 pb-3"  style="letter-spacing: 1px solid ;">EIPulse人事入口網站</h3>

            <div class="form-outline mb-4">
              <input v-model="empId" type="text" class="form-control form-control-lg" placeholder="員工代號"/>
      
            </div>

            <div class="form-outline mb-4">
              <input v-model="password" type="password" class="form-control form-control-lg" id="floatingPassword"
                placeholder="密碼"/>
            
            </div>
            <div class="form-check">
              <input class="form-check-input" type="checkbox" value="" id="ckempId" checked />
              <label class="form-check-label" for="ckempId"> 記住我 </label>
            </div>
            <div class="pt-1 mb-4">
              <button class="btn btn-warning btn-lg btn-block" type="submit">登入</button>
            </div>
            <button @click="toggleForgetPassword" class="btn btn-secondary btn-sm">忘記密碼</button>
            <div class="card-footer text-body-secondary">
              Copyright ©  EIPulse科技 All Rights Reserved.
          </div>
          </form>
        
      
    </div>
    
      </div>
      
      <div class="col-sm-6 px-0 d-none d-sm-block">
        <img src="..\assets\images\login-background.jpg"
          alt="Login image" class="w-100 vh-100" style="object-fit: cover; object-position: left;">
      </div>
    
    </div>
    
  </div>
</section>

</template>

<script>

import axios from "axios";
import Swal from "sweetalert2";
import {empStore} from "../stores/employee.js";

export default {
  // 全局員工資料
  setup(){
    const store = empStore();
    return{
      empId:store.empId,
      password:store.password,
      isLogin: store.isLogin,
      setLoginStatus:store.setLoginStatus,
      permissionId: store.permissionId
    }
  },
  methods: {
    //登入 function
    async handleSubmit() {
      try {
        const response = await axios.post('http://localhost:8090/eipulse/login', {
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
        if (parseInt(sessionStorage.getItem('permissionId'), 10) === 6 || parseInt(this.permissionId, 10) === 6){
          this.$router.push({name:'manage-index',params:{empId:this.empId}})
        }else {
          this.$router.push({name:'user-index',params:{empId:this.empId}})
        }
      } catch (e) {
        await Swal.fire({
          title: '登入失敗',
          text: '請確認員工代號與密碼',
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


<!-- <style scoped>
.div1 {
  margin: 50px auto;
  width: 400px;
}

</style> -->
<style>
.bg-image-vertical {
  position: relative;
  overflow: hidden;
  background-repeat: no-repeat;
  background-position: right center;
  background-size: auto 100%;
}

@media (min-width: 1025px) {
  .h-custom-2 {
    height: 100%;
  }
}
.vh-100{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-color: antiquewhite;
}
html, body {
  overflow: hidden;
}

</style>
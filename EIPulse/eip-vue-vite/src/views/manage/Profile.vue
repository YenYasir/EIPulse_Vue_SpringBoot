<template>
  <div class="container">
    <div class="main-body">
    
          <!-- Breadcrumb -->
          <nav aria-label="breadcrumb" class="main-breadcrumb">
            <ol class="breadcrumb">
              <li class="breadcrumb-item"><router-link :to="getHomeLink">主頁</router-link></li>
              <li class="breadcrumb-item active" aria-current="page">個人中心</li>
            </ol>
          </nav>
          <!-- /Breadcrumb -->
    
          <div class="row gutters-sm">
            <div class="col-md-4 mb-3">
              <div class="card">
                <div class="card-body">
                  <div class="d-flex flex-column align-items-center text-center">
                    <div class="cropper-container">
                      <label for="photoUrl" class="image-upload-label" style="cursor: pointer;">
                    <img :src="store.photoUrl" ref="image" class="rounded-circle" alt="Profile Photo" style="max-width: 150px;">
                    <input class="form-control form-control-sm" id="photoUrl" type="file" @change="AvatarChange" style="display: none;">
                  </label>
                      </div>
                      <br>
                    <form @submit.prevent="addHandler">
                    <div class="col-12">
                    <button type="submit" class="btn btn-warning">上傳頭像</button>
                   </div>
                   </form>
                    <div class="mt-3">
                      <h4 style="color: orange;">{{store.empName}}</h4>
                      <p class="text-secondary mb-1">{{store.titleName}}</p>
                      <p class="text-muted font-size-sm">{{store.deptName}}</p>
                    </div>
                  </div>
                </div>
              </div>
            
            </div>
            <div class="col-md-8">
              <div class="card mb-3">
                <div class="card-body">
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">員工代號</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      {{store.empId}}
                    </div>
                  </div> 
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">員工姓名</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      {{store.empName}}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">員工信箱</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      {{store.email}}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">員工生日</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      {{store.birth}}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">員工身分證字號</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      {{store.idNumber}}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">員工性別</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      {{store.gender}}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">員工手機</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      {{store.phone}}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">員工市話</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      {{store.tel}}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">員工住址</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      {{store.address}}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                   
                  </div>
                </div>
              </div>

        


            </div>
          </div>

        </div>
    </div>
</template>

<script setup>
import axios from "axios";
import Cropper from 'cropperjs';
import { ref,computed } from 'vue';
import {empStore} from "../../stores/employee.js";
import { useRouter } from 'vue-router';
import Swal from 'sweetalert2'
const router = useRouter();
const store = empStore();

const getHomeLink = computed(() => {
      return `/manage/${store.empId}`; 
    })


const employeeData = ref(store);

const addHandler = async () => {
  // 利用 formData 處理圖片
  if (!employeeData.value.empId) {
    console.error('empId is null or undefined');
    return;
  }

  const formData = new FormData();
  formData.append('file', file.value);

  const API_URL = `${import.meta.env.VITE_API_JAVAURL}employee/changeAvatar`;
  try {
    employeeData.value.file = ""
    const json = JSON.stringify(employeeData.value);
    const blob = new Blob([json], {
      type: 'application/json'
    });
    formData.append('data', blob);
    ////////////////////////////
    const response = await axios.post(API_URL, formData);
    console.log(employeeData.value);
    console.log(response);
    if (response.status == 200) {
      store.setphotoUrl(response.data.photoUrl); 
      Swal.fire(
        '儲存成功',
        '',
        'success'
      )
      router.push(`/manage/${store.empId}/profile`);
    } else {
      alert(response.data.message);
    }
  } catch (error) {
    console.error("An error occurred while adding: ", error);
    console.log('emp.value before sending:', employeeData.value);
  }
}


const file = ref("");
const AvatarChange = (e) => {
  
  const selectedFile = e.target.files[0]; // 獲取第一個選擇的檔案
  if (!selectedFile) {
   
    console.log('No file selected');
    return;
  }
  const maxSizeInBytes = 1024 * 1024 * 5; // 5MB

  
    if (selectedFile.size > maxSizeInBytes) {
      Swal.fire({
        icon: 'error',
        title: '檔案超過大小',
        text: `${selectedFile.name}超過5MB`,
      });
      return;
    } 
  
  file.value = selectedFile;
};
  


</script>

<style>

</style>
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
                    <img :src="store.photoUrl" class="rounded-circle" alt="Profile Photo" style="max-width: 150px;">
                    <p>上傳頭像待施工</p>
                  <br>
                    <div class="mt-3">
                      <h4>{{store.empName}}</h4>
                      <br>
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
import { ref,computed } from 'vue';
import {empStore} from "../../stores/employee.js";


const store = empStore();




const getHomeLink = computed(() => {
      return `/manage/${store.empId}`; 
    })


  


</script>

<style>

</style>
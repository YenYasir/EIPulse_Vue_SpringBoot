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
                    <img src="../../assets/images/profile.jpg" alt="Admin" class="rounded-circle" width="150">
                    <div class="mt-3">
                      <h4>{{empName}}</h4>
                      <br>
                      <p class="text-secondary mb-1">{{titleName}}</p>
                      <p class="text-muted font-size-sm">Bay Area, San Francisco, CA</p>
                     
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
                      {{empId}}
                    </div>
                  </div> 
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">員工姓名</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      {{empName}}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">員工信箱</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      {{email}}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">員工生日</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      {{birth}}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">員工身分證字號</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      {{idNumber}}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">員工性別</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      {{gender}}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">員工手機</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      {{phone}}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">員工市話</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      {{tel}}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">員工住址</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      {{address}}
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
import Swal from 'sweetalert2';
import axios from "axios";
import { computed } from 'vue';
import {empStore} from "../../stores/employee.js";
import router from "../../router/index.js";

const store = empStore();
const empId = store.empId;
const empName = store.empName;
const email = store.email;
const birth = store.birth;
const idNumber = store.idNumber;
const gender = store.gender;
const phone = store.phone;
const tel = store.tel;
const address = store.address;
const titleName = store.titleName;
const getHomeLink = computed(() => {
      return `/manage/${empId}`; 
    })

</script>

<style>

</style>
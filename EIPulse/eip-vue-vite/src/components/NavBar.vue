

<template>
 <!-- Navbar-->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid justify-content-between">
    <!-- Left elements -->
    <div class="d-flex">
 
      <!-- Search form -->
      <!-- <form class="input-group w-auto my-auto d-none d-sm-flex">
        <input
          autocomplete="off"
          type="search"
          class="form-control rounded"
          placeholder="Search"
          style="min-width: 125px;"
          v-model="searchTerm" 
          @input="filterNavItems"
        />
        <span class="input-group-text border-0 d-none d-lg-flex"
          ><i class="fas fa-search"></i
        ></span>
      </form>-->
    </div> 
    <!-- Left elements -->

    <!-- Center elements -->
    <ul class="navbar-nav flex-row d-none d-md-flex">
      <li v-if="isManager" class="nav-item me-3 me-lg-3">
          <router-link :to="getManageHomeLink" title="主頁">
            <span><i class="bi bi-house"></i></span>
          </router-link>
        </li>
        <li v-else class="nav-item me-3 me-lg-3">
          <router-link :to="getUserHomeLink" title="主頁">
            <span><i class="bi bi-house"></i></span>
          </router-link>
        </li>

    
      <li v-if="isManager" class="nav-item me-3 me-lg-3">
          <router-link :to="getManageSalaryLink" title="薪資管理">
            <span><i class="bi bi-coin"></i></span>
          </router-link>
        </li>
        <li v-else class="nav-item me-3 me-lg-3">
          <router-link :to="getUserSalaryLink" title="薪資查詢">
            <span><i class="bi bi-coin"></i></span>
          </router-link>
        </li>
      
        <li v-if="isManager" class="nav-item me-3 me-lg-3">
          <router-link :to="getManageAuditLink" title="簽核">
            <span><i class="bi bi-pen"></i></span>
          </router-link>
        </li>

      <li class="nav-item me-3 me-lg-3" title="福委會">
        <router-link :to="getMallLink">
          <span><i class="bi bi-bag"></i></span>
        </router-link> 
      </li>

      <li v-if="isManager" class="nav-item me-3 me-lg-3">
          <router-link :to="getManageChatLink" title="聊天室">
            <span><i class="bi bi-chat-left-text"></i></span>
          </router-link>
        </li>
        <li v-else class="nav-item me-3 me-lg-3">
          <router-link :to="getUserChatLink" title="聊天室">
            <span><i class="bi bi bi-chat-left-text"></i></span>
          </router-link>
        </li>
    </ul>
    <!-- Center elements -->

    <!-- Right elements -->
    <ul class="navbar-nav flex-row">
        
      <li v-if="isManager" class="nav-item me-3 me-lg-3">
        <router-link :to="getManageProfileLink" title="個人中心" class="d-flex align-items-center" style="text-decoration: none; color: black;">
          <img :src="store.photoUrl" class="rounded-circle" height="40" />
          <strong class="d-none d-sm-block ms-1">{{ store.empName }}</strong>
        </router-link>
        </li>
        <li v-else class="nav-item me-3 me-lg-3">
          <router-link :to="getUserProfileLink" title="個人中心" class="d-flex align-items-center" style="text-decoration: none; color: black;">
          <img :src="store.photoUrl" class="rounded-circle" height="40" />
          <strong class="d-none d-sm-block ms-1">{{ store.empName }}</strong>
        </router-link>
        </li>
        <li class="nav-item me-3 me-lg-3">
        <button @click="logout" class="btn btn-warning">登出</button>
      </li>
    </ul>
    <!-- Right elements -->
  </div>
</nav>
<!-- Navbar -->
</template>
<script setup>
import { computed,ref } from 'vue';
import {empStore} from "../stores/employee.js";
import Swal from 'sweetalert2';
import router from "../router/index.js";

const store = empStore();
const isManager = computed(() => parseInt(store.permissionId, 10) === 6);
const props = defineProps({
  homePath:{
    type:String,
    required:true
  }
})
const getManageHomeLink = computed(() => `/manage/${store.empId}`);
const getUserHomeLink = computed(() => `/user/${store.empId}`);

const getManageSalaryLink = computed(() => `/salary`);
const getUserSalaryLink = computed(() => `/user/${store.empId}/paySlip`);


const getManageAuditLink = computed(() => {
      return `/manage/form/audit`; 
    })

const getMallLink = computed(()=>{
    return `/mall`;
})



const getManageChatLink = computed(() => `/manage/chats`);
const getUserChatLink = computed(() => `/user/chats`);


const getManageProfileLink = computed(() => `/manage/${store.empId}/profile`);
const getUserProfileLink = computed(() => `/user/${store.empId}/profile`);

const logout =  () => {
  return new Promise((resolve) => {
    Swal.fire({
    title: '確認登出',
    text: '是否確定登出EIPulse人事入口網站',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: '登出',
    cancelButtonText: '取消',
  }).then((result) => {
    if (result.isConfirmed) {
    store.isLogin = false;
    store.permissionId = false;
    sessionStorage.clear();
    router.push({ name: 'login' });
  } 
  resolve;
  console.log("Logout function");
  });
}); 
};
//搜尋菜單
const searchTerm = ref('');
const navItems = [
{label: '主頁',route: `/manage/${store.empId}`},

];
const filteredNavItems= ref([...navItems]);
const filterNavItems= () =>{
  const search = searchTerm.value.toLowerCase.trim();


  const filterNavItems = [
   

  ]
}
</script>

<style scoped>

</style>
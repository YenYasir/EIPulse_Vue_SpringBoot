

<template>
 <!-- Navbar-->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid justify-content-between">
    <!-- Left elements -->
    <div class="d-flex">
 
      <!-- Search form 
      <form class="input-group w-auto my-auto d-none d-sm-flex">
        <input
          autocomplete="off"
          type="search"
          class="form-control rounded"
          placeholder="Search"
          style="min-width: 125px;"
        />
        <span class="input-group-text border-0 d-none d-lg-flex"
          ><i class="fas fa-search"></i
        ></span>
      </form>-->
    </div>
    <!-- Left elements -->

    <!-- Center elements -->
    <ul class="navbar-nav flex-row d-none d-md-flex">
      <li class="nav-item me-3 me-lg-3">
        <router-link :to="getHomeLink" title="主頁">
          <span><i class="bi bi-house" ></i></span>
        </router-link> 
      </li>

      <li class="nav-item me-3 me-lg-3" title="薪資管理">
        <router-link :to="getSalaryLink">
          <span><i class="bi bi-coin"></i></span>
        </router-link> 
      </li>

      <li class="nav-item me-3 me-lg-3" title="簽核">
          <router-link :to="getAuditLink">
          <span><i class="bi bi-pen"></i> </span>
        </router-link> 
      </li>

      <li class="nav-item me-3 me-lg-3" title="福委會">
        <router-link :to="getMallLink">
          <span><i class="bi bi-bag"></i></span>
        </router-link> 
      </li>

      <li class="nav-item me-3 me-lg-3" title="聊天室">
          <router-link :to="getChatLink">
          <span><i class="bi bi-people"></i></span>
            </router-link> 
      </li>
    </ul>
    <!-- Center elements -->

    <!-- Right elements -->
    <ul class="navbar-nav flex-row">
      <li class="nav-item me-3 me-lg-1">
        <router-link :to="getProfileLink" title="個人中心">
           <img src="../assets/images/profile.jpg" class="rounded-circle" height="22" />
         </router-link>
         <strong class="d-none d-sm-block ms-1">{{ empName }}</strong>
      </li>
      <!-- <li class="nav-item me-3 me-lg-1">
        <a class="nav-link" href="#">
          <span><i class="bi bi-patch-plus"></i></span>
        </a>
      </li> -->
      <!-- <li class="nav-item dropdown me-3 me-lg-1">90
        <a
          class="nav-link dropdown-toggle hidden-arrow"
          href="#"
          id="navbarDropdownMenuLink"
          role="button"
          data-mdb-toggle="dropdown"
          aria-expanded="false"
        >
          <i class="bi bi-chat-dots-fill"></i>

        </a>
        <ul
          class="dropdown-menu dropdown-menu-end"
          aria-labelledby="navbarDropdownMenuLink"
        >
          <li>
            <a class="dropdown-item" href="#">Some news</a>
          </li>
          <li>
            <a class="dropdown-item" href="#">Another news</a>
          </li>
          <li>
            <a class="dropdown-item" href="#">Something else here</a>
          </li>
        </ul>
      </li> -->
      <!-- <li class="nav-item dropdown me-3 me-lg-1">
        <a
          class="nav-link dropdown-toggle hidden-arrow"
          href="#"
          id="navbarDropdownMenuLink"
          role="button"
          data-mdb-toggle="dropdown"
          aria-expanded="false"
        >
        <i class="bi bi-bell-fill">
          <span class="badge rounded-pill badge-notification bg-danger">12</span></i>
        </a>
        <ul
          class="dropdown-menu dropdown-menu-end"
          aria-labelledby="navbarDropdownMenuLink"
        >
          <li>
            <a class="dropdown-item" href="#">Some news</a>
          </li>
          <li>
            <a class="dropdown-item" href="#">Another news</a>
          </li>
          <li>
            <a class="dropdown-item" href="#">Something else here</a>
          </li>
        </ul>
      </li> -->
      <!-- <li class="nav-item dropdown me-3 me-lg-1">
        <a
          class="nav-link dropdown-toggle hidden-arrow"
          href="#"
          id="navbarDropdownMenuLink"
          role="button"
          data-mdb-toggle="dropdown"
          aria-expanded="false"
        >
        <i class="bi bi-arrow-down-circle-fill"></i>
        </a>
        <ul
          class="dropdown-menu dropdown-menu-end"
          aria-labelledby="navbarDropdownMenuLink"
        >
          <li>
            <a class="dropdown-item" href="#">Some news</a>
          </li>
          <li>
            <a class="dropdown-item" href="#">Another news</a>
          </li>
          <li>
            <a class="dropdown-item" href="#">Something else here</a>
          </li>
        </ul>
      </li> -->
    </ul>
    <!-- Right elements -->
  </div>
</nav>
<!-- Navbar -->
</template>
<script setup>
import { computed } from 'vue';
import {empStore} from "../stores/employee.js";
import ClockTime from "./clocktime/ClockTime.vue";
import {nextTick, ref} from "vue";
import router from "../router/index.js";



const clockModal = ref(null);
const store = empStore();
const empId = store.empId;
const empName = store.empName;
const props = defineProps({
  homePath:{
    type:String,
    required:true
  }
})
const getHomeLink = computed(() => {
      return `/manage/${empId}`; 
    })
   
const getProfileLink = computed(() =>  {
   return  `/manage/${empId}/profile`;
})
const getAuditLink = computed(() => {
      return `/manage/form/audit`; 
    })

const getSalaryLink = computed(() =>{
  return `/salary`;
})

const getMallLink = computed(()=>{
    return `/mall`;
})

const getChatLink = computed(()=>{
  return `/manage/chats`;
}) 
const logout = () => {
  store.isLogin = false;
  sessionStorage.clear();
  router.push({name: 'login'})
}
const showModal = () => {
  nextTick(() => {
    let modelEl = clockModal.value.$el;
    let modal = new bootstrap.Modal(modelEl, {});
    modal.show();
  })
}


</script>

<style scoped>

</style>
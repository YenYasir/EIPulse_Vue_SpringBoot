
<template>
  <clock-time ref="clockModal"></clock-time>
  <div class="d-flex flex-column p-0 bg-dark text-white"  :class="{ 'sidebar': sidebarVisible }" 
       style="width: 250px; height: 100vh;overflow-y: auto;">
    <h3 class=" rounded-4 text-center " style="line-height: 2 ;background-color: black ; color:rgb(255, 204, 0);" >
      <img src="../assets/logo/logo.png" height="40" alt="Logo" loading="lazy"/>
      EIPulse
    </h3>
    <ul v-if="!isCollapsed" class="nav nav-pills flex-column mb-auto">
      <li class="nav-item">
        <router-link :to="homePath" class="nav-link text-warning">
          <i class="bi bi-house-door">主頁</i>
        </router-link>
      </li>
      <li class="nav-item">
        <!-- <button class="nav-link text-warning" @click="showModal">
          <i class="bi bi-clock">打卡</i>
        </button>  -->
      </li>
      <!-- ... 其他選項 ... -->
      <slot></slot>
      <!-- 下拉選單 -->
      <!-- ... 其他選項 ... -->
    </ul>
  </div>
 

</template>
<script setup>
import Swal from 'sweetalert2';
import DropDown from "./DropDown.vue";
import {empStore} from "../stores/employee.js";

import ClockTime from "./clocktime/ClockTime.vue";
import {nextTick, ref} from "vue";
import router from "../router/index.js";


const clockModal = ref(null);
const store = empStore();
const props = defineProps({
  homePath:{
    type:String,
    required:true
  }
})



const showModal = () => {
    nextTick(() => {
      let modelEl = clockModal.value.$el;
      let modal = new bootstrap.Modal(modelEl, {});
      modal.show();
  });
}





</script>


<style scoped>
.hamburger-icon {
  display: none; 
}

.sidebar {
  transition: transform 0.3s ease;
}

.sidebar.collapsed {
  transform: translateX(-100%);
}

@media screen and (max-width: 767px) {
  .sidebar {
    transform: translateX(-100%); /* Initially collapsed on smaller screens */
  }

  .hamburger-icon {
    display: block; 
  }
}

.dropdown-menu {
  display: none;
}
.dropdown {
  display: none;
}
@media screen and (max-width: 767px) {
  .dropdown-menu {
    display: block;
  }
}
</style>
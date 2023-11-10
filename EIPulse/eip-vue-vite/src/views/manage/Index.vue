<template>
  <div class="d-flex">
    <aside-bar :home-path="`/manage/${emp.empId}`" style="flex: 1">
      <drop-down
        title="員工管理"
        iconName="person-circle"
        :items="[
          { name: '員工查詢', path: '/employee/find-emp' },
          { name: '員工資料新增', path: '/employee/add-emp' },
          { name: '員工權限異動列表', path: '/employee/permission' },
          { name: '離職員工紀錄', path: '/employee/resign-record' },
          { name: '員工緊急聯絡人列表', path: '/employee/emergencyList' },
        ]"
        menuId="submenu0"
      ></drop-down>
      <drop-down
        title="部門管理"
        iconName="people"
        :items="[
          { name: '部門查詢', path: '/employee/find-dept' },
          { name: '依部門查詢員工', path: '/employee/alldeptpepole' },
          { name: '部門資料新增', path: '/employee/add-dept' },
          { name: '部門異動紀錄', path: '/employee/dept-move' },
          { name: '權限管理', path: '/employee/permission' },
          { name: '權限異動紀錄', path: '/employee/permission/history' },
        ]"
        menuId="submenu1"
      ></drop-down>
      <drop-down
        title="薪資管理"
        iconName="coin"
        :items="[{ name: '薪資設定', path: '/salary' }]"
        menuId="submenu2"
      ></drop-down>
      <drop-down
        title="表單簽核"
        iconName="pen"
        :items="[
          { name: '申請表單', path: '/manage/form/apply' },
          { name: '審核表單', path: '/manage/form/audit' },
          { name: '檢視表單', path: '/manage/form/myform' },
        ]"
        menuId="submenu3"
      ></drop-down>
      <drop-down
        title="出勤管理"
        iconName="clipboard2-check"
        :items="[
          { name: '出席管理', path: '/manage/attendance' },
          { name: '打卡管理', path: '/manage/clocktime' },
          { name: '個人出席紀錄', path: `/manage/${emp.empId}/attendance` },
          { name: '個人打卡記錄', path: `/manage/${emp.empId}/clocktime` },
        ]"
        menuId="submenu4"
      ></drop-down>
      <drop-down
        title="聊天室"
        iconName="chat-left-text"
        :items="[
          { name: '聊天室', path: '/manage/chats' },
          { name: '私訊聊天室', path: '/manage/privateChats' },
        ]"
        menuId="submenu5"
      ></drop-down>
      <drop-down
        title="福委會資訊"
        iconName="shop-window"
        :items="[
          { name: '商品新增', path: '/manage/mall/product/save' },
          { name: '所有商品', path: '/manage/mall/product' },
          { name: '訂單管理', path: '/manage/mall/order' },
          { name: '商城首頁', path: '/mall' },
        ]"
        menuId="submenu6"
      >
        <li class="nav-item">
          <button class="nav-link text-white" @click="showSaveType">
            新增類別
          </button>
        </li>
      </drop-down>
      <drop-down title="行事曆" iconName="calendar-check" :items="[
        { name: '行事曆', path: '/manage/calendar' },
      ]" menuId="submenu7"></drop-down>
    </aside-bar>
    <section style="flex: 3" class="shadow-sm">
      <nav-bar></nav-bar>

      <index-clock-time
        v-if="emp.showClock"
        class="d-flex justify-content-end"
      ></index-clock-time>
      <router-view class="mx-auto"></router-view>
    </section>
  </div>
  <product-type ref="showModal"></product-type>
</template>

<script setup>
import AsideBar from "../../components/AsideBar.vue";
import { empStore } from "../../stores/employee.js";
import DropDown from "../../components/DropDown.vue";
import NavBar from "../../components/NavBar.vue";
import { nextTick, onBeforeMount, reactive, ref } from "vue";
import ProductType from "../../components/mall/ProdcutType.vue";
import axios from "axios";
import Swal from "sweetalert2";
import IndexClockTime from "../../components/clocktime/IndexClockTime.vue";

const emp = empStore();
const showModal = ref(null);
const center = reactive({ lat: 22.99297785113601, lng: 120.18681223016014 });
const userLocation = navigator.geolocation;

const showSaveType = () => {
  nextTick(() => {
    let modalElemnt = showModal.value.$el;
    let modal = new bootstrap.Modal(modalElemnt, {});
    modal.show();
  });
};
</script>
<style scoped>
section {
  background-color: #f0f0f0;
  max-height: 99vh;
  overflow-x: auto;
}
</style>

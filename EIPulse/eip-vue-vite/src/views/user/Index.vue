<template>
  <div class="d-flex">
    <aside-bar :home-path="`/user/${emp.empId}`" style="flex: 1">
      <drop-down
        title="個人管理"
        iconName="person-circle"
        :items="[
          { name: '薪資查詢', path: `/user/${emp.empId}/paySlip` },
        ]"
        menuId="submenu0"
      ></drop-down>
      <drop-down
        title="出勤管理"
        iconName="bi-clipboard2-check"
        :items="[
          { name: '個人出席紀錄', path: `/user/${emp.empId}/attendance` },
          { name: '個人打卡記錄', path: `/user/${emp.empId}/clocktime` },
        ]"
        menuId="submenu1"
      ></drop-down>
      <drop-down
        title="表單申請"
        iconName="pen"
        :items="[
          { name: '申請表單', path: '/user/form/apply' }
        ]"
        menuId="submenu3"
      ></drop-down>
      <drop-down
        title="聊天室"
        iconName="chat-left-text"
        :items="[
          { name: '員工聊天室', path: '/user/chats' },
          { name: '私訊聊天室', path: '/user/privatechats' },
        ]"
        menuId="submenu5"
      ></drop-down>
      <drop-down
        title="福委會"
        iconName="shop-window"
        :items="[{ name: '商城首頁', path: '/mall' }]"
        menuId="submenu6"
      >
      </drop-down>
      <drop-down title="行事曆" iconName="calendar-check" :items="[
        { name: '行事曆', path: '/user/calendar' },
      ]" menuId="submenu7">
      </drop-down>
      <drop-down title="消息快訊" iconName="info-circle" :items="[
        { name: '消息一覽', path: '/user/:empId/bulletinboard' },
      ]" menuId="submenu8"></drop-down>
    </aside-bar>
    <section style="flex: 3" class="border-0 shadow-sm">
      <nav-bar></nav-bar>
      <h5>777</h5>
      <index-clock-time
      v-if="emp.showClock"
        class="d-flex justify-content-end"
      ></index-clock-time>
      <router-view></router-view>
    </section>
  </div>
</template>

<script setup>
import DropDown from "../../components/DropDown.vue";
import AsideBar from "../../components/AsideBar.vue";
import NavBar from "../../components/NavBar.vue";
import { empStore } from "../../stores/employee.js";
import IndexClockTime from "../../components/clocktime/IndexClockTime.vue";
const emp = empStore();
</script>

<style scoped>
section {
  background-color: #f0f0f0;
  max-height: 100vh;
  overflow-x: auto;
}
</style>

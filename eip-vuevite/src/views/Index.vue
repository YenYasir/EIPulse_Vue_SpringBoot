<script>
import AsideBar from "../components/AsideBar.vue";
import {empStore} from "../stores/employee.js";
import NavBar from "../components/NavBar.vue";
import EditModal from "../components/mall/WindowModal.vue";
import DropDown from "../components/DropDown.vue";

export default {
  components: {DropDown, EditModal, NavBar, AsideBar},

  setup() {
    const store = empStore();
    return {
      empId: store.empId,
      empName: store.empName,
      isLogin: store.isLogin,
    }
  },
  methods: {},
  name: "index"
}
</script>

<template>
  <!--  <nav-bar></nav-bar>-->
  <div class="d-flex">
    <aside-bar>
      <drop-down title="員工管理" iconName="people" :items="[
             { name: '員工查詢', path: '/' },
             { name: '員工資料新增', path: '/'}
           ]" menuId="submenu0"></drop-down>
      <drop-down title="出勤管理" iconName="clock-history" :items="[
             { name: '出席管理', path: '/manage/attendance' },
             { name: '打卡管理', path: '/manage/clocktime' },
             { name: '員工個人出席紀錄', path: '/user/attendance' },
             { name: '員工個人打卡記錄', path: '/user/clocktime' }
           ]" menuId="submenu1"></drop-down>
      <drop-down title="福委會資訊" iconName="shop-window" :items="[
             { name: '後台管理', path: '/mall/manage' }
           ]" menuId="submenu6">
      </drop-down>
    </aside-bar>
    <section class=" w-75" style="margin-left: 250px;">
      <router-view></router-view>
    </section>
  </div>
</template>

<style scoped>


</style>
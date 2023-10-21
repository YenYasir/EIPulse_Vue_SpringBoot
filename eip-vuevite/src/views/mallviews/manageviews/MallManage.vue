<script setup>
import AsideBar from "@/components/AsideBar.vue";
import DropDown from "@/components/DropDown.vue";
import WindowModal from "../../../components/mall/WindowModal.vue";
import {nextTick, ref, reactive, computed, onMounted} from 'vue';
import axios from "axios";
import Swal from "sweetalert2";
import ProductType from "../../../components/mall/ProdcutType.vue";

const showModal = ref(null)
const showSaveType = () => {
  nextTick(() => {
    let modalElemnt = showModal.value.$el;
    let modal = new bootstrap.Modal(modalElemnt, {});
    modal.show();
  })
}
</script>

<template>

  <aside-bar>
    <drop-down title="福委會管理" iconName="shop-window" :items="[
             { name: '商品新增', path: '/mall/manage/product/save' },
             { name: '所有商品', path: '/mall/manage/product' }
           ]" menuId="submenu6">
      <li class="nav-item">
        <button class="nav-link text-muted text-white" @click="showSaveType">新增類別</button>
      </li>
    </drop-down>
  </aside-bar>
  <section class="flex-row w-75">
    <router-view></router-view>
  </section>
  <product-type ref="showModal"></product-type>

</template>

<style>
section {
  margin-left: 300px; /* 與 aside 的寬度相同 */
}

</style>
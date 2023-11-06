
<template>
  <mall-nav-bar></mall-nav-bar>
  <router-view v-if="mall.changeOrderPage"></router-view>
  <product-table v-else></product-table>
</template>

<script setup>
import MallNavBar from "../../components/mall/MallNavBar.vue";
import ProductTable from "../../components/mall/ProductTable.vue";
import {mallStore} from "../../stores/mallStore.js";

const mall = mallStore();

</script>

<style scoped>

</style>
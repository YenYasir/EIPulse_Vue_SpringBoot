<script setup>

import UserCart from "./UserCart.vue";
import { onMounted, ref} from "vue";
import {mallStore} from "../../stores/mallStore.js";
import router from "../../router/index.js";
import {empStore} from "../../stores/employee.js";
const emp = empStore();
const cartModal = ref(null);
const mall = mallStore();
let bootstrapModal = null;

//預先定義modal
onMounted(() => {
  let modelEl = cartModal.value.$el;
  bootstrapModal = new bootstrap.Modal(modelEl, {});
});

//顯示modal
const showCart = () => {
  if (bootstrapModal) {
    bootstrapModal.show();
  }
};

//按下結帳後跳轉至新增訂單結帳畫面
const closeModal = () => {
  if (bootstrapModal) {
    bootstrapModal.hide();
    router.push({name:'create-order'})
  }
};


//跳轉到我的訂單
const changeOrderButton = (change) => {
  mall.setChangeOrderPage(change)
}
</script>

<template>
  <user-cart ref="cartModal" @go-to-create-order="closeModal"></user-cart>
  <nav class="navbar navbar-dark bg-dark mb-3">
    <div class="container-fluid">
      <router-link to="/mall" class="navbar-brand" @click="changeOrderButton(false)">EIPulseMall</router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
              aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link class="nav-link active" aria-current="page" to="/login">Home</router-link>
          </li>
        </ul>
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <a href="#" @click.prevent="showCart" class="nav-link active" aria-current="page">購物車</a>
          </li>
          <li class="nav-item">
            <router-link to="/mall/order" @click="changeOrderButton(true)" class="nav-link active" aria-current="page">
              我的訂單
            </router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped>

</style>
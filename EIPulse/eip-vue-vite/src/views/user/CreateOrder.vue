
<template>
  <card title="訂單建立" class="w-75 mx-auto ">
    <template #body>
      <div class="col">
        <div class="card mx-2 mb-2 h-auto p-3" v-for="cartItem in cart.cartItems">
          <div class="row align-items-center"> <!-- 使用 align-items-center 讓內容垂直置中 -->
            <!-- 圖片區塊 -->
            <div class="col-3">
              <img :src="cartItem.product.imageUrl" class="card-img-top" alt="...">
            </div>

            <!-- 產品名稱區塊 -->
            <div class="col-6">
              <h5 class="card-title">{{ cartItem.product.productName }}</h5>
            </div>

            <!-- 數量輸入框區塊 -->
            <div class="col-3"> <!-- text-end 讓內容靠右 -->
              數量：<input class="input-group-text w-25 " disabled v-model="cartItem.quantity" type="number">
            </div>

          </div>
        </div>
        <p>總價額${{ totalPrice }}</p>
      </div>
    </template>
    <template #page>
      <button @click="ecPlayPage" class="btn btn-secondary mx-1"><i class="bi bi-credit-card-fill">付款</i>
      </button>
    </template>




  </card>
</template>

<script setup>

import {empStore} from "../../stores/employee.js";
import axios from "axios";
import {onMounted, reactive, ref, watchEffect} from "vue";
import Card from "../../components/Card.vue";
import Swal from "sweetalert2";

const totalPrice = ref(0);
const emp = empStore();
const orderDetails = reactive({});
const cart = reactive({});
const getCart = () => {
  axios.get(`http://localhost:8090/eipulse/cart/${emp.empId}`).then((res) => {
    Object.assign(cart, res.data)
    console.log(cart)
  })
}
const getOrderDetails = () => {
  axios.get(`http://localhost:8090/eipulse/order/details/${emp.empId}`).then((res) => {
    Object.assign(orderDetails, res.data);
    console.log(orderDetails)
  })
}

onMounted(() => {
  getCart();
  getOrderDetails();
})

const ecPlayPage = async () => {
  try {
    const res = await axios.post('http://localhost:8090/eipulse/order/ecPlay', {empId: cart.empId})
    await Swal.fire({
      title: '訂單已建即將跳轉至付款畫面',
      timer: 3000,
      timerProgressBar: true,
      icon: 'success'
    })
    let wrapper = document.createElement('div');
    wrapper.innerHTML = res.data;
    document.body.appendChild(wrapper);
    let form = wrapper.querySelector('form');
    if (form) {
      form.submit();
    }
  } catch (e) {
    Swal.fire('錯誤!', '訂單建立失敗', 'error');
    console.log(e)
  }
}

watchEffect(() => {
  let total = 0;
  if (Array.isArray(cart.cartItems)) {
    cart.cartItems.forEach(item => {
      total += item.product.price * item.quantity;
    });
  }
  totalPrice.value = total;
});
</script>

<style scoped>
img {
  width: 50px;
}
</style>
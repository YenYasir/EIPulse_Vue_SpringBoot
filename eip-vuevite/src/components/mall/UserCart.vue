<script setup>
const URL = import.meta.env.VITE_API_JAVAURL;

import WindowModal from "./WindowModal.vue";
import {empStore} from "../../stores/employee.js";
import axios from "axios";
import {onMounted, reactive, ref, watch, watchEffect} from "vue";
import Swal from "sweetalert2";
import {mallStore} from "../../stores/mallStore.js";
import router from "../../router/index.js";

const totalPrice = ref(0);
const mall = mallStore();
const emp = empStore();
const cart = reactive({});
const emit = defineEmits(['goToCreateOrder'])
const getCart = () => {
  axios.get(`${URL}cart/${emp.empId}`).then((res) => {
    Object.assign(cart, res.data)
    console.log(cart)
  })
}
const removeItem = async (cartItemId) => {
  try {
    const res = await axios.delete(`${URL}cartItem/${cartItemId}`)
    Swal.fire({
      title: '刪除成功',
      timer: 1000,
      timerProgressBar: true,
      icon: 'success'
    })
    getCart();
  } catch (e) {
    Swal.fire('錯誤!', '刪除失敗', 'error');
  }
}
watch(() => mall.addCartItem, (newVal, oldVal) => {
  if (newVal === true) {
    getCart();
    mall.setAddCartItem(false)
  }
})

const updateQuantity = (cartItem) => {
  axios.put(`${URL}cartItem`, cartItem);
}
onMounted(() => {
  getCart();
})
watchEffect(() => {
  let total = 0;
  if (Array.isArray(cart.cartItems)) {
    cart.cartItems.forEach(item => {
      total += item.product.price * item.quantity;
    });
  }
  totalPrice.value = total;
});

const getOrderPage =async () => {
  if(cart.cartItems && cart.cartItems.length > 0){
    const result = await Swal.fire({
      title: '請確認購物車內容',
      text: '即將跳轉至結帳畫面',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: '確定',
      cancelButtonText: '取消'
    })
    if(result.isConfirmed){
      //按下去結帳後發送事件給 MallNavBar.vue 進行觸發跳轉
      emit('goToCreateOrder')
    }
  }else {
    Swal.fire('錯誤!', '空空如也，什麼也沒有', 'error');
  }
}

</script>

<template>
  <window-modal  button-name="去結帳" title-name="購物車" class="modal-xl" @submit="getOrderPage">
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
            數量：<input class="input-group-text w-25 " type="number" v-model="cartItem.quantity"
                        @input="updateQuantity(cartItem)" min="1"
                        @keydown.prevent>
          </div>

          <div class="col-12 text-end">
            <button class="btn btn-secondary mx-1" @click="removeItem(cartItem.cartItemId)">移除</button>
          </div>
        </div>
      </div>
      <p>總價額${{ totalPrice }}</p>
    </div>
  </window-modal>
</template>


<style scoped>
img {
  width: 50px;
}
</style>

<template>
  <div class="row mx-4">
    <div class="card row mx-2 mb-2 h-auto p-3" style="width: 14rem;" v-for="product in filteredProducts">
      <img :src="product.imageUrl" class="card-img-top" alt="...">
      <div class="card-body">
        <h5 class="card-title">{{ product.productName }}</h5>
        <p class="card-text">{{ product.description }}</p>
      </div>
      <p class="card-text">價格：{{ product.price }}元</p>
      數量：<input class="input-group-text" type="number" :value="1" min="1" max="5" @keydown.prevent
                  @change="updateQuantity($event)">
      <button href="#" class="btn btn-primary mt-2" @click="addCartItem(product)">加入購物車</button>
    </div>
  </div>
</template>

<script setup>
import {computed, onMounted, reactive} from "vue";
import axios from "axios";
import {empStore} from "../../stores/employee.js";
import {mallStore} from "../../stores/mallStore.js";
import Swal from "sweetalert2";

const emp = empStore();
const mall =mallStore();
const products = reactive([]);
const cartItem = reactive({
  productId: null,
  empId: emp.empId,
  quantity: 1
});
const loadProducts = async () => {

  try {
    const res = await axios.get('http://localhost:8090/eipulse/products')
    products.length = 0
    products.push(res.data)
    console.log(products)
  } catch (e) {
    console.log(e)
  }
}

const addCartItem = async (product) => {
  if (cartItem.quantity <= 0) {
    Swal.fire('錯誤!', '數量不得為0', 'error');
    return
  }
  cartItem.productId = product.id
  console.log(cartItem)
  // cartItem.quantity
  try {
    await axios.post('http://localhost:8090/eipulse/cartItem',
        cartItem
    )
    mall.setAddCartItem(true);

    Swal.fire({
      title: '已新增至購物車',
      timer: 1000,
      timerProgressBar: true,
      icon: 'success'
    })
  } catch (e) {
    Swal.fire('錯誤!', '加入購物車失敗', 'error');
    console.log(e)
  }
}
const updateQuantity = (event) => {
  cartItem.quantity = event.target.value;
  console.log(cartItem.quantity)
}
const filteredProducts = computed(() => {
  return products[0]?.filter(product => product.status === '上架') || [];
});

onMounted(() => {
  loadProducts();
})
</script>

<style scoped>
img {
  width: 100px;
  object-fit: cover;
}
</style>

<template>
  <div class="row">
    <div class="col">
      <div class="form-floating mb-3">
        <input v-model="product.productName" @input="productSave" type="text" class="form-control" id="productName" placeholder="產品名稱">
        <label for="productName">產品名稱</label>
      </div>
    </div>
    <div class="col">
      <div class="form-floating">
        <input v-model="product.price" @input="productSave" type="text" class="form-control mb-2" id="price"
               placeholder="單價">
        <label for="price">單價</label>
      </div>
    </div>
    <div class="col">
      <div class="form-floating">
        <input v-model="product.description" @input="productSave" type="text" class="form-control mb-2" id="description"
               placeholder="商品說明">
        <label for="description">商品說明</label>
      </div>
    </div>
    <div class="col">
      <div class="form-floating">
        <input v-model="product.stockQuantity" @input="productSave" type="text" class="form-control mb-2" id="stockQuantity"
               placeholder="庫存量">
        <label for="stockQuantity">庫存量</label>
      </div>
    </div>
    <div class="col">
      <div class="form-floating">
        <input v-model="product.imageUrl" @input="productSave" type="text" class="form-control mb-2" id="imageUrl"
               placeholder="圖片連結">
        <label for="imageUrl">圖片連結</label>
      </div>
    </div>
  </div>
</template>
<script setup>

import { watch, defineProps } from 'vue';

const props = defineProps(['editProduct']);
let product = props.editProduct;

watch(() => props.editProduct, (newValue, oldValue) => {
      product = newValue;
    }
);
</script>


<style scoped>

</style>
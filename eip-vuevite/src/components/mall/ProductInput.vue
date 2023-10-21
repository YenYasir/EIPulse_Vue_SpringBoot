<script>
import axios from "axios";
import login from "../../views/Login.vue";

export default {
  data() {
    return {
      productTypes: [],
      selectTypeId:"",
      product: {
        productName: '',
        subTypeId: "",
        description: '',
        price: 0,
        stockQuantity: 0,
        imageUrl: '',
      },
    }
  },
  methods: {
    productSave() {
      console.log(this.product)
      this.$emit('product-update', this.product);
    }
  },
  computed:{
    selectType(){
      console.log(this.selectTypeId)
      return this.productTypes.find(type=>type.id === this.selectTypeId) ||{};
    },
  },
  watch:{
    selectType(newType){
      if(newType.subTypes && newType.subTypes.length > 0){
        this.product.subTypeId = newType.subTypes[0].id;
      }else {
        this.product.subTypeId="";
      }
    }
  },
   mounted() {
     axios.get('http://localhost:8090/eipulse/productTypes').then(response=>{
       this.productTypes = response.data;
       console.log(this.productTypes)
     }).catch((e)=>{
       console.log(e)
     })
  },
  name: 'ProductForm'
}
</script>

<template>
  <div class="row">
    <div class="col">
      <div class="form-floating mb-3">
        <select class="form-control" v-model="selectTypeId">
          <option value="" disabled>選擇主類別</option>
          <option v-for="type in productTypes" :value="type.id" :key="type.id">{{type.typeName}}</option>
        </select>
      </div>
    </div>
    <div class="col">
      <div class="form-floating mb-3">
        <select class="form-control" v-model="product.subTypeId" @input="productSave">
          <option value="" disabled>選擇子類別</option>
          <option v-for="subType in selectType.subTypes" :value="subType.id" :key="subType.id">{{ subType.subName }}</option>
        </select>
      </div>
    </div>
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

<style scoped>

</style>

<template>
  <div class="card text-center div1" >
    <div class="card-header">新增商品</div>
    <div class="card-body">
      <form @submit.prevent="productsSave">
        <div v-for="(n,index) in formCount" :key="n">
          <span>No.{{ n }}</span>
          <product-input @product-update="product =>updateProduct(product,index)"></product-input>
        </div>
        <div class="row mb-4">
          <button @click.prevent="addFrom" class="btn btn-outline-primary ">+</button>
        </div>
        <input class="btn btn-secondary mb-4" type="submit" value="新增">
      </form>
    </div>
    <div class="card-footer text-body-secondary">
      Copyright ©  EIPulse科技 All Rights Reserved.
    </div>
  </div>
</template>

<script>
import ProductInput from "../../components/mall/ProductInput.vue";
import axios from "axios";
import index from "./Index.vue";
import Swal from "sweetalert2";
import Papa from 'papaparse';

export default {
  computed: {
    index() {
      return index
    }
  },
  data() {
    return {
      formCount: 1,
      products:[],
    }
  },
  methods: {
    updateProduct(product,index){
      console.log(this)
      this.products[index] = product;
    },
    async productsSave() {
      try {
        const response=await axios.post('http://localhost:8090/eipulse/products',this.products)
        await Swal.fire({
          title: '產品新增成功',
          timer: 1000,
          timerProgressBar: true,
          icon: 'success'
        })
        this.$router.push({name:'product'})
      }catch (e){
        Swal.fire({
          title: '產品資訊錯誤請確認',
          icon: 'error'
        })
      }
    },
    addFrom() {
      this.formCount++;
    },
  },
  components: {ProductInput}
}
</script>

<style scoped>

</style>
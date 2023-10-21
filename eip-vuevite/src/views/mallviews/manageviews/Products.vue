<script>
import axios from "axios";
import Swal from "sweetalert2";
import EditInput from "../../../components/mall/EditInput.vue";
import WindowModal from "../../../components/mall/WindowModal.vue";
import ProductInput from "../../../components/mall/ProductInput.vue";

export default {
  data() {
    return {
      productTypes: [],
      isEdit: false,
      editProduct: {},
    }
  },
  methods: {
    async deleteProduct(id) {
      const result = await Swal.fire({
        title: '確定要刪除此商品嗎?',
        text: '您將無法還原操作',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: '確定',
        cancelButtonText: '取消'
      });
      if (result.isConfirmed) {
        try {
          await axios.delete(`http://localhost:8090/eipulse/product/${id}`);
          Swal.fire('已刪除!', '您的商品已被刪除。', 'success');
          this.loadProducts();
        } catch (e) {
          Swal.fire('錯誤!', '商品刪除失敗。', 'error');
        }
      }
    },
    loadProducts() {
      axios.get('http://localhost:8090/eipulse/productTypes').then(res => {
        this.productTypes = res.data;
      }).catch((e) => {
        console.log(e)
      })
    },
    startEdit(product) {
      this.editProduct = product;
      this.$nextTick(() => {
        let modalElemnt = this.$refs.editModal.$el;
        let modal = new bootstrap.Modal(modalElemnt, {});
        modal.show();
      })
    },
    async editSubmit() {
      try {
        const response = await axios.put('http://localhost:8090/eipulse/product', this.editProduct)
        Swal.fire({
          title: '修改成功',
          timer: 1000,
          timerProgressBar: true,
          icon: 'success'
        }).then(() => {
          let modalElemnt = this.$refs.editModal.$el;
          let modal = bootstrap.Modal.getInstance(modalElemnt);
          if (modal) {
            modal.hide();
          }
        })
      } catch (e) {
        Swal.fire({
          title: '修改失敗請確認',
          icon: 'error'
        })
        console.log(e)
      }
    },
    hasProducts(productType) {
      if (!productType || !Array.isArray(productType.subTypes)) return false;
      return productType.subTypes.some(subType => Array.isArray(subType.products) && subType.products.length > 0);
    }
  },
  watch: {},
  computed: {},
  name: "Products",
  components: {WindowModal, EditInput, ProductInput},
  mounted() {
    this.loadProducts();
  }
}
</script>

<template>
  <div class="card text-center  rounded ">
    <div class="card-header">所有商品</div>
    <div class="card-body ">
      <table class="table table-bordered" v-for="productType in productTypes" :key="productType.id">
        <thead>
        <tr v-if="hasProducts(productType)">
          <th>主類別</th>
          <th>子類別</th>
          <th>產品名稱</th>
          <th>產品價格</th>
          <th>庫存</th>
          <th>產品介紹</th>
          <th>產品圖片</th>
          <th>最後更新時間</th>
          <th>編輯</th>
        </tr>
        </thead>
        <tbody v-for="subType in productType.subTypes">
        <tr v-for="product in subType.products">
          <td>{{ productType.typeName }}</td>
          <td>{{ subType.subName }}</td>
          <td>{{ product.productName }}</td>
          <td>{{ product.price }}</td>
          <td>{{ product.stockQuantity }}</td>
          <td>{{ product.description }}</td>
          <td><img :src="product.imageUrl"></td>
          <td>{{ product.updatedAt }}</td>
          <td>
            <button class="btn btn-secondary mx-1" @click="startEdit(product)"><i class="bi bi-pencil-square"></i>
            </button>
            <button class="btn btn-secondary mx-1" @click="deleteProduct(product.id)"><i class="bi bi-bucket"></i>
            </button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="card-footer text-body-secondary">
      ©2023 EIPulse
    </div>
    <window-modal @submit="editSubmit" class="modal-xl" titleName="產品編輯" ref="editModal">
      <form>
        <edit-input :editProduct="editProduct"></edit-input>
      </form>
    </window-modal>
  </div>

</template>

<style scoped>

img {
  width: 50px;
}
</style>
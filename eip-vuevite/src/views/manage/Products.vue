

<template>
  <div class="card text-center  rounded ">
    <div class="card-header">所有商品</div>
    <div class="card-body ">
      <table class="table table-bordered">
        <thead>
        <tr >
          <th>主類別</th>
          <th>子類別</th>
          <th>產品名稱</th>
          <th>產品價格</th>
          <th>庫存</th>
          <th>產品介紹</th>
          <th>產品圖片</th>
          <th>最後更新時間</th>
          <th>狀態</th>
          <th>編輯</th>
        </tr>
        </thead>
        <tbody v-for="productType in productTypes" >
        <tr >
          <td>{{ productType.productTypeName}}</td>
          <td>{{ productType.subTypeName }}</td>
          <td>{{ productType.productName }}</td>
          <td>{{ productType.price }}</td>
          <td>{{ productType.stockQuantity }}</td>
          <td>{{ productType.description }}</td>
          <td><img :src="productType.imageUrl"></td>
          <td>{{ productType.updatedAt }}</td>
          <td>
            <button class="btn btn-secondary mx-1"  @click="changeStatus(productType)">{{ productType.status }}
            </button>
          </td>
          <td>
            <button class="btn btn-secondary mx-1" @click="startEdit(productType)"><i class="bi bi-pencil-square"></i>
            </button>
            <button class="btn btn-secondary mx-1" @click="deleteProduct(productType.id)"><i class="bi bi-bucket"></i>
            </button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="card-footer text-body-secondary">
      <page :total-pages="this.totalPages" :current-page="this.currentPage" @select-page='changePage'></page>
      ©2023 EIPulse
    </div>
    <window-modal @submit="editSubmit" class="modal-xl" titleName="產品編輯" ref="editModal">
      <form>
        <edit-input :editProduct="editProduct"></edit-input>
      </form>
    </window-modal>
  </div>

</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
import EditInput from "../../components/mall/EditInput.vue";
import WindowModal from "../../components/mall/WindowModal.vue";
import ProductInput from "../../components/mall/ProductInput.vue";
import Page from "../../components/Page.vue";

export default {
  data() {
    return {
      productTypes: [],
      isEdit: false,
      editProduct: {},
      status:'上架',
      totalPages:0,
      currentPage:1,
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
      axios.get(`http://localhost:8090/eipulse/products/${this.currentPage}`).then(res => {
        this.productTypes = res.data.content;
        this.totalPages = res.data.totalPages;
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
    async changeStatus(productType){
      if(productType.status ==='上架'){
        this.status = '下架'
      }else {
        this.status='上架'
      }
      const result = await Swal.fire({
        title: `目前狀態為${productType.status},確定要${this.status}商品嗎?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: '確定',
        cancelButtonText: '取消'
      })
      try {
        if(result.isConfirmed){
          const res = await  axios.put('http://localhost:8090/eipulse/product/status',{
            id:productType.id
          })
          this.loadProducts();
          Swal.fire({
            title: '狀態更改成功',
            timer: 1000,
            timerProgressBar: true,
            icon: 'success'
          })
        }
      }catch (e){
        Swal.fire('錯誤!', '狀態更改失敗。', 'error');
      }
    },
    changePage(selectPage){
      this.currentPage = selectPage;
      this.loadProducts();
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
  name: "Products",
  components: {Page, WindowModal, EditInput, ProductInput},
  mounted() {
    this.loadProducts();
  }
}
</script>

<style scoped>

img {
  width: 50px;
}
</style>
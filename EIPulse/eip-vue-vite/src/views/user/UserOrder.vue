
<template>
  <window-modal ref="orderModal" button-name="取貨" @submit="pickUpOrder(order)" title-name="訂單明細">
    <div class="col">
      <div class="card mx-2 mb-2 h-auto p-3" v-for="orderItem in order.orderItems">
        <div class="row align-items-center" > <!-- 使用 align-items-center 讓內容垂直置中 -->
          <!-- 圖片區塊 -->
          <div class="col-3">
            <img :src="orderItem.product.imageUrl" class="card-img-top" alt="...">
          </div>

          <!-- 產品名稱區塊 -->
          <div class="col-6">
            <h5 class="card-title">{{ orderItem.product.productName }}</h5>
          </div>

          <!-- 數量輸入框區塊 -->
          <div class="col-3"> <!-- text-end 讓內容靠右 -->
            <p>數量：{{orderItem.quantity}}</p>
          </div>

        </div>
      </div>
    </div>
  </window-modal>


  <card title="我的訂單" class="w-75 mx-auto">
    <template #findSearch>
      <user-find-search  @sendSearchDate="getEmpOrder"></user-find-search>
    </template>
    <template #body>
      <table class="table table-hover">
        <thead>
        <tr>
          <th>訂單編號</th>
          <th>總金額</th>
          <th>狀態</th>
          <th>建立時間</th>
          <th>查詢</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="item in orders">
          <td>{{ item.orderId }}</td>
          <td>{{ item.totalPrice }}</td>
          <td>{{ item.status }}</td>
          <td>{{ item.createdAt }}</td>
          <td><button class="btn btn-secondary mx-1" @click="getOrderItem(item.orderId)">明細</button></td>
        </tr>
        </tbody>
      </table>
    </template>
    <template #page>
      <page :total-pages="totalPages" :current-page="currentPage" @select-page="updateCurrentPage"></page>
    </template>
  </card>

</template>

<script setup>
import Card from "../../components/Card.vue";
import axios from "axios";
import {empStore} from "../../stores/employee.js";
import {onMounted, reactive,ref} from "vue";
import index from "../manage/Index.vue";
import WindowModal from "../../components/mall/WindowModal.vue";
import Swal from "sweetalert2";
import Page from "../../components/Page.vue";
import FindSearch from "../../components/clocktime/FindSearch.vue";
import UserFindSearch from "../../components/clocktime/UserFindSearch.vue";

const emp = empStore()
const orders = ref({});
const order = reactive({});
const orderModal =ref(null);
const URL = import.meta.env.VITE_API_JAVAURL;
const totalPages = ref(0);
const currentPage = ref(1);
const currentSearchDate = ref(null); // 用於保存當前的搜索條件
const getEmpOrder=(date)=>{
  currentSearchDate.value =date;
  axios.get(`${URL}order/${emp.empId}/${date.startDate}/${date.endDate}/${currentPage.value}`).then((res)=>{
    orders.value= res.data.content;
    totalPages.value = res.data.totalPages
  }).catch((e)=>{

  })
}
const getOrderItem=(orderId)=>{
  axios.get(`http://localhost:8090/eipulse/order/item/${orderId}`).then((res)=>{
    Object.assign(order,res.data)
    showModal();
  }).catch((e)=>{

  })
}
const showModal=()=>{
  let modelEl = orderModal.value.$el;
  let  bootstrapModal = new bootstrap.Modal(modelEl, {});
  bootstrapModal.show();
}
const pickUpOrder= async (order)=>{

  try {
    if(order.status ==='可取貨'){
      const result = await Swal.fire({
        title: `請確認已至福委課領取商品`,
        text:'按下後將完成訂單',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: '確定',
        cancelButtonText: '取消'
      })
      if(result.isConfirmed) {
        order.status ='已取貨'
        const res =await axios.put('http://localhost:8090/eipulse/order',order)
        getEmpOrder(currentSearchDate.value);
        Swal.fire({
          title: '取貨成功',
          timer: 1000,
          timerProgressBar: true,
          icon: 'success'
        })
      }
    }else if(order.status ==='已取貨'){
      Swal.fire('錯誤!', '已取貨，等待福委課完成訂單', 'error');
    }else {
      Swal.fire('錯誤!', '未付款、訂單未準備完成', 'error');
    }
  }catch (e){
    Swal.fire('錯誤!', '訂單異常，請聯絡福委課。', 'error');
  }
}
const updateCurrentPage = (newPage) => {
  currentPage.value = newPage;
  getEmpOrder(currentSearchDate.value)
  // 這裡加載新頁面的數據
};

onMounted(()=>{
})
</script>

<style scoped>

</style>
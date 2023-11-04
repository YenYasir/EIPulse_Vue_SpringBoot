<script setup>
import Card from "../../components/Card.vue";
import axios from "axios";
import {empStore} from "../../stores/employee.js";
import {onMounted, reactive,ref} from "vue";
import index from "../manage/Index.vue";
import WindowModal from "../../components/mall/WindowModal.vue";
import Swal from "sweetalert2";
const URL = import.meta.env.VITE_API_JAVAURL;

const emp = empStore()
const orders = reactive({});
const order = reactive({});
const orderModal =ref(null);
const getEmpOrder=()=>{
  axios.get(`${URL}order/${emp.empId}`).then((res)=>{
    Object.assign(orders,res.data)
    console.log(orders)
  }).catch((e)=>{

  })
}
const getOrderItem=(orderId)=>{
  axios.get(`${URL}order/item/${orderId}`).then((res)=>{
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
        const res =await axios.put(`${URL}order`,order)
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

onMounted(()=>{
  getEmpOrder();
})
</script>

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
  </card>

</template>

<style scoped>

</style>
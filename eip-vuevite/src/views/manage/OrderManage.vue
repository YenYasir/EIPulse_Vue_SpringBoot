<script setup>
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import Card from "../../components/Card.vue";
import WindowModal from "../../components/mall/WindowModal.vue";
import Swal from "sweetalert2";
const URL = import.meta.env.VITE_API_JAVAURL;

const orders = reactive({});
const orderModal =ref(null);
const order = reactive({});
const status=ref("");
//獲取全部訂單
const getOrders=()=>{
  axios.get(`${URL}orders`).then((res)=>{
    Object.assign(orders,res.data)
    console.log(orders)
  }).catch((e)=>{

  })
}
//獲取單個訂單明細
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

const changeStatus=async ()=>{
  if(order.status ==='已付款'){
    status.value='可取貨'
  }else if(order.status ==='未付款'){
    status.value='已付款'
  }else {
    status.value='完成訂單'
  }
 try {
   const result = await Swal.fire({
     title: `目前狀態為${order.status},確定要更改為${status.value}嗎?`,
     icon: 'warning',
     showCancelButton: true,
     confirmButtonText: '確定',
     cancelButtonText: '取消'
   })
   if(result.isConfirmed) {
     order.status = status.value;
     const res =await axios.put(`${URL}order`,order)
     getOrders();
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

}

onMounted(()=>{
  getOrders();
})

</script>

<template>
  <window-modal ref="orderModal" button-name="更改狀態" @submit="changeStatus" title-name="訂單明細">
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


  <card title="訂單管理" >
    <template #body>
      <table class="table table-hover">
        <thead>
        <tr>
          <th>訂單編號</th>
          <th>員工編號</th>
          <th>總金額</th>
          <th>狀態</th>
          <th>建立時間</th>
          <th>狀態編輯</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="item in orders">
          <td>{{ item.orderId }}</td>
          <td>{{ item.empId }}</td>
          <td>{{ item.totalPrice }}</td>
          <td>{{ item.status }}</td>
          <td>{{ item.createdAt }}</td>
          <td> <button class="btn btn-secondary mx-1" @click="getOrderItem(item.orderId)"><i class="bi bi-pencil-square"></i>
          </button></td>
        </tr>
        </tbody>
      </table>
    </template>
  </card>
</template>

<style scoped>

</style>
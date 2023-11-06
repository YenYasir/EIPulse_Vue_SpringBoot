
<template>
  <form @submit.prevent="addHandler">
    <div class="container">
      <h2>新增員工資料</h2>
      <div class="row">

        <!-- 員工姓名 -->
        <div class="col-md-3">
          <label for="empName" class="form-label">員工姓名</label>
          <input type="text" class="form-control" id="empName" v-model="emp.empName" required>
        </div>

        <!-- 生日 -->
        <div class="col-md-3">
          <label for="birth" class="form-label">生日</label>
          <input type="date" class="form-control" id="birth" v-model="emp.birth" required>
        </div>

        <!-- Email -->
        <div class="col-md-3">
          <label for="email" class="form-label">Email</label>
          <input type="email" class="form-control" id="email" v-model="emp.email" required>
        </div>

        <!-- 身分證號碼 -->
        <div class="col-md-3">
          <label for="idNumber" class="form-label">身分證號</label>
          <input type="text" class="form-control" id="idNumber" v-model="emp.idNumber" required>
        </div>

        <!-- 性別 -->
        <div class="col-md-3">
          <label class="form-label">性別</label>
          <select class="form-control" v-model="emp.gender" required>
            <option value="男">男</option>
            <option value="女">女</option>
          </select>
        </div>

        <!-- 電話 -->
        <div class="col-md-3">
          <label for="phone" class="form-label">手機</label>
          <input type="tel" class="form-control" id="phone" v-model="emp.phone" required>
        </div>

        <!-- 座機 -->
        <div class="col-md-3">
          <label for="tel" class="form-label">室內電話</label>
          <input type="tel" class="form-control" id="tel" v-model="emp.tel">
        </div>

        <!-- 圖片網址 -->
        <div class="col-md-3">
          <label for="photoUrl" class="form-label">員工照片</label>
          <input class="form-control form-control-sm" id="photoUrl" type="file" @change="handleFileUpload">
        </div>

        <!-- 縣市 -->
        <div class="col-md-3">
          <label for="city" class="form-label">縣市</label>
          <select class="form-control" id="city" v-model="selectedCity" @change="watchEffect()">
            <option v-for="city in addressAPI" :key="city.CityName" :value="city.CityName">{{ city.CityName }}</option>
          </select>
        </div>

        <!-- 鄉鎮 -->
        <div class="col-md-3">
          <label for="town" class="form-label">鄉鎮</label>
          <select class="form-control" id="town" v-model="selectedTown">
            <option v-for="town in towns" :key="town" :value="town.AreaName">{{ town.AreaName }}</option>
          </select>
        </div>

        <!-- 路名地址 -->
        <div class="col-md-3">
          <label for="fullAddress" class="form-label">路名地址</label>
          <input type="text" class="form-control" id="fullAddress" v-model="detailAddress" required>
        </div>

        <!-- 職位 -->
        <!-- 需要再增加選擇部門+對應職位 -->
        <div class="col-md-3">
          <label for="titleId" class="form-label">職位</label>
          <input type="text" class="form-control" id="titleId" v-model="emp.titleId" required>
        </div>

        <!-- 入職日 -->
        <div class="col-md-3">
          <label for="hireDate" class="form-label">入職日</label>
          <input type="date" class="form-control" id="hireDate" v-model="emp.hireDate" required>
        </div>

        <!-- 按鈕 -->
        <div class="col-12">
          <button type="submit" class="btn btn-primary">新增</button>
        </div>
      </div>
    </div>
  </form>
</template>


<script setup>
import { useRouter } from 'vue-router';
import axios from 'axios';
import { emp } from "@/model/Emp";
import { addressAPI } from '@/address/AllData.js';
import { ref, computed } from 'vue';
import Swal from 'sweetalert2'

const router = useRouter();
const selectedCity = ref(''); // 用於保存選擇的縣市
const selectedTown = ref(''); // 用於保存選擇的鄉鎮
const detailAddress = ref(''); // 用於保存剩下的詳細地址

const towns = ref([]);


const getTowns = () => {
  const selectedCityData = addressAPI.find(city => city.CityName === selectedCity.value);
  return selectedCityData ? selectedCityData.AreaList : [];
};

const watchEffect = async () => {
  towns.value = getTowns();
};

// 定義一個計算屬性
const fullAddress = computed(() => {
  return `${selectedCity.value}${selectedTown.value}${detailAddress.value}`;
});


const employeeData = ref(emp)
// 呼叫watchEffect或其他初始化操作

const addHandler = async () => {
  emp.value.address = selectedCity.value + selectedTown.value + detailAddress.value
  const API_URL = `${import.meta.env.VITE_API_JAVAURL}employee/add`;
  try {
    const response = await axios.post(API_URL, employeeData.value);
    console.log(employeeData.value);
    console.log(response);
    if (response.status == 200) {
      Swal.fire(
        '儲存成功',
        '',
        'success'
      )
      router.push('/xukai/find-emp');
    } else {
      alert(response.data.message);
    }
  } catch (error) {
    console.error("An error occurred while adding: ", error);
    console.log('emp.value before sending:', employeeData.value);
  }
};
</script>

<style >
h2 {
  text-align: center;
}

/* 為表格添加圓角 */
.form-table {
  width: 50%;
  margin: 20px auto 10px auto;
  border-collapse: separate;
  /* 使用separate而非collapse以使border-radius生效 */
  border-spacing: 0;
  /* 移除單元格間的間距 */
  border-radius: 10px;
  /* 圓角設置 */
  overflow: hidden;
  /* 隱藏內部超出部分 */
}

/* 為單元格添加邊線 */
.form-table th,
.form-table td {
  border: 1px solid black;
}


.form-table th,
.form-table td {
  border: 1px solid #ccc;
  padding: 10px;
}

.form-table th {
  text-align: center;
  background-color: #a3c4e8;
}

.form-control {
  width: 100%;
  padding: 10px;
  margin: 5px 0;
  box-sizing: border-box;
}

.btn {
  width: 50%;
  display: block;
  margin: 2px auto;
}
</style>
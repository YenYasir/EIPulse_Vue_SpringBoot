
<script setup>
import { useRouter } from 'vue-router';
import axios from 'axios';
import { emp } from "@/model/Emp";
import { addressAPI } from '@/address/AllData.js';
import { ref, computed, reactive } from 'vue';
import Swal from 'sweetalert2'
//////////////////////////////////////////////////////////////////////
const router = useRouter();
const selectedCity = ref(''); // 用於保存選擇的縣市
const selectedTown = ref(''); // 用於保存選擇的鄉鎮
const detailAddress = ref(''); // 用於保存剩下的詳細地址
const depts = ref([]);
const selectedDept = ref(null);
const selectedTitle = ref(null);
const titles = ref([]);
const towns = ref([]);
const sendTitle = reactive({
  empId: '',
  titleId: '',
  reason: '',
  effectDate: ''
})


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
  // 利用 formData 處理圖片
  const formData = new FormData();
  formData.append('file', file.value);
  emp.value.address = selectedCity.value + selectedTown.value + detailAddress.value
  const API_URL = `${import.meta.env.VITE_API_JAVAURL}employee/add`;
  try {
    const [_, month, day] = employeeData.value.birth.split('-');
    console.log(month + "" + day);
    employeeData.value.password = month + "" + day
    console.log(employeeData.value.password);
    ///////////把物件轉換成json/////////////////
    employeeData.value.file = ""
    const json = JSON.stringify(employeeData.value);
    const blob = new Blob([json], {
      type: 'application/json'
    });
    formData.append('data', blob);
    ////////////////////////////
    const response = await axios.post(API_URL, formData);
    console.log(employeeData.value);
    console.log(response);
    if (response.status == 200) {
      Swal.fire(
        '儲存成功',
        '',
        'success'
      )
      router.push('/employee/find-emp');
    } else {
      alert(response.data.message);
    }
  } catch (error) {
    console.error("An error occurred while adding: ", error);
    console.log('emp.value before sending:', employeeData.value);
  }
}
// 快速輸入demo
const quickEnter = async () => {
  console.log(456);
  const quickData = {
    empName: '拾久典武',
    idNumber: 'A122488164',
    birth: '1988/02/09',
    gender: '男',
    email: 'meow@gmail.com',
    phone: '0954225713',
    tel: '05-2314572',
    selectedCity: '嘉義縣',
    selectedTown: '竹崎鄉',
    detailAddress: '中正路100號',
    hireDate: '2023/01/01',
  };

  // Updating the emp object with quickData
  for (const key in quickData) {
    if (key === 'selectedCity' || key === 'selectedTown' || key === 'detailAddress') {
      // Special handling for address parts, because they are split into different refs
      selectedCity.value = quickData.selectedCity;
      selectedTown.value = quickData.selectedTown;
      detailAddress.value = quickData.detailAddress;
    } else {
      // Update the rest of the properties
      employeeData.value[key] = quickData[key];
    }
  }
  // As the address has changed, call this function to update the town data
  await watchEffect();
  console.log("Quick data entered");
};
const loadDept = async () => {
  // get api of dept datas 
  const api = `http://localhost:8090/eipulse/dapt/findAll`
  try {
    const response = await axios.get(api);
    depts.value = response.data; // 假设API返回的是一个数组

  } catch (error) {
  }
}

const loadTitle = async () => {
  // get api of title datas 
  const api = `http://localhost:8090/eipulse/title/findAll`
  try {
    const response = await axios.get(api);
    titles.value = response.data; // 假设API返回的是一个数组

  } catch (error) {
  }
}
// 根據部門顯示出對應的職位
const changeSubType = () => {
  Object.assign(selectedTitle, selectedDept.value)

}
loadDept();
loadTitle();
const file = ref("");
const fileChange = (e) => {
  file.value = '';
  const selectedFile = e.target.files[0]; // 獲取第一個選擇的檔案
  const maxSizeInBytes = 1024 * 1024 * 5; // 5MB

  if (selectedFile) {
    if (selectedFile.size > maxSizeInBytes) {
      Swal.fire({
        icon: 'error',
        title: '檔案超過大小',
        text: `${selectedFile.name}超過5MB`,
      });
      return;
    } else {
      file.value = selectedFile;
      sendmsg();
    }
  }
  file.value = '';
};
</script>
<template>
    <div class="card-body">
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
          <input class="form-control form-control-sm" id="photoUrl" type="file" @change="fileChange">
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
        <!-- <div class="col-md-3">
          <label for="titleId" class="form-label">職位</label>
          <input type="text" class="form-control" id="titleId" v-model="emp.titleId" required>
        </div> -->

        <!-- 入職日 -->
        <div class="col-md-3">
          <label for="hireDate" class="form-label">入職日</label>
          <input type="date" class="form-control" id="hireDate" v-model="emp.hireDate" required>
        </div>

        <!-- 部門 -->
        <div class="col-md-3">
          <label for="price" class="form-label">部門</label>
          <select class="form-select form-control" id="deptSelect" aria-label="Floating label select example"
            v-model="selectedDept">
            <option v-for="dept in depts" :key="dept.deptId" :value="dept.deptId">
              {{ dept.deptName }}
            </option>
          </select>
        </div>
        <!-- 職位 -->
        <div class="col-md-3">
          <label for="price" class="form-label">職位</label>
          <select class="form-select form-control" id="titleSelect" aria-label="Floating label select example"
            v-model="sendTitle.titleId" @input="saveEditedData">
            <template v-if="selectedDept != null">
              <option v-for="title in depts[selectedDept - 1].titleDTOS" :key="title.id" :value="title.id">
                {{ title.titleName }}
              </option>
            </template>
          </select>
        </div>

        <!-- 按鈕 -->
        <div class="col-12">
          <button type="submit" class="btn btn-warning">新增</button>
        </div>
      </div>
    </div>
    <button type="button" @click="quickEnter">快速輸入</button>
  </form>
</div>
</template>


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

.btn.btn-warning {

  display: block;
  margin: 2px auto;
}
</style>
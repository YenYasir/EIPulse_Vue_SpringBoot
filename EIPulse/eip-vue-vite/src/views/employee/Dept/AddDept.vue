<template>
  <form @submit.prevent="addHandler">
    <div style=" margin-top: 100px; ">
      <h2>新增部門資料</h2>
      <table class="form-table" style="width: 410px; ">
        <!-- 部門名稱 -->
        <tr>
          <th><label for="deptName" class="form-label">部門名稱</label></th>
          <td><input type="text" class="form-control" id="deptName" v-model="deptData.deptName" required></td>
        </tr>

        <!-- 部門辦公室 -->
        <tr>
          <th><label for="deptOffice" class="form-label">部門辦公室</label></th>
          <td><input type="text" class="form-control" id="deptOffice" v-model="deptData.deptOffice" required></td>
        </tr>
      </table>

      <button class="btn btn-warning" type="submit" style="width: 400px;">
        新增
      </button>
      <button type="button" @click="quickEnter">快速輸入</button>
    </div>
  </form>
</template>
  
<script setup>

import axios from 'axios';
import { ref, computed } from 'vue';
import Swal from 'sweetalert2';
import { useRouter } from 'vue-router';

const router = useRouter();
const deptData = ref(
  {
    "deptName": "",
    "deptOffice": "",
  }
)
const addHandler = async () => {
  const API_URL = `${import.meta.env.VITE_API_JAVAURL}dept/add`;
  try {
    const response = await axios.post(API_URL, deptData.value);
    console.log(response.status);
    console.log("this is my values>>>>>>>>" + deptData.value.deptName + ' , ' + deptData.value.deptOffice);
    if (response.status == 200) {
      Swal.fire(
        '儲存成功',
        '',
        'success'
      )
      router.push('/employee/find-dept');
    } else {
      alert(response.data.message);
    }
  } catch (error) {
    console.error("An error occurred while adding: ", error);
    console.log('emp.value before sending:', deptData.value);
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
  background-color: black;
  color:white;
  
}

.form-control {
  width: 98%;
  padding: 10px;
  margin: 5px 0;
  box-sizing: border-box;
  border-color: #0161c7;
}

.btn {
  width: 50%;
  display: block;
  margin: 2px auto;
}
</style>
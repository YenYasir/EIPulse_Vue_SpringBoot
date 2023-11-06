<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';
import Swal from 'sweetalert2'
// import { addressAPI } from '@/address/AllData.js';
const route = useRoute()
const router = useRouter()

const empData = ref({})
let API_URL = 'http://localhost:8090/eipulse/employee/'
// 用來找尋 這筆要更新的資料
const loadData = async () => {
    const id = route.params.id
    console.log(id);
    const API = `${API_URL}${id}`
    const { data } = await axios.get(API);
    console.log(data);
    empData.value = data;

}
// 處發送出事件後 去後端更新
const savaEdit = async () => {
    const API = `${API_URL}updateEmp`
    const response = await axios.put(API, empData.value)
    console.log(response);
    console.log(response.data);
    if (response.status == 200) {

        Swal.fire({
            title: 'Are you sure?',
            text: "你確定要更新嗎?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '確定'
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire(
                    '儲存成功',
                    '',
                    'success'
                )
                router.push('/xukai/find-emp');
            }
        })
    }
}
loadData();

</script>

<template>
    <form @submit.prevent="addHandler">
        <div style=" margin-top: 10px; ">
            <h2>編輯資料</h2>
            <table class="form-table" style="width: 410px; ">
                <!-- 員工姓名 -->
                <tr>
                    <th><label for="empName" class="form-label">員工姓名</label></th>
                    <td><input type="text" class="form-control" id="empName" v-model="empData.empName"></td>
                </tr>
                <!-- 郵箱 -->
                <tr>
                    <th><label for="email" class="form-label">Email</label></th>
                    <td><input type="email" class="form-control" id="email" v-model="empData.email"></td>
                </tr>
                <!-- 電話 -->
                <tr>
                    <th><label for="phone" class="form-label">手機</label></th>
                    <td><input type="tel" class="form-control" id="phone" v-model="empData.phone"></td>
                </tr>
                <!-- 座機 -->
                <tr>
                    <th><label for="tel" class="form-label">室內電話</label></th>
                    <td><input type="tel" class="form-control" id="tel" v-model="empData.tel"></td>
                </tr>
                <!-- 圖片網址 -->
                <tr>
                    <th><label for="photoUrl" class="form-label">員工照片</label></th>
                    <td><input class="form-control form-control-sm" id="photoUrl" type="file"></td>
                </tr>

                <!-- 地址：縣市下拉選單 -->
                <!-- <tr>
                    <th><label for="city" class="form-label">縣市</label></th>
                    <td><select class="form-control" id="city" v-model="selectedCity" @change="watchEffect()">
                    <option v-for="city in addressAPI" :key="city.CityName" :value="city.CityName">{{ city.CityName }}</option>
                    </select></td>
                </tr> -->

                <!-- 地址：鄉鎮下拉選單 -->
                <!-- <tr>
                    <th><label for="town" class="form-label">鄉鎮</label></th>
                    <td><select class="form-control" id="town" v-model="selectedTown">
                    <option v-for="town in towns" :key="town" :value="town.AreaName">{{ town.AreaName }}</option>
                    </select></td>
                </tr> -->
                <!-- 輸入路名 -->
                <tr>
                    <th><label for="fullAddress" class="form-label">路名地址</label></th>
                    <td><input type="text" class="form-control" id="fullAddress" v-model="empData.address"></td>
                </tr>
            </table>
            <button class="btn btn-primary" type="submit" @click="savaEdit" style="width: 400px;">
                儲存更新
            </button>
        </div>
    </form>
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
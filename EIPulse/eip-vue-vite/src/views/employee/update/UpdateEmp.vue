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
// 接收圖檔的物件
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

// 處發送出事件後 去後端更新
const savaEdit = async () => {
    // 利用 formData 處理圖片
    const formData = new FormData();
    console.log(file.value);
    formData.append('file', file.value);
    const API = `${API_URL}updateEmp`
    ///////////把物件轉換成json/////////////////
    empData.value.file = ""
    const json = JSON.stringify(empData.value);
    const blob = new Blob([json], {
        type: 'application/json'
    });
    formData.append('data', blob);
    ////////////////////////////
    const response = await axios.put(API, formData)
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
                router.push('/employee/find-emp');
            }
        })
    }
}
const backPage = () => {
    Swal.fire({
        title: 'Are you sure?',
        text: "即將返回員工列表，若有編輯資料將不保存",
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '確定'
    }).then((result) => {
        if (result.isConfirmed) {

            router.push('/employee/find-emp');
        }
    })
}
loadData();
</script>

<template>
    <form @submit.prevent="addHandler">
        <div style=" margin-top: 10px; ">
            <h2>編輯資料</h2>
            <table class="form-table" style="width: 410px; ">

                <div><i class="bi bi-box-arrow-left" style="font-size: 30px;" @click="backPage"></i></div>
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
                    <td><input class="form-control form-control-sm" id="photoUrl" type="file" @change="fileChange"></td>
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
<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';
import Swal from 'sweetalert2'
// import { addressAPI } from '@/address/AllData.js';
const route = useRoute()
const router = useRouter()

const deptData = ref({})
let API_URL = 'http://localhost:8090/eipulse/dept/'
// 用來找尋 這筆要更新的資料
const loadData = async () => {
    const id = route.params.id
    console.log(id);
    const API = `${API_URL}${id}`
    const { data } = await axios.get(API);
    console.log(data);
    deptData.value = data;

}
// 處發送出事件後 去後端更新
const savaEdit = async () => {
    console.log(deptData.value);
    const API = `${API_URL}update`
    const response = await axios.put(API, deptData.value)
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
                router.push('/employee/find-dept');
            }
        })
    }
}
const backPage = () => {
    Swal.fire({
        title: '即將返回部門列表',
        text: "若有編輯資料將不保存",
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '確定',
        cancelButtonText: '取消',
    }).then((result) => {
        if (result.isConfirmed) {

            router.push('/employee/find-dept');
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
                <div><i class="bi bi-box-arrow-left" style="font-size: 30px;cursor: pointer;" @click="backPage"></i></div>
                <!-- 部門名稱 -->
                <tr>
                    <th><label for="deptName" class="form-label">部門名稱</label></th>
                    <td><input type="text" class="form-control" id="deptName" v-model="deptData.deptName"></td>
                </tr>
                <!-- 部門辦公室 -->
                <tr>
                    <th><label for="deptOffice" class="form-label">部門辦公室</label></th>
                    <td><input type="text" class="form-control" id="deptOffice" v-model="deptData.deptOffice"></td>
                </tr>
            </table>
            <button class="btn btn-warning" type="submit" @click="savaEdit">
                儲存更新
            </button>
        </div>
    </form>
</template>
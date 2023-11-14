<script setup>
import { onMounted, ref } from 'vue';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';
import Swal from 'sweetalert2'
const route = useRoute()
const router = useRouter()
console.log(789);
const emergencyData = ref({})
let API_URL = 'http://localhost:8090/eipulse/emergency'
// 用來找尋 這筆要更新的資料
const loadData = async () => {
    console.log(123);
    try {
        const id = route.params.id;
        console.log(id);
        const API = `${API_URL}/${id}`;
        console.log(123);
        const data = await axios.get(API);
        console.log(API);
        console.log(data.value);
        emergencyData.value = data.data;
    } catch (error) {
        console.log(123);
        console.error("Error loading data:", error);
    }
};

// 處發送出事件後 去後端更新
const savaEdit = async () => {
    console.log(emergencyData.value);
    const API = `${API_URL}/update`
    const response = await axios.put(API, emergencyData.value)
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
                router.push('/employee/emergencyList');
            }
        })
    }
}
const backPage = () => {
    Swal.fire({
        title: 'Are you sure?',
        text: "即將返回部門列表，若有編輯資料將不保存",
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '確定'
    }).then((result) => {
        if (result.isConfirmed) {

            router.push('/employee/emergencyList');
        }
    })
}

onMounted(() => {
    loadData();

})
</script>

<template>
    <form @submit.prevent="addHandler">
        <div style=" margin-top: 10px; ">
            <h2>編輯資料</h2>
            <table class="form-table" style="width: 410px; ">
                <div><i class="bi bi-box-arrow-left" style="font-size: 30px;" @click="backPage"></i></div>
                <!-- 聯絡人姓名 -->
                <tr>
                    <th><label for="deptName" class="form-label">聯絡人姓名</label></th>
                    <td><input type="text" class="form-control" id="emergencyName" v-model="emergencyData.emergencyName">
                    </td>
                </tr>
                <!-- 聯絡人電話 -->
                <tr>
                    <th><label for="deptOffice" class="form-label">連絡電話</label></th>
                    <td><input type="text" class="form-control" id="phone" v-model="emergencyData.phone"></td>
                </tr>
                <!-- 關係 -->
                <tr>
                    <th><label for="deptOffice" class="form-label">關係</label></th>
                    <td><input type="text" class="form-control" id="relation" v-model="emergencyData.relation"></td>
                </tr>
            </table>
            <button class="btn btn-warning" type="submit" @click="savaEdit">
                儲存更新
            </button>
        </div>
    </form>
</template>
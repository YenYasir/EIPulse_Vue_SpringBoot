<script setup>

import { ref, onMounted, watch } from 'vue';
import axios from 'axios';
import Swal from 'sweetalert2';
import Page from "../../components/Page.vue";
import { useRouter } from 'vue-router';


const totalPages = ref(0);
const currentPage = ref(1);
const router = useRouter();
const dataList = ref([]);
const emps = ref([]);
// 新增selectedEmp來綁定<select>的v-model
const selectedEmp = ref(null);

const loadData = async () => {
    let url = `http://localhost:8090/eipulse/emergency/${selectedEmp.value}/${currentPage.value}`;

    try {
        const res = await axios.get(url);
        console.log(res.data);
        dataList.value = res.data.content;
        totalPages.value = res.data.totalPages;
    } catch (e) {
        console.log(e);
    }
};
// 監聽selected變化
watch(selectedEmp, (newValue, oldValue) => {
    if (newValue !== oldValue) { // 檢查以確保值真的改變了
        console.log(newValue);
        loadData(); // 呼叫loadData函數來加載新的部門數據
    }
}, { immediate: true }); // 設置immediate為true使得在監聽開始後會立即執行一次回調
// 下拉式列出員工名單
const loadEmp = async () => {
    // get api of dept datas 
    const api = `http://localhost:8090/eipulse/employees/name`
    console.log(123);
    console.log(emps.value);
    try {
        const response = await axios.get(api);
        emps.value = response.data; // 假设API返回的是一个数组
        console.log(emps.value);

    } catch (error) {
        console.log(emps.value);
    }
};
const updateData = (emergencyId) => {
    router.push(`/employee/updateEmergency/${emergencyId}`);
    console.log(emergencyId);
}


// 轉跳至新增畫面
const addData = () => {
    router.push('/employee/emergency');
};
const updateCurrentPage = (newPage) => {
    currentPage.value = newPage;
    loadData();
};

onMounted(loadData);
loadEmp();
</script>


<template>
    <div class="card rounded ">
        <div class="card-header text-center  text-bg-dark fs-5">員工緊急聯絡人列表</div>
        <div class="card-body">
            <button type="button" class="btn btn-outline-success  mb-2" @click="addData">新增緊急聯絡人資料</button>
            <tr>
                <th><label for="phone" class="form-label">員工</label></th>
                <td><select class="form-select form-control" id="empSelect" aria-label="Floating label select example"
                        v-model="selectedEmp">
                        <option v-for="emp in emps" :key="emp.empId" :value="emp.empId">
                            {{ emp.empName }}
                        </option>
                    </select></td>
            </tr>
            <table class="table table-bordered text-center">
                <thead>
                    <tr style="background-color: rgb(255, 204, 0); border-color: rgb(0, 0, 0);">
                        <!-- <th>編號</th> -->
                        <th>員工編號</th>
                        <th>員工姓名</th>
                        <th>聯絡人姓名</th>
                        <th>連絡電話</th>
                        <th>關係</th>
                        <th>編輯</th>
                    </tr>
                </thead>
                <tbody style="background-color: rgb(255, 255, 255);border-color: rgb(0, 0, 0);;">
                    <tr v-for="data in dataList" :key="data.id">
                        <!-- <td>{{ data.emergencyId }}</td> -->
                        <td>{{ data.empId }}</td>
                        <td>{{ data.empName }}</td>
                        <td>{{ data.emergencyName }}</td>
                        <td>{{ data.phone }}</td>
                        <td>{{ data.relation }}</td>
                        <td>
                            <button class="btn btn-secondary mx-1"><i class="bi bi-pencil-square"
                                    @click="updateData(data.emergencyId)"></i>
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>
            <Page :total-pages="totalPages" :current-page="currentPage" @select-page="updateCurrentPage"></Page>
        </div>
        <div class="card-footer text-body-secondary">
            Copyright © EIPulse科技 All Rights Reserved.
        </div>
    </div>
</template>

<style>
.form-control{
    width: 20vh;
}
</style>
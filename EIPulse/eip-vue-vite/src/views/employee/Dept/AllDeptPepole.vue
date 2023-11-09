
<template>
    <div class="card rounded ">
        <div class="card-header text-center text-bg-dark fs-5">部門人員</div>
        <div class="card-body">
            <label for="price" class="form-label">請選擇部門</label>
            <select class="form-select form-control w-25" id="deptSelect" aria-label="Floating label select example"
                v-model="selectedDept">
                <option v-for="dept in depts" :key="dept.deptId" :value="dept.deptId">
                    {{ dept.deptName }}
                </option>
            </select>
            <table class="table table-bordered text-center">
                <thead>
                    <tr style="background-color: rgb(255, 204, 0); border-color: rgb(0, 0, 0);">
                        <th>職位</th>
                        <th>員工編號</th>
                        <th>員工姓名</th>
                        <th>聯絡資訊</th>
                    </tr>
                </thead>
                <tbody style="background-color: rgb(255, 255, 255);border-color: rgb(0, 0, 0);;">
                    <tr v-for="data in dataList" :key="data.id">
                        <td>{{ data.titleName }}</td>
                        <td>{{ data.empId }}</td>
                        <td>{{ data.empName }}</td>
                        <td>{{ data.email }}</td>
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

<script setup>

import { ref, onMounted, watch } from 'vue';
import axios from 'axios';
import Swal from 'sweetalert2';
import Page from "../../../components/Page.vue";
import { useRouter } from 'vue-router';


const totalPages = ref(0);
const currentPage = ref(1);
const router = useRouter();
const dataList = ref([]);
const depts = ref([]);
const selectedDept = ref(null);


const loadDept = async () => {
    // get api of dept datas 
    console.log("安安");
    const api = `http://localhost:8090/eipulse/dapt/findAll`
    try {
        const response = await axios.get(api);
        depts.value = response.data; // 假设API返回的是一个数组

    } catch (error) {
    }
};


const loadData = async () => {
    let url = `http://localhost:8090/eipulse/dept/name/${selectedDept.value}/${currentPage.value}`;

    try {
        const res = await axios.get(url);
        console.log(res.data);
        dataList.value = res.data.content;
        totalPages.value = res.data.totalPages;
    } catch (e) {
        console.log(e);
    }
};

const updateCurrentPage = (newPage) => {
    currentPage.value = newPage;
    loadData();
};
// 監聽selectedDept變化
watch(selectedDept, (newValue, oldValue) => {
    if (newValue !== oldValue) { // 檢查以確保值真的改變了
        console.log(newValue);
        loadData(); // 呼叫loadData函數來加載新的部門數據
    }
}, { immediate: true }); // 設置immediate為true使得在監聽開始後會立即執行一次回調
// loadDept()
onMounted(loadDept);
</script>


<style scoped>
.card {
    margin: 20px;
    padding: 20px;

}
</style>
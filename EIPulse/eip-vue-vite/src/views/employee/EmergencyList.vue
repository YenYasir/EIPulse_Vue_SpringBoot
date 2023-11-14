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


const loadData = async () => {
    let url = `http://localhost:8090/eipulse/emergency/paged/${currentPage.value}`;

    try {
        const res = await axios.get(url);
        console.log(res.data);
        dataList.value = res.data.content;
        totalPages.value = res.data.totalPages;
    } catch (e) {
        console.log(e);
    }
};

// 轉跳至新增畫面
const addData = () => {
    router.push('/employee/emergency');
};
const updateCurrentPage = (newPage) => {
    currentPage.value = newPage;
    loadData();
};

onMounted(loadData);
</script>


<template>
    <div class="card rounded ">
        <div class="card-header text-center  text-bg-dark fs-5">員工緊急聯絡人列表</div>
        <div class="card-body">
            <button type="button" class="btn btn-outline-success  mb-2" @click="addData">新增緊急聯絡人資料</button>
            <table class="table table-bordered text-center">
                <thead>
                    <tr style="background-color: rgb(255, 204, 0); border-color: rgb(0, 0, 0);">
                        <!-- <th>編號</th> -->
                        <th>員工編號</th>
                        <th>員工姓名</th>
                        <th>聯絡人姓名</th>
                        <th>連絡電話</th>
                        <th>關係</th>
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
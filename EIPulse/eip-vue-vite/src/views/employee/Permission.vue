<script setup>

import { ref, onMounted, watch } from 'vue';
import axios from 'axios';
import Swal from 'sweetalert2';
import Page from "../../components/Page.vue";
import { useRouter } from 'vue-router';


const totalPages = ref(0);
const currentPage = ref(1);
const router = useRouter();
const permissionData = ref([]);


const loadData = async () => {
    let url = `http://localhost:8090/eipulse/permissionMove/paged/${currentPage.value}`;

    try {
        const res = await axios.get(url);
        console.log(res.data);
        permissionData.value = res.data.content;
        totalPages.value = res.data.totalPages;
    } catch (e) {
        console.log(e);
    }
};

const updateCurrentPage = (newPage) => {
    currentPage.value = newPage;
    loadData();
};

onMounted(loadData);
</script>


<template>
    <div class="card rounded ">
        <div class="card-header text-center text-bg-dark fs-5">權限變動列表</div>
        <div class="card-body">
            <table class="table table-bordered text-center">
                <thead>
                    <tr style="background-color:rgb(255, 204, 0) ; border-color: rgb(0, 0, 0);">
                        <th>編號</th>
                        <th>員工編號</th>
                        <th>員工姓名</th>
                        <th>更改前</th>
                        <th>更改後</th>
                        <th>更動原因</th>
                        <th>生效日</th>
                        <th>簽核主管</th>
                        <th>編輯日期</th>
                    </tr>
                </thead>
                <tbody style="background-color: rgb(255, 255, 255);border-color: rgb(0, 0, 0);;">
                    <tr v-for="data in permissionData" :key="data.id">
                        <td>{{ data.id }}</td>
                        <td>{{ data.empId }}</td>
                        <td>{{ data.empName }}</td>
                        <td>{{ data.beforePermissionName }}</td>
                        <td>{{ data.afterPermissionName }}</td>
                        <td>{{ data.reason }}</td>
                        <td>{{ data.effectDate }}</td>
                        <td>{{ data.approver }}</td>
                        <td>{{ data.editDate }}</td>
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
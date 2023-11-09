<script setup>
import { ref } from 'vue';
import axios from "axios";
import { onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
const route = useRoute()
const router = useRouter()

const personalHistory = ref()
const empId = ref(0)
const loadData = async () => {
    const empId = route.params.empId
    console.log(empId)
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}salaryHistory/${empId}`
    const { data } = await axios.get(API_URL)
    console.log(data)
    personalHistory.value = data
    empId = personalHistory.value[0].empId
}


onMounted(loadData)
</script>

<template>
    <!-- <div class="nav1">
        <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
            <ol class="breadcrumb">
                <router-link :to="{ name: 'infoPersonal', params: { empId: empId } }">
                    <li class="breadcrumb-item"><i class="bi bi-person-fill"></i>{{ empId }} 薪資資訊</li>
                </router-link>
                <li class="breadcrumb-item active" aria-current="page">
                    <router-link :to="{ name: 'historyPersonal', params: { empId: info.empId } }"><i
                            class="bi bi-tag-fill"></i>
                        薪資異動紀錄</router-link>
                </li>
            </ol>
        </nav>
    </div> -->
    <div class="div1" style=" margin-top:20px;">
        <table class="table table-sm table-hover">
            <thead>
                <tr>
                    <th scope="col" class="hidden-column">紀錄單號</th>
                    <th scope="col">員工編號</th>
                    <th scope="col">員工姓名</th>
                    <th scope="col">異動前薪資</th>
                    <th scope="col">異動後薪資</th>
                    <th scope="col">備註</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(history, index) in personalHistory" :key="history.empId">
                    <!-- <td><router-Link :to="'/info/' + salaryInfo.empId" @click="handleLinkClick"><i
                                class="bi bi-zoom-in"></i></router-Link></td> -->
                    <td class="hidden-column">{{ history.id }}</td>
                    <td>{{ history.empId }}</td>
                    <td>{{ history.empName }}</td>
                    <td>{{ history.originalSalary }}</td>
                    <td>{{ history.adjustSalary }}</td>
                    <td>{{ history.remark }}</td>
                    <!-- <td>
                        <router-link class="btn btn-secondary mx-1" :to="'/salary/update/' + history.id"><i
                                class="bi bi-pencil-square"></i></router-link>
                        <button class="btn btn-secondary mx-1" @click="deleteHistory(history.id)"><i
                                class="bi bi-bucket"></i></button>
                    </td> -->
                </tr>
            </tbody>
        </table>
    </div>
</template>

<style scoped>
.hidden-column {
    display: none;
    /* visibility: hidden; */
    /* 或者使用 opacity: 0; */
}

table {
    text-align: center;
}
</style>
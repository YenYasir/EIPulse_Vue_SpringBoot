<script setup>
import { ref } from 'vue';
import axios from "axios";
import { onMounted, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
const route = useRoute()
const router = useRouter()
const empId = computed(() => route.params.empId);

const empName = ref()
const personalHistory = ref()

const loadData = async () => {
    const empId = route.params.empId
    console.log(empId)
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}salaryHistory/${empId}`
    const { data } = await axios.get(API_URL)
    console.log(data)
    personalHistory.value = data
    empName.value = personalHistory.value[0].empName

    for (let i = 0; i < personalHistory.value.length; i++) {
        const dateDB = new Date(personalHistory.value[i].adjustedDate)
        personalHistory.value[i].adjustedDate = dateDB.toISOString().split('T')[0];
    }
}


onMounted(loadData)
</script>

<template>
    <div class="nav1">
        <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
            <ol class="breadcrumb">
                <router-link :to="{ name: 'infoPersonal', params: { empId: empId } }">
                    <li class="breadcrumb-item"><i class="bi bi-person-fill"></i>&nbsp{{ empName }} 薪資資訊</li>
                </router-link><span>&nbsp＞&nbsp</span>
                <li class="breadcrumb-item active" aria-current="page">
                    薪資異動紀錄
                </li>
            </ol>
        </nav>
    </div>
    <div class="container">
        <div class="div1" style=" margin-top:20px;">
            <div class="card">
                <div class="card-body">
                    <table class="table table-sm table-hover">
                        <thead>
                            <tr>
                                <th scope="col" style="width:100px;" class="hidden-column">員工編號</th>
                                <th scope="col" style="width:100px;" class="hidden-column">員工姓名</th>
                                <th scope="col" style="width:100px;">異動日期</th>
                                <th scope="col" style="width:100px;">異動前薪資</th>
                                <th scope="col" style="width:100px;">異動後薪資</th>
                                <th scope="col" style="width:150px;">備註</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(history, index) in personalHistory" :key="history.empId">
                                <td style="width:100px;" class="hidden-column">{{ history.empId }}</td>
                                <td style="width:100px;" class="hidden-column">{{ history.empName }}</td>
                                <td style="width:100px;">{{ history.adjustedDate }}</td>
                                <td style="width:100px;">{{ history.originalSalary }}</td>
                                <td style="width:100px;">{{ history.adjustSalary }}</td>
                                <td style="width:150px;">{{ history.remark }}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
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

a {
    text-decoration: none;
}

.nav1 {
    font-size: 15px;
    margin-top: 10px;
    margin-left: 30px;
}
</style>
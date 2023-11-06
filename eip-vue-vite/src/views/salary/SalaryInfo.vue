<script setup>
import axios from "axios";
import { ref, reactive, defineEmits, defineProps } from "vue"
import { onMounted } from 'vue';
import Paging from "@/components/salary/Paging.vue";

const salaryInfos = ref([])
const laborInsuranceRateEmp = ref(0.2)
const healthInsuranceRate = ref(0.0517)
const healthInsuranceRateEmp = ref(0.3)
const employmentInsuranceRate = ref(0.12)

const totalPages = ref(0)
const currentPage = ref(1)
const keyword = ref('')


// 分頁
const getPage = async () => {
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/page/${currentPage.value}`
    const response = await axios.get(API_URL)
    console.log(response.data);
    totalPages.value = response.data.totalPages
    salaryInfos.value = response.data.content
}

const selectPage = (newPage) => {
    currentPage.value = newPage;
    console.log("當前頁" + currentPage.value)
    getPage()
}

const inputHandler = async () => {
    if (keyword.value !== null && keyword.value !== '') {

        const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/page/${keyword.value}/${currentPage.value}`

        try {
            const response = await axios.get(API_URL)
            console.log(response.data);
            totalPages.value = response.data.totalPages
            salaryInfos.value = response.data.content
        } catch (error) {
            console.error(error);
        }
    }
}
onMounted(() => {
    // loadSalaryInfos()
    getPage()
});


</script>
<template>
    <!-- <SalaryBar :items="[{ name: '薪資設定', path: '/info' }, { name: '薪資科目', path: '/subject' }]"></SalaryBar> -->

    <div class="container c">
        <div class="row mb-2">
            <div class="col-3">
                <RouterLink class="btn btn-primary btn-sm mb-3" :to="{ name: 'salaryForm' }">
                    <i class="bi bi-plus-lg"></i>新增
                </RouterLink>
            </div>
            <div class="col-6"></div>
            <div class="col-3">
                <div class="input-group text-right">
                    <input type="text" class="form-control " v-model="keyword" placeholder="姓名搜尋" @input="inputHandler">
                    <button type="submit" class="btn btn-secondary  btn-sm"><i class="bi bi-search"></i></button>
                </div>

            </div>
        </div>
        <div class="div1 ">
            <table class="table table-sm table-hover">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>員工編號</th>
                        <th>員工姓名</th>
                        <th>基本薪資(元)</th>
                        <th>勞保投保級距</th>
                        <th>勞保費</th>
                        <th>勞退自願提撥率</th>
                        <!-- <th>勞退自願提繳退休金</th> -->
                        <th>健保投保級距</th>
                        <th>眷屬扶養人數</th>
                        <th>健保費</th>
                        <th>福利金扣繳(Y/N)</th>
                        <th>動作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="salaryInfo in salaryInfos" :key="salaryInfo.empId">
                        <td><router-Link :to="'/info/' + salaryInfo.empId" @click="handleLinkClick"><i class="bi bi-zoom-in"
                                    style="color:black"></i></router-Link></td>
                        <td>{{ salaryInfo.empId }}</td>
                        <td>{{ salaryInfo.empName }}</td>
                        <td>{{ salaryInfo.basicSalary }}</td>
                        <td>{{ salaryInfo.laborInsuranceGrade }}</td>
                        <td>{{ Math.round(salaryInfo.laborInsuranceGrade * laborInsuranceRateEmp * employmentInsuranceRate)
                        }}
                        </td>
                        <td>{{ salaryInfo.laborVolunteerPensionRate * 100 }}%</td>
                        <!-- <td>{{ Math.round(salaryInfo.laborVolunteerPensionRate * salaryInfo.laborInsuranceGrade) }}
                        </td> -->
                        <td>{{ salaryInfo.healthInsuranceGrade }} </td>
                        <td>{{ salaryInfo.familyDependantsNum }} </td>
                        <td>{{ Math.round(salaryInfo.healthInsuranceGrade * healthInsuranceRate
                            * healthInsuranceRateEmp * (1 + salaryInfo.familyDependantsNum)) }} </td>


                        <td v-if="salaryInfo.welfareBenefitsDeduction == 0">是</td>
                        <td v-else>否</td>


                        <!-- <td>{{ salaryInfo.welfareBenefitsDeduction }} </td> -->
                        <!-- <td><button class="btn btn-secondary mx-1" @click="editSalaryInfo(salaryInfo.empId)"><i
                                    class="bi bi-pencil-square"></i></button> -->
                        <td>
                            <router-link class="btn btn-secondary  btn-sm mx-1"
                                :to="'/salary/info/update/' + salaryInfo.empId"><i
                                    class="bi bi-pencil-square "></i></router-link>
                            <button class="btn btn-secondary  btn-sm mx-1" @click="deleteSalaryInfo(salaryInfo.empId)"><i
                                    class="bi bi-bucket"></i></button>
                        </td>
                    </tr>
                </tbody>
            </table>
            <!-- </div> -->
        </div>
    </div>
    <!-- <div class="card-footer text-body-secondary"> -->
    <!-- </div> -->
    <div class="row mb-2">
        <div class="col-3">
            <Paging :totalPages="totalPages" :currentPage="currentPage" @selectPage="selectPage"></Paging>
        </div>
    </div>
</template>
<style scoped>
table {
    text-align: center;
    font-size: 12px;
}

a {
    text-decoration: none;
}

.c {
    margin-top: 20px;
}
</style>
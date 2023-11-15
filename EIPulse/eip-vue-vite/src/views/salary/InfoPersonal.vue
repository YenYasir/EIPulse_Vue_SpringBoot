<script setup>
import { ref } from 'vue';
import SalaryInfo from "@/models/SalaryInfo.js"
import axios from "axios";
import { onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
const route = useRoute()
const router = useRouter()
const info = ref({})

const laborGrades = [26400, 27600, 28800, 30300, 31800, 33300, 34800, 36300, 38200, 40100, 42000, 43900, 45800]
const pensionRate = [0, 0.01, 0.02, 0.03, 0.04, 0.05, 0.06]
const healthGrades = [26400, 27600, 28800, 30300, 31800, 33300, 34800, 36300, 38200, 40100, 42000, 43900, 45800, 48200, 50600, 53000, 55400, 57800, 60800, 69800, 72800, 76500]
const nums = [0, 1, 2, 3]

//找要更新的那筆資料
const loadData = async () => {
    const empId = route.params.empId
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/${empId}`
    const { data } = await axios.get(API_URL)
    console.log(data)
    info.value = data

}
loadData()
</script>

<template>
    <div class="nav1">
        <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><i class="bi bi-person-fill"></i>&nbsp{{ info.empName }} 薪資資訊</li>
                <li class="breadcrumb-item active" aria-current="page">
                    <router-link :to="{ name: 'historyPersonal', params: { empId: info.empId } }">
                        薪資異動紀錄</router-link>
                </li>
            </ol>
        </nav>
    </div>

    <div class="container">
        <form>
            <!-- <div class="row justify-content-md-center"> -->
            <div class="row">
                <div class="col-md-4">
                    <label for="empId" class="form-label">員工編號</label>
                    <input type="text" class="form-control" id="empId" v-model="info.empId" disabled readonly>
                </div>
                <div class="col-md-4">
                    <label for="empName" class="form-label">員工姓名</label>
                    <input type="text" class="form-control" id="empName" v-model="info.empName" disabled>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <label for="empId" class="form-label">基本薪資</label>
                    <input type="text" class="form-control" id="empId" v-model.trim="info.basicSalary" placeholder=" "
                        required="" disabled readonly>
                </div>
                <div class="col-md-4">
                    <label for="welfare" class="form-label">福利金扣款</label>
                    <select class="form-select" aria-label="welfare" v-model="info.welfareBenefitsDeduction" disabled
                        readonly>
                        <option :value="1" :selected="info.welfareBenefitsDeduction.value == value">是</option>
                        <option :value="0" :selected="info.welfareBenefitsDeduction.value == value">否</option>
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <label for="laborInsurance" class="form-label">勞保投保級距</label>

                    <select class="form-select" aria-label="Default select example" v-model="info.laborInsuranceGrade"
                        disabled readonly>
                        <option v-for=" laborGrade  in  laborGrades " :value="laborGrade"
                            :selected="laborGrade === info.laborInsuranceGrade">第{{
                                laborGrades.indexOf(laborGrade) +
                                1
                            }}級({{ laborGrade }}元以下)</option>

                    </select>
                </div>
                <div class="col-md-4">
                    <label for="pensionRate" class="form-label">勞退自願提撥率</label>

                    <select v-model="info.laborVolunteerPensionRate" class="form-select" disabled readonly>
                        <option v-for=" rate in pensionRate " :value="rate"
                            :selected="rate === info.laborVolunteerPensionRate">
                            {{ rate * 100 }}% </option>
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <label for="healthInsuranceGrade" class="form-label">健保投保級距</label>
                    <select v-model="info.healthInsuranceGrade" class="form-select" disabled readonly>
                        <option v-for=" healthGrade  in  healthGrades " :value="healthGrade"
                            :selected="healthGrade === info.healthInsuranceGrade">
                            第{{ healthGrades.indexOf(healthGrade) + 1 }}級({{ healthGrade }}元)</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <label for="familyDepedent" class="form-label">眷屬加保人數</label>
                    <select v-model="info.familyDependantsNum" class="form-select" disabled readonly>
                        <option v-for=" num  in  nums " :value="num" :selected="num === info.familyDependantsNum">{{ num
                        }}人
                        </option>
                    </select>
                </div>
            </div>
            <div class="row">
            </div>
        </form>
    </div>
</template>
   
<style scoped>
.form-label {
    margin-top: 10px;
    font-weight: bold;
}

a {
    text-decoration: none;
}

.nav1 {
    font-size: 15px;
    margin-top: 10px;
    margin-left: 30px;
}

.container {
    margin-left: 40px;
    margin-top: 5px;
    overflow-x: hidden;

}
</style>
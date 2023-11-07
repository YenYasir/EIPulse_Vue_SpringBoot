<script setup>
import { ref } from 'vue';
import SalaryInfo from "@/models/SalaryInfo.js"
import axios from "axios";
import { onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
const route = useRoute()
const router = useRouter()
// const info = ref(SalaryInfo)
const info = ref({})

const laborGrades = [26400, 27600, 28800, 30300, 31800, 33300, 34800, 36300, 38200, 40100, 42000, 43900, 45800]
const pensionRate = [0, 0.01, 0.02, 0.03, 0.04, 0.05, 0.06]
const healthGrades = [26400, 27600, 28800, 30300, 31800, 33300, 34800, 36300, 38200, 40100, 42000, 43900, 45800, 48200, 50600, 53000, 55400, 57800, 60800, 69800, 72800, 76500]
const nums = [0, 1, 2, 3]

//找要更新的那筆資料
const loadData = async () => {
    const empId = route.params.empId
    console.log(empId)
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/${empId}`
    const { data } = await axios.get(API_URL)
    console.log(data)
    info.value = data
    //     info.value.isEditing = true
}
loadData()
// const edit = () => {
//     info.value.isEditing = !isEditing
// }


onMounted(loadData);
</script>

<template>
    <!-- <SalaryBar :items="[
        { name: '員工薪資', path: '/info' },
        { name: '調薪紀錄', path: '/history' }]"></SalaryBar> -->
    <!-- <router-view></router-view> -->
    <div class="nav1">
        <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><i class="bi bi-person-fill"></i>{{ info.empName }} 薪資資訊</li>
                <!-- <li class="breadcrumb-item"><a href="#">條新紀錄</a></li>
            <router-link :to="{ name: 'salaryForm' }" class="btn btn-primary" href="#" role="button">Link</router-link> -->

                <li class="breadcrumb-item active" aria-current="page">
                    <router-link :to="{ name: 'historyPersonal', params: { empId: info.empId } }"><i
                            class="bi bi-tag-fill"></i>
                        薪資異動紀錄</router-link>
                </li>
            </ol>
        </nav>
    </div>

    <div class="container">
        <form>
            <div class="row justify-content-md-center">

                <!-- <div class="row">
                <div class="col-md-6"></div>
                <div class="col-md-6"></div>
            </div> -->
                <div class="row">
                    <div class="col-md-4">
                        <label for="empId" class="form-label">員工編號</label>
                        <input type="text" class="form-control" id="empId" v-model="info.empId" readonly>
                    </div>
                    <div class="col-md-4">
                        <label for="empName" class="form-label">員工姓名</label>
                        <input type="text" class="form-control" id="empName" v-model="info.empName">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <label for="empId" class="form-label">基本薪資</label>
                        <input type="text" class="form-control" id="empId" v-model.trim="info.basicSalary" placeholder=" "
                            required="" readonly>
                    </div>
                    <!-- <div class=" col-md-4">
                        <label for="empId" class="form-label">伙食津貼</label>
                        <input type="text" class="form-control" id="empId" placeholder="" required="" readonly
                            :readonly="isEditing">
                    </div> -->
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
                    <div class="col-md-4">
                        <label for="welfare" class="form-label">福利金扣款</label>

                        <select class="form-select" aria-label="welfare" v-model="info.welfareBenefitsDeduction" disabled
                            readonly>
                            <option :value="1" :selected="info.welfareBenefitsDeduction.value == value">是</option>
                            <option :value="2" :selected="info.welfareBenefitsDeduction.value == value">否</option>
                        </select>


                    </div>
                    <!-- <div class="col-md-2">
                        <button type="submit" class="btn btn-primary" @click="edit">編輯</button>
                    </div> -->
                </div>
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
    font-size: 20px;
    margin-top: 20px;
    margin-left: 40px;
}
</style>
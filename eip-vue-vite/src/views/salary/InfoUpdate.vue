<script setup>
// const route = useRoute()
// router = useRouter()
import { ref } from 'vue';
import SalaryBar from "@/components/salary/SalaryBar.vue";
import axios from "axios";
import { onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import Swal from "sweetalert2"

const route = useRoute()
const router = useRouter()
// const info = ref(SalaryInfo)
const info = ref({})

//同步更新調薪紀錄
const salaryHistory = ref({
    empId: '',
    adjustSalary: info.value.basicSalary,
    remark: '',
})
// const salaryHistory = ref({})
const laborGrades = [26400, 27600, 28800, 30300, 31800, 33300, 34800, 36300, 38200, 40100, 42000, 43900, 45800]
const pensionRate = [0, 0.01, 0.02, 0.03, 0.04, 0.05, 0.06]
const healthGrades = [26400, 27600, 28800, 30300, 31800, 33300, 34800, 36300, 38200, 40100, 42000, 43900, 45800, 48200, 50600, 53000, 55400, 57800, 60800, 69800, 72800, 76500]
const nums = [0, 1, 2, 3]

const loadData = async () => {
    const empId = route.params.empId
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/${empId}`
    const { data } = await axios.get(API_URL)

    info.value = data
    console.log(`output->`, info.value)
    console.log(`output->`, typeof info.value.welfareBenefitsDeduction)
    console.log(`output->`, info.value.welfareBenefitsDeduction)
    info.value.welfareBenefitsDeduction
}
const handleSubmit = async () => {
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/newSalaryInfo`
    try {
        const response =
            await axios.post(API_URL, info.value)
        if (response.status === 200) {

            Swal.fire({
                title: '更新成功',
                icon: 'success',

                confirmButtonText: "OK"
            }).then(() => {
                router.push('/info')
            })
        }
        else if (response.status === 400) {
            Swal.fire({
                title: '更新失敗',
                icon: 'warning',

                confirmButtonText: "OK"
            });
        }
        console.log(response);
    } catch (error) {
        console.error(error);
    }


}





const updateHistory = async () => {

    salaryHistory.value.empId = info.value.empId
    salaryHistory.value.adjustSalary = info.value.basicSalary
    salaryHistory.value.remark = info.value.remark
    // console.log(`編號` + info.value.empId)

    const API_URL = `${import.meta.env.VITE_API_JAVAURL}salaryHistory/new`
    try {
        const response =
            // console.log(salaryHistory.value)
            await axios.post(API_URL, salaryHistory.value);

        if (response.status === 200) {

            Swal.fire({
                title: '已新增調薪紀錄',
                icon: 'success',

                confirmButtonText: "OK"
            })
        }
        else if (response.status === 202) {
            Swal.fire({
                title: '薪資未異動，不用更薪拉',
                icon: 'warning',

                confirmButtonText: "OK"
            });
        }
        console.log(response);
    } catch (error) {
        console.error(error);
    }
}
// loadData()

onMounted(loadData);
</script>

<template>
    <div class="container">
        <form @submit.prevent="handleSubmit">

            <div class="row justify-content-md-center mx-auto ">
                <form @submit.prevent="updateHistory">
                    <div class="row">
                        <div class="col-md-4">
                            <label for="empId" class="form-label">員工編號</label>
                            <input type="text" class="form-control" id="empId" v-model="info.empId" placeholder="" disabled
                                readonly>
                        </div>
                        <div class="col-md-4">
                            <label for="empName" class="form-label">員工姓名</label>
                            <input type="text" class="form-control" id="empName" v-model="info.empName" disabled readonly
                                placeholder="" required="">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <label for="empId" class="form-label">基本薪資</label>
                            <input type="text" class="form-control" id="empId" v-model.trim="info.basicSalary"
                                placeholder=" " required="">
                        </div>
                        <div class="col-md-4">
                            <label for="welfare" class="form-label">福利金扣款</label>

                            <select class="form-select" aria-label="welfare" v-model="info.welfareBenefitsDeduction">
                                <option :value="'1'" :selected="info.welfareBenefitsDeduction === '1'">是</option>

                                <option :value="'0'" :selected="info.welfareBenefitsDeduction === '0'">否</option>
                            </select>


                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-8">

                            <label for="empId" class="form-label">薪資調整原因</label>
                            <div class="input-group">
                                <input type="text" class="form-control" id="empId" placeholder="若薪資有調整，請說明"
                                    v-model="info.remark"><button type="submit" class="btn btn-secondary"><i
                                        class="bi bi-arrow-repeat"></i></button>
                            </div>
                        </div>

                    </div>
                </form>
                <div style="margin-right:0;">
                    <div class="row">
                        <div class="col-md-4">
                            <label for="laborInsurance" class="form-label">勞保投保級距</label>

                            <select class="form-select" aria-label="Default select example"
                                v-model="info.laborInsuranceGrade">
                                <option v-for=" laborGrade  in  laborGrades " :value="laborGrade"
                                    :selected="laborGrade === info.laborInsuranceGrade">第{{
                                        laborGrades.indexOf(laborGrade) +
                                        1
                                    }}級({{ laborGrade }}元以下)</option>

                            </select>
                        </div>
                        <div class="col-md-4">
                            <label for="pensionRate" class="form-label">勞退自願提撥率</label>

                            <select v-model="info.laborVolunteerPensionRate" class="form-select">
                                <option v-for=" rate in pensionRate " :value="rate"
                                    :selected="rate === info.laborVolunteerPensionRate">
                                    {{ rate * 100 }}% </option>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <label for="healthInsuranceGrade" class="form-label">健保投保級距</label>
                            <select v-model="info.healthInsuranceGrade" class="form-select">
                                <option v-for=" healthGrade  in  healthGrades " :value="healthGrade"
                                    :selected="healthGrade === info.healthInsuranceGrade">
                                    第{{ healthGrades.indexOf(healthGrade) + 1 }}級({{ healthGrade }}元)</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label for="familyDepedent" class="form-label">眷屬加保人數</label>
                            <select v-model="info.familyDependantsNum" class="form-select">
                                <option v-for=" num  in  nums " :value="num" :selected="num === info.familyDependantsNum">{{
                                    num
                                }}人
                                </option>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <!-- <div class="col-md-4">
                            <label for="welfare" class="form-label">福利金扣款</label>

                            <select class="form-select" aria-label="welfare" v-model="info.welfareBenefitsDeduction">
                                <option :value="1">是</option>
                                <option :value="0">否</option>
                            </select>


                        </div> -->
                        <div class="col-md-6"></div>
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-primary btn-sm mt-3">儲存</button>
        </form>
    </div>
</template>
   
<style scoped>
.form-label {
    margin-top: 10px;
    font-weight: bold;
}
</style>
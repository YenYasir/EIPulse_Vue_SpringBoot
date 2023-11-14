<script setup>
import axios from "axios";
import Swal from "sweetalert2"
import { ref } from "vue"
import { onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
const route = useRoute()
const router = useRouter()

const laborGrades = [26400, 27600, 28800, 30300, 31800, 33300, 34800, 36300, 38200, 40100, 42000, 43900, 45800]
const pensionRate = [0, 0.01, 0.02, 0.03, 0.04, 0.05, 0.06]
const healthGrades = [26400, 27600, 28800, 30300, 31800, 33300, 34800, 36300, 38200, 40100, 42000, 43900, 45800, 48200, 50600, 53000, 55400, 57800, 60800, 69800, 72800, 76500]
const nums = [0, 1, 2, 3]
const datas = ref({
    empId: " ",
    basicSalary: "",
    laborInsuranceGrade: "",
    laborVolunteerPensionRate: "",
    healthInsuranceGrade: "",
    familyDependantsNum: "",
    welfareBenefitsDeduction: "",

})

const handleSubmit = async () => {
    const empId = datas.value.empId
    const response =
        await axios.post('http://localhost:8090/eipulse/payroll/newSalaryInfo', datas.value)
    Swal.fire({
        title: '新增成功',
        icon: 'success',
        confirmButtonText: "確定"

    }).then(() => {
        router.push('/info')
    })
}

const checkEmp = async () => {
    try {
        const empId = datas.value.empId;

        // response.data==>empId / empName
        const response = await axios.get('http://localhost:8090/eipulse/payroll/checkEmpId', { params: { empId: empId } })
        console.log(`outputQ->`, response)
        datas.value.empName = response.data.empName;
        datas.value.empId = response.data.empId;
        if (response.data === '') {
            await Swal.fire({
                title: '尚未建立員工基本資料',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: "了解",
                cancelButtonText: "取消"
            })
        }
        // response2.data==>true/false
        const response2 = await axios.get('http://localhost:8090/eipulse/payroll/checkInfo', { params: { empId: empId } })

        if (response2.data === true) {
            await Swal.fire({
                title: `「${response.data.empName}」已建立`,
                text: '如需修改，請點選下方按鈕',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: "修改資料",
                cancelButtonText: "取消"
            }).then((result) => {
                if (result.isConfirmed) {
                    // Confirm 按钮点击
                    console.log(`outputEmpId->`, datas.value.empId)
                    router.push('/salary/info/update/' + datas.value.empId)
                } else if (result.isDismissed) {
                    // Cancel 按钮点击
                }
            })
        }
    } catch (e) {
        console.error(e);
    }
}
</script>

<template>
    <div class="container c ">
        <form @submit.prevent="handleSubmit">
            <div class="row justify-content-md-center">
                <div class="row">
                    <div class="col-md-4">
                        <label for="empId" class="form-label">員工編號</label>
                        <input type="text" class="form-control" id="empId" v-model.trim="datas.empId" @change="checkEmp"
                            placeholder="">
                    </div>
                    <div class="col-md-4">
                        <label for="empName" class="form-label">員工姓名</label>
                        <input type="text" class="form-control" id="empName" v-model="datas.empName" placeholder=""
                            required="">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <label for="empId" class="form-label">基本薪資</label>
                        <input type="text" class="form-control" id="empId" v-model.trim="datas.basicSalary"
                            placeholder="請輸入數字 " required="">
                    </div>
                    <div class="col-md-4">
                        <label for="welfare" class="form-label">福利金扣款</label>
                        <select class="form-select" aria-label="welfare" v-model="datas.welfareBenefitsDeduction"
                            required="">
                            <option disabled value="">---請選擇---</option>
                            <option :value="'1'">是</option>
                            <option :value="'0'">否</option>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <label for="laborInsurance" class="form-label">勞保投保級距</label>
                        <select class="form-select" aria-label="Default select example" v-model="datas.laborInsuranceGrade"
                            required="">
                            <option disabled value="">--請選擇投保級距--</option>
                            <option v-for=" laborGrade  in  laborGrades " :value="laborGrade">第{{
                                laborGrades.indexOf(laborGrade) +
                                1
                            }}級({{ laborGrade }}元以下)</option>

                        </select>
                    </div>
                    <div class="col-md-4">
                        <label for="pensionRate" class="form-label">勞退自願提撥率</label>
                        <select v-model="datas.laborVolunteerPensionRate" class="form-select" required="">
                            <option disabled value="">---請選擇自願提撥率---</option>
                            <option v-for=" rate in pensionRate " :value="rate">
                                {{ rate * 100 }}% </option>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <label for="healthInsuranceGrade" class="form-label">健保投保級距</label>
                        <select v-model="datas.healthInsuranceGrade" class="form-select" required="">
                            <option disabled value="">---請選擇投保級距---</option>
                            <option v-for=" healthGrade  in  healthGrades " :value="healthGrade">
                                第{{ healthGrades.indexOf(healthGrade) + 1 }}級({{ healthGrade }}元)</option>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <label for="familyDepedent" class="form-label">眷屬加保人數</label>
                        <select v-model="datas.familyDependantsNum" class="form-select">
                            <option disabled value="">---請選擇眷屬加保人數---</option>
                            <option v-for=" num  in  nums " :value="num">{{ num }}人
                            </option>
                        </select>
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-warning btn-sm mt-3 " style="margin-left:10px">儲存</button>
        </form>
    </div>
</template>
<style scoped>
.formbox {
    margin: 50px auto;
    width: 75%;
}

.label {
    padding-left: 10px;
}

.inputArea {
    width: 300px;
    height: 40px;
    border: 1px solid #ced4da;
    border-radius: 10px;
    padding-left: 10px;
    margin: 0 1em 1.1em 0;

    /* 上右下左 */
}

.radioarea {
    width: 300px;
    height: 40px;
    border: 1px solid #ced4da;
    border-radius: 10px;
    padding-left: 10px;
    margin: 0 1em 1.1em 0;
}

.yn {
    text-align: justify;
    padding-left: 10px;
}

.ynArea {
    text-align: center;
    padding: 5px;
}

.c {
    margin-top: 5px;
    max-height: 500px;
    /* max-height: 50%; */
    overflow-y: auto;
}
</style>
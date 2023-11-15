<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import axios from "axios";
import { empStore } from '../../stores/employee';
const emp = empStore();
const route = useRoute()
const router = useRouter()
const date = new Date();
// const formater = date.toISOString().split('T')[0];

const slYear = ref()
const slMonth = ref()
const trials = ref({})
const addSumTotal = ref(0)
const deduSumTotal = ref(0)
const netTotal = ref(0)

const endDate = ref(emp.endDate);
const test = () => {
    console.log(222);
    getMonth()
    getYear()
}
const getYear = () => {
    // slYear.value = trials.value.endDate.getFullYear
    const inputDate = document.getElementById("endDate");
    const dateValue = new Date(inputDate.value);
    slYear.value = dateValue.getFullYear()
}
const getMonth = () => {
    console.log(3331);
    const inputDate = document.getElementById("endDate");
    const dateValue = new Date(inputDate.value);
    slMonth.value = dateValue.getMonth() + 1
    console.log("月" + slMonth.value);
}

const getSalaryTrial = async () => {
    console.log(`outputDATE->`, endDate)
    const params = {
        date: endDate.value
    }

    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/salaryTrial/findAll`

    const { data } = await axios.get(API_URL, { params })
    console.log(data);
    trials.value = data;

    // 重置 basicSalarySum 的值
    addSumTotal.value = 0;

    for (let key in trials.value) {
        const t = trials.value[key];
        addSumTotal.value += t.salaryMonthRecordDto.addSum;
        deduSumTotal.value += t.salaryMonthRecordDto.deduSum;
        netTotal.value += t.salaryMonthRecordDto.netSalary;

    }
    console.log(`outputsUM->`, addSumTotal.value)
}



const sendTrial = async () => {
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/record/saveAll`
}

const saveSearchData = () => {
    emp.setSearchDate(endDate.value);
    endDate.value = emp.endDate;
    console.log(`outputD->`, endDate.value)
}

onMounted(() => {

    // endDate.value = date;
    // if (endDate.value === '') {
    //     // console.log("formater!!!!");
    //     endDate.value = emp.endDate;
    // }
    endDate.value = emp.endDate;
    console.log(`outputDDDDDDD->`, endDate.value)
    getSalaryTrial();

})
// const isShow = ref(false)
// const show = () => {
//     isShow.value = true
// }
</script>
<template>
    <div class="container c">
        <form @submit.prevent="getSalaryTrial">
            <div class="row g-3 align-items-center mb-3">
                <div class="col-auto">
                    <label for="calculate" class="col-form-label">計算區間</label>
                </div>
                <div class="col-auto">
                    <input type="date" id="inputPassword6" class="form-control form-control-sm" aria-describedby="startDate"
                        @input="saveSearchData">
                </div>
                <div class="col-auto">
                    <span id="date" class="form-text">
                        ~
                    </span>
                </div>
                <div class="col-auto">
                    <input type="date" id="endDate" class="form-control form-control-sm" aria-describedby="endDate"
                        v-model="endDate" @change="saveSearchData">
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-warning btn-sm" @click="show">查詢</button>
                </div>
            </div>
        </form>
        <!-- <div class="table" v-show="isShow"> -->
        <form @submit.prevent="sendTrial">
            <!-- <button type="submit" class="btn btn-primary btn-sm mb-3">儲存</button> -->
            <div class="card">
                <div class="card-header">
                    <p class="head">薪資試算表</p>
                </div>
                <div class="card-body">
                    <table class="table table-sm table-hover">
                        <thead>
                            <tr>
                                <th scope="col" class="hidden-column">紀錄單號</th>
                                <th scope="col">員工編號</th>
                                <th scope="col">姓名</th>
                                <th scope="col">加項總和</th>
                                <!-- <th scope="col">應領薪資grossSalary</th> -->
                                <th scope="col">減項總和</th>
                                <th scope="col">實領薪資</th>
                                <th scope="col">動作</th>
                            </tr>
                        </thead>
                        <tbody>

                            <tr v-for="(trial, index) in trials" :key="index">
                                <!-- <td class="hidden-column"><router-Link :to="'/salary/trial/update/' + trial.salaryMonthRecordDto.id"
                            @click="handleLinkClick"><i class="bi bi-zoom-in"></i></router-Link> -->

                                <td class="hidden-column"> {{ trial.salaryMonthRecordDto.id }}
                                </td>
                                <!-- <input type="text" v-model="trial.salaryMonthRecordDto.id"> -->

                                <!-- <td><router-Link :to="'/salary/trial/update/' + trial.salaryMonthRecordDto.id"
                            @click="handleLinkClick"><i class="bi bi-zoom-in"></i></router-Link>
                    </td> -->
                                <td>
                                    {{ trial.salaryMonthRecordDto.empId }}
                                    <!-- <input type="text" v-model="trial.salaryMonthRecordDto.empId" readonly> -->
                                </td>
                                <td>
                                    {{ trial.salaryMonthRecordDto.empName }}
                                    <!-- <input type="text" v-model="trial.salaryMonthRecordDto.empName" readonly> -->
                                </td>
                                <td>
                                    <!-- <input type="text" v-model="trial.salaryMonthRecordDto.addSum"> -->
                                    {{ trial.salaryMonthRecordDto.addSum.toLocaleString() }}
                                </td>

                                <!-- <td>
                        <input type="text" v-model="trial.salaryMonthRecordDto.grossSalary"> -->
                                <!-- {{ trial.salaryMonthRecordDto.grossSalary }}
                    </td> -->
                                <td>
                                    <!-- <input type="text" v-model="trial.salaryMonthRecordDto.deduSum"> -->
                                    {{ trial.salaryMonthRecordDto.deduSum.toLocaleString() }}
                                </td>
                                <td>
                                    <!-- <input type="text" v-model="trial.salaryMonthRecordDto.netSalary"> -->
                                    {{ trial.salaryMonthRecordDto.netSalary.toLocaleString() }}
                                </td>
                                <td><router-Link :to="'/salary/trial/update/' + trial.salaryMonthRecordDto.id"
                                        @click="handleLinkClick" class="btn btn-secondary  btn-sm mx-1">
                                        <i class=" bi bi-pencil-square"></i></router-Link>
                                </td>
                            </tr>
                        </tbody>
                        <tbody>
                            <tr>
                                <td></td>
                                <td style="font-weight: bold;">小計</td>
                                <td style="font-weight: bold;">{{ addSumTotal.toLocaleString() }}</td>
                                <td style=" font-weight: bold;">{{ deduSumTotal.toLocaleString() }}</td>
                                <td style="font-weight: bold;">{{ netTotal.toLocaleString() }}</td>
                                <td></td>
                            </tr>
                        </tbody>

                    </table>
                </div>
            </div>
        </form>
    </div>
    <!-- </div> -->
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

.c {
    margin-top: 20px;

    margin-top: 5px;
    max-height: 450px;
    /* max-height: 50%; */
    overflow-y: auto;
}


.div1 {

    margin-bottom: 15px;
    margin-top: 20px;
}

.head {
    font-weight: bold;
    margin: 2px;
    font-size: 20px;
    text-align: center;
}
</style>

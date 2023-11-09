<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import axios from "axios";
import { empStore } from '../../stores/employee';
const route = useRoute()
const router = useRouter()
const selectYear = ref(123);
const selectMonth = ref()
const isShow = ref(false)
// const paySlips = ref([])
// const datas = ref({
//     empId: route.params.empId,
//     slYear: '',
//     slMonth: ''
// })
const trial = ref({
    salaryMonthRecordDto: {},
    detaildDto: []
})

const addList = ref([])
const deduList = ref([])

const loadData = async () => {

    // 清空 addList 和 deduList 数组
    addList.value = []
    deduList.value = []

    console.log(`outputyyy->`, selectYear)
    console.log(`outputType->`, typeof parseInt(selectYear.value))

    const params = {
        empId: route.params.empId,
        slYear: parseInt(selectYear.value),
        slMonth: parseInt(selectMonth.value)

    }
    console.log(params);
    const empId = route.params.empId
    console.log(empId)
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/record`
    const { data } = await axios.get(API_URL, { params })
    console.log(data)
    trial.value = data
    console.log(`output->`, 11111111111)
    console.log(`outputPPP->`, trial.value)
    // for (let a = 0; a < paySlips.value.length; a++) {
    //     paySlips.value[a].switch = false;
    // }

    // console.log(`outputY->`, year.value)
    // console.log(`outputM->`, month.value)
    // 分別取出加項或減項明細
    for (let b = 0; b < trial.value.detaildDto.length; b++) {

        if (trial.value.detaildDto[b].calculateType === 'P') {
            // const addItem = ({})
            // addItem.value =
            addList.value.push(trial.value.detaildDto[b]);
        } else {
            // const deduItem = ({})
            // deduItem.value = trial.value.detaildDto[b]
            deduList.value.push(trial.value.detaildDto[b])
        }
    }
    // $ 千進位
    trial.value.salaryMonthRecordDto.netSalary = trial.value.salaryMonthRecordDto.netSalary.toLocaleString();
    trial.value.salaryMonthRecordDto.addSum = trial.value.salaryMonthRecordDto.addSum.toLocaleString();
    trial.value.salaryMonthRecordDto.deduSum = trial.value.salaryMonthRecordDto.deduSum.toLocaleString();
}




const year = ref([])
const month = ref([])

const selectYearAndMonth = () => {
    let currentYear = new Date().getFullYear()  // 2023
    let currentMonth = new Date().getMonth() + 1   // 9

    for (let i = currentYear; i >= currentYear - 2; i--) {
        year.value.push(i)
    }
    // 預設10號發薪水,10號之前看不到號之前看不到
    let dateNow = new Date()
    console.log(`outputNow->`, dateNow)
    if (dateNow.getDate() >= 10) {
        for (let j = 1; j < currentMonth; j++) {
            month.value.push(j)
        }
    } else {
        for (let j = 1; j <= currentMonth - 1; j++) {
            month.value.push(j)
        }
    }
}

// created(() => {
//     loadData()
// }),

const show = () => {
    isShow.value = true
}
// onMounted(loadData);
onMounted(() => {
    selectYearAndMonth()
    loadData()

})

// onMounted(selectYearAndMonth)
</script>

<template>
    <div class="search">
        <form @submit.prevent="loadData" class="row row-cols-lg-auto g-3 align-items-center">
            <label class="#" for="inlineFormSelectPref">請輸入欲查詢的月份</label>
            <div class="col-12">

                <select class="form-select" id="selectYear" v-model.trim="selectYear">
                    <option selected>Choose...</option>
                    <option v-for="y in year">{{ y }}</option>
                </select>
            </div>

            <div class="col-12">
                <label class="visually-hidden" for="inlineFormSelectPref">Year</label>
                <select class="form-select" id="selectMonth" v-model.trim="selectMonth">
                    <option selected>Choose...</option>
                    <option v-for="m in month">{{ m }}</option>
                </select>
            </div>
            <div class="col-12">
                <button type="submit" class="btn btn-primary" @click="show">查詢</button>
                <!-- <button class="btn btn-primary" @click="loadData">查詢</button> -->
            </div>
        </form>
    </div>
    <!-- 薪資單 -->
    <div class="container">
        <div class="div1" style="margin-bottom:15px" v-show="isShow">

            <div class="card">
                <div class="card-body">
                    <th>
                        <!-- <div class="col-md-2"> -->
                        <!-- <label for="empId" class="form-label">員工編號</label> -->
                        員工編號
                    </th>
                    <th> {{ trial.salaryMonthRecordDto.empId }}
                    </th>
                    <td>員工姓名</td>
                    <td> {{ trial.salaryMonthRecordDto.empName }}</td>
                    <table class="table table-bordered">
                        <tr>
                            <th>計薪區間(年)</th>
                            <!-- <div class="row">
                                    <div class="col-md-2"> -->
                            <!-- <label for="empId" class="form-label">計薪區間(年)</label> -->


                            <th> {{
                                trial.salaryMonthRecordDto.slYear }}/{{
        trial.salaryMonthRecordDto.slMonth }}</th>
                            <!-- <input type="text" class="form-control" id="empId" readonly
                                            v-model="trial.salaryMonthRecordDto.slYear"> -->
                            <!-- </div>
                                </div> -->

                            <!-- <div class="col-md-2">
                                <label for="empId" class="form-label">計薪區間(月)</label>
                                <input type="text" class="form-control" id="empId" readonly
                                    v-model="trial.salaryMonthRecordDto.slMonth">
                            </div> -->

                            <!-- <input type="text" class="form-control" id="empId" readonly
                                        v-model="trial.salaryMonthRecordDto.empId"> -->
                            <!-- </div> -->


                            <!-- <div class="col-md-2"> -->
                            <!-- <label for="empId" class="form-label">員工姓名</label> -->

                            <!-- <input type="text" class="form-control" id="empName" readonly
                                        v-model="trial.salaryMonthRecordDto.empName"> -->
                            <!-- </div> -->

                        </tr>


                        <!-- <div class="row">
                            <div class="col-md-2">
                                <label for="empId" class="form-label">實領薪資</label>
                                <input type="text" class="form-control" id="empName" readonly
                                    v-model="trial.salaryMonthRecordDto.netSalary">
                            </div>
                            <div class="col-md-2">
                                <label for="empId" class="form-label">應發小計</label>
                                <input type="text" class="form-control" id="empName" readonly
                                    v-model="trial.salaryMonthRecordDto.addSum">
                            </div>
                            <div class="col-md-2">
                                <label for="empId" class="form-label">應扣小計</label>
                                <input type="text" class="form-control" id="empName" readonly
                                    v-model="trial.salaryMonthRecordDto.deduSum">
                            </div>
                        </div> -->
                        <tr>
                            <th>實領薪資</th>
                            <th>{{ trial.salaryMonthRecordDto.netSalary }}</th>
                            <!-- <th>應發小計</th>
                            <th>{{ trial.salaryMonthRecordDto.addSum }}</th>
                            <th>應扣小計</th>
                            <th>{{ trial.salaryMonthRecordDto.deduSum }}</th> -->

                        </tr>

                    </table>

                </div>


                <!-- <div class="container"> -->
                <div class="row g-5">
                    <div class="col-md-6 col-lg-6">
                        <!-- <div class="card"> -->
                        <div class="card-body">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th colspan="2">應發明細</th>
                                    </tr>
                                    <tr>
                                        <th scope="col">項目</th>
                                        <th scope="col">金額</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="(a, index) in addList" :key="index">
                                        <td>{{ a.subjectName }}
                                            <!-- <input type="text" class="form-control fc text-center" id="add" readonly
                                            v-model="a.subjectName"> -->
                                        </td>
                                        <td>{{ a.amount.toLocaleString() }}
                                            <!-- <input type="text" class="form-control fc text-center" id="add" v-model="a.amount"> -->
                                        </td>
                                    </tr>
                                    <th>應發小計</th>
                                    <th>{{ trial.salaryMonthRecordDto.addSum }}</th>
                                </tbody>
                            </table>
                            <!-- </div> -->
                        </div>
                    </div>
                    <div class="col-md-6 col-lg-6">
                        <!-- <div class="card"> -->

                        <div class="card-body">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th colspan="2">應扣明細</th>
                                    </tr>
                                    <tr>
                                        <th scope="col">項目</th>
                                        <th scope="col">金額</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="(d, index) in deduList" :key="index">
                                        <td>{{ d.subjectName }}
                                            <!-- <input type="text" class="form-control fc" id="add" readonly
                                            v-model="d.subjectName"> -->
                                        </td>
                                        <td>{{ d.amount.toLocaleString() }}
                                            <!-- <input type="text" class="form-control fc" id="add" v-model="d.amount"> -->
                                        </td>
                                    </tr>
                                    <th>應扣小計</th>
                                    <th>{{ trial.salaryMonthRecordDto.deduSum }}</th>
                                </tbody>
                            </table>
                            <!-- </div> -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>



    <!-- <template v-for="(p, index) in    paySlips   " :key="p.empId">
        <div class="accordion accordion-flush" id="accordionFlushExample">
            <div class="accordion-item">
                <h2 class="accordion-header" id="flush-headingOne">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                        data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne"
                        @click="toggleList(index)">
                        {{ p.salaryMonthRecordDto.slYear }}/{{ p.salaryMonthRecordDto.slMonth }}
                    </button>
                </h2>
                <div id="flush-collapseOne" class="accordion-collapse collapse" aria-labelledby="flush-headingOne"
                    data-bs-parent="#accordionFlushExample">
                    <div class="accordion-body"></div>
                </div>
            </div> -->
    <!-- <div class="accordion-item">
                <h2 class="accordion-header" id="flush-headingTwo">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                        data-bs-target="#flush-collapseTwo" aria-expanded="false" aria-controls="flush-collapseTwo">
                        <td>{{ p.salaryMonthRecordDto.slYear }}</td>
                        <td>{{ p.salaryMonthRecordDto.slMonth }}</td>
                    </button>
                </h2>
                <div id="flush-collapseTwo" class="accordion-collapse collapse" aria-labelledby="flush-headingTwo"
                    data-bs-parent="#accordionFlushExample">
                    <div class="accordion-body"></div>
                </div>
            </div>
            <div class="accordion-item">
                <h2 class="accordion-header" id="flush-headingThree">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                        data-bs-target="#flush-collapseThree" aria-expanded="false" aria-controls="flush-collapseThree">
                        Accordion Item #3
                    </button>
                </h2>
                <div id="flush-collapseThree" class="accordion-collapse collapse" aria-labelledby="flush-headingThree"
                    data-bs-parent="#accordionFlushExample">
                    <div class="accordion-body"></div>
                </div>
            </div> -->
    <!-- </div>
    </template> -->
    <!-- 
    <table class="table">
        <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">發薪年</th>
                <th scope="col">發薪月</th>
                <th scope="col">加項總和</th>
                <th scope="col">減項總和</th>
                <th scope="col">實領總額</th>
            </tr>
        </thead>
        <tbody>
            <template v-for="(p, index) in    paySlips   " :key="p.empId">

                <tr>
                    <td scope="row">
                        <button class="btn btn-link" type="button" @click="toggleList(index)"><i
                                class="bi bi-activity"></i></button>
                    </td>
                    <td>{{ p.salaryMonthRecordDto.slYear }}</td>
                    <td>{{ p.salaryMonthRecordDto.slMonth }}</td>
                    <td>{{ p.salaryMonthRecordDto.addSum }}</td>
                    <td>{{ p.salaryMonthRecordDto.deduSum }}</td>
                    <td>{{ p.salaryMonthRecordDto.netSalary }}</td>

                </tr>
                <tr v-show="p.switch"> -->
    <!-- <td colspan="9"> -->
    <!-- <table class="table" v-show="p.switch" style="background-color:bisque">

                        <tr v-show="p.switch">

                            <td scope="col"></td>
                            <td scope="col"></td>
                            <td scope="col"></td>
                            <td scope="col"></td>
                            <td scope="col">金額</td>
                            <td scope="col"></td>
                            <td scope="col"></td>
                            <td scope="col"></td>
                            <td scope="col"></td>
                        </tr>

                        <tr v-show="p.switch">
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr v-show="p.switch">
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr v-show="p.switch">
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td>金額</td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr v-show="p.switch">
                            <td>勞保費</td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td>SSSSD</td>
                            <td></td>
                            <td></td>
                        </tr>
                    </table>

                </tr>


            </template>
        </tbody>
    </table> -->
</template>
    
    
<style scoped>
.search {
    margin-top: 30px;
    margin-left: 30px;
    margin-bottom: 10px;
}

td {
    vertical-align: middle;
    width: 200px;
    text-align: center;
}

th {
    vertical-align: middle;
    text-align: center;
}
</style>
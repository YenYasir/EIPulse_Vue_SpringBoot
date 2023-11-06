<script setup>
import { ref } from 'vue';

import axios from "axios";
import { onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import SalaryBar from "@/components/salary/SalaryBar.vue";
import Swal from "sweetalert2"

const route = useRoute()
const router = useRouter()

const trial = ref({
    salaryMonthRecordDto: {},
    detaildDto: []
})



const subPrice = ref([]);
const subjectPlus = ref([])
const getPlusSubject = async () => {

    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/subject/plus`
    const response = await axios.get(API_URL)
    subjectPlus.value = response.data
    console.log(response.data);
    console.log("回傳值" + subjectPlus.value)
    // for (let i = 0; i < subject.value.length; i++) {
    //     selectedAmount.value = subject.value[i].amountDefault
    // }

    // console.log(`output->`, selectedAmount.value)
}

const subjectMinus = ref({})
const getMinusSubject = async () => {

    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/subject/minus`
    const response = await axios.get(API_URL)
    subjectMinus.value = response.data
    console.log(response.data);
    console.log("回傳值" + subjectMinus.value)
    // for (let i = 0; i < subject.value.length; i++) {
    //     selectedAmount.value = subject.value[i].amountDefault
    // }

    // console.log(`output->`, selectedAmount.value)
}





const selectedSubjectA = ref([
    // n: '',
    // subjectId: '',
    // amount: ''
])
const A = (i) => {
    selectedSubjectA[i].value.empId = trial.value.salaryMonthRecordDto.empId
}
const D = (i) => {
    selectedSubjectD[i].value.empId = trial.value.salaryMonthRecordDto.empId

}
const selectedSubjectD = ref([
    // n: '',
    // subjectId: '',
    // amount: ''
])

// 加項新增欄位
const countA = ref(0)
const formCountA = ref(0)
const addList = ref([])
const addForm = () => {
    const newitem = {
        // n: '',
        // // id: '',
        subjectId: '',
        amount: ''
    }
    // formCount.value.push(count)
    selectedSubjectA.value.push(newitem)
    formCountA.value++
    getPlusSubject()
}

// 減項新增欄位
const countD = ref(0)
const formCountD = ref(0)
const deduList = ref([])
const deduForm = () => {
    const newitem = {
        // n: '',
        subjectId: '',
        amount: ''
    }
    // formCount.value.push(count)
    selectedSubjectD.value.push(newitem)
    formCountD.value++
    getMinusSubject()
}

// 載入資料
const loadData = async () => {
    const id = route.params.id
    console.log(`output->`, id)
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/salaryTrial/${id}`
    const response = await axios.get(API_URL)
    // console.log(`output111->`, response)
    // console.log(`更新`, response.data)
    trial.value = response.data
    console.log(`output->`, trial.value)
    // console.log(`output->`, trial.value)
    // console.log(`output->`, trial.value.salaryMonthRecordDto.slYear)
    // console.log(`type`, trial.value.detaildDto[1].calculateType)
    // console.log(`$$`, trial.value.detaildDto[4].amount)
    // console.log(`sName`, trial.value.detaildDto[4].subjectName)
    // console.log(`output->`, subjectPlus.value)

    // for (let a = 0; a < trial.value.detaildDto.length; a++) {

    //     if (trial.value.detaildDto[a].calculateType === 'P') {
    //         trial.value.detaildDto[a].add = true;
    //     } else {
    //         trial.value.detaildDto[a].add = false;
    //     }

    // }
    // const a = trial.value.detaildDto[1].add
    // console.log(`output->`, a)

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
    console.log(`output->dedu`, deduList.value[1])


}

const sendData = ref({
    salaryMonthRecordDto: {
        recordId: '',
        empId: '',
        slYear: '',
        slMonth: '',
        addSum: '',
        deduSum: '',
        netSalary: '',
        createdDate: '',
    },
    detaildDto: [{
        id: '',
        empId: '',
        subjectId: '',
        amount: '',
        createdDate: '',
        recoirdId: '',
    }]
})

const handleSubmit = async () => {

    for (let obj of selectedSubjectA.value) {
        obj.recordId = trial.value.salaryMonthRecordDto.id
        obj.empId = trial.value.salaryMonthRecordDto.empId
    }

    for (let obj of selectedSubjectD.value) {
        obj.recordId = trial.value.salaryMonthRecordDto.id
        obj.empId = trial.value.salaryMonthRecordDto.empId
    }

    sendData.value.salaryMonthRecordDto = trial.value.salaryMonthRecordDto
    sendData.value.detaildDto = trial.value.detaildDto.concat(selectedSubjectA.value, selectedSubjectD.value)

    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/record/update`
    try {
        const response = await axios.post(API_URL, trial.value)
        console.log(`output->`, response.data)
    } catch (error) {
        console.error(error);
    }
    // sendData.value.salaryMonthRecordDto.recordId = trial.value.salaryMonthRecordDto.id,
    //     sendData.value.salaryMonthRecordDto.empId = trial.value.salaryMonthRecordDto.empId,
    //     sendData.value.salaryMonthRecordDto.slYear = trial.value.salaryMonthRecordDto.slYear
    // sendData.value.salaryMonthRecordDto.slMonth = trial.value.salaryMonthRecordDto.slMonth,
    //     sendData.value.salaryMonthRecordDto.addSum = trial.value.salaryMonthRecordDto.slYear,
    //     sendData.value.salaryMonthRecordDto.deduSum = trial.value.salaryMonthRecordDto.deduSum,
    //     sendData.value.salaryMonthRecordDto.netSalary = trial.value.salaryMonthRecordDto.addSum,
    //     sendData.value.salaryMonthRecordDto.createdDate = trial.value.salaryMonthRecordDto.createdDate,

    //     sendData.value.detaildDto = trial.value.detaildDto.concat(addList.value)
    // sendData.value.detaildDto = trial.value.detaildDto.concat(deduList.value)

    // sendData.value.detaildDto = trial.value.detaildDto.concat(selectedSubjectB.value)
    // console.log(`回傳output->`, sendData.value.recordId)
    console.log(`output->output`, sendData.value)

}

// const getAmountDefaultById = (subjectId) => {
//     subjectPlus.find(subjectId)
// }

const getAmountBySubjectId = (id) => {
    const subject = selectedSubjectA[index].find(s => s.id === id)

    return subject ? subject.amount : 0
}

// change事件
const changeAmountA = (index) => {
    const id = selectedSubjectA[index].subjectId
    const amount = getAmountBySubjectId(id)

    selectedSubjectA[index].amount.value = amount

    console.log(selectedSubjectA[index].amount.value)

}


// const changeAmountA = (subjectId, index) => {
//     for (let a of selectedSubjectA.value) {
//         if (a.subjectId === subjectId)
//             selectedSubjectA[index].amount.value = a.amount
//     }
//     // selectedSubjectA[index].amount.value = selectedSubjectA[index].value.amount

//     // Object.assign(selectedSubjectA[index].amount, selectedSubjectA[index].subjectId.value)
//     console.log('~~~' + selectedSubjectA[index].value.amount);
// }
const add = ref(true)



// 計算加項總和
const calculateAddSum = () => {
    let a = trial.value.salaryMonthRecordDto.addSum;
    for (let i = 0; i < selectedSubjectA.value.length; i++) {
        a = parseInt(selectedSubjectA.value[i].amount) + a;
    }
    console.log(a);

    trial.value.salaryMonthRecordDto.addSum = a
}
// 計算減項總和
const calculateDeduSum = () => {
    let d = trial.value.salaryMonthRecordDto.deduSum;
    for (let i = 0; i < selectedSubjectD.value.length; i++) {
        d = parseInt(selectedSubjectD.value[i].amount) + d;
    }
    console.log(d);

    trial.value.salaryMonthRecordDto.deduSum = d
}

onMounted(loadData)

</script>
<template>
    <form @submit.prevent="handleSubmit">
        <button type="submit" class="btn btn-primary btn-sm mb-3">更新</button>
        <div class="container">
            <div class="div1" style="margin-bottom:15px">
                <div class="row">
                    <div class="col-md-2 hidden-column">
                        <label for="empId" class="form-label">recordId</label>
                        <td><input type="hidden" class="form-control-sm " id="recordId"
                                v-model="trial.salaryMonthRecordDto.id">
                        </td>
                    </div>
                    <div class="col-md-2 hidden-column">
                        <label for="empId" class="form-label"></label>
                        <td><input type="text" class="form-control-sm" id="recordId"
                                v-model="trial.salaryMonthRecordDto.createdDate" disabled readonly></td>
                    </div>
                    <div class="col-md-2">
                        <label for="empId" class="form-label">計薪區間(年)</label>
                        <input type="text" class="form-control" id="empId" readonly
                            v-model="trial.salaryMonthRecordDto.slYear">
                    </div>
                    <div class="col-md-2">
                        <label for="empId" class="form-label">計薪區間(月)</label>
                        <input type="text" class="form-control" id="empId" readonly
                            v-model="trial.salaryMonthRecordDto.slMonth">

                    </div>
                    <div class="col-md-2">
                        <label for="empId" class="form-label">員工編號</label>
                        <input type="text" class="form-control" id="empId" readonly
                            v-model="trial.salaryMonthRecordDto.empId">
                    </div>
                    <div class="col-md-2">
                        <label for="empId" class="form-label">員工姓名</label>
                        <input type="text" class="form-control" id="empName" readonly
                            v-model="trial.salaryMonthRecordDto.empName">
                    </div>
                </div>
                <div class="div2">
                    <div class="row">
                        <div class="col-md-2">
                            <label for="empId" class="form-label">實領薪資</label>
                            <input type="text" class="form-control" id="empName" readonly
                                v-model="trial.salaryMonthRecordDto.netSalary">
                        </div>
                        <div class="col-md-2">
                            <label for="empId" class="form-label">加項加總</label>
                            <input type="text" class="form-control" id="empName" readonly
                                v-model="trial.salaryMonthRecordDto.addSum">
                        </div>
                        <div class="col-md-2">
                            <label for="empId" class="form-label">減項加總</label>
                            <input type="text" class="form-control" id="empName" readonly
                                v-model="trial.salaryMonthRecordDto.deduSum">
                        </div>
                    </div>
                </div>
            </div>

            <!-- <div class="container"> -->
            <div class="row g-5">
                <div class="col-md-6 col-lg-6">
                    <div class="card">
                        <div class="card-header">

                            <h5 class="card-title" style="margin:0;font-weight: bold;"> <span><button
                                        @click.prevent="addForm"
                                        style="border: transparent;background-color:transparent;"><i class=" bi
                                    bi-plus-circle"></i></button></span>加項明細</h5>
                        </div>
                        <div class="card-body">
                            <table class="table table-sm">
                                <thead>
                                    <tr>
                                        <th scope="col" class="hidden-column">#</th>
                                        <th scope="col" class="hidden-column">項目id</th>
                                        <th scope="col">科目名稱</th>
                                        <th scope="col">金額</th>
                                        <th scope="col" class="hidden-column">recordId</th>
                                        <th scope="col" class="hidden-column">createdDate</th>

                                    </tr>
                                </thead>
                                <tbody>
                                    <!-- <tr v-for="d in trial.detaildDto"> -->

                                    <tr v-for="(a, index) in addList" :key="index">
                                        <th scope="row" class="hidden-column ">
                                            <input type="text" class="form-control fc" id="add" disabled readonly
                                                v-model="a.id">
                                            <!-- <input type="text" class="form-control" id="empName" disabled readonly
                                            v-model="d.calculateType"> -->
                                        </th>
                                        <th scope="row" class="hidden-column">
                                            <input type="text" class="form-control fc" id="add" readonly
                                                v-model="a.subjectId">
                                        </th>
                                        <th scope="row">
                                            <input type="text" class="form-control fc text-center" id="add" readonly
                                                v-model="a.subjectName">
                                        </th>
                                        <th scope="row">
                                            <input type="text" class="form-control fc text-center" id="add"
                                                v-model="a.amount">
                                        </th>
                                        <th scope="row" class="hidden-column"> <input type="text" class="form-control "
                                                id="add" v-model="a.recordId"></th>
                                        <th scope="row"> <input type="text" class="form-control hidden-column" id="add"
                                                v-model="a.createdDate"></th>
                                    </tr>


                                    <template v-for="(n, index) in formCountA   " :key="n">
                                        <tr>
                                            <!-- <div class="row"> -->
                                            <th>
                                                <div class=" form-floating text-center">
                                                    <select class="form-control  text-center"
                                                        v-model="selectedSubjectA[index].subjectId" @change="changeAmountA">
                                                        <option value="">科目名稱</option>
                                                        <option v-for="s in subjectPlus" :value="s.subjectId"
                                                            :key="s.subjectId">{{
                                                                s.subjectName }}</option>
                                                    </select>
                                                </div>

                                            </th>
                                            <th>
                                                <div class="form-floating ">
                                                    <!-- <label for="price">金額</label> -->
                                                    <input v-model="selectedSubjectA[index].amount" type="text"
                                                        class="form-control  text-center" id="price" placeholder="請輸入數字"
                                                        @change="calculateAddSum">

                                                </div>

                                                <!-- </div> -->
                                            </th>
                                        </tr>
                                    </template>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-lg-6">
                    <div class="card">
                        <div class="card-header">
                            <h5 class="card-title" style="margin:0;font-weight: bold;"> <span><button
                                        @click.prevent="deduForm"
                                        style="border: transparent;background-color:transparent;"><i class=" bi
                                    bi-plus-circle"></i></button></span>減項明細</h5>
                        </div>
                        <div class="card-body">
                            <table class="table table-sm">
                                <thead>
                                    <tr>
                                        <th scope="col" class="hidden-column"></th>
                                        <th scope="col" class="hidden-column">項目id</th>
                                        <th scope="col">科目名稱</th>
                                        <th scope="col">金額</th>
                                        <th scope="col" class="hidden-column">recordId</th>

                                    </tr>
                                </thead>
                                <tbody>
                                    <!-- <tr v-for="d in trial.detaildDto"> -->
                                    <tr v-for="(d, index) in deduList" :key="index">
                                        <!-- <th scope="row" style="display: none;">
                                            <input type="text" class="form-control" id="add" disabled readonly
                                                v-model="d.subjectId">-->
                                        <!-- <input type="text" class="form-control" id="empName" disabled readonly
                                            v-model="d.calculateType"> -->
                                        <!-- </th> -->
                                        <th scope="row" class="hidden-column">
                                            <input type="text" class="form-control fc" id="add" readonly
                                                v-model="d.subjectId">
                                        </th>
                                        <th scope="row">
                                            <input type="text" class="form-control fc" id="add" readonly
                                                v-model="d.subjectName">
                                        </th>
                                        <th scope="row">
                                            <input type="text" class="form-control fc" id="add" v-model="d.amount">
                                        </th>
                                        <th scope="row" class="hidden-column fc"> <input type="text" class="form-control"
                                                id="add" v-model="d.recordId"></th>
                                    </tr>
                                    <!-- <button @click.prevent="deduForm" class="btn btn-outline-primary">+</button> -->
                                    <template v-for="(n, index) in formCountD   " :key="n">
                                        <tr>
                                            <th>
                                                <div class=" form-floating text-center">
                                                    <select class="form-control text-center"
                                                        v-model="selectedSubjectD[index].subjectId" @change="changeSubType">
                                                        <option value="" disabled>科目名稱</option>
                                                        <option v-for="m in subjectMinus" :value="m.subjectId"
                                                            :key="m.subjectId">{{
                                                                m.subjectName }}</option>
                                                    </select>
                                                </div>

                                            </th>
                                            <th>
                                                <div class="form-floating ">
                                                    <!-- <label for="price">金額</label> -->
                                                    <input v-model="selectedSubjectD[index].amount" type="text"
                                                        class="form-control text-center" id="price" placeholder="請輸數字"
                                                        @change="calculateDeduSum">
                                                </div>

                                                <!-- </div> -->
                                            </th>
                                        </tr>
                                    </template>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- </div> -->
        </div>
    </form>
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

/* 去除输入框边框 */
.fc {
    background-color: transparent;
    border: none;
    /* border-radius: 0; */
    text-align: justify;
    /* 可选，去除圆角 */
    /* border-bottom: 1px solid #333; */
    text-align: center;
}

/* 设置底部边线的样式和颜色 */
/* 鼠标悬停时的效果，可选 */
input.form-control:hover {
    border-color: #333;
    /* 更改底部边线的颜色 */
}

/* 输入框聚焦时的效果，可选 */
input.form-control:focus {
    border-color: #555;
    /* 更改底部边线的颜色 */
    box-shadow: none;
    /* 去除默认的聚焦时阴影效果 */
}

.no-background {
    background-color: transparent;
    border: none;
    /* 可选，去除边框 */
    border-radius: 0;
    /* 可选，去除圆角 */
}
</style>
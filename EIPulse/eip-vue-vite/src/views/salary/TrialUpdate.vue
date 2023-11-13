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

const subjectPlus = ref([])
const getPlusSubject = async () => {
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/subject/plus`
    const response = await axios.get(API_URL)
    subjectPlus.value = response.data

}

const subjectMinus = ref({})
const getMinusSubject = async () => {

    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/subject/minus`
    const response = await axios.get(API_URL)
    subjectMinus.value = response.data
}

const selectedSubjectA = ref([
])

const selectedSubjectD = ref([
])

// 加項新增欄位
const countA = ref(0)
const formCountA = ref(0)
const addList = ref([])
const addForm = () => {
    const newitem = {
        subjectId: '',
        amount: 0
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
        subjectId: '',
        amount: ''
    }
    selectedSubjectD.value.push(newitem)
    formCountD.value++
    getMinusSubject()
}
// 刪除新增的減項項目
const deleteDeduItem = (index) => {

    //重新計算減項小計
    trial.value.salaryMonthRecordDto.deduSum = trial.value.salaryMonthRecordDto.deduSum - selectedSubjectD.value[index].amount;
    // 將被刪掉的減項金額從netSalary加回
    trial.value.salaryMonthRecordDto.netSalary = trial.value.salaryMonthRecordDto.netSalary + parseInt(selectedSubjectD.value[index].amount);

    deduFormRemove()
    selectedSubjectD.value.splice(index, 1)

}
const deduFormRemove = () => {
    formCountD.value--
    getMinusSubject()
}

// 刪除新增的加項項目
const deleteAddItem = (index) => {

    //重新計算加項小計
    trial.value.salaryMonthRecordDto.addSum = trial.value.salaryMonthRecordDto.addSum - selectedSubjectA.value[index].amount;

    // 將被刪掉的加項金額從netSalary減掉
    trial.value.salaryMonthRecordDto.netSalary = trial.value.salaryMonthRecordDto.netSalary - parseInt(selectedSubjectA.value[index].amount);

    selectedSubjectA.value.splice(index, 1)
    addFormRemove()
}
const addFormRemove = () => {
    formCountA.value--
    getPlusSubject()
}


// 載入資料
const loadData = async () => {

    const id = route.params.id
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/salaryTrial/${id}`
    const response = await axios.get(API_URL)

    trial.value = response.data

    // 分別取出加項或減項明細
    for (let b = 0; b < trial.value.detaildDto.length; b++) {

        if (trial.value.detaildDto[b].calculateType === 'P') {
            addList.value.push(trial.value.detaildDto[b]);
        } else {
            deduList.value.push(trial.value.detaildDto[b])
        }
    }
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
        amount: 0,
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
        const response = await axios.post(API_URL, sendData.value)
        if (response.status === 200) {
            Swal.fire({
                title: '更新成功',
                icon: 'success',

                confirmButtonText: "OK"
            })
            // .then(() => {
            //     router.push('/salary/calculate')
            // })

        }
    } catch (error) {
        console.error(error);
    }
}
// change事件
const changeAmountA = (Obj, index) => {
    console.log('index');
    console.log(Obj);

    const newObj = subjectPlus.value.find(item => item.subjectId === Obj)

    if (newObj.amountDefault != undefined) {
        selectedSubjectA.value[index].amount = newObj.amountDefault
    } else {
        selectedSubjectA.value[index].amount = 0
    }
    calculateAddSum();

}

const changeAmountD = (Obj, index) => {
    console.log('index');
    console.log(Obj);

    const newObj = subjectPlus.value.find(item => item.subjectId === Obj)

    if (newObj.amountDefault != undefined) {
        selectedSubjectD.value[index].amount = newObj.amountDefault
    } else {
        selectedSubjectD.value[index].amount = 0
    }
    calculateDeduSum()

}
// 修改從資料庫取出的減項明細金額
const calculateDeduFromDB = (index) => {
    trial.value.salaryMonthRecordDto.deduSum = 0
    let d = 0
    for (let j = 0; j < deduList.value.length; j++) {
        d = parseInt(deduList.value[j].amount) + d

    }
    trial.value.salaryMonthRecordDto.deduSum = d

    trial.value.salaryMonthRecordDto.netSalary = 0

    trial.value.salaryMonthRecordDto.netSalary = trial.value.salaryMonthRecordDto.netSalary + trial.value.salaryMonthRecordDto.addSum - d

}

// 修改從資料庫取出的加項明細金額
const calculateAddFromDB = (index) => {
    trial.value.salaryMonthRecordDto.addSum = 0
    let a = 0
    for (let j = 0; j < addList.value.length; j++) {
        a = parseInt(addList.value[j].amount) + a

    }
    trial.value.salaryMonthRecordDto.addSum = a

    trial.value.salaryMonthRecordDto.netSalary = 0

    trial.value.salaryMonthRecordDto.netSalary = trial.value.salaryMonthRecordDto.netSalary + a - trial.value.salaryMonthRecordDto.deduSum

}

// 新增加項後計算加項總和
const calculateAddSum = () => {
    let size = selectedSubjectA.value.length
    // trial.value.salaryMonthRecordDto.netSalary = 0
    // let a = trial.value.salaryMonthRecordDto.addSum;
    trial.value.salaryMonthRecordDto.addSum = trial.value.salaryMonthRecordDto.addSum + parseInt(selectedSubjectA.value[size - 1].amount)
    // 修改從資料庫取的加項金額後計算加項總和
    // let a = 0
    // for (let j = 0; j < addList.value.length; j++) {
    //     a += parseInt(addList.value[j].amount)
    //     console.log(`outputaa->`, a)
    // }

    // trial.value.salaryMonthRecordDto.netSalary = trial.value.salaryMonthRecordDto.netSalary + a
    // for (let i = 0; i < selectedSubjectA.value.length; i++) {
    //     a = parseInt(selectedSubjectA.value[i].amount) + a;
    // }

    // 取出最後一筆被新增的項目


    // trial.value.salaryMonthRecordDto.addSum = a
    trial.value.salaryMonthRecordDto.netSalary = trial.value.salaryMonthRecordDto.netSalary + parseInt(selectedSubjectA.value[size - 1].amount)
}

// 新增減項後計算減項總和
const calculateDeduSum = () => {
    // let d = trial.value.salaryMonthRecordDto.deduSum;
    let size = selectedSubjectD.value.length

    trial.value.salaryMonthRecordDto.deduSum = trial.value.salaryMonthRecordDto.deduSum + parseInt(selectedSubjectD.value[size - 1].amount)

    // for (let i = 0; i < selectedSubjectD.value.length; i++) {
    //     d = parseInt(selectedSubjectD.value[i].amount) + d;
    // }
    // 取出最後一筆被新增的項目
    // trial.value.salaryMonthRecordDto.deduSum = d
    trial.value.salaryMonthRecordDto.netSalary = trial.value.salaryMonthRecordDto.netSalary - parseInt(selectedSubjectD.value[size - 1].amount)

}

onMounted(loadData)

</script>
<template>
    <form @submit.prevent="handleSubmit">
        <div class="container  c">
            <button type="submit" class="btn btn-warning btn-sm mb-3">更新</button>


            <div class="card">
                <div class="card-body">
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
                            <div class="col-md-3">
                                <td><label for="empId" class="form-label">計薪區間(年/月)：</label>{{
                                    trial.salaryMonthRecordDto.slYear
                                }}/{{ trial.salaryMonthRecordDto.slMonth }}</td>
                                <!-- <input type="text" class="form-control f" id="empId" readonly
                                    v-model="trial.salaryMonthRecordDto.slYear"> -->
                            </div>
                            <!-- <div class="col-md-2">
                                <label for="empId" class="form-label">計薪區間(月)</label>
                                <input type="text" class="form-control f" id="empId" readonly
                                    v-model="trial.salaryMonthRecordDto.slMonth">

                            </div> -->
                            <div class="col-md-3">
                                <td> <label for="empId" class="form-label">員工編號：</label>{{ trial.salaryMonthRecordDto.empId
                                }}</td>
                                <!-- <input type="text" class="form-control f" id="empId" readonly
                                    v-model="trial.salaryMonthRecordDto.empId"> -->
                            </div>
                            <div class="col-md-3">
                                <td><label for="empId" class="form-label">員工姓名：</label>{{ trial.salaryMonthRecordDto.empName
                                }}</td>
                                <!-- <input type="text" class="form-control f" id="empName" readonly
                                    v-model="trial.salaryMonthRecordDto.empName"> -->
                            </div>
                        </div>
                        <div class="div2">
                            <div class="row">
                                <div class="col-md-3">
                                    <label for="empId" class="form-label">實領薪資</label>
                                    <input type="text" class="form-control f" id="empName"
                                        v-model="trial.salaryMonthRecordDto.netSalary">
                                </div>
                                <div class="col-md-3">
                                    <label for="empId" class="form-label">加項加總</label>
                                    <input type="text" class="form-control f" id="empName"
                                        v-model="trial.salaryMonthRecordDto.addSum">
                                </div>
                                <div class="col-md-3">
                                    <label for="empId" class="form-label">減項加總</label>
                                    <input type="text" class="form-control f" id="empName"
                                        v-model="trial.salaryMonthRecordDto.deduSum" @input="">
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- <div class="container"> -->
                    <div class="row g-5">
                        <div class="col-md-6 col-lg-6">
                            <div class="card">
                                <!-- <div class="card-body"> -->
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
                                                <th scope="col"></th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr v-for="(a, index) in addList" :key="index">
                                                <th scope="row" class="hidden-column ">
                                                    <input type="text" class="form-control fc" id="add" disabled readonly
                                                        v-model="a.id">
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
                                                        v-model="a.amount" @change=calculateAddFromDB(index)>
                                                </th>
                                                <th scope="row" class="hidden-column"> <input type="text"
                                                        class="form-control " id="add" v-model="a.recordId"></th>
                                                <th scope="row"> <input type="text" class="form-control hidden-column"
                                                        id="add" v-model="a.createdDate"></th>
                                            </tr>


                                            <template v-for="(n, index) in formCountA   " :key="n">
                                                <tr>
                                                    <!-- <div class="row"> -->
                                                    <th>
                                                        <div class=" form-floating text-center">
                                                            <select class="form-control  text-center"
                                                                v-model="selectedSubjectA[index].subjectId"
                                                                @change="changeAmountA(selectedSubjectA[index].subjectId, index)">
                                                                <option value="">科目名稱</option>
                                                                <option v-for="s in subjectPlus" :value="s.subjectId"
                                                                    :key="s.subjectId">{{
                                                                        s.subjectName }}</option>
                                                            </select>
                                                        </div>

                                                    </th>
                                                    <th>
                                                        <div class="form-floating ">
                                                            <input v-model.trim="selectedSubjectA[index].amount" type="text"
                                                                class="form-control  text-center" id="price"
                                                                placeholder="請輸入數字" @change="calculateAddSum">

                                                        </div>
                                                    </th>
                                                    <th>
                                                        <button @click.prevent="deleteAddItem(index)" class="btn1"><i
                                                                class="bi bi-trash3"></i></button>
                                                    </th>
                                                </tr>
                                            </template>
                                        </tbody>
                                    </table>
                                </div>
                                <!-- </div> -->
                            </div>
                        </div>
                        <div class="col-md-6 col-lg-6">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title" style="margin:0;font-weight: bold;"> <span><button
                                                @click.prevent="deduForm" style="border: none; background: none;"><i class=" bi
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
                                            <tr v-for="(d, index) in deduList" :key="index">
                                                <th scope="row" class="hidden-column">
                                                    <input type="text" class="form-control fc" id="add" readonly
                                                        v-model="d.subjectId">
                                                </th>
                                                <th scope="row">
                                                    <input type="text" class="form-control fc" id="add" readonly
                                                        v-model="d.subjectName">
                                                </th>
                                                <th scope="row">
                                                    <input type="text" class="form-control fc" id="add" v-model="d.amount"
                                                        @change=calculateDeduFromDB>
                                                </th>
                                                <th scope="row" class="hidden-column fc"> <input type="text"
                                                        class="form-control" id="add" v-model="d.recordId"></th>
                                            </tr>
                                            <!-- <button @click.prevent="deduForm" class="btn btn-outline-primary">+</button> -->
                                            <template v-for="(n, index) in formCountD   " :key="n">
                                                <tr>
                                                    <th>
                                                        <div class=" form-floating text-center">
                                                            <select class="form-control text-center"
                                                                v-model="selectedSubjectD[index].subjectId"
                                                                @change="changeAmountD(selectedSubjectD[index].subjectId, index)">
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
                                                                class="form-control text-center" id="price"
                                                                placeholder="請輸數字" @change="calculateDeduSum">
                                                        </div>

                                                        <!-- </div> -->
                                                    </th>
                                                    <th><button @click.prevent="deleteDeduItem(index)" class="btn1"><i
                                                                class="bi bi-trash3"></i></button>
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
            </div>
        </div>
    </form>
</template>
    
    
<style scoped>
.c {
    margin-top: 5px;
    max-height: 500px;
    /* max-height: 60%; */
    overflow-y: auto;
}

.hidden-column {
    display: none;
    /* visibility: hidden; */
    /* 或者使用 opacity: 0; */
}

table {
    /* 文本水平居中 */
    text-align: center;

    /* 文本垂直居中 */
    vertical-align: middle;
}

/* 去除輸入框邊框 */
.fc {
    background-color: transparent;
    border: none;
    /* border-radius: 0; */
    text-align: justify;
    /* 可選，去除圓角 */
    /* border-bottom: 1px solid #333; */
    text-align: center;
}

/* 設置底部邊線的樣式和顏色 */
/* 滑鼠懸停時的效果，可選 */
input.form-control:hover {
    border-color: #333;
    /* 更改底部邊線的顏色 */
}

.f {
    background-color: transparent;
    border: none;
    border-radius: 0;
    text-align: justify;
    /* 可選，去除圓角 */
    border-bottom: 1px solid #333;
    text-align: center;
}

/* 輸入框聚焦時的效果，可選 */
input.form-control:focus {
    border-color: #555;
    /* 更改底部邊線的顏色 */
    box-shadow: none;
    /* 去除預設的聚焦時陰影效果 */
}

.no-background {
    background-color: transparent;
    border: none;
    /* 可選，去除邊框 */
    border-radius: 0;
    /* 可選，去除圓角 */
}

.btn1 {
    border: none;
    background: none;
    color: red;
}
</style>
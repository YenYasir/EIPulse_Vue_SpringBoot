<script setup>
import axios from "axios";
import { ref, reactive } from "vue"
import { onMounted } from 'vue';
import Swal from "sweetalert2"

const subject = ref([]);
const subjectInput = ref({})
// const open = ref({
//     subjectId: s.subjectId,
//     status: s.status
// })

const handleSubmit = async () => {
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/subject/edit`
    const response = await axios.post(API_URL, subjectInput.value)
    subjectInput.value = {}
    loadSubject()
}


const loadSubject = async () => {
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/subjects`
    const { data } = await axios.get(API_URL)
    subject.value = data;
    console.log(`output->121212`, 121212)
    console.log(`outputZZZZ->`, subject.value)
    // const newStatus = {
    //     subjectId: '',
    //     status: ''
    // }
    // statusList.value.push(newStatus)
    // for (let a = 0; a < subject.value.length; a++) {
    //     if (subject.value[a].status === ) {
    //         subject.value[a].status = true
    //     } else {
    //         subject.value[a].status = false
    //     }
    // }
    console.log(`outputSSS->`, subject.value.status)

}

const changeStatus = async (s) => {
    // if (s.status === true) {
    //     s.status = '1';
    // } else if (s.status === false) {
    //     s.status = '0';
    // }

    s.status = !s.status; // 切換布林值
    // if (s.status === true) {
    //     s.status = '1';
    // } else if (s.status === false) {
    //     s.status = '0';
    // }
    // const statusString = s.status ? '1' : '0'; // 將布林值轉換為字串
    // s.status = s.status === true ? '1' : '0';
    console.log(`outputPPPP->`, s.status)
    // const params = {
    //     status: subject.status,
    //     subjectId: subject.subjectId

    console.log(`status=${s.status}`);
    console.log(`subjectId=${s.subjectId}`);

    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/subject/status?status=${s.status}&subjectId=${s.subjectId}`

    // const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/subject/status`
    try {
        const response = await axios.post(API_URL, {
            status: s.status,
            subjectId: s.subjectId
        })
        console.log(`outputDDD->`, response.status)
        if (response.status === 200) {

            Swal.fire({
                title: '更新成功',
                icon: 'success',
                confirmButtonText: "OK"
            })
            // .then(() => {
            //     loadSubject()
            // })

            console.log(`outputBSSS->`, response.data)
        }
    } catch (error) {
        console.error(error);
    }
}

const openModal = async (subjectId) => {

    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/subject/${subjectId}`
    const { data } = await axios.get(API_URL)
    subjectInput.value = data;

}

onMounted(loadSubject);

</script>

<template>
    <div class="container c">
        <div class="row mb-1 mt-2">
            <div class="col-3">
                <button class="btn btn-warning" type="button"  data-bs-toggle="modal"
                    data-bs-target="#exampleModal" data-bs-whatever="@fat"><i class="bi bi-plus-lg" ></i>新增</button>
            </div>
            <div class="col-6"></div>
            <div class="col-3">
                <SearchBox></SearchBox>
            </div>
        </div>
        <!-- 互動式視窗(新增) -->
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modalLabel">新增薪資科目</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form @submit.prevent="handleSubmit">
                        <div class="modal-body">
                            <div class="row">
                                <div class="col mb-3">
                                    <label for="inputSubjectName" class="form-label"><span
                                            style="color:red">*</span>薪資科目</label>
                                    <input type="text" id="inputSubjectName" class="form-control"
                                        aria-describedby="inputSubjectName" v-model="subjectInput.subjectName">
                                </div>
                                <div class="col mb-3">
                                    <label for="amountDefault" class="form-label">預設金額</label>
                                    <input type="text" id="amountDefault" class="form-control"
                                        aria-describedby="amountDefault" v-model="subjectInput.amountDefault">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col mb-3">
                                    <label for="subjectfrequency" class="form-label"><span
                                            style="color:red">*</span>計薪頻率</label>
                                    <select class="form-select" aria-label="Default select example"
                                        v-model="subjectInput.frequency">
                                        <option :value="1">固定</option>
                                        <option :value="2">變動</option>
                                    </select>
                                </div>
                                <div class="col mb-3">
                                    <label for="inputPassword5" class="form-label"><span
                                            style="color:red">*</span>計薪類型</label>
                                    <select class="form-select" aria-label="Default select example"
                                        v-model="subjectInput.calculateType">
                                        <option :value="'P'">加項</option>
                                        <option :value="'M'">減項</option>
                                    </select>
                                </div>
                                <div class="col mb-3">
                                    <label for="inputPassword5" class="form-label"><span
                                            style="color:red">*</span>狀態</label>
                                    <select class="form-select" aria-label="Default select example"
                                        v-model="subjectInput.status">
                                        <option :value="true" selected>啟用</option>
                                        <option :value="false">暫停</option>
                                    </select>
                                </div>

                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">離開</button>
                            <button type="submit" class="btn btn-warning">儲存</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="card ">
            <div class="card-header">
                <p class="head">薪資科目表</p>
            </div>
            <div class=" card-body c2 ">
                <table class="table table-hover table-sm ">
                    <thead>
                        <tr>
                            <th scope="col" class="hidden-column">科目代號</th>
                            <th scope="col" class="t1">科目名稱</th>
                            <th scope="col">計薪類型</th>
                            <th scope="col">計薪頻率</th>
                            <th scope="col">預設金額</th>
                            <th scope="col">狀態</th>
                            <th scope="col">動作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="s in subject" :key="s.subjectId">
                            <!-- <th scope="row">1</th> -->
                            <td class="hidden-column">{{ s.subjectId }}</td>
                            <td class="t1">{{ s.subjectName }}</td>
                            <td class="t1" v-if="s.calculateType == 'P'">加項</td>
                            <td class="t1" v-else>減項</td>
                            <td class="t1" v-if="s.frequency == '1'">固定</td>
                            <td class="t1" v-else>變動</td>
                            <td>{{ s.amountDefault }}</td>
                            <!-- <td> {{ s.status }} </td> -->
                            <!-- <td class="t1" v-if="s.status == '1'">啟用</td>
                            <td class="t1" v-else>停止</td> -->
                            <!-- <td v-if="s.status === '1'"> -->
                            <td style="text-align: center; line-height: 1;">
                                <div class="form-check form-switch">
                                    <input class="form-check-input" type="checkbox" role="switch"
                                        id="flexSwitchCheckChecked" @click="changeStatus(s)" v-model="s.status">
                                </div>
                            </td>
                            <!-- </td> -->
                            <!-- <td v-else>
                                <div class="form-check form-switch">
                                    <input class=" form-check-input" type="checkbox" role="switch"
                                        id="flexSwitchCheckDefault" v-model="open">
                                </div>
                            </td> -->
                            <td>
                                <!-- 互動視窗(更新) -->
                                <button class="btn btn-secondary btn-sm mx-1" data-bs-toggle="modal"
                                    data-bs-target="#Modal2" data-bs-whatever="update" @click="openModal(s.subjectId)">
                                    <i class="bi bi-pencil-square"></i>
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="modal fade" id="Modal2" tabindex="-1" aria-labelledby="Modal2" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title " id="Modal2">更新薪資科目</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form @submit.prevent="handleSubmit">
                        <div class="modal-body">
                            <div class="row">
                                <div class="col mb-3 hidden-column">
                                    <label for="subjectName" class="form-label "><span
                                            style="color:red">*</span>科目代號</label>
                                    <input type="text" id="subjectName" class="form-control " aria-describedby="subjectName"
                                        v-model="subjectInput.subjectId">
                                </div>
                                <div class="col mb-3">
                                    <label for="inputSubjectName" class="form-label"><span
                                            style="color:red">*</span>薪資科目</label>
                                    <input type="text" id="inputSubjectName" class="form-control"
                                        aria-describedby="inputSubjectName" v-model="subjectInput.subjectName">
                                </div>
                                <div class="col mb-3">
                                    <label for="amountDefault" class="form-label">預設金額</label>
                                    <input type="text" id="amountDefault" class="form-control"
                                        aria-describedby="amountDefault" v-model="subjectInput.amountDefault">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col mb-3">
                                    <label for="subjectfrequency" class="form-label"><span
                                            style="color:red">*</span>計薪頻率</label>
                                    <select class="form-select" aria-label="#" v-model="subjectInput.frequency">
                                        <option :value="1">固定</option>
                                        <option :value="2">變動</option>
                                    </select>
                                </div>
                                <div class="col mb-3">
                                    <label for="inputPassword5" class="form-label"><span
                                            style="color:red">*</span>計薪類型</label>
                                    <select class="form-select" aria-label="#" v-model="subjectInput.calculateType">
                                        <option :value="'P'">加項</option>
                                        <option :value="'M'">減項</option>
                                    </select>
                                </div>
                                <div class="col mb-3">
                                    <label for="inputPassword5" class="form-label"><span
                                            style="color:red">*</span>狀態</label>
                                    <select class="form-select" aria-label="Default select example"
                                        v-model="subjectInput.status">
                                        <option :value="true" selected>啟用</option>
                                        <option :value="false">暫停</option>
                                    </select>
                                </div>

                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">離開</button>
                            <button type="submit" class="btn btn-warning">儲存</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>



    </div>
</template>
    
<style scoped>
.c {
    max-height: 450px;
    /* max-height: 50%; */
    overflow-y: auto;
}

table {
    text-align: center;
    font-size: 15px;
}

#Modal2 {
    text-align: left;
}

td {
    width: 80px;
}

th {
    width: 80px;
}

.t1 {
    width: 120px;
}

.hidden-column {
    display: none;
    /* visibility: hidden; */
    /* 或者使用 opac
    ity: 0; */
}

/* .c {
    margin-top: 20px;
    max-height: 400px;
    overflow-y: auto;

} */

/* .c2 {
    max-height: 500px;
    overflow-y: auto;
} */


.head {
    font-weight: bold;
    margin: 2px;
    font-size: 20px;
    text-align: center;
}
</style>
<script setup>
import axios from "axios";
import { ref, reactive } from "vue"
import { onMounted } from 'vue';

const subject = ref([]);
const subjectInput = ref({})
const open = ref()

const handleSubmit = async () => {
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/subject/edit`
    const response = await axios.post(API_URL, subjectInput.value)
    subjectInput.value = {}
    loadSubject()
    // location.reload()
}
const loadSubject = async () => {
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/subjects`
    const { data } = await axios.get(API_URL)
    subject.value = data;
    console.log(`output->121212`, 121212)
    console.log(`output->`, data.value)

}


const openModal = async (subjectId) => {
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/subject/${subjectId}`
    const { data } = await axios.get(API_URL)
    subjectInput.value = data;

}

// const changeStatus = async () => {
//     console.log(`output111@->`, open.value)
//     if (open.value === true) {
//         status.valueOf = '1'
//     } else {
//         status.valueOf = '0'
//     }

//     const params = {
//         subjectId: subject.value.subjectId,
//         status: status.valueOf
//     }
//     const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/subject/status`
//     const { data } = await axios.post(API_URL, { params })
//     console.log(`outputBBB->`, data.value)
// }

onMounted(loadSubject);

</script>

<template>
    <div class="container c">
        <div class="row mb-2">
            <div class="col-3">
                <button type="button" class="btn btn-primary btn-sm mb-2" data-bs-toggle="modal"
                    data-bs-target="#exampleModal" data-bs-whatever="@fat"><i class="bi bi-plus-lg"></i>新增</button>
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
                                        <option :value="1" selected>啟用</option>
                                        <option :value="0">暫停</option>
                                    </select>
                                </div>

                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">離開</button>
                            <button type="submit" class="btn btn-primary">儲存</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-header">
                <p style="font-weight: bold ; margin:2px ;">薪資科目表</p>
            </div>
            <div class="card-body">
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
                            <!-- <td>{{ s.calculateType }}</td> -->
                            <td class="t1" v-if="s.frequency == '1'">固定</td>
                            <td class="t1" v-else>變動</td>
                            <!-- <td>{{ s.frequency }}</td> -->
                            <td>{{ s.amountDefault }}</td>
                            <!-- <td> {{ s.status }} </td>-->
                            <td v-if="s.status === '1'">
                                <div class="form-check form-switch">
                                    <input class="form-check-input" type="checkbox" role="switch"
                                        id="flexSwitchCheckChecked" checked @click="changeStatus" v-model="open">
                                </div>
                            </td>
                            <td v-else>
                                <div class="form-check form-switch">
                                    <input class=" form-check-input" type="checkbox" role="switch"
                                        id="flexSwitchCheckDefault" v-model="s.status">
                                </div>
                            </td>
                            <td>
                                <!-- <router-link class="btn btn-secondary mx-1" :to=" "><i class="bi bi-pencil-square"></i></router-link> -->
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
                                    <label for="amountDefault" class="form-label"></label>
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
                                        <option :value="1" selected>啟用</option>
                                        <option :value="0">暫停</option>
                                    </select>
                                </div>

                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">離開</button>
                            <button type="submit" class="btn btn-primary">儲存</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>



    </div>
</template>
    
<style scoped>
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

.c {
    margin-top: 20px;
}
</style>
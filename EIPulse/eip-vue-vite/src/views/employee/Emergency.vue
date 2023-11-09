<script setup >
import { ref, computed, reactive } from 'vue';
import Swal from 'sweetalert2'
import { useRouter } from 'vue-router';
import axios from 'axios';


const router = useRouter();
const data = ref({
    "emergencyName": "",
    "relation": "",
    "phone": "",
    "empId": "",
})
const emps = ref([]);
// 新增selectedEmp來綁定<select>的v-model
const selectedEmp = ref(null);
const addHandler = async () => {
    // 這個 API 剩下缺少 empId 的輸入
    try {
        data.value.empId = selectedEmp.value;
        const response = await axios.post('http://localhost:8090/eipulse/emergency/add', data.value)
        Swal.fire({
            title: '增加成功',
            timer: 1000,
            timerProgressBar: true,
            icon: 'success'
        });
        router.push('/employee/emergencyList');
        console.log(data);
    } catch (e) {
        Swal.fire({
            title: '資料有誤請確認',
            icon: 'error'
        });
        console.log(data);
        console.error(e);

    }
};
const loadEmp = async () => {
    // get api of dept datas 
    const api = `http://localhost:8090/eipulse/employee/findAll`
    try {
        const response = await axios.get(api);
        emps.value = response.data; // 假设API返回的是一个数组

    } catch (error) {
    }
}

loadEmp();
</script>


<template>
    <form @submit.prevent="addHandler">
        <div style=" margin-top: 100px; ">
            <h2>新增緊急聯絡人資料</h2>
            <table class="form-table" style="width: 410px; ">
                <!-- 員工列表 -->
                <tr>
                    <th><label for="phone" class="form-label">員工</label></th>
                    <td><select class="form-select form-control" id="empSelect" aria-label="Floating label select example"
                            v-model="selectedEmp">
                            <option v-for="emp in emps" :key="emp.empId" :value="emp.empId">
                                {{ emp.empName }}
                            </option>
                        </select></td>
                </tr>
                <!-- 家屬姓名 -->
                <tr>
                    <th><label for="emergencyName" class="form-label">姓名</label></th>
                    <td><input type="text" class="form-control" id="emergencyName" v-model="data.emergencyName" required>
                    </td>
                </tr>

                <!-- 關係 -->
                <tr>
                    <th><label for="relation" class="form-label">關係</label></th>
                    <td><input type="text" class="form-control" id="relation" v-model="data.relation" required></td>
                </tr>
                <!-- 電話 -->
                <tr>
                    <th><label for="phone" class="form-label">連絡電話</label></th>
                    <td><input type="tel" class="form-control" id="phone" v-model="data.phone" required></td>
                </tr>

            </table>
            <button class="btn btn-primary" type="submit" style="width: 400px;">
                新增
            </button>
        </div>
    </form>
</template>

<style scoped>
h2 {
    text-align: center;
}

/* 為表格添加圓角 */
.form-table {
    width: 50%;
    margin: 20px auto 10px auto;
    border-collapse: separate;
    /* 使用separate而非collapse以使border-radius生效 */
    border-spacing: 0;
    /* 移除單元格間的間距 */
    border-radius: 10px;
    /* 圓角設置 */
    overflow: hidden;
    /* 隱藏內部超出部分 */
}

/* 為單元格添加邊線 */




.form-table th {
    text-align: center;
    background-color: #a3c4e8;
}

.form-control {
    width: 98%;
    padding: 10px;
    margin: 5px;
    box-sizing: border-box;
    border-color: #0161c7;
}

.btn {
    width: 50%;
    display: block;
    margin: 2px auto;
}
</style>
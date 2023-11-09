<template>
    <div>
        <div class="container">
            <div class="handle-box">
                <button type="button" class="btn btn-success" @click="downloadCurrentData">下載資料</button>
            </div>
            <div class="container mt-4">
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="startDate">開始日期</label>
                            <input type="date" class="form-control" id="startDate" v-model="startDate" />
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="endDate">結束日期</label>
                            <input type="date" class="form-control" id="endDate" v-model="endDate" />
                        </div>
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="table table-bordered table-hover">
                        <thead class="thead-dark">
                            <tr>
                                <th>員工編號</th>
                                <th>日期</th>
                                <th>員工姓名</th>
                                <th>上班時間</th>
                                <th>下班時間</th>
                                <th>請假時數</th>
                                <th>加班時數</th>
                                <th>考勤狀態</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="item in currentData" :key="item.id">
                                <td>{{ item.id }}</td>
                                <td>{{ item.date }}</td>
                                <td>{{ item.name }}</td>
                                <td>{{ item.on_clock }}</td>
                                <td>{{ item.off_clock }}</td>
                                <td>{{ item.leavetime }}</td>
                                <td>{{ item.overtime }}</td>
                                <td>{{ item.attendance }}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div class="d-flex justify-content-center mt-4">
                    <nav aria-label="Page navigation example">
                        <ul class="pagination">
                            <li class="page-item" :class="{ 'disabled': currentPage === 1 }">
                                <a class="page-link" href="#" @click.prevent="handlePageChange(currentPage - 1)"
                                    aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <li class="page-item" v-for="page in totalPages" :key="page"
                                :class="{ 'active': page === currentPage }">
                                <a class="page-link" href="#" @click.prevent="handlePageChange(page)">{{ page }}</a>
                            </li>
                            <li class="page-item" :class="{ 'disabled': currentPage === totalPages }">
                                <a class="page-link" href="#" @click.prevent="handlePageChange(currentPage + 1)"
                                    aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup >
import { ref, onMounted, watch } from 'vue'
import axios from 'axios'
import { utils, writeFile } from 'xlsx';

//  ----查詢設定----
const startDate = ref('');
const endDate = ref('');
const currentData = ref([]);

// 頁碼設計
const totalPages = ref('');
const currentPage = ref(1);

// 使用物件來儲存整理後的數據
const integratedData = {};

onMounted(async () => {
    watch([startDate, endDate], async () => {
        const response = await axios.get(`http://localhost:8090/eipulse/clockTimes/${startDate.value}/${endDate.value}`);

        // 遍歷從後端收到的打卡記錄
        response.data.content.forEach(item => {
            // 依照日期及員工 id 設定唯一鍵值
            const key = item.time.split(' ')[0] + item.empId;
            // 檢查是否已經為這個 key 創建了一個物件。如果還沒有，則創建一個新的物件，並初始化相關屬性
            if (!integratedData[key]) {
                integratedData[key] = {
                    id: item.empId,
                    name: item.empName,
                    date: item.time.split(' ')[0],
                    on_clock: null,
                    off_clock: null,
                    leavetime: '',
                    overtime: '',
                    attendance: ''
                };
            }
            // 紀錄最早上班打卡時間
            if (item.type === '上班') {
                if (!integratedData[key].on_clock || item.time.split(' ')[1] < integratedData[key].on_clock) {
                    integratedData[key].on_clock = item.time.split(' ')[1];
                }
            }

            // 紀錄最早下班打卡時間
            if (item.type === '下班') {
                if (!integratedData[key].off_clock || item.time.split(' ')[1] < integratedData[key].off_clock) {
                    integratedData[key].off_clock = item.time.split(' ')[1];
                }
            }
        });

        // 計算考勤狀態
        for (const key in integratedData) {
            const onTime = new Date(integratedData[key].date + 'T' + integratedData[key].on_clock);
            const offTime = new Date(integratedData[key].date + 'T' + integratedData[key].off_clock);
            const diffTime = offTime - onTime;
            const diffHours = diffTime / (1000 * 60 * 60);

            // 請假時數為空，當日上班打卡時間晚於早上八點且當日下班打卡時間早於下午五點，則考勤為異常
            if (!integratedData[key].leavetime && onTime.getHours() > 8 && offTime.getHours() < 17) {
                integratedData[key].attendance = '異常';
            }

            // 請假時數不為空，則當日打卡下班時間減去當日打卡上班時間加上請假時數，要大於9個小時，否則判定異常
            if (integratedData[key].leavetime && diffHours <= 9) {
                integratedData[key].attendance = '異常';
            }

            // 加班時數不為空，則當日打卡下班時間減去當日打卡上班時間，要等於9個小時加上加班時數，否則考勤判定異常
            if (integratedData[key].overtime && diffHours !== (9 + parseFloat(integratedData[key].overtime))) {
                integratedData[key].attendance = '異常';
            }

            // 以上條件都不符合，考勤顯示正常
            if (!integratedData[key].attendance) {
                integratedData[key].attendance = '正常';
            }
        };


        // 將整理後的數據放入 currentData
        currentData.value = Object.values(integratedData);
    })
});

// 更改目前頁碼
const handlePageChange = (val) => {
    currentPage.value = val
}


const downloadCurrentData = () => {
    // 將目前的資料轉換成 Excel 的工作表
    const worksheet = utils.json_to_sheet(currentData.value);
    // 創建一個新的 Excel 工作簿
    const workbook = utils.book_new();
    // 將工作表添加到工作簿中，並指定工作表的名稱為 '考勤表'。
    utils.book_append_sheet(workbook, worksheet, '考勤表');
    // 將工作簿寫入到一個 Excel 檔案中，並將檔案下載到用戶的裝置上，檔名為 '考勤查詢' 
    writeFile(workbook, '考勤查詢' + '.xlsx');
};
</script>

<style scoped>
.handle-box {
    display: flex;
    margin-bottom: 20px;
}

.table {
    width: 100%;
    font-size: 14px;
}

.div {
    margin-bottom: 20px;

}
</style>

<template>
    <div class='demo-app'>
        <div class='demo-app-sidebar'>
            <!-- 側邊欄 -->
            <div class='demo-app-sidebar-section'>
                <b>說明</b>
                <ul>
                    <li>選擇日期加入新活動</li>
                    <li>單擊操作活動資訊或刪除活動</li>
                </ul>
            </div>

            <label>
                <input type='checkbox' :checked='calendarOptions.weekends' @change='handleWeekendsToggle' />
                <b>是否顯示周六日</b>
            </label>

            <div class='demo-app-sidebar-section'>
                <b>行程總覽 ({{ afterTodayEvents.length }})</b>
                <ul>
                    <!-- 顯示活動的標題及起始時間 -->
                    <li v-for='event in afterTodayEvents' :key='event.id'>
                        <b>{{ formatDateTime(event.start) }}</b>
                        <i>{{ event.title }}</i>
                    </li>
                </ul>
            </div>
        </div>

        <!-- 日曆畫面 -->
        <div class='demo-app-main'>
            <!-- 透過 calendarOptions 設定 FullCalendar 顯示元素 -->
            <FullCalendar class='demo-app-calendar' :options='calendarOptions'>
                <!-- eventContent 是父組件（即包含了FullCalendar組件的組件）中定義的一個插槽 -->
                <template v-slot:eventContent='arg'>
                    <b>{{ arg.timeText }}</b>
                    <i>{{ arg.event.title }}</i>
                </template>
            </FullCalendar>
        </div>
    </div>

    <!-- 新增活動的彈出表單 -->
    <el-dialog v-model="dialogFormVisible" title="新增活動">
        <el-form :model="form">
            <el-form-item label="活動標題" required>
                <el-input v-model="form.title"></el-input>
            </el-form-item>

            <el-form-item label="開始時間" required>
                <el-date-picker v-model="form.start" type="datetime"></el-date-picker>
            </el-form-item>

            <el-form-item label="結束時間" required>
                <el-date-picker v-model="form.end" type="datetime"></el-date-picker>
            </el-form-item>

            <el-form-item label="活動說明">
                <el-input v-model="form.description" type="textarea"></el-input>
            </el-form-item>
            <el-form-item label="活動發起者" required>
                <el-input v-model="form.userId" :readonly="true"></el-input>
            </el-form-item>

        </el-form>

        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取消</el-button>
                <el-button type="primary" @click="handleConfirm">確定</el-button>
            </span>
        </template>
    </el-dialog>

    <!-- 顯示活動的彈出表單 -->
    <el-dialog v-model="showFormVisible" title="活動詳情">
        <el-form :model="showForm">
            <el-form-item label="活動標題" required>
                <el-input v-model="showForm.title"></el-input>
            </el-form-item>

            <el-form-item label="開始時間" required>
                <el-date-picker v-model="showForm.start" type="datetime"></el-date-picker>
            </el-form-item>

            <el-form-item label="結束時間" required>
                <el-date-picker v-model="showForm.end" type="datetime"></el-date-picker>
            </el-form-item>

            <el-form-item label="活動說明">
                <el-input v-model="showForm.description" type="textarea"></el-input>
            </el-form-item>
            <el-form-item label="活動發起者" required>
                <el-input v-model="showForm.userId" :readonly="true"></el-input>
            </el-form-item>

        </el-form>

        <template #footer>
            <span class="dialog-footer">
                <el-button @click="changeEvent()">儲存活動</el-button>
                <el-button type="primary" @click="handleEventDelete()">刪除活動</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { empStore } from "../../stores/employee.js";
import FullCalendar from '@fullcalendar/vue3'
import zhLocale from '@fullcalendar/core/locales/zh-tw'
import dayGridPlugin from '@fullcalendar/daygrid'
import timeGridPlugin from '@fullcalendar/timegrid'
import interactionPlugin from '@fullcalendar/interaction'
import { ElDialog, ElInput, ElFormItem, ElDatePicker, ElButton } from 'element-plus'
import Swal from "sweetalert2";

// 導入員工登入id
const emp = empStore();
// 導入路徑
const URL = import.meta.env.VITE_API_JAVAURL;
// 設置 FullCalendar 
const calendarOptions = ref({
    plugins: [
        dayGridPlugin, // 顯示一個月中的每一天
        timeGridPlugin, // 顯示一週中每一天的時間表
        interactionPlugin //與使用者的互動功能，例如選擇日期、拖動事件等
    ],
    locale: zhLocale, // 使用繁體中文
    headerToolbar: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay'
    },
    initialView: 'dayGridMonth', // 初始顯示月畫面
    editable: true,
    selectable: true,
    selectMirror: true,
    dayMaxEvents: true,
    weekends: true, // 是否在週末顯示事件
    select: handleDateSelect,
    eventsSet: handleEvents,
    eventClick: handleEventChange,
});

// 儲存當前之後事件的列表
const afterTodayEvents = ref([]);
// 儲存所有事件的列表
const currentEvents = ref([]);

// 新增活動用的儲存表單
const form = ref({
    title: '',
    start: '',
    end: '',
    description: '',
    userId: emp.empId
});

// 顯示活動詳情的儲存表單
const showForm = ref({
    id: '',
    title: '',
    start: '',
    end: '',
    description: '',
    userId: emp.empId
});

// 預設彈出視窗不顯示
const dialogFormVisible = ref(false);
const showFormVisible = ref(false);

// 切換周六日
function handleWeekendsToggle() {
    calendarOptions.value.weekends = !calendarOptions.value.weekends
}

// 送出表單新增活動
async function handleConfirm() {
    if (checkEndDate(form.value.start, form.value.end)) {

        const utcStartDate = new Date(form.value.start);
        utcStartDate.setUTCHours(utcStartDate.getUTCHours() + 8);

        const utcEndDate = new Date(form.value.end);
        utcEndDate.setUTCHours(utcEndDate.getUTCHours() + 8);

        form.value.start = utcStartDate;
        form.value.end = utcEndDate;

        try {
            await axios.post(`${URL}events/`, form.value);
            Swal.fire('提交成功!', '活動已新增。', 'success');


            // 新增成功後，重新讀取事件列表
            await readEventsFromSQL();

            // 重置表單的值，避免下次新增點開留有舊值
            form.value = {
                title: '',
                start: '',
                end: '',
                description: '',
                userId: emp.empId
            };

        } catch (error) {
            Swal.fire('提交失敗!', '請檢查輸入欄位', 'error');
        }

        // 關閉彈出表單
        dialogFormVisible.value = false;
    } else {
        dialogFormVisible.value = false
        // 提示用戶調整日期
        Swal.fire('日期錯誤!', '結束日不能早於起始日。', 'error');
        // 重置表單的值，避免下次新增點開留有舊值
        form.value = {
            title: '',
            start: '',
            end: '',
            description: '',
            userId: emp.empId
        };
    }
}


// 點擊日期後打開輸入表單
function openAddEventModal() {
    dialogFormVisible.value = true;
}

// 打開新增事件的表單
function handleDateSelect() {
    openAddEventModal()
}

// 打開活動詳情的表單
function handleEventChange(info) {
    openChangeEventModal(info)
    // 將點選事件的值賦給showForm
    showForm.value.id = info.event.extendedProps.eventId;
    showForm.value.title = info.event.title;
    showForm.value.start = info.event.start;
    showForm.value.end = info.event.end;
    showForm.value.description = info.event.extendedProps.description;
}

// 顯示活動詳情彈出視窗
function openChangeEventModal() {
    showFormVisible.value = true;
}

// 讀取資料庫的活動
async function readEventsFromSQL() {
    try {
        const response = await axios.get(`${URL}events/self/${emp.empId}`);

        // 將後端返回的活動資訊處理成 FullCalendar 所需的格式
        const events = response.data.map(event => {
            // 轉換日期格式，避免 fullcalendar 錯誤
            const start = new Date(event.start).toISOString();
            const end = new Date(event.end).toISOString();

            // 將處理後的活動資訊儲存在events陣列中
            return {
                eventId: event.eventId,
                title: event.title,
                start,
                end,
                description: event.description,
                userId: event.userId,

            }
        });

        // 賦值給所有活動列表
        currentEvents.value = events;

        // 過濾當前時間之後的活動
        const now = new Date();
        afterTodayEvents.value = events.filter(event => new Date(event.start) >= now);
        // 更新日曆顯示
        calendarOptions.value.events = events;

    } catch (error) {
        console.error('獲取活動時發生錯誤', error);
    }
}

// 刪除行程
async function handleEventDelete() {
    // 先關閉活動詳情視窗
    showFormVisible.value = false;

    const result = await Swal.fire({
        title: '確定刪除嗎？',
        text: `確定刪除此行程嗎？ '${showForm.value.title}'`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: '確定',
        cancelButtonText: '取消',
    });

    if (result.isConfirmed) {
        try {
            await axios.delete(`${URL}events/${showForm.value.id}`);
            // 顯示刪除成功的 SweetAlert
            await Swal.fire('刪除成功!', '活動已刪除。', 'success');
        } catch (error) {
            // 顯示刪除失敗的 SweetAlert
            await Swal.fire('刪除失敗', '請再操作一次。', 'error');
        }
        // 刪除成功後，重新讀取事件列表
        await readEventsFromSQL();
    }
}

// 修改行程
async function changeEvent() {
    if (checkEndDate(showForm.value.start, showForm.value.end)) {
        // 先關閉活動詳情視窗
        showFormVisible.value = false;

        // 修改時區
        const utcStartDate = new Date(showForm.value.start);
        utcStartDate.setUTCHours(utcStartDate.getUTCHours() + 8);

        const utcEndDate = new Date(showForm.value.end);
        utcEndDate.setUTCHours(utcEndDate.getUTCHours() + 8);

        showForm.value.start = utcStartDate;
        showForm.value.end = utcEndDate;

        try {
            await axios.put(`${URL}events/${showForm.value.id}`, showForm.value);
            Swal.fire('修改成功!', '活動已成功修改。', 'success');

        } catch (error) {
            Swal.fire('修改失敗!', '請再次確認活動內容。', 'error');
        }

        // 重置表單的值，避免下次新增點開留有舊值
        showForm.value = {
            title: '',
            start: '',
            end: '',
            description: '',
            userId: emp.empId
        };
        // 修改成功後，重新讀取事件列表
        await readEventsFromSQL();
    } else {
        showFormVisible.value = false;
        // 提示用戶調整日期
        Swal.fire('日期錯誤!', '結束日不能早於起始日。', 'error');
        // 重置表單的值，避免下次新增點開留有舊值
        showForm.value = {
            title: '',
            start: '',
            end: '',
            description: '',
            userId: emp.empId
        };

    }

}

// 設置結束日的選擇不能早於起始日
function checkEndDate(startDate, endDate) {
    const start = new Date(startDate);
    const end = new Date(endDate);

    return end >= start;
}

// 預設一進入日曆畫面顯示所有活動
onMounted(() => {
    readEventsFromSQL()
})

// 事件數據更新時，更新欄位中顯示的事件列表
function handleEvents(events) {
    currentEvents.value = events
}

// 轉換時間格式顯示
function formatDateTime(dateTimeString) {
    const dateTime = new Date(dateTimeString);
    return dateTime.toLocaleString();
}
</script>

<style scoped>
h2 {
    margin: 0;
    font-size: 16px;
}

ul {
    margin: 0;
    padding: 0 0 0 1.5em;
}

li {
    margin: 1.5em 0;
    padding: 0;
}

b {
    margin-right: 3px;
}

.demo-app {
    display: flex !important;
    min-height: 100% !important;
    font-family: Arial, Helvetica Neue, Helvetica, sans-serif !important;
    font-size: 14px !important;
}

.demo-app-sidebar {
    width: 250px !important;
    line-height: 1.5 !important;
    background: #eaf9ff !important;
    border-right: 1px solid #d3e2e8 !important;
    max-height: 500px;
    /* 設定最大高度 */
    overflow-y: auto;
    /* 啟用垂直滾動條 */
}

.demo-app-sidebar-section {
    padding: 2em !important;
}

.demo-app-main {
    flex-grow: 1 !important;
    padding: 3em !important;
}
</style>

<template>
    <div class="container">
        <el-tabs v-model="message">
            <div style="display: flex; justify-content: space-between; align-items: center;">
                <el-input v-model="searchQuery" placeholder="請輸入消息標題查詢" style="width: 200px;" />

            </div>
            <el-tab-pane :label="`全部消息`" name="normal">
                <el-table :data="normalNews" :show-header="false" style="width: 100%">
                    <el-table-column>
                        <template #default="scope">
                            <span class="message-title" @click="showMessageDetails(scope.row)">{{ scope.row.title }}</span>
                        </template>
                    </el-table-column>
                    <!-- 消息發布時間 -->
                    <el-table-column prop="postTime" width="180"></el-table-column>

                </el-table>
                <!-- 全部消息的分頁 -->
                <el-pagination background layout="prev, pager, next" :total="totalPage * 10"
                    :current-page.sync="currentPage" @current-change="handlePageChange" />
            </el-tab-pane>
        </el-tabs>

        <!-- 訊息詳細內容的彈窗 -->
        <el-dialog v-model="showFormVisible" title="消息資訊">
            <!-- 顯示詳細內容的內容 -->
            <p><strong>標題：</strong>{{ showFormContent.title }}</p>
            <p><strong>內文：</strong>{{ showFormContent.content }}</p>
            <p><strong>檔案：</strong>{{ showFormContent.file }}</p>
            <p><strong>發布時間：</strong>{{ showFormContent.postTime }}</p>
            <p><strong>發布人：</strong>{{ showFormContent.publisherTitle }}&nbsp;{{ showFormContent.publisherName }}</p>
        </el-dialog>

    </div>
</template>
  
<script setup>
import Swal from "sweetalert2";
import { empStore } from "../../stores/employee.js";
import axios from "axios";
import { ref, onMounted } from 'vue';
import { ElDialog, ElInput } from 'element-plus'

const URL = import.meta.env.VITE_API_JAVAURL;

const normalNews = ref([]); // 全部消息
const totalPage = ref(null); // 全部消息的總頁數
const searchQuery = ref(); // 模糊搜尋標題

// 設置一開始進入的頁面為全部消息
const message = ref('normal');

// 預設第一頁開始
const currentPage = ref(1);
const offCurrentPage = ref(1);

// 預設詳細消息視窗為false(不彈出)
const showFormVisible = ref(false);


const showFormContent = ref({}); // 消息詳情
const editFormData = ref({}); // 編輯消息
const addFormData = ref({}); // 新增消息
const resetFormData = ref({}); // 重新上架消息

// 全部消息的換頁
const handlePageChange = async (newPage) => {
    currentPage.value = newPage;
    await readNewsFromDB();
};

// 讀取資料庫的消息
const readNewsFromDB = async () => {
    try {
        const response = await axios.get(`${URL}news/`, {
            params: {
                page: currentPage.value,
                size: 8,
            },
        });
        // 存入 normalNews
        normalNews.value = response.data.content;
        // 賦予總頁數
        totalPage.value = response.data.totalPages;

    } catch (error) {
        console.error('獲取消息時發生錯誤', error);
    }
};

// content 就是傳入的 scope.row
const showMessageDetails = content => {
    showFormContent.value = content;
    showFormVisible.value = true;
};


onMounted(() => {
    readNewsFromDB();
});


</script>
  
<style>
.message-title {
    cursor: pointer;
}

.handle-row {
    margin-top: 30px;
}
</style>
  
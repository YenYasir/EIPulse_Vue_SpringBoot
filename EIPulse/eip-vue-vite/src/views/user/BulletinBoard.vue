<template>
    <div class="container">
        <el-tabs v-model="message">
            <div style="display: flex; justify-content: space-between; align-items: center;">
                <el-input v-if="message === 'normal'" v-model="searchQuery" placeholder="請輸入消息標題查詢" style="width: 200px;"
                    @input="handleLikeSearch" />
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
                <br />

                <!-- 全部消息的分頁 -->
                <el-pagination v-if="message === 'normal' && !useTitlePage" background layout="prev, pager, next"
                    :total="totalPage * 10" :current-page.sync="currentPage" @current-change="handlePageChange" />

                <!-- 模糊搜尋的分頁 -->
                <el-pagination v-if="message === 'normal' && useTitlePage" background layout="prev, pager, next"
                    :total="titleTotalPage * 10" :current-page.sync="titleCurrentPage" @current-change="titlePageChange" />

            </el-tab-pane>
        </el-tabs>

        <!-- 訊息詳細內容的彈窗 -->
        <el-dialog v-model="showFormVisible" title="消息資訊">
            <!-- 顯示詳細內容的內容 -->
            <p><strong>標題：</strong>{{ showFormContent.title }}</p>
            <p><strong>內文：</strong>{{ showFormContent.content }}</p>
            <p><strong>發布時間：</strong>{{ showFormContent.postTime }}</p>
            <p><strong>發布人：</strong>{{ showFormContent.publisherTitle }}&nbsp;{{ showFormContent.publisherName }}</p>
        </el-dialog>
    </div>
</template>
  
<script setup>
import axios from "axios";
import { ref, onMounted } from 'vue';
import { ElDialog, ElInput, } from 'element-plus'
const URL = import.meta.env.VITE_API_JAVAURL;

const normalNews = ref([]); // 全部消息
const totalPage = ref(null); // 全部消息的總頁數
const searchQuery = ref(''); // 搜尋框
const titleTotalPage = ref(null); // 模糊搜尋的總頁數
const useTitlePage = ref(0); // 確認是否有使用模糊搜尋

// 設置一開始進入的頁面為全部消息
const message = ref('normal');

// 預設第一頁開始
const currentPage = ref(1);
const titleCurrentPage = ref(1);

// 預設詳細消息視窗為false(不彈出)
const showFormVisible = ref(false);

const showFormContent = ref({}); // 消息詳情

// 全部消息的換頁
const handlePageChange = async (newPage) => {
    currentPage.value = newPage;
    await readNewsFromDB();
};

// 模糊搜尋的換頁
const titlePageChange = async (titlePage) => {
    titleCurrentPage.value = titlePage;
    await handleLikeSearch(searchQuery.value);
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

// 模糊搜尋
const handleLikeSearch = async (newQuery) => {
    if (newQuery !== '') {
        useTitlePage.value = 1; // 改為true，告訴程式啟用模糊搜尋
        try {
            const response = await axios.get(`${URL}news/search`, {
                params: {
                    title: newQuery,
                    page: titleCurrentPage.value,
                    size: 8,
                },
            });
            // 將搜尋結果賦值給normalNews
            normalNews.value = response.data.content;
            // 賦予總頁數
            titleTotalPage.value = response.data.totalPages;
        } catch (error) {
            console.error('搜尋時發生錯誤', error);
        }
    } else {
        // 如果搜索框為空，重新讀取所有消息
        useTitlePage.value = 0;
        await readNewsFromDB();
        await readOffNewsFromDB();
    }
}

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

.add-button-container {
    display: flex;
    justify-content: flex-end;
    margin-bottom: 10px;
}
</style>
  
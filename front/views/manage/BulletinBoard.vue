<template>
    <div class="container">
        <el-tabs v-model="message">
            <div style="display: flex; justify-content: space-between; align-items: center;">
                <el-input v-model="searchQuery" placeholder="請輸入消息標題查詢" style="width: 200px;" />
                <div class="add-button-container">
                    <el-button v-if="message === 'normal'" type="primary" @click="addNews()">新增</el-button>
                </div>
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
                    <!-- 按鈕區塊 -->
                    <el-table-column width="180">
                        <template #default="scope">
                            <el-button v-if="scope.row.publisher == emp.empId" size="small"
                                @click="removeNews(scope.row)">下架</el-button>
                            <el-button v-if="scope.row.publisher == emp.empId" size="small"
                                @click="changeNews(scope.row)">修改</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <!-- 全部消息的分頁 -->
                <el-pagination background layout="prev, pager, next" :total="totalPage * 10"
                    :current-page.sync="currentPage" @current-change="handlePageChange" />
            </el-tab-pane>
            <!-- 下架消息區塊 -->
            <el-tab-pane :label="`已下架消息`" name="off">
                <div class="scrollable-container">
                    <el-table :data="offNews" :show-header="false" style="width: 100%">
                        <el-table-column>
                            <template #default="scope">
                                <span class="message-title" @click="showMessageDetails(scope.row)">{{ scope.row.title
                                }}</span>
                            </template>
                        </el-table-column>
                        <!-- 消息發布時間 -->
                        <el-table-column prop="postTime" width="180"></el-table-column>
                        <el-table-column width="180">
                            <template #default="scope">
                                <el-button v-if="scope.row.publisher == emp.empId" size="small"
                                    @click="resetNews(scope.row)">修改</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <!-- 下架消息的分頁 -->
                    <el-pagination background layout="prev, pager, next" :total="offTotalPage * 10"
                        :current-page.sync="offCurrentPage" @current-change="handleOldageChange" />
                </div>
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

        <!-- 新增消息的彈窗 -->
        <el-dialog v-model="addFormVisible" title="新增消息">
            <el-form :model="addFormData">
                <el-form-item label="標題" required>
                    <el-input v-model="addFormData.title" />
                </el-form-item>
                <el-form-item label="內文" required>
                    <el-input v-model="addFormData.content" />
                </el-form-item>
                <el-form-item label="檔案">
                    <el-input v-model="addFormData.file" />
                </el-form-item>
                <el-form-item label="發佈者">
                    <el-input v-model="addFormData.publisher" :readonly="true" />
                </el-form-item>
            </el-form>
            <template #footer>
                <div style="display:flex; justify-content: flex-end;">
                    <el-button @click="submitAddForm()">新增</el-button>
                </div>
            </template>
        </el-dialog>

        <!-- 編輯消息的彈窗 -->
        <el-dialog v-model="editFormVisible" title="編輯消息">
            <el-form :model="editFormData">
                <el-form-item label="標題" required>
                    <el-input v-model="editFormData.title" />
                </el-form-item>
                <el-form-item label="內文" required>
                    <el-input v-model="editFormData.content" />
                </el-form-item>
                <el-form-item label="檔案">
                    <el-input v-model="editFormData.file" />
                </el-form-item>
            </el-form>
            <template #footer>
                <div style="display:flex; justify-content: flex-end;">
                    <el-button @click="submitEditForm()">保存</el-button>
                </div>
            </template>
        </el-dialog>

        <!-- 重新上架消息的彈窗 -->
        <el-dialog v-model="resetFormVisible" title="重新設定消息">
            <el-form :model="resetFormData">
                <el-form-item label="標題" required>
                    <el-input v-model="resetFormData.title" />
                </el-form-item>
                <el-form-item label="內文" required>
                    <el-input v-model="resetFormData.content" />
                </el-form-item>
                <el-form-item label="檔案">
                    <el-input v-model="resetFormData.file" />
                </el-form-item>
            </el-form>
            <template #footer>
                <div style="display:flex; justify-content: flex-end;">
                    <el-button @click="resetForm()">重新上架</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>
  
<script setup>
import Swal from "sweetalert2";
import { empStore } from "../../stores/employee.js";
import axios from "axios";
import { ref, onMounted, watch } from 'vue';
import { ElDialog, ElInput, ElFormItem, ElButton } from 'element-plus'

const emp = empStore();
const URL = import.meta.env.VITE_API_JAVAURL;

const normalNews = ref([]); // 全部消息
const offNews = ref([]); // 下架消息
const totalPage = ref(null); // 全部消息的總頁數
const offTotalPage = ref(null); // 下架消息的總頁數
const searchQuery = ref(); // 模糊搜尋標題

// 設置一開始進入的頁面為全部消息
const message = ref('normal');

// 預設第一頁開始
const currentPage = ref(1);
const offCurrentPage = ref(1);

// 預設詳細消息視窗為false(不彈出)
const showFormVisible = ref(false);
// 預設編輯消息視窗為false
const editFormVisible = ref(false);
// 預設新增消息視窗為false
const addFormVisible = ref(false);
// 預設重新上架消息視窗為false
const resetFormVisible = ref(false);

const showFormContent = ref({}); // 消息詳情
const editFormData = ref({}); // 編輯消息
const addFormData = ref({}); // 新增消息
const resetFormData = ref({}); // 重新上架消息

// 全部消息的換頁
const handlePageChange = async (newPage) => {
    currentPage.value = newPage;
    await readNewsFromDB();
};

// 下架消息的換頁
const handleOldageChange = async (oldPage) => {
    offCurrentPage.value = oldPage;
    await readOffNewsFromDB();
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

// 下架消息
const removeNews = async (row) => {

    try {
        const result = await Swal.fire({
            title: '確定下架嗎？',
            text: '確定下架此消息嗎？',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: '確定',
            cancelButtonText: '取消',
        });

        if (result.isConfirmed) {
            // 使用消息的 id 來發送 put 請求，將消息的 visible 設為 false
            row.visible = false
            await axios.put(`${URL}news/${row.newsId}`, row);

            // 刪除成功後，重新讀取事件列表
            await readNewsFromDB();
            await readOffNewsFromDB();
            await Swal.fire('下架成功!', '訊息已隱藏。', 'success');
        }
    } catch (error) {
        console.error('下架消息時發生錯誤', error);
        await Swal.fire('下架失敗', '請再操作一次。', 'error');
    }
};

// 編輯消息
const submitEditForm = async () => {

    editFormVisible.value = false;
    try {
        await axios.put(`${URL}news/${editFormData.value.newsId}`, editFormData.value);
        Swal.fire('修改成功!', '消息已成功修改。', 'success');
        await readNewsFromDB();
    } catch (error) {
        Swal.fire('修改失敗!', '請再次確認消息內容。', 'error');
    }
};

// 新增消息
const submitAddForm = async () => {
    addFormVisible.value = false;

    try {
        await axios.post(`${URL}news/`, addFormData.value);
        await readNewsFromDB();
        Swal.fire('新增成功!', '消息已成功新增。', 'success');
    } catch (error) {
        Swal.fire('新增失敗!', '請再次確認消息內容。', 'error');
        console.error('新增消息時發生錯誤', error);
    }
};

// 重新上架消息
const resetForm = async () => {
    resetFormVisible.value = false;
    resetFormData.value.visible = true;
    try {
        await axios.put(`${URL}news/${resetFormData.value.newsId}`, resetFormData.value);
        await readNewsFromDB();
        await readOffNewsFromDB();
        Swal.fire('修改成功!', '消息已重新上架。', 'success');
    } catch (error) {
        Swal.fire('修改失敗!', '請再次確認修改內容。', 'error');
        console.error('修改消息時發生錯誤', error);
    }
};

// 新增消息
const addNews = async () => {
    addFormVisible.value = true;
    addFormData.value.publisher = emp.empId;
};

// 取得下架消息的資訊
const readOffNewsFromDB = async () => {
    try {
        const response = await axios.get(`${URL}news/remove`, {
            params: {
                page: offCurrentPage.value,
                size: 8,
            },
        });
        // 存入 offNews
        offNews.value = response.data.content;
        // 賦予總頁數
        offTotalPage.value = response.data.totalPages;

    } catch (error) {
        console.error('獲取消息時發生錯誤', error);
    }
};

// content 就是傳入的 scope.row
const showMessageDetails = content => {
    showFormContent.value = content;
    showFormVisible.value = true;
};

const changeNews = content => {
    editFormVisible.value = true;
    editFormData.value = content;
};

const resetNews = content => {
    resetFormVisible.value = true;
    resetFormData.value = content;
}

onMounted(() => {
    readNewsFromDB();
    readOffNewsFromDB();
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
  
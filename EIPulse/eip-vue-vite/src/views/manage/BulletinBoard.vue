<template>
    <div class="container">
        <el-tabs v-model="message">
            <div style="display: flex; justify-content: space-between; align-items: center;">
                <el-input v-if="message === 'normal'" v-model="searchQuery" placeholder="請輸入消息標題查詢" style="width: 200px;"
                    @input="handleLikeSearch" />
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
                <br />

                <!-- 全部消息的分頁 -->
                <el-pagination v-if="message === 'normal' && !useTitlePage" background layout="prev, pager, next"
                    :total="totalPage * 10" :current-page.sync="currentPage" @current-change="handlePageChange" />

                <!-- 模糊搜尋的分頁 -->
                <el-pagination v-if="message === 'normal' && useTitlePage" background layout="prev, pager, next"
                    :total="titleTotalPage * 10" :current-page.sync="titleCurrentPage" @current-change="titlePageChange" />

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
                    <br />
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
import { ref, onMounted } from 'vue';
import { ElDialog, ElInput, ElFormItem, ElButton } from 'element-plus'
import { news } from "@/model/News";
const emp = empStore();
const URL = import.meta.env.VITE_API_JAVAURL;

const normalNews = ref([]); // 全部消息
const offNews = ref([]); // 下架消息
const totalPage = ref(null); // 全部消息的總頁數
const offTotalPage = ref(null); // 下架消息的總頁數
const searchQuery = ref(''); // 搜尋框
const titleTotalPage = ref(null); // 模糊搜尋的總頁數
const useTitlePage = ref(0); // 三元判斷式，確認是否有使用模糊搜尋

// 設置一開始進入的頁面為全部消息
const message = ref('normal');

// 預設第一頁開始
const currentPage = ref(1);
const offCurrentPage = ref(1);
const titleCurrentPage = ref(1);

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

const newsData = ref(news)
const files = ref([]);
const fileList = ref([]);
const fileChange = (e) => {
    file.value = '';
    const selectedFiles = e.target.files; // 獲取第N個選擇的檔案
    const maxSizeInBytes = 1024 * 1024 * 5; // 5MB

    Array.from(selectedFiles).forEach(selectedFile => {
        if (selectedFile.size > maxSizeInBytes) {
            Swal.fire({
                icon: 'error',
                title: '檔案超過大小',
                text: `${selectedFile.name}超過5MB`,
            });
        } else {
            files.value.push(selectedFile);
        }
    });
};
const addHandler = async () => {
    // 利用 formData 處理檔案
    const formData = new FormData();
    files.value.forEach((file, index) => {
        formData.append(`file${index}`, file);
    });
    // 其他表單數據轉為 JSON，添加到 formData
    const json = JSON.stringify(newsData.value);
    const blob = new Blob([json], {
        type: 'application/json'
    });
    formData.append('data', blob);
    formData.append('file', file.value);
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}news/add`;
    try {
        const response = await axios.post(API_URL, formData);
        console.log(newsData.value);
        console.log(response);

        if (response.status === 200) {
            Swal.fire(
                '儲存成功',
                '',
                'success'
            );
            // 清空檔案陣列
            files.value = [];
            router.push('/manage/:empId/bulletinboard');
        } else {
            alert(response.data.message);
        }
    } catch (error) {
        console.error("An error occurred while adding: ", error);
        console.log('emp.value before sending:', newsData.value);
    }
}

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
  
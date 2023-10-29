<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="字串欄位" prop="columnA">
        <el-input
          v-model="queryParams.columnA"
          placeholder="請輸入字串欄位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="int欄位" prop="columnB">
        <el-input
          v-model="queryParams.columnB"
          placeholder="請輸入int欄位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="decimal欄位" prop="columnC">
        <el-input
          v-model="queryParams.columnC"
          placeholder="請輸入decimal欄位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重設</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['business:ttt:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['business:ttt:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['business:ttt:remove']"
        >刪除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:ttt:export']"
        >導出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tttList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主鍵" align="center" prop="id" />
      <el-table-column label="字串欄位" align="center" prop="columnA" />
      <el-table-column label="int欄位" align="center" prop="columnB" />
      <el-table-column label="decimal欄位" align="center" prop="columnC" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:ttt:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:ttt:remove']"
          >刪除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改測試程式碼生成器對話框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="字串欄位" prop="columnA">
          <el-input v-model="form.columnA" placeholder="請輸入字串欄位" />
        </el-form-item>
        <el-form-item label="int欄位" prop="columnB">
          <el-input v-model="form.columnB" placeholder="請輸入int欄位" />
        </el-form-item>
        <el-form-item label="decimal欄位" prop="columnC">
          <el-input v-model="form.columnC" placeholder="請輸入decimal欄位" />
        </el-form-item>
        <el-form-item label="創建時間" prop="createTime">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="form.createTime"
                          type="date"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="選擇創建時間">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="創建者" prop="createBy">
          <el-input v-model="form.createBy" placeholder="請輸入創建者" />
        </el-form-item>
        <el-form-item label="更新時間" prop="updateTime">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="form.updateTime"
                          type="date"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="選擇更新時間">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="更新者" prop="updateBy">
          <el-input v-model="form.updateBy" placeholder="請輸入更新者" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">確 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listTtt, getTtt, delTtt, addTtt, updateTtt, exportTtt } from "@/api/business/ttt";

  export default {
    name: "Ttt",
    components: {
    },
    data() {
      return {
        // 遮罩層
        loading: true,
        // 選中數組
        ids: [],
        // 非單個禁用
        single: true,
        // 非多個禁用
        multiple: true,
        // 顯示搜索條件
        showSearch: true,
        // 總條數
        total: 0,
        // 測試程式碼生成器表格數據
        tttList: [],
        // 彈出層標題
        title: "",
        // 是否顯示彈出層
        open: false,
        // 主鍵字典
        idOptions: [],
        // int欄位字典
        columnBOptions: [],
        // decimal欄位字典
        columnCOptions: [],
        // 創建時間字典
        createTimeOptions: [],
        // 創建者字典
        createByOptions: [],
        // 更新時間字典
        updateTimeOptions: [],
        // 更新者字典
        updateByOptions: [],
        // 查詢參數
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          columnA: null,
          columnB: null,
          columnC: null,
        },
        // 表單參數
        form: {},
        // 表單校驗
        rules: {
          columnA: [
            { required: true, message: "字串欄位不能為空", trigger: "blur" }
          ],
          columnB: [
            { required: true, message: "int欄位不能為空", trigger: "blur" }
          ],
          createTime: [
            { required: true, message: "創建時間不能為空", trigger: "blur" }
          ],
        }
      };
    },
    created() {
      this.getList();
      this.getDicts("${column.dictType}").then(response => {
        this.idOptions = response.data;
      });
      this.getDicts("${column.dictType}").then(response => {
        this.columnBOptions = response.data;
      });
      this.getDicts("${column.dictType}").then(response => {
        this.columnCOptions = response.data;
      });
      this.getDicts("${column.dictType}").then(response => {
        this.createTimeOptions = response.data;
      });
      this.getDicts("${column.dictType}").then(response => {
        this.createByOptions = response.data;
      });
      this.getDicts("${column.dictType}").then(response => {
        this.updateTimeOptions = response.data;
      });
      this.getDicts("${column.dictType}").then(response => {
        this.updateByOptions = response.data;
      });
    },
    methods: {
      /** 查詢測試程式碼生成器列表 */
      getList() {
        this.loading = true;
        listTtt(this.queryParams).then(response => {
          this.tttList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 取消按鈕
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表單重設
      reset() {
        this.form = {
          id: null,
          columnA: null,
          columnB: null,
          columnC: null,
          createTime: null,
          createBy: null,
          updateTime: null,
          updateBy: null
        };
        this.resetForm("form");
      },
      /** 搜索按鈕操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重設按鈕操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多選框選中數據
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length!==1
        this.multiple = !selection.length
      },
      /** 新增按鈕操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加測試程式碼生成器";
      },
      /** 修改按鈕操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getTtt(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改測試程式碼生成器";
        });
      },
      /** 提交按鈕 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateTtt(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addTtt(this.form).then(response => {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 刪除按鈕操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否確認刪除測試程式碼生成器編號為"' + ids + '"的數據項?', "警告", {
          confirmButtonText: "確定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTtt(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("刪除成功");
        })
      },
      /** 導出按鈕操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否確認導出所有測試程式碼生成器數據項?', "警告", {
          confirmButtonText: "確定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportTtt(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
      }
    }
  };
</script>

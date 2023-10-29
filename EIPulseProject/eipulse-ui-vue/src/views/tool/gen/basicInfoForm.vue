<template>
  <el-form ref="basicInfoForm" :model="info" :rules="rules" label-width="150px">
    <el-row>
      <el-col :span="12">
        <el-form-item label="表名稱" prop="tableName">
          <el-input placeholder="請輸入倉庫名稱" v-model="info.tableName" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="表描述" prop="tableComment">
          <el-input placeholder="請輸入" v-model="info.tableComment" />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="實體類名稱" prop="className">
          <el-input placeholder="請輸入" v-model="info.className" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="作者" prop="functionAuthor">
          <el-input placeholder="請輸入" v-model="info.functionAuthor" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="備註" prop="remark">
          <el-input type="textarea" :rows="3" v-model="info.remark"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
<script>
export default {
  name: "BasicInfoForm",
  props: {
    info: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      rules: {
        tableName: [
          { required: true, message: "請輸入表名稱", trigger: "blur" }
        ],
        tableComment: [
          { required: true, message: "請輸入表描述", trigger: "blur" }
        ],
        className: [
          { required: true, message: "請輸入實體類名稱", trigger: "blur" }
        ],
        functionAuthor: [
          { required: true, message: "請輸入作者", trigger: "blur" }
        ]
      }
    };
  }
};
</script>

<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="80px">
    <el-form-item label="員工暱稱" prop="nickName">
      <el-input v-model="user.nickName" />
    </el-form-item> 
    <el-form-item label="手機號碼" prop="phonenumber">
      <el-input v-model="user.phonenumber" maxlength="11" />
    </el-form-item>
    <el-form-item label="信箱" prop="email">
      <el-input v-model="user.email" maxlength="50" />
    </el-form-item>
    <el-form-item label="性別">
      <el-radio-group v-model="user.sex">
        <el-radio label="0">男</el-radio>
        <el-radio label="1">女</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">保存</el-button>
      <el-button type="danger" size="mini" @click="close">關閉</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserProfile } from "@/api/system/user";
import Global from "@/layout/components/global.js";

export default {
  props: {
    user: {
      type: Object
    }
  },
  data() {
    return {
      // 表單校驗
      rules: {
        nickName: [
          { required: true, message: "員工暱稱不能為空", trigger: "blur" }
        ],
        email: [
          { required: true, message: "信箱位址不能為空", trigger: "blur" },
          {
            type: "email",
            message: "'請輸入正確的信箱位址",
            trigger: ["blur", "change"]
          }
        ],
        phonenumber: [
          { required: true, message: "手機號碼不能為空", trigger: "blur" },
          {
            pattern: /^09\d{2}-?\d{3}-?\d{3}$/,
            message: "請輸入正確的手機號碼",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserProfile(this.user).then(response => {
            this.msgSuccess("修改成功");
          });
        }
      });
    },
    close() {
      Global.$emit("removeCache", "closeSelectedTag", this.$route);
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.push({ path: "/index" });
    }
  }
};
</script>

<template>
  <el-form ref="userRef" :model="user" :rules="rules" label-width="80px">
     <el-form-item label="員工暱稱" prop="nickName">
        <el-input v-model="user.nickName" maxlength="30" />
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
     <el-button type="primary" @click="submit">保存</el-button>
     <el-button type="danger" @click="close">關閉</el-button>
     </el-form-item>
  </el-form>
</template>

<script setup>
import { updateUserProfile } from "@/api/system/user";

const props = defineProps({
 user: {
   type: Object
 }
});

const { proxy } = getCurrentInstance();

const rules = ref({
 nickName: [{ required: true, message: "員工暱稱不能為空", trigger: "blur" }],
 email: [{ required: true, message: "電子信箱不能為空", trigger: "blur" }, { type: "email", message: "請輸入正確的電子信箱", trigger: ["blur", "change"] }],
 phonenumber: [{ required: true, message: "手機號碼不能為空", trigger: "blur" }, { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "請輸入正確的手機號碼", trigger: "blur" }],
});

/** 提交按鈕 */
function submit() {
 proxy.$refs.userRef.validate(valid => {
   if (valid) {
     updateUserProfile(props.user).then(response => {
       proxy.$modal.msgSuccess("修改成功");
     });
   }
 });
};
/** 關閉按鈕 */
function close() {
 proxy.$tab.closePage();
};
</script>

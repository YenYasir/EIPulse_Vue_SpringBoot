<template>
  <el-form ref="pwdRef" :model="user" :rules="rules" label-width="80px">
     <el-form-item label="舊密碼" prop="oldPassword">
        <el-input v-model="user.oldPassword" placeholder="請輸入舊密碼" type="password" show-password />
     </el-form-item>
     <el-form-item label="新密碼" prop="newPassword">
        <el-input v-model="user.newPassword" placeholder="請輸入新密碼" type="password" show-password />
     </el-form-item>
     <el-form-item label="確認密碼" prop="confirmPassword">
        <el-input v-model="user.confirmPassword" placeholder="請確認新密碼" type="password" show-password/>
     </el-form-item>
     <el-form-item>
     <el-button type="primary" @click="submit">保存</el-button>
     <el-button type="danger" @click="close">關閉</el-button>
     </el-form-item>
  </el-form>
</template>

<script setup>
import { updateUserPwd } from "@/api/system/user";

const { proxy } = getCurrentInstance();

const user = reactive({
 oldPassword: undefined,
 newPassword: undefined,
 confirmPassword: undefined
});

const equalToPassword = (rule, value, callback) => {
 if (user.newPassword !== value) {
   callback(new Error("兩次輸入的密碼不一致"));
 } else {
   callback();
 }
};
const rules = ref({
 oldPassword: [{ required: true, message: "舊密碼不能為空", trigger: "blur" }],
 newPassword: [{ required: true, message: "新密碼不能為空", trigger: "blur" }, { min: 6, max: 20, message: "長度在 6 到 20 個字元", trigger: "blur" }],
 confirmPassword: [{ required: true, message: "確認密碼不能為空", trigger: "blur" }, { required: true, validator: equalToPassword, trigger: "blur" }]
});

/** 提交按鈕 */
function submit() {
 proxy.$refs.pwdRef.validate(valid => {
   if (valid) {
     updateUserPwd(user.oldPassword, user.newPassword).then(response => {
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

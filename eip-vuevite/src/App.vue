<template>
  <index v-if="isLoggedIn" :emp-id="empId"></index>
  <new-password v-else-if  ="isEmailVerified" :emp-id="empId" @new-password-success="handleNewPassword"></new-password>
  <login v-else  @login-success="handleLoginSuccess" @email-verified="handleEmailOtp" :emp-id="empId"></login>
</template>

<script>
export default {
  mounted() {
    console.log("NewPassword組建已掛載")
  }
  ,
  data() {
    return {
      empId: null,
      isLoggedIn: false,
      isEmailVerified: false,
    };
  },
  methods: {
    handleLoginSuccess(empIdFromChild) {
      console.log("ˋ這是handleLoginSuccess")
      this.isLoggedIn = true;
      this.empId = empIdFromChild;
    },
    handleEmailOtp(empIdFromChild) {
      console.log("這是handleLoginSuccess")
      this.isEmailVerified = true;
      this.empId = empIdFromChild;
    },
    handleNewPassword() {
      console.log("這是handleNewPassword，我被執行了");
      console.log("isEmailVerified 之前是：" + this.isEmailVerified);
      this.isEmailVerified = false;
      this.isLoggedIn = false;
      console.log("isEmailVerified 之後是：" + this.isEmailVerified);
    }
    ,
  },
};
</script>

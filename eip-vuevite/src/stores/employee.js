import{ref} from "vue";
import {defineStore} from "pinia";
export const empStore = defineStore({
    id:'employee',
    state:()=>({
        empId:sessionStorage.getItem('empId') || null,
        empName:sessionStorage.getItem('empName') || null,
        password:'',
        email:'',
        isLogin:sessionStorage.getItem('isLogin') ||false,
        otp:'',
    }),
    getters:{

    },
    actions:{
        setLoginStatus(status,emp){
            this.isLogin = status;
            this.empId = emp.empId;
            this.empName = emp.empName
            sessionStorage.setItem('empId',this.empId)
            sessionStorage.setItem('empName',this.empName)
            sessionStorage.setItem('isLogin',this.isLogin)
            if(this.empId===''){
                this.empId=sessionStorage.getItem('empId')
            }
        },
        updateEmpId(empId){
            this.empId = empId;
        }
    }
})
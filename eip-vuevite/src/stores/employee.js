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
        permission:"",
        showClock:true
    }),
    getters:{

    },
    actions:{
        setLoginStatus(status,emp){
            this.isLogin = status;
            this.empId = emp.empId;
            this.empName = emp.empName
            this.permission = emp.permissionId[0].toString();
            sessionStorage.setItem('empId',this.empId)
            sessionStorage.setItem('empName',this.empName)
            sessionStorage.setItem('isLogin',this.isLogin)
            sessionStorage.setItem('permission',this.permission)
            if(this.empId===''){
                this.empId=sessionStorage.getItem('empId')
            }
        },
        updateEmpId(empId){
            this.empId = empId;
        },
        toggleClockVisibility(visible) {
            this.showClock = visible;
        }
    }
})
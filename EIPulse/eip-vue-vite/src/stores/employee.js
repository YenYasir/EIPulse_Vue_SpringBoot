import {defineStore} from "pinia";
export const empStore = defineStore({
    id:'employee',
    state:()=>({
        empId:sessionStorage.getItem('empId') || null,
        empName:sessionStorage.getItem('empName') || null,
        email:sessionStorage.getItem('email') || null,
        birth:sessionStorage.getItem('birth') || null,
        idNumber:sessionStorage.getItem('idNumber') || null,
        gender:sessionStorage.getItem('gender') || null,
        phone:sessionStorage.getItem('phone') || null,
        tel:sessionStorage.getItem('tel') || null,
        address:sessionStorage.getItem('address') || null,
        password:'',
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
            this.empName = emp.empName;
            this.email = emp.email;
            this.birth = emp.birth;
            this.idNumber = emp.idNumber;
            this.gender = emp.gender;
            this.phone = emp.phone;
            this.tel = emp.tel;
            this.address = emp.address;
            this.permission = emp.permissionId[0].toString();
            sessionStorage.setItem('empId',this.empId)
            sessionStorage.setItem('empName',this.empName)
            sessionStorage.setItem('isLogin',this.isLogin)
            sessionStorage.setItem('permission',this.permission)
            sessionStorage.setItem('email', this.email)
            sessionStorage.setItem('birth',this.birth)
            sessionStorage.setItem('idNumber',this.idNumber)
            sessionStorage.setItem('gender',this.gender)
            sessionStorage.setItem('phone',this.phone)
            sessionStorage.setItem('tel',this.tel)
            sessionStorage.setItem('address',this.address)
            if(this.empId===''){
                this.empId=sessionStorage.getItem('empId')
        
            }
        },
     
        updateEmpId(empId){
            this.empId = empId;
        },
        toggleClockVisibility(visible) {
            this.showClock = visible;
        },
       
    }
})
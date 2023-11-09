import {defineStore} from "pinia";
import { ref } from "vue";
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
        titleName:sessionStorage.getItem('titleName') || null,
        deptName:sessionStorage.getItem('deptName') || null,
        photoUrl: sessionStorage.getItem('photoUrl') || null,
        isLogin:sessionStorage.getItem('isLogin') ||false,
        permissionId:sessionStorage.getItem('permissionId') || null,
        password:'',
        otp:'',
        endDate: '',
        showClock:true
    }),
    getters:{
       
    }
    ,
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
            this.titleName = emp.titleName;
            this.deptName = emp.deptName;
            this.photoUrl = emp.photoUrl;
            this.permissionId = emp.permissionId.toString();
            sessionStorage.setItem('empId',this.empId)
            sessionStorage.setItem('empName',this.empName)
            sessionStorage.setItem('isLogin',this.isLogin)
            sessionStorage.setItem('permissionId',this.permissionId)
            sessionStorage.setItem('email', this.email)
            sessionStorage.setItem('birth',this.birth)
            sessionStorage.setItem('idNumber',this.idNumber)
            sessionStorage.setItem('gender',this.gender)
            sessionStorage.setItem('phone',this.phone)
            sessionStorage.setItem('tel',this.tel)
            sessionStorage.setItem('address',this.address)
            sessionStorage.setItem('titleName',this.titleName)
            sessionStorage.setItem('deptName',this.deptName)
            sessionStorage.setItem('photoUrl',this.photoUrl)
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
        setSearchDate(endDate) {
            this.endDate = endDate;
        },
        setphotoUrl(photoUrl){
            this.photoUrl = photoUrl;
            sessionStorage.setItem('photoUrl', photoUrl);
        }
    }
})
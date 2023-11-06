import { ref } from "vue";
import { defineStore } from "pinia";

export const useEmpInfoStore = defineStore('empInfo', {
    // state 屬性 
    state: () => ({
        empId: '',
        empName: '',
        email: '',
        phone: '',
        tel: '',
        photoUrl: '',
        address: '',
        titileId: '',
        titleName: '',
        deptId: '',
        deptName: '',
        deptOffice: '',
    }),
    getters: {

    },
    actions: {
        // actions 可以包含任何數量的異步或同步方法
        setUpdateData(data) {
            // 更新 state 中的資料，通常這些數據從一個 API 呼叫中獲得
            // 確保你調用這個方法時提供了一個對象，如 { empId: '...', empName: '...' } 等
            this.empId = data.empId || this.empId;
            this.empName = data.empName || this.empName;
            this.email = data.email || this.email;
            this.phone = data.phone || this.phone;
            this.tel = data.tel || this.tel;
            this.photoUrl = data.photoUrl || this.photoUrl;
            this.address = data.address || this.address;
            this.titleId = data.titleId || this.titleId;
            this.titleName = data.titleName || this.titleName;
            this.deptId = data.deptId || this.deptId;
            this.deptName = data.deptName || this.deptName;
            this.deptOffice = data.deptOffice || this.deptOffice;
        }
    }
})
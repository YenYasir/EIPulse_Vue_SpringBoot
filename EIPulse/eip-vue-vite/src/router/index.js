import {createRouter, createWebHistory} from "vue-router";
import {empStore} from "../stores/employee.js";
import {mallStore} from "../stores/mallStore.js";
const router = createRouter({
    history: createWebHistory(),
    routes: [

        //管理者頁面
        {
            path: `/manage/:empId`,
            component: () => import('@/views/manage/Index.vue'),
            name: 'manage-index',
            meta: {requiresAuth: true},
            children: [
                {
                    path: '/manage/attendance',
                    component: () => import('@/views/manage/AttendanceStatus.vue'),
                    name: 'attendance',
                },
                {
                    path: '/manage/clocktime',
                    component: () => import('@/views/manage/ClocktimeStatus.vue'),
                    name: 'clockTime',
                },
                {
                    path: '/manage/:empId/attendance',
                    component: () => import('@/views/user/UserAttendanceStatus.vue'),
                },
                {
                    path: '/manage/:empId/clocktime',
                    component: () => import('@/views/user/UserClocktimeStatus.vue'),
                },
                {
                    path: '/manage/mall/product',
                    component: () => import('@/views/manage/Products.vue'),
                    name: 'product'
                },
                {
                    path: '/manage/mall/product/save',
                    component: () => import('@/views/manage/ProductForm.vue'),
                    name: 'product-save'
                },
                {
                    path: '/manage/mall/order',
                    component:()=>import('@/views/manage/OrderManage.vue'),
                    name:'mall-manage-order'
                },
                {
                    path: '/manage/form/myform',
                    component: () => import('@/views/form/FormList.vue'),
                    name: 'my-form',
                },
                {
                    path: '/manage/form/apply',
                    component: () => import('@/views/form/Apply.vue'),
                    name: 'apply',
                },
                {
                    path: '/manage/form/audit',
                    component: () => import('@/views/form/Audit.vue'),
                    name: 'audit',
                },
                {
                    path: '/manage/:empId/profile',
                    component: () => import('@/views/manage/Profile.vue'),
            
                },
                {
                    path: '/manage/chats',
                    component: () => import('@/views/chats/Chats.vue'),
                    name: 'chat',
                },
                {
                    path: '/manage/privatechats',
                    component: () => import('@/views/chats/PrivateChats.vue'),
                    name: 'privatechat',
                },
                {   // 新增員工
                    path: '/employee/add-emp',
                    component: () => import('@/views/employee/AddEmp.vue'),
                    name: 'AddEmp',
                },
                {   // 新增員工緊急聯絡人
                    path: '/employee/emergency',
                    component: () => import('@/views/employee/Emergency.vue'),
                    name: 'Emergency',
                },
                {
                    // 查詢員工
                    path: '/employee/find-emp',
                    component: () => import('@/views/employee/FindEmp.vue'),
                    name: 'FindEmp',
                },
                {
                    // 更新員工
                    path: '/employee/updateEmp/:id',
                    component: () => import('@/views/employee/update/UpdateEmp.vue'),
                    name: 'UpdateEmp',
                },
                {
                    // 離職申請
                    path: '/employee/resign-record',
                    component: () => import('@/views/employee/ResignRecord.vue'),
                    name: 'Resign',
                },
                {
                    // 新增部門
                    path: '/employee/add-dept',
                    component: () => import('@/views/employee/Dept/AddDept.vue'),
                    name: 'AddDept',
                },
                {
                    // 查詢部門
                    path: '/employee/find-dept',
                    component: () => import('@/views/employee/Dept/FindDept.vue'),
                },
                {
                    // 更新部門
                    path: '/employee/updateDept/:id',
                    component: () => import('@/views/employee/update/UpdateDept.vue'),
                    name: 'UpdateDept',
                },
                {
                    // 部門異動紀錄
                    path: '/employee/dept-move',
                    component: () => import('@/views/employee/Dept/TitleMove.vue'),
                    name: 'UpdateDept',
                },
                {
                    // 部門異動紀錄
                    path: '/employee/update-title',
                    component: () => import('@/component/employee/UpdateTitle.vue'),
                    name: 'UpdateTitle',
                },
                // {
                //     path: '/manage/:empId/salary',
                //     component: () => import('@/views/salary/SalaryView.vue'),
                //     name: 'salary',
                // },
                // {
                //     path: '/manage/:empId/salary/info',
                //     component: () => import('@/views/salary//SalaryInfo.vue'),   
                    
                // },
                // {
                //     path: '/manage/:empId/salary/info/add',
                //     component: () => import('@/views/salary/SalaryForm.vue'),
                //     name: 'salaryForm'
                // },
                // {
                //     path: '/manage/salary/info/update/:empId',
                //     component: () => import('@/views/salary/InfoUpdate.vue'),
                //     name: 'infoUpdate'
                // },
                // {
                //     path: '/manage/:empId/salary/info/:empId',
                //     component: () => import('@/views/salary/InfoPersonal.vue'),
                //     name: 'infoPersonal'
                // },
                // {
                //     path: '/manage/:empId/salary/history/:empId',
                //     component: () => import('@/views/salary/SalaryHistory.vue'),
                //     name: 'salaryHistory'
                // },
                // {
                //     path: '/manage/:empId/salary/subject',
                //     component: () => import('@/views/salary/subjectView.vue'),
                //     name: 'subject'
                // },
                // {
                //     path: '/manage/:empId/salary/trial/calculate',
                //     component: () => import('@/views/salary/SalaryTrial.vue'),
                //     name: 'trial'
                // },
                // {
                //     path: '/trial/update/:id',
                //     component: () => import('@/views/salary/TrialUpdate.vue'),
                //     name: 'trialUpdate'
                // }, 
                    {
                    path: '/salary',
                    component: () => import('@/views/salary/SalaryView.vue'),
                    name: 'salary',
                    children: [
                        {
                            path: '/info',
                            component: () => import('@/views/salary/SalaryInfo.vue'),
                            name: 'salaryInfo'
                        },
                        {
                            path: '/info/:empId',
                            component: () => import('@/views/salary/InfoPersonal.vue'),
                            name: 'infoPersonal'
                        },
                        // {
                        //     path: '/history',
                        //     component: () => import('@/views/salary/SalaryHistory.vue'),
                        //     name: 'salaryHistory'
                        // },
                        {
                            path: '/history/:empId',
                            component: () => import('@/views/salary/SalaryHistory.vue'),
                            name: 'historyPersonal'
                        },
                        {
                            path: '/info/add',
                            component: () => import('@/views/salary/SalaryForm.vue'),
                            name: 'salaryForm'
                        },
                        {
                            path: '/subject',
                            component: () => import('@/views/salary/subjectView.vue'),
                            name: 'subject'
                        },
                        {
                            path: '/salary/calculate',
                            component: () => import('@/views/salary/SalaryTrial.vue'),
                            name: 'trial'
                        },
                        {
                            path: '/salary/info/update/:empId',
                            component: () => import('@/views/salary/InfoUpdate.vue'),
                            name: 'infoUpdate'
                        },
                        {
                            path: '/salary/trial/update/:id',
                            component: () => import('@/views/salary/TrialUpdate.vue'),
                            name: 'trialUpdate'
                        },

                    ]
                 
                }
            ]
        },

        //員工頁面
        {
            path:'/user/:empId',
            component:()=>import('@/views/user/Index.vue'),
            name:'user-index',
            meta: {requiresAuth: true},
            children:[
                {
                    path: '/user/:empId/attendance',
                    component: () => import('@/views/user/UserAttendanceStatus.vue'),
                },
                {
                    path: '/user/:empId/clocktime',
                    component: () => import('@/views/user/UserClocktimeStatus.vue'),
                },
                {
                    path: '/user/:empId/profile',
                    component: () => import('@/views/manage/Profile.vue'),
            
                },
                {
                    path: '/user/chats',
                    component: () => import('@/views/chats/Chats.vue'),
                   
                },
                {
                    path: '/user/privatechats',
                    component: () => import('@/views/chats/PrivateChats.vue'),
              
                }
            ]
        },


        // 登入用路徑
        {
            path: '/login',
            component: () => import('@/views/Login.vue'),
            name: 'login',
        },
        {
            path: '/forget',
            component: () => import('@/views/ForgetPassword.vue'),
            name: 'forget',
        },
        {
            path: '/new-password',
            component: () => import('@/views/NewPassword.vue'),
            name: 'new-password'
        },

        //商城路徑
        {
            path: '/mall',
            component: () => import('@/views//user/MallIndex.vue'),
            children: [
                {
                    path: '/mall/order',
                    component: () => import('../views/user/UserOrder.vue'),
                    name: 'mall-order'
                },
            ]
        },
        {
            path: '/order/create',
            component: () => import('../views/user/CreateOrder.vue'),
            name: 'create-order'
        },

        //無權訪問頁面
        {
            path:'/',
            component:()=>import('@/views/Unauthorized.vue'),
            name:'unauthorized',
            meta: {requiresAuth: true},
        }

    ]
})


//未登入導向登入頁面
router.beforeEach((to, from, next) => {
    const store = empStore();
    const mall = mallStore();

    const isLogin = store.isLogin || sessionStorage.getItem('isLogin');
    const permission = store.permission || sessionStorage.getItem('permission');

    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (isLogin) {
            if (to.name === 'manage-index'|| to.name === 'user-index') {
                store.toggleClockVisibility(true);
                return next();
                
            }
            store.toggleClockVisibility(false);
            return next();
        }
        return next({ name: 'login' });
    } else if (from.path === '/mall/order') {
        mall.setChangeOrderPage(false);
    }
    next();
});


export default router;

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
            if (to.name === 'manage-index' || to.name === 'user-index') {
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

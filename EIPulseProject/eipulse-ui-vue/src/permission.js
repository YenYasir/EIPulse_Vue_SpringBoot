import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken } from '@/utils/auth'

NProgress.configure({ showSpinner: false })

const whiteList = ['/login', '/auth-redirect', '/bind', '/register']

router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    /* has token*/
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else {
      if (store.getters.roles.length === 0) {
        // 判斷當前員工是否已拉取完user_info資訊
        store.dispatch('GetInfo').then(res => {
          // 拉取user_info
          const roles = res.roles
          store.dispatch('GenerateRoutes', { roles }).then(accessRoutes => {
          // 測試 默認靜態頁面
          // store.dispatch('permission/generateRoutes', { roles }).then(accessRoutes => {
            // 根據roles權限生成可訪問的路由表
            router.addRoutes(accessRoutes) // 動態添加可訪問路由表
            next({ ...to, replace: true }) // hack方法 確保addRoutes已完成
          })
        })
          .catch(err => {
            store.dispatch('FedLogOut').then(() => {
              Message.error(err)
              next({ path: '/' })
            })
          })
      } else {
        next()
        // 沒有動態改變權限的需求可直接next() 刪除下方權限判斷 ↓
        // if (hasPermission(store.getters.roles, to.meta.roles)) {
        //   next()
        // } else {
        //   next({ path: '/401', replace: true, query: { noGoBack: true }})
        // }
        // 可刪 ↑
      }
    }
  } else {
    // 沒有token
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登入白名單，直接進入
      next()
    } else {
      next(`/login?redirect=${to.fullPath}`) // 否則全部重定向到登入頁
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})

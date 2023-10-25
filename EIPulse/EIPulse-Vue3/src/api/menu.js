import request from '@/utils/request'

// 獲取路由
export const getRouters = () => {
  return request({
    url: '/getRouters',
    method: 'get'
  })
}
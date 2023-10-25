import request from '@/utils/request'

// 獲取伺服器資訊
export function getServer() {
  return request({
    url: '/monitor/server',
    method: 'get'
  })
}
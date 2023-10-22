import request from '@/utils/request'

// 獲取伺服器訊息
export function getServer() {
  return request({
    url: '/monitor/server',
    method: 'get'
  })
}
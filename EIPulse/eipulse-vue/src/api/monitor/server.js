import request from '@/utils/request'

// 查詢伺服器詳細
export function getServer() {
  return request({
    url: '/monitor/server',
    method: 'get'
  })
}
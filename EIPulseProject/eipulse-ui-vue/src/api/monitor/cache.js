import request from '@/utils/request'

// 查詢快取詳細
export function getCache() {
  return request({
    url: '/monitor/cache',
    method: 'get'
  })
}

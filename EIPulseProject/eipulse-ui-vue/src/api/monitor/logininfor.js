import request from '@/utils/request'

// 查詢登入日誌列表
export function list(query) {
  return request({
    url: '/monitor/logininfor/list',
    method: 'get',
    params: query
  })
}

// 刪除登入日誌
export function delLogininfor(infoId) {
  return request({
    url: '/monitor/logininfor/' + infoId,
    method: 'delete'
  })
}

// 清空登入日誌
export function cleanLogininfor() {
  return request({
    url: '/monitor/logininfor/clean',
    method: 'delete'
  })
}

// 導出登入日誌
export function exportLogininfor(query) {
  return request({
    url: '/monitor/logininfor/export',
    method: 'get',
    params: query
  })
}
import request from '@/utils/request'

// 查詢調度日誌列表
export function listJobLog(query) {
  return request({
    url: '/monitor/jobLog/list',
    method: 'get',
    params: query
  })
}

// 刪除調度日誌
export function delJobLog(jobLogId) {
  return request({
    url: '/monitor/jobLog/' + jobLogId,
    method: 'delete'
  })
}

// 清空調度日誌
export function cleanJobLog() {
  return request({
    url: '/monitor/jobLog/clean',
    method: 'delete'
  })
}

// 導出調度日誌
export function exportJobLog(query) {
  return request({
    url: '/monitor/jobLog/export',
    method: 'get',
    params: query
  })
}
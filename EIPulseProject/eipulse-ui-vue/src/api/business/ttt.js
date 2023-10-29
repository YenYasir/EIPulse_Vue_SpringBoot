import request from '@/utils/request'

// 查詢測試程式碼生成器列表
export function listTtt(query) {
  return request({
    url: '/business/ttt/list',
    method: 'get',
    params: query
  })
}

// 查詢測試程式碼生成器詳細
export function getTtt(id) {
  return request({
    url: '/business/ttt/' + id,
    method: 'get'
  })
}

// 新增測試程式碼生成器
export function addTtt(data) {
  return request({
    url: '/business/ttt',
    method: 'post',
    data: data
  })
}

// 修改測試程式碼生成器
export function updateTtt(data) {
  return request({
    url: '/business/ttt',
    method: 'put',
    data: data
  })
}

// 刪除測試程式碼生成器
export function delTtt(id) {
  return request({
    url: '/business/ttt/' + id,
    method: 'delete'
  })
}

// 導出測試程式碼生成器
export function exportTtt(query) {
  return request({
    url: '/business/ttt/export',
    method: 'get',
    params: query
  })
}
import request from '@/utils/request'

// 查询笔记列表
export function listNote(query) {
  return request({
    url: '/system/note/list',
    method: 'get',
    params: query
  })
}

// 查询笔记详细
export function getNote(noteId) {
  return request({
    url: '/system/note/' + noteId,
    method: 'get'
  })
}

// 新增笔记
export function addNote(data) {
  return request({
    url: '/system/note',
    method: 'post',
    data: data
  })
}

// 修改笔记
export function updateNote(data) {
  return request({
    url: '/system/note',
    method: 'put',
    data: data
  })
}

// 删除笔记
export function delNote(noteId) {
  return request({
    url: '/system/note/' + noteId,
    method: 'delete'
  })
}

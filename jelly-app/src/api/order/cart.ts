import request from '@/common/utils/request'

export function addCart(data: any) {
  return request({
    url: 'oApi/cart',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function getCart(id: string) {
  return request({
    url: `oApi/cart/user/${id}`,
    method: 'get'
  })
}

export function updateNum(data: any) {
  return request({
    url: `oApi/cart`,
    method: 'put',
    data: JSON.stringify(data)
  })
}

export function deleteCart(id: number) {
  return request({
    url: `oApi/cart/${id}`,
    method: 'delete'
  })
}

export function batchDelete(ids: any) {
  return request({
    url: `oApi/cart/batch`,
    method: 'delete',
    data: { ids }
  })
}

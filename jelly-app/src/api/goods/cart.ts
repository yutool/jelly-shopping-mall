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

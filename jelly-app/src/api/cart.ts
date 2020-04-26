import request from '@/common/utils/request'

export function addCart(data: any) {
  return request({
    url: 'oApi/v1/cart',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function getCartList(id: string) {
  return request({
    url: `oApi/v1/cart/${id}`,
    method: 'get'
  })
}

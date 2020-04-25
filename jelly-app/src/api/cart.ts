import request from '@/common/utils/request'

export function addCart(data: any) {
  return request({
    url: 'oApi/v1/cart',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function getCartList(id: string, page: number, size: number) {
  return request({
    url: `oApi/v1/cart/${id}/${page}/${size}`,
    method: 'get'
  })
}

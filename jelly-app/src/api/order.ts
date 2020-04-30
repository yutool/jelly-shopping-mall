import request from '@/common/utils/request'

export function getOrder(id: string) {
  return request({
    url: `oApi/v1/order/${id}`,
    method: 'get'
  })
}

export function getUserOrder(id: string) {
  return request({
    url: `oApi/v1/order/user/${id}`,
    method: 'get'
  })
}

export function createOrder(data: any) {
  return request({
    url: 'oApi/v1/order/create',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function weixinPay(data: any) {
  return request({
    url: 'oApi/v1/pay/wx/native',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function deleteOrder(id: string) {
  return request({
    url: `oApi/v1/order/${id}`,
    method: 'delete',
  })
}

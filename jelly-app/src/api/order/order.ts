import request from '@/common/utils/request'

export function getOrder(id: string) {
  return request({
    url: `oApi/order/${id}`,
    method: 'get'
  })
}

export function getUserOrder(id: string, page: number, size: number) {
  return request({
    url: `oApi/order/user/${id}/${page}/${size}`,
    method: 'get'
  })
}

export function prepareOrder(data: any) {
  return request({
    url: 'oApi/order/prepare',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function getPrepareOrder(id: string) {
  return request({
    url: `oApi/order/prepare/${id}`,
    method: 'get',
  })
}

export function checkPrepareOrder(id: string) {
  return request({
    url: `oApi/order/prepare/${id}`,
    method: 'delete',
  })
}

export function createOrder(data: any) {
  return request({
    url: 'oApi/order/create',
    method: 'post',
    data: JSON.stringify(data)
  })
}


export function deleteOrder(id: string) {
  return request({
    url: `oApi/order/${id}`,
    method: 'delete',
  })
}

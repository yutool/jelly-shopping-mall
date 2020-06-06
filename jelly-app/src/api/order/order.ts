import request from '@/common/utils/request'

export function getOrder(id: string) {
  return request({
    url: `oApi/v1/order/${id}`,
    method: 'get'
  })
}

export function getUserOrder(id: string, page: number, size: number) {
  return request({
    url: `oApi/v1/order/user/${id}/${page}/${size}`,
    method: 'get'
  })
}

export function prepareOrder(data: any) {
  return request({
    url: 'oApi/v1/order/prepare',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function getPrepareOrder(id: string) {
  return request({
    url: `oApi/v1/order/prepare/${id}`,
    method: 'get',
  })
}

export function checkPrepareOrder(id: string) {
  return request({
    url: `oApi/v1/order/prepare/${id}`,
    method: 'delete',
  })
}

export function createOrder(data: any) {
  return request({
    url: 'oApi/v1/order/create',
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

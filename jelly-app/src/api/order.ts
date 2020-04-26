import request from '@/common/utils/request'

export function createOrder(data: any) {
  return request({
    url: 'oApi/v1/order/create',
    method: 'post',
    data: JSON.stringify(data)
  })
}

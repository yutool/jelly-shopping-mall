import request from '@/common/utils/request'

export function weixinPay(data: any) {
  return request({
    url: 'pApi/pay/wx/native',
    method: 'post',
    data: JSON.stringify(data)
  })
}

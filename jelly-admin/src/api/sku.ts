import request from '@/common/utils/request'

export function getSku(id: string) {
  return request({
    url: `gApi/v1/sku/${id}`,
    method: 'get'
  })
}

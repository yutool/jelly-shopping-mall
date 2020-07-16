import request from '@/common/utils/request'

export function getSku(id: string) {
  return request({
    url: `gApi/sku/${id}`,
    method: 'get'
  })
}

import request from '@/common/utils/request'

export function getSpuList() {
  return request({
    url: 'gApi/v1/spu/list/1/10',
    method: 'get'
  })
}

export function getGoods(id: string) {
  return request({
    url: `gApi/v1/spu/${id}`,
    method: 'get'
  })
}

import request from '@/common/utils/request'

export function getSpuList() {
  return request({
    url: 'gApi/spu/list/1/10',
    method: 'get'
  })
}

export function getGoods(id: string) {
  return request({
    url: `gApi/spu/${id}`,
    method: 'get'
  })
}

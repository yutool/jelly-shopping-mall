import request from '@/common/utils/request'

export function addGoods(data: any) {
  return request({
    url: 'gApi/spu',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function getSpuList(page: number, size: number) {
  return request({
    url: `gApi/spu/list/${page}/${size}`,
    method: 'get'
  })
}

export function getGoods(id: string) {
  return request({
    url: `gApi/spu/${id}`,
    method: 'get'
  })
}

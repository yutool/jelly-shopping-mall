import request from '@/common/utils/request'

export function addGoods(data: any) {
  return request({
    url: 'gApi/v1/spu',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function getSpuList(page: number, size: number) {
  return request({
    url: `gApi/v1/spu/list/${page}/${size}`,
    method: 'get'
  })
}

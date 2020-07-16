import request from '@/common/utils/request'

export function addSeckillGodos(data: any) {
  return request({
    url: 'sApi/seckill/goods',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function getSeckillGoods(page: number, size: number) {
  return request({
    url: `sApi/seckill/goods/list/${page}/${size}`,
    method: 'get'
  })
}

import request from '@/common/utils/request'

export function getDateMenu() {
  return request({
    url: 'sApi/v1/seckill/goods/menus',
    method: 'get'
  })
}

export function getGoods(menu: string) {
  return request({
    url: `sApi/v1/seckill/goods/time_list/${menu}`,
    method: 'get'
  })
}

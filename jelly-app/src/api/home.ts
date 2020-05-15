import request from '@/common/utils/request'

export function getMenu() {
  return request({
    url: 'gApi/v1/home/menu',
    method: 'get'
  })
}

export function getContentMenu() {
  return request({
    url: 'gApi/v1/home/content/menu',
    method: 'get'
  })
}

export function getContent(data: any) {
  return request({
    url: 'gApi/v1/home/content',
    method: 'post',
    params: { menus: data }
  })
}

import request from '@/common/utils/request'

export function getMenu() {
  return request({
    url: 'gApi/home/menu',
    method: 'get'
  })
}

export function getContentMenu() {
  return request({
    url: 'gApi/home/content/menu',
    method: 'get'
  })
}

export function getContent(data: any) {
  return request({
    url: 'gApi/home/content',
    method: 'post',
    params: { menus: data }
  })
}

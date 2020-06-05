import request from '@/common/utils/request'

export function login(data: any) {
  return request({
    url: 'aApi/oauth/login',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function getCurrentUser() {
  return request({
    url: 'uApi/user/current',
    method: 'get'
  })
}

export function register(data: any) {
  return request({
    url: 'uApi/user/register',
    method: 'post',
    data: JSON.stringify(data)
  })
}

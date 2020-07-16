import request from '@/common/utils/request';

// 获取目录
export function getRootCategory() {
  return request({
    url: `gApi/category/root`,
    method: 'get'
  });
}
export function getSecondCategory(data: number) {
  return request({
    url: `gApi/category/second/${data}`,
    method: 'get'
  });
}
export function getThreeCategory(data: number) {
  return request({
    url: `gApi/category/three/${data}`,
    method: 'get'
  });
}

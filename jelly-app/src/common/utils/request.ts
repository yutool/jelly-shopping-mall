import axios from 'axios';
import * as auth from '@/common/utils/auth';

// 创建实例
const instance = axios.create({
  baseURL: '/api', // url = base url + request url
  headers: { 'Content-Type': 'application/json' },
  timeout: 30000,
});

// 拦截请求
instance.interceptors.request.use(
  (config: any) => {
    const token = auth.getToken();
    if (token) { // 如果本地存在token，请求时带上
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  }, (error: any) => {
    return Promise.reject(error);
  }
);

// 拦截相应
const isRefreshing = false;
const requests: any = [];
instance.interceptors.response.use(
  (response: any) => {
    /*
      当服务器返回相应吗status吗1000说明token失效
      1 设置标记，说明正在刷新token
      2 如果这时有请求，将其添加到requests
      3 刷新token后，requests中的请求发至服务器
    */
    // if (response.data.status === 1000) {
    //   alert('fsdfdsfds')
    //   const config = response.config
    //   if (!isRefreshing) {
    //     isRefreshing = true
    //     return refreshToken().then(res => {
    //       var token = res.data.token
    //       auth.setToken(token)
    //       config.headers.Authorization = auth.addAuthorization(token)
    //       // 已经刷新了token，将所有队列中的请求进行重试
    //       requests.forEach(cb => cb(token))
    //       requests = []
    //       return instance(config)
    //     }).catch(res => {
    //       console.error('refreshtoken error =>', res)
    //       window.location.href = '/'
    //     }).finally(() => {
    //       isRefreshing = false
    //     })
    //   } else { // 正在刷新token
    //     return new Promise((resolve) => {
    //       // 将resolve放进队列，用一个函数形式来保存，等token刷新后直接执行
    //       requests.push((token) => {
    //         config.headers.Authorization = auth.addAuthorization(token)
    //         resolve(instance(config))
    //       })
    //     })
    //   }
    // }
    return response.data;
  }, (error: any) => {
    return Promise.reject(error);
  }
);

export default instance;

const routes = [
  // { path: '/', redirect: '/blogs' }, // 测试
  { path: '/', component: () => import('@/views/market/index.vue') },
];

export default routes

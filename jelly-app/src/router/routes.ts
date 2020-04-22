const routes = [
  // { path: '/', redirect: '/blogs' }, // 测试
  { path: '/', component: () => import('@/views/home/index.vue') },
  {
    path: '/market',
    component: () => import('@/views/market/index.vue'),
    children: [
      { path: '/', redirect: 'list' },
      { path: 'list', component: () => import('@/views/market/list.vue') },
      { path: 'detail/:id', component: () => import('@/views/market/detail.vue') },
    ]
  },
  {
    path: '/cart/:id',
    component: () => import('@/views/cart/index.vue'),
    children: [
      { path: '/', redirect: 'list' },
      { path: 'list', component: () => import('@/views/cart/list.vue') },
    ]
  },
  {
    path: '/order/:id',
    component: () => import('@/views/order/index.vue'),
    children: [
      { path: '/', redirect: 'list' },
      { path: 'list', component: () => import('@/views/order/list.vue') },
    ]
  },
];

export default routes

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
    path: '/cart',
    component: () => import('@/views/cart/index.vue'),
    children: [
      { path: '/', redirect: 'list' },
      { path: 'list/:id', component: () => import('@/views/cart/list.vue') },
    ]
  },
  {
    path: '/order',
    component: () => import('@/views/order/index.vue'),
    children: [
      { path: '/', redirect: 'list' },
      { path: 'cart', component: () => import('@/views/order/cart.vue') },
      { path: 'buy', name: 'buy' , component: () => import('@/views/order/buy.vue') },
      { path: 'pay/:id', name: 'pay', component: () => import('@/views/order/pay.vue') },
      { path: 'pay_success', name: 'pay_success', component: () => import('@/views/order/pay_success.vue') },
      { path: 'pay_fail', name: 'pay_fail', component: () => import('@/views/order/pay_fail.vue') },
      { path: 'list', component: () => import('@/views/order/list.vue') },
    ]
  },
];

export default routes

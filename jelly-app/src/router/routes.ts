const routes = [
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
    path: '/order',
    component: () => import('@/views/order/index.vue'),
    children: [
      { path: '/', redirect: 'center' },
      { path: 'cart/:id', component: () => import('@/views/order/cart.vue') },
      { path: 'buy', name: 'buy' , component: () => import('@/views/order/buy.vue') },
      { path: 'pay/:id', name: 'pay', component: () => import('@/views/order/pay.vue') },
      { path: 'pay_success', name: 'pay_success', component: () => import('@/views/order/pay_success.vue') },
      { path: 'pay_fail', name: 'pay_fail', component: () => import('@/views/order/pay_fail.vue') },
    ]
  },
  {
    path: '/center',
    component: () => import('@/views/center/index.vue'),
    children: [
      { path: '/', redirect: 'info' },
      { path: 'info/:id', component: () => import('@/views/center/info.vue' )},
      { path: 'order/:id', component: () => import('@/views/center/order.vue' )},
    ]
  },
  {
    path: '/seckill',
    component: () => import('@/views/seckill/index.vue'),
    children: [
      { path: '/', redirect: 'list' },
      { path: 'list', component: () => import('@/views/seckill/list.vue') },
    ]
  },
];

export default routes

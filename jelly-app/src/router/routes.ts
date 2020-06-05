const routes = [
  { path: '/', component: () => import('@/views/home/index.vue'), meta: { title: '果冻商城'} },
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
    path: '/seckill',
    component: () => import('@/views/seckill/index.vue'),
    children: [
      { path: '/', redirect: 'list' },
      { path: 'list', component: () => import('@/views/seckill/list.vue') },
      { path: 'detail/:time/:id', component: () => import('@/views/seckill/detail.vue') },
    ]
  },
  {
    path: '/order',
    component: () => import('@/views/order/index.vue'),
    meta: { requireAuth: true },
    children: [
      { path: '/', redirect: {name: 'cart'} },
      { path: 'cart/:id', name: 'cart', component: () => import('@/views/order/cart.vue') },
      { path: 'buy/:id', name: 'buy' , component: () => import('@/views/order/buy.vue') },
      { path: 'pay/:id', name: 'pay', component: () => import('@/views/order/pay.vue') },
      { path: 'pay_success', name: 'pay_success', component: () => import('@/views/order/pay_success.vue') },
      { path: 'pay_fail', name: 'pay_fail', component: () => import('@/views/order/pay_fail.vue') },
    ]
  },
  {
    path: '/center/:id',
    component: () => import('@/views/center/index.vue'),
    meta: { requireAuth: true },
    children: [
      { path: '/', redirect: 'order' },
      { path: 'order', component: () => import('@/views/center/order.vue' ) },
    ]
  },
  {
    path: '/user/:id',
    component: () => import('@/views/user/index.vue'),
    meta: { requireAuth: true },
    children: [
      { path: '/', redirect: 'info' },
      { path: 'info', component: () => import('@/views/user/info.vue') },
      { path: 'address', component: () => import('@/views/user/address.vue') },
      { path: 'security', component: () => import('@/views/user/security.vue') },
    ]
  },
];

export default routes

const routes = [
  { path: '/', component: () => import('@/views/index.vue') },
  {
    path: '/goods',
    component: () => import('@/views/goods/index.vue'),
    children: [
      { path: '/', redirect: 'list'},
      { path: 'list', component: () => import('@/views/goods/list.vue') },
      { path: 'add', component: () => import('@/views/goods/add.vue') },
    ]
  },
  {
    path: '/seckill',
    component: () => import('@/views/seckill/index.vue'),
    children: [
      { path: '/', redirect: 'list'},
      { path: 'list', component: () => import('@/views/seckill/list.vue') },
      { path: 'add/:id', component: () => import('@/views/seckill/add.vue') },
    ]
  },
];

export default routes

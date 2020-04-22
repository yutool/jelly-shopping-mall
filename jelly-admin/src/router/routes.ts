const routes = [
  { path: '/', component: () => import('@/views/index.vue') },
  {
    path: '/goods',
    component: () => import('@/views/goods/index.vue'),
    children: [
      { path: '/', redirect: 'list'},
      { path: 'list', component: () => import('@/views/goods/list.vue') },
      { path: 'add', component: () => import('@/views/goods/add.vue') },
      { path: 'seckill_list', component: () => import('@/views/goods/seckillList.vue') },
    ]
  },
];

export default routes

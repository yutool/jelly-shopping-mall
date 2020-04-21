import Vue from 'vue';
import VueRouter from 'vue-router';

import routes from './routes';

Vue.use(VueRouter);

// 解决跳转相同路由报错
const originalPush: any = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location: any) {
  return originalPush.call(this, location).catch((err: any) => err);
};

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;

import router from '@/router'
import store from '@/store'
import { Message } from 'element-ui'
import { getToken, removeToken } from '@/common/utils/auth'
// import getPageTitle from '@/utils/get-page-title'


const whiteList = ['/login', '/auth-redirect'] // no redirect whitelist

router.beforeEach(async (to, from, next) => {
  // set page title
  document.title = to.meta.title ? to.meta.title : '果冻商城'

  // determine whether the user has logged in
  const hasToken = getToken()

  if (hasToken) {
    // determine whether the user has obtained his permission roles through getInfo
    // const hasRoles = store.getters.roles && store.getters.roles.length > 0;
    // get user info
    // note: roles must be a object array! such as: ['admin'] or ,['developer','editor']
    store.dispatch('account/currentUser').then((res: any) => {
      next()
      console.log('当前用户：', res.data)
    }).catch ((error: any) => {
      // remove token and go to login page to re-login
      // store.dispatch('user/resetToken');
      removeToken()
      Message.error(error || 'Has Error')
    })
  } else {
    if (to.matched.some((r: any) => r.meta.requireAuth)) {
      store.dispatch('app/openLoginDialog')
    } else {
      next()
    }
  }
});

router.afterEach(() => {
  // finish progress bar
});

import { login, getCurrentUser } from '@/api/user/user'
import { setToken, removeToken } from '@/common/utils/auth'

const state = {
  user: {}
};

const mutations = {
  SET_USER: (state: any, user: any) => {
    state.user = user
  }
};

const actions = {
  login(cxy: any, user: any) {
    return new Promise((resolve: any, reject: any) => {
      login(user).then((res: any) => {
        const { data } = res
        if (data) {
          console.log(data)
        }
        setToken(data.accessToken)
        resolve(res)
      }).catch((error: any) => {
        reject(error)
      })
    })
  },
  currentUser(cxy: any, user: any) {
    return new Promise((resolve: any, reject: any) => {
      getCurrentUser().then((res: any) => {
        const data = {res}
        if (data) {
          cxy.commit('SET_USER', res.data)
        }
        resolve(res)
      }).catch((error: any) => {
        reject(error)
      })
    })
    
  },
  logout(cxy: any) {
    cxy.commit('SET_USER', {})
    removeToken()
  }
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};

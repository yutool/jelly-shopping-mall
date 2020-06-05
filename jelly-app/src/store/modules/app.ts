const state = {
  loading: false,
  loginDialog: false
}

const mutations = {
  SET_LOADING: (state: any, loading: any) => {
    state.loading = loading
  },
  SET_LOGIN_DIALOG: (state: any, isShow: any) => {
    state.loginDialog = isShow
  }
}

const actions = {
  setLoading(ctx: any, loading: any) {
    ctx.commit('SET_LOADING', loading)
  },
  closeLoginDialog(ctx: any) {
    ctx.commit('SET_LOGIN_DIALOG', false)
  },
  openLoginDialog(ctx: any) {
    ctx.commit('SET_LOGIN_DIALOG', true)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};

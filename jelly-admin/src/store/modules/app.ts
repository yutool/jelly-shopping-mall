const state = {
  loading: false
};

const mutations = {
  SET_LOADING: (state: any, loading: any) => {
    state.loading = loading
  }
};

const actions = {
  setLoading(ctx: any, loading: any) {
    ctx.commit('SET_LOADING', loading)
  }
};

export default {
  namespaced: true,
  state,
  mutations,
  actions
};

const state = {
  spu: {},
  spuTemplate: {}
};

const mutations = {
  SET_SPU: (state: any, spu: any) => {
    state.spu = spu
  }
};

const actions = {
  setSpu(ctx: any) {
    ctx.commit('SET_SPU')
  }
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};

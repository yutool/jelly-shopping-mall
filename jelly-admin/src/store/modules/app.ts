const state = {
  loginDialog: false,
};

const mutations = {
  OPEN_LOGIN_DIALOG: (state: any) => {
    state.loginDialog = true;
  },
  CLOSE_LOGIN_DIALOG: (state: any) => {
    state.loginDialog = false;
  },
};

const actions = {
  openLoginDialog(ctx: any) {
    ctx.commit('OPEN_LOGIN_DIALOG');
  },
  closeLoginDialog(ctx: any) {
    ctx.commit('CLOSE_LOGIN_DIALOG');
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};

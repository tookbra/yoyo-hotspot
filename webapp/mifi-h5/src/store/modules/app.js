import * as types from './mutation-types'
const app = {
  state: {
    device: 'browse'
  },
  actions: {
    setDevice ({ commit }, device) {
      commit(types.TOGGLE_DEVICE, device)
    }
  },
  mutations: {
    [types.TOGGLE_DEVICE]: (state, device) => {
      state.device = device
    }
  }
}

export default app

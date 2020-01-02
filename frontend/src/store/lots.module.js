import LotsService from '../services/lots.service'

const user = JSON.parse(localStorage.getItem('user'))
const initialState = user
  ? { status: { loggedIn: true }, user }
  : { status: {}, user: null }

export const lots = {
  namespaced: true,
  state: initialState,
  actions: {
    bid ({ commit }, bidObj) {
      return LotsService.bid(bidObj).then(
        response => {
          commit('bidSuccessful')
          return Promise.resolve(response)
        },
        error => {
          commit('bidFailed')
          console.log(error.response.data)
          return Promise.reject(error.response.data)
        }
      )
    }
  },
  mutations: {
    bidSuccessful (state) {
      state.status = {}
    },
    bidFailed (state) {
      state.status = {}
    }
  }
}

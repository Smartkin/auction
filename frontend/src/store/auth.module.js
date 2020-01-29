import AuthService from '../services/auth.service'
import UserService from '../services/user.service'

const user = JSON.parse(localStorage.getItem('user'))
console.log(user)

const initialState = user
  ? { status: { loggedIn: true, dataAcquired: true }, user }
  : { status: {}, user: null }

export const auth = {
  namespaced: true,
  state: initialState,
  actions: {
    login ({ commit }, user) {
      return AuthService.login(user).then(
        user => {
          console.log(commit)
          commit('loginSuccess', user)
          return UserService.getUserById(user.id).then(response => {
            console.log(response)
            commit('fetchUserDataSuccessful', response.data)
          })
        },
        error => {
          commit('loginFailure')
          return Promise.reject(error.response.data)
        }
      )
    },
    logout ({ commit }) {
      AuthService.logout()
      commit('logout')
    },
    fetchUserData ({ commit }, user) {
      UserService.getUserById(user.id).then(response => {
        console.log(response)
        commit('fetchUserDataSuccessful', response.data)
      })
    },
    register ({ commit }, user) {
      return AuthService.register(user).then(
        response => {
          commit('registerSuccess')
          return Promise.resolve(response.data)
        },
        error => {
          commit('registerFailure')
          return Promise.reject(error.response.data)
        }
      )
    }
  },
  mutations: {
    loginSuccess (state, user) {
      state.status = { loggedIn: true }
      state.user = user
    },
    loginFailure (state) {
      state.status = {}
      state.user = null
    },
    fetchUserDataSuccessful (state, userData) {
      state.status.dataAcquired = true
      state.user.data = userData
      let userState = state.user
      state.user = null
      state.user = userState
      console.log(state.user)
      localStorage.setItem('user', JSON.stringify(state.user))
    },
    logout (state) {
      state.status = {}
      state.user = null
    },
    registerSuccess (state) {
      state.status = {}
    },
    registerFailure (state) {
      state.status = {}
    }
  }
}

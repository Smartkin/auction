import LotsService from '../services/lots.service'
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'

const initialState = {
  status: { serverConnected: false },
  socket: null,
  stompClient: null
}

export const lots = {
  namespaced: true,
  state: initialState,
  actions: {
    bid ({ commit }, bidObj) {
      return LotsService.bid(bidObj).then(
        response => {
          return Promise.resolve(response)
        },
        error => {
          console.log(error.response.data)
          return Promise.reject(error.response.data)
        }
      )
    },
    subscribe ({ commit }, websockSubLink) {
      if (this.state.lots.status.serverConnected) {
        console.log(websockSubLink.callback)
        commit('channelSubscription', websockSubLink)
        return Promise.resolve()
      }
      return Promise.reject(Error('Не было осуществлено подключение к серверу'))
    },
    unsubscribe ({ commit }, subLink) {
      if (this.state.lots.status.serverConnected) {
        commit('channelUnsubscription', subLink)
      }
      return Promise.reject(Error('Не было осуществлено подключение к серверу'))
    },
    connect ({ commit }) {
      if (!this.state.lots.status.serverConnected && this.state.lots.socket === null) {
        commit('serverConnect')
        return Promise.resolve()
      }
      return Promise.reject(Error('Подключение к серверу уже осуществлено или осущетсвляется'))
    },
    disconnect ({ commit }) {
      commit('serverDisconnect')
      return Promise.resolve()
    },
    send ({ commit }, payload) {
      commit('sendData', payload)
      return Promise.resolve()
    },
    create ({ commit }, lotObj) {
      return LotsService.createLot(lotObj).then(
        response => {
          // commit('createSuccessful')
          return Promise.resolve(response)
        },
        error => {
          // commit('createFailed')
          return Promise.reject(error)
        }
      )
    }
  },
  mutations: {
    channelSubscription (state, websockSubLink) {
      console.log(websockSubLink)
      state.stompClient.subscribe(websockSubLink.link, response => {
        console.log('Received object: ' + response)
        websockSubLink.callback(response)
      })
    },
    channelUnsubscription (state, subLink) {
      state.stompClient.unsubscribe(subLink)
    },
    serverConnect (state) {
      state.socket = SockJS('/auction-websocket')
      state.stompClient = Stomp.over(state.socket, { heartbeat: false })
      state.stompClient.connect({
        'accept-version': '1.2'
      }, () => {
        console.log('Connected to a server ready for subscriptions!')
        state.status.serverConnected = true
      })
    },
    serverDisconnect (state) {
      console.log('Server disconnected...')
      state.stompClient.disconnect()
      state.socket.close()
      state.status.serverConnected = false
      state.stompClient = null
      state.socket = null
    },
    sendData (state, payload) {
      console.log(payload.payloadString)
      state.stompClient.send(payload.link, payload.payloadString, payload.headers)
    }
  }
}

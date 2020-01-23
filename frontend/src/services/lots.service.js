import axios from 'axios'
import authHeader from './auth-header'

const API_URL = '/api/lots'

class LotsService {
  getLot (id) {
    return axios.get(API_URL, {
      params: {
        id: id,
        type: 'single'
      }
    })
  }

  getLots (page = 1, category = null, amount = 5, id = -1) {
    let paramsObj = {
      amount: amount,
      page: page,
      type: 'multiple'
    }
    if (category !== null) {
      paramsObj.category = category
    }
    if (id !== -1) {
      paramsObj.id = id
    }
    return axios.get(API_URL, {
      params: paramsObj
    })
  }

  bid (bidObj) {
    console.log(bidObj)
    console.log(authHeader())
    return axios
      .post(API_URL + '/bid', {
        lotId: bidObj.lotId,
        priceIncrease: bidObj.priceIncrease
      }, {
        headers: authHeader()
      })
      .then(this.handleResponse)
      .then(response => {
        console.log(response.data)
        return response.data
      })
  }

  createLot (lotObj) {
    console.log(lotObj)
    console.log(authHeader())
    return axios
      .post(API_URL + '/create', {
        name: lotObj.name,
        description: lotObj.description,
        endDate: lotObj.endDate,
        startPrice: lotObj.startPrice,
        buyout: lotObj.isBuyout ? 1 : 0
      }, {
        headers: authHeader()
      })
      .then(this.handleResponse)
      .then(response => {
        console.log(response.data)
        return response.data
      })
  }

  handleResponse (response) {
    console.log(response)
    if (response.status === 405 || response.status === 500) {
      const error = response.data && response.data.message
      console.log(error)
      return Promise.reject(error)
    }
    return Promise.resolve(response)
  }
}

export default new LotsService()

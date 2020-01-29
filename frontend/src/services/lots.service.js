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

  getLots (paramsObj) {
    // page = 1, category = null, amount = 5, id = -1
    if (!paramsObj.hasOwnProperty('page')) {
      paramsObj.page = 1
    }
    if (!paramsObj.hasOwnProperty('amount')) {
      paramsObj.amount = 5
    }
    if (!paramsObj.hasOwnProperty('type')) {
      paramsObj.type = 'multiple'
    }
    if (!paramsObj.hasOwnProperty('multipleType')) {
      paramsObj.multipleType = ''
    }
    if (paramsObj.category === null || paramsObj.category === 'all') {
      paramsObj.category = undefined
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
    let timeAmount = lotObj.days * 24 * 60 * 60 * 1000
    let headers = authHeader()
    let dataObj = {
      name: lotObj.name,
      description: lotObj.description,
      timeAmount: timeAmount,
      startPrice: lotObj.startPrice,
      buyout: lotObj.isBuyout ? 1 : 0
    }
    return axios({
      method: 'post',
      url: API_URL + '/create',
      data: dataObj,
      headers: headers
    }).then(this.handleResponse)
      .then(response => {
        console.log(response.data)
        return response.data
      })
  }

  uploadImages (images, lotId) {
    let headers = authHeader()
    console.log(images)
    headers['Content-Type'] = 'multipart/form-data'
    let data = new FormData()
    data.set('lotId', lotId)
    images.forEach(image => {
      data.append('images', image)
    })
    return axios({
      method: 'post',
      url: API_URL + '/uploadImages',
      data: data,
      headers: headers
    }).then(this.handleResponse)
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

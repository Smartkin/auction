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

  getLots (count = 5, id = -1) {
    let paramsObj = {
      count: count,
      type: 'multiple'
    }
    if (id !== -1) {
      paramsObj['id'] = id
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
  }
}

export default new LotsService()

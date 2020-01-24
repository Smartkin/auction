import axios from 'axios'

const API_URL = '/api/categories/'

class CategoryService {
  getCategories (id = null) {
    let params = {}
    if (id !== null) {
      params.id = id
    }
    return axios.get(API_URL, {
      params: params
    }).then(response => {
      let categories = response.data
      return Promise.resolve(categories.filter(cat => !Number.isInteger(cat)))
    })
  }
}

export default new CategoryService()

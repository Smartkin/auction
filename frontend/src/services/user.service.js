import axios from 'axios'

const API_URL = '/api/users'

class UserService {
  getUserById (id) {
    return axios.get(API_URL, {
      params: {
        id: id
      }
    })
  }
}

export default new UserService()

import axios from 'axios'
import authHeader from './auth-header'

const API_URL = '/api/users'

class UserService {
  getUserById (id) {
    return axios.get(API_URL, {
      params: {
        id: id
      }
    })
  }

  uploadAvatar (avatar) {
    let data = new FormData()
    let headers = authHeader()
    headers['Content-Type'] = 'multipart/form-data'
    data.append('avatar', avatar)
    return axios({
      method: 'post',
      url: API_URL + '/changeAvatar',
      data: data,
      headers: headers
    })
      .then(response => {
        return response.data
      })
  }

  getAvatar (avatar) {
    return axios.get('/api/images', {
      params: {
        id: avatar.id
      }
    }).then(response => {
      console.log(response)
      return response
    })
  }
}

export default new UserService()

import Vue from 'vue'
import Vuetify from 'vuetify'

Vue.use(Vuetify)

const opts = {
  theme: {
    themes: {
      light: {
        primary: '#607d8b',
        secondary: '#4caf50',
        accent: '#ffc107',
        error: '#f44336',
        warning: '#ff5722',
        info: '#2196f3',
        success: '#8bc34a'
      }
    }
  }
}

export default new Vuetify(opts)

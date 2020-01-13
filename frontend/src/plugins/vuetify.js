import Vue from 'vue'
import Vuetify from 'vuetify'

Vue.use(Vuetify)

const opts = {
  theme: {
    themes: {
      light: {
        primary: '#009688',
        secondary: '#00bcd4',
        accent: '#9c27b0',
        error: '#f44336',
        warning: '#ffc107',
        info: '#3f51b5',
        success: '#4caf50'
      }
    }
  }
}

export default new Vuetify(opts)

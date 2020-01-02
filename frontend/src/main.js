// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import { localize } from 'vee-validate'
import './plugins/vee-validate'
import '@mdi/font/css/materialdesignicons.css'
import 'roboto-fontface/css/roboto/roboto-fontface.css'
import 'vuetify/dist/vuetify.css'
import vuetify from './plugins/vuetify'
Vue.config.productionTip = false

// Switch vee-validate to Russian language
localize('ru')
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  vuetify,
  components: { App },
  template: '<App/>'
})

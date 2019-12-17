// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import cookies from 'vue-cookies'
import VeeValidate from 'vee-validate'
Vue.use(cookies)
cookies.config('30d') // Store cookies for a month by default
Vue.config.productionTip = false
Vue.use(VeeValidate)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})

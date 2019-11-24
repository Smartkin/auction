import Vue from 'vue'
import Router from 'vue-router'
import HomePage from '@/components/HomePage'
import Contacts from '@/components/Contacts'
import LotsList from '@/components/LotsList'
import About from '@/components/About'
import Login from '@/components/Login'
import Profile from '@/components/Profile'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HomePage',
      component: HomePage
    },
    {
      path: '/contacts',
      name: 'contacts',
      component: Contacts
    },
    {
      path: '/lots_list',
      name: 'lots_list',
      component: LotsList
    },
    {
      path: '/about',
      name: 'about',
      component: About
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/profile',
      name: 'profile',
      component: Profile
    }
  ]
})

import Vue from 'vue'
import Router from 'vue-router'
import HomePage from '@/views/HomePage'
import Contacts from '@/views/Contacts'
import LotsList from '@/views/LotsList'
import Login from '@/views/Login'
import Profile from '@/views/Profile'
import Lot from '@/views/Lot'
import Registration from '@/views/Registration'
import CreateLot from '@/views/CreateLot'
import Logout from '@/views/Logout'

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
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/logout',
      name: 'logout',
      component: Logout
    },
    {
      path: '/profile',
      name: 'profile',
      component: Profile
    },
    {
      path: '/lot/:id',
      name: 'lot',
      component: Lot,
      props: true
    },
    {
      path: '/register',
      name: 'register',
      component: Registration
    },
    {
      path: '/create_lot',
      name: 'createLot',
      component: CreateLot
    }
  ]
})

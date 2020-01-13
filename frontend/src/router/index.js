import Vue from 'vue'
import Router from 'vue-router'
import HomePage from '@/components/HomePage'
import Contacts from '@/components/Contacts'
import LotsList from '@/components/LotsList'
import Login from '@/components/Login'
import Profile from '@/components/Profile'
import Lot from '@/components/Lot'
import Registration from '@/components/Registration'
import CreateLot from '@/components/CreateLot'
import Logout from '@/components/Logout'

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

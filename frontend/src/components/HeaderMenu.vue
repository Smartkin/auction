<template>
  <header style="position: fixed; top: 0; width: 100%; z-index: 100">
    <b-navbar type="dark" variant="info">
      <b-navbar-brand to="/">Античный тороговец</b-navbar-brand>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item v-for="item in leftItems" :to="item.link" :active="item.isActive" :key="item.id" :id="item.nameId" @click="item.onClick">
            {{item.name}}
          </b-nav-item>
        </b-navbar-nav>
      </b-collapse>
      <b-navbar-nav>
        <b-nav-item v-for="item in rightItems" :to="item.link" :active="item.isActive" :key="item.id" :id="item.nameId" @click="item.onClick">
          {{item.name}}
        </b-nav-item>
      </b-navbar-nav>
    </b-navbar>
  </header>
</template>

<script>
import HeaderItem from '../models/header-item'

export default {
  name: 'header-menu',
  methods: {
    initHeaderItems () {
      // Init left hand side items
      this.leftItems.push(new HeaderItem('homePage', 'Главная страница', '/'),
        new HeaderItem('lotsList', 'Список лотов', '/lots_list'),
        new HeaderItem('contacts', 'О нас', '/contacts'))
      // Init right hand side items
      if (this.currentUser !== null) {
        this.rightItems.push(new HeaderItem('profile', 'Личный Кабинет', '/profile'),
          new HeaderItem('logout', 'Выйти', '/logout', this.handleLogout))
      } else {
        this.rightItems.push(new HeaderItem('login', 'Войти', '/login'))
      }
    },
    handleLogout () {
      this.$store.dispatch('auth/logout').then(
        () => {
          this.$router.push('/')
        }
      )
    }
  },
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    }
  },
  data () {
    return {
      leftItems: [],
      rightItems: []
    }
  },
  props: {
    activePanel: {
      type: String,
      required: true
    }
  },
  mounted () {
    // Init panel with items
    this.initHeaderItems()
    // Find currently active panel and set it to active
    const panel = this.activePanel
    this.leftItems.forEach(function (value, index, array) {
      array[index]['isActive'] = (value['nameId'] === panel)
    })
    this.rightItems.forEach(function (value, index, array) {
      array[index]['isActive'] = (value['nameId'] === panel)
    })
  }
}
</script>

<style>
/*.header-panel-list{*/
/*  list-style-type: none;*/
/*  position: fixed;*/
/*  top: 0;*/
/*  width: 100%;*/
/*  margin: 0;*/
/*  padding: 0;*/
/*  overflow: hidden;*/
/*  background-color: #333333;*/
/*  border-bottom: 1px solid #5f5f5f;*/
/*}*/
/*.header-list-item{*/
/*  float: left;*/
/*  border-right: 1px solid #5f5f5f;*/
/*}*/
/*.float-right{*/
/*  float: right;*/
/*}*/
/*.header-list-item a{*/
/*  display: block;*/
/*  color: white;*/
/*  text-align: center;*/
/*  padding: 14px 16px;*/
/*  text-decoration: none;*/
/*  transition: background-color 0.4s ease-in-out;*/
/*}*/
/*.header-list-item a:hover{*/
/*  background-color: #111111;*/
/*}*/
/*.active{*/
/*  background-color: #111111;*/
/*}*/
</style>

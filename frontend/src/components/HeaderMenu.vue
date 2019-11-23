<template>
  <header>
    <ul class="header-panel-list">
      <li v-for="item in menuItems" v-bind:class="{'float-right': item.isOnRight, active: item.isActive}" v-bind:key="item.id" v-bind:id="item.nameId">
        <router-link v-bind:to="item.link">{{item.name}}</router-link>
      </li>
    </ul>
  </header>
</template>

<script>
export default {
  name: 'header-menu',
  data () {
    return {
      menuItems: [
        {
          nameId: 'homePage',
          name: 'Главная страница',
          link: '/',
          isOnRight: false,
          isActive: false
        },
        {
          nameId: 'lotsList',
          name: 'Список лотов',
          link: '/lots_list',
          isOnRight: false,
          isActive: false
        },
        {
          nameId: 'contacts',
          name: 'Контакты',
          link: '/contacts',
          isOnRight: false,
          isActive: false
        },
        {
          nameId: 'about',
          name: 'О нас',
          link: '/about',
          isOnRight: true,
          isActive: false
        },
        {
          nameId: 'login',
          name: 'Войти',
          link: '/login',
          isOnRight: true,
          isActive: false
        }
      ]
    }
  },
  props: {
    activePanel: {
      type: String,
      required: true
    }
  },
  mounted () {
    // Find currently active panel and set it to active
    const panel = this.activePanel
    this.menuItems.forEach(function (value, index, array) {
      array[index]['isActive'] = (value['nameId'] === panel)
    })
  }
}
</script>

<style>
.header-panel-list{
  list-style-type: none;
  position: fixed;
  top: 0;
  width: 100%;
  margin: 0;
  padding: 0;
  overflow: hidden;
  background-color: #333333;
  border-bottom: 1px solid #5f5f5f;
}
li{
  float: left;
  border-right: 1px solid #5f5f5f;
}
li:last-child{
  border-right: none;
}
.float-right{
  float: right;
}
li a{
  display: block;
  color: white;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  transition: background-color 0.4s ease-in-out;
}
li a:hover{
  background-color: #111111;
}
.active{
  background-color: #111111;
}
</style>

<template>
  <div id="app">
    <v-app light>
      <AuctionDrawer :drawer="drawer" @category-change="onCategoryChange" @drawer-change="onDrawerChange"/>
      <AuctionAppBar :drawer="drawer" @drawer-change="onDrawerChange"/>
      <v-content>
        <v-container fluid>
          <router-view :category="category"/>
        </v-container>
      </v-content>
      <AuctionFooter/>
    </v-app>
  </div>
</template>

<script>
import AuctionFooter from './components/AuctionFooter'
import AuctionAppBar from './components/AuctionAppBar'
import AuctionDrawer from './components/AuctionDrawer'
export default {
  name: 'App',
  components: {
    AuctionFooter,
    AuctionAppBar,
    AuctionDrawer
  },
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    }
  },
  data () {
    return {
      drawer: false,
      category: 'all'
    }
  },
  mounted () {
    console.log('Connecting to SockJS server...')
    this.$store.dispatch('lots/connect')
  },
  beforeDestroy () {
    this.$store.dispatch('lots/disconnect')
  },
  methods: {
    onDrawerChange: function (newDrawer) {
      this.drawer = newDrawer
    },
    onCategoryChange: function (newCategory) {
      console.log('Category change: ' + newCategory)
      this.category = newCategory
    }
  }
}
</script>

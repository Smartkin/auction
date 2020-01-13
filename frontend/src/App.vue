<template>
  <div id="app">
    <v-app light>
      <AuctionDrawer :drawer="drawer" @drawer-change="onDrawerChange"/>
      <AuctionAppBar :drawer="drawer" @drawer-change="onDrawerChange"/>
      <v-content>
        <v-container fluid>
          <router-view/>
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
      drawer: false
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
    }
  }
}
</script>

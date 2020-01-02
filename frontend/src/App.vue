<template>
  <div id="app">
    <v-app light>
      <v-navigation-drawer color="primary" v-model="drawer" app>
        <template v-if="$vuetify.breakpoint.smAndDown">
          <v-list-item>
            <v-list-item-content>
              <router-link to="/" style="text-decoration: none; color: black">
                <v-list-item-title class="title">
                  Античный Торговец
                </v-list-item-title>
              </router-link>
              <v-list-item-subtitle>
                Аукцион антиквариата
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
          <v-divider/>
          <v-list nav>
            <v-list-item to="/">
              <v-list-item-icon>
                <v-icon>mdi-home</v-icon>
              </v-list-item-icon>
              <v-list-item-content>
                <v-list-item-title>Главная страница</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-list-item to="/lots_list">
              <v-list-item-icon>
                <v-icon>mdi-format-list-bulleted</v-icon>
              </v-list-item-icon>
              <v-list-item-content>
                <v-list-item-title>Список лотов</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-list-item to="/contacts">
              <v-list-item-icon>
                <v-icon>mdi-information</v-icon>
              </v-list-item-icon>
              <v-list-item-content>
                <v-list-item-title>О нас</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </template>
      </v-navigation-drawer>
      <v-app-bar color="primary" dense app>
        <v-app-bar-nav-icon @click.stop="drawer = !drawer"/>
        <router-link to="/" style="text-decoration: none; color: black" v-if="$vuetify.breakpoint.mdAndUp">
          <v-toolbar-title class="mr-4">Античный Торговец</v-toolbar-title>
        </router-link>
        <v-toolbar-items v-if="$vuetify.breakpoint.mdAndUp">
          <v-btn text to="/">Главная страница</v-btn>
          <v-btn text to="/lots_list">Список лотов</v-btn>
          <v-btn text to="/contacts">О нас</v-btn>
        </v-toolbar-items>
        <v-spacer/>
        <v-text-field
          dense
          outlined
          :clearable="$vuetify.breakpoint.mdAndUp"
          color="accent"
          label="Поиск лотов"
          class="mt-6"
          prepend-icon="mdi-magnify"
          rounded/>
        <v-toolbar-items>
          <v-btn icon to="/login" v-if="!currentUser">
            <v-icon>mdi-login</v-icon>
          </v-btn>
          <template v-if="currentUser">
            <v-btn icon to="/profile">
              <v-icon>mdi-account</v-icon>
            </v-btn>
          </template>
        </v-toolbar-items>
      </v-app-bar>
      <v-content>
        <v-container fluid>
          <router-view/>
        </v-container>
      </v-content>
      <v-footer app>
        <v-col class="text-center">
          2019-{{ new Date().getFullYear() }} — <strong>Античный Торговец</strong>
        </v-col>
      </v-footer>
    </v-app>
  </div>
</template>

<script>
export default {
  name: 'App',
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    }
  },
  data: () => ({
    drawer: false
  })
}
</script>

<style>
/*body {*/
/*  background-color: #d9d4d7;*/
/*}*/
/*#app {*/
/*  font-family: 'Avenir', Helvetica, Arial, sans-serif;*/
/*  -webkit-font-smoothing: antialiased;*/
/*  -moz-osx-font-smoothing: grayscale;*/
/*  text-align: center;*/
/*  color: #475c63;*/
/*  margin-top: 60px;*/
/*}*/
/*a:link, a:visited {*/
/*  color: #191e22;*/
/*  text-decoration: none;*/
/*}*/
/*a:hover, a:active {*/
/*  color: #920000;*/
/*}*/
/*img {*/
/*  width: 10%;*/
/*  height: auto;*/
/*}*/
</style>

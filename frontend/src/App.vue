<template>
  <div id="app">
    <v-app light>
      <v-navigation-drawer v-model="drawer" app>
        <template v-if="$vuetify.breakpoint.smAndDown">
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
      <v-app-bar dense app>
        <v-app-bar-nav-icon @click.stop="drawer = !drawer"/>
        <v-toolbar-title class="mr-4">Античный Торговец</v-toolbar-title>
        <v-toolbar-items v-if="$vuetify.breakpoint.mdAndUp">
          <v-btn to="/">Главная страница</v-btn>
          <v-btn to="/lots_list">Список лотов</v-btn>
          <v-btn to="/contacts">О нас</v-btn>
        </v-toolbar-items>
        <v-spacer/>
        <v-text-field dense outlined clearable label="Поиск лотов" class="mt-6" prepend-icon="mdi-magnify" rounded/>
        <v-toolbar-items>
          <v-btn icon to="/login">
            <v-icon>mdi-login</v-icon>
          </v-btn>
        </v-toolbar-items>
      </v-app-bar>
      <v-content>
        <v-container fluid>
          <router-view/>
        </v-container>
      </v-content>
      <v-footer app>
        <v-col class="text-center">
          {{ new Date().getFullYear() }} — <strong>Античный Торговец</strong>
        </v-col>
      </v-footer>
    </v-app>
  </div>
</template>

<script>
export default {
  name: 'App',
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

<template>
  <v-navigation-drawer dark color="primary" v-model="resultDrawer" app>
    <template v-if="$vuetify.breakpoint.mdAndDown">
      <v-list-item>
        <v-list-item-content>
          <router-link to="/" style="text-decoration: none; color: black">
            <v-list-item-title class="title white--text">
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
</template>

<script>
export default {
  data () {
    return {
      currentDrawer: false
    }
  },
  computed: {
    resultDrawer: {
      get: function () {
        return this.drawer
      },
      set: function (newDrawer) {
        this.currentDrawer = newDrawer
        this.$emit('drawer-change', newDrawer)
      }
    }
  },
  props: {
    drawer: {
      type: Boolean,
      required: true
    }
  }
}
</script>

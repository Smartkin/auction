<template>
  <v-app-bar clipped-left :color="color" dense app>
    <v-app-bar-nav-icon @click.stop="$emit('drawer-change', !drawer)"/>
    <router-link to="/" style="text-decoration: none;" v-if="$vuetify.breakpoint.lgAndUp">
      <v-toolbar-title class="mr-4 white--text">Античный Торговец</v-toolbar-title>
    </router-link>
    <v-toolbar-items v-if="$vuetify.breakpoint.lgAndUp">
      <v-btn depressed :color="color" to="/">Главная страница</v-btn>
      <v-btn depressed :color="color" to="/lots_list">Список лотов</v-btn>
      <v-btn depressed :color="color" to="/contacts">О нас</v-btn>
    </v-toolbar-items>
    <v-spacer/>
    <v-text-field
      dense
      outlined
      dark
      :clearable="$vuetify.breakpoint.mdAndUp"
      color="warning"
      label="Поиск лотов"
      class="mt-6 mr-3"
      prepend-icon="mdi-magnify"
      rounded/>
    <v-toolbar-items>
      <v-card
        flat
        tile
      >
        <v-list
          tile
          dense
          max-width="300"
          min-width="300"
          elevation="0"
          flat
          dark
          subheader
          expand
          :color="color"
          v-if="$vuetify.breakpoint.lgAndUp"
        >
          <v-list-group
            color="white"
            no-action
            prepend-icon="mdi-account"
          >
            <template v-slot:activator>
              <v-list-item-title>
                {{ userData ? userData.username : 'Гость' }}
              </v-list-item-title>
            </template>
            <v-list-item
              to="/profile"
              v-if="userData"
              color="black"
              class="mt-1"
            >
              <v-list-item-title>Личный кабинет</v-list-item-title>
              <v-list-item-icon>
                <v-icon>mdi-account</v-icon>
              </v-list-item-icon>
            </v-list-item>
            <v-list-item
              v-for="(action, i) in actions"
              :key="i"
              :to="action.link"
              v-if="!userData"
              color="black"
            >
              <v-list-item-title v-text="action.name"/>
              <v-list-item-icon>
                <v-icon v-text="action.icon"/>
              </v-list-item-icon>
            </v-list-item>
            <v-list-group
              sub-group
              no-action
              color="white"
              value="true"
              v-if="userData"
            >
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title>Управление</v-list-item-title>
                </v-list-item-content>
              </template>
              <v-list-item
                v-for="(action, i) in actions"
                :key="i"
                color="black"
                :to="action.link"
              >
                <v-list-item-title v-text="action.name"/>
                <v-list-item-icon>
                  <v-icon v-text="action.icon"/>
                </v-list-item-icon>
              </v-list-item>
            </v-list-group>
          </v-list-group>
        </v-list>
      </v-card>
    </v-toolbar-items>
  </v-app-bar>
</template>

<script>
export default {
  data () {
    return {
      color: 'primary',
      actions: []
    }
  },
  computed: {
    userData: {
      get () {
        this.setActions()
        return this.$store.state.auth.status.dataAcquired ? this.$store.state.auth.user.data : null
      }
    }
  },
  methods: {
    setActions () {
      console.log('Changing user actions...')
      this.actions = []
      if (this.$store.state.auth.user && this.$store.state.auth.user.data) {
        let user = this.$store.state.auth.user.data
        console.log(user)
        this.actions.push({
          name: 'Создать лот',
          icon: 'mdi-briefcase-plus',
          link: '/create_lot'
        })
        this.actions.push({
          name: 'Редактировать данные',
          icon: 'mdi-account-edit',
          link: '/edit_profile'
        })
        this.actions.push({
          name: 'Выйти',
          icon: 'mdi-account-arrow-right',
          link: '/logout'
        })
      } else {
        this.actions.push({
          name: 'Войти',
          icon: 'mdi-account-arrow-left',
          link: '/login'
        })
        this.actions.push({
          name: 'Регистрация',
          icon: 'mdi-account-plus',
          link: '/register'
        })
      }
    }
  },
  mounted () {
    this.setActions()
  },
  props: {
    drawer: {
      type: Boolean,
      required: true
    }
  }
}
</script>

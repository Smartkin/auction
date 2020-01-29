<template>
  <v-navigation-drawer
    clipped
    dark
    color="primary"
    v-model="resultDrawer"
    app
    width="380"
  >
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
      <v-list
        expand
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
              :to="action.link"
            >
              <v-list-item-title v-text="action.name"/>
              <v-list-item-icon>
                <v-icon v-text="action.icon"/>
              </v-list-item-icon>
            </v-list-item>
          </v-list-group>
        </v-list-group>
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
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="title white--text">
            Категории
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-divider/>
    </template>
    <v-treeview
      :items="categories"
      item-children="subCategory"
      color="success"
      hoverable
      transition
      open-on-click
    >
      <template #append="{ item }">
        <v-btn
          text
          small
          @click="switchCategory(item)"
        >
          Открыть
        </v-btn>
      </template>
    </v-treeview>
  </v-navigation-drawer>
</template>

<script>
import CategoryService from '../services/category.service'

export default {
  data () {
    return {
      currentDrawer: false,
      color: 'primary',
      actions: [],
      categories: []
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
    },
    userData: {
      get () {
        this.setActions()
        return this.$store.state.auth.status.dataAcquired ? this.$store.state.auth.user.data : null
      }
    }
  },
  mounted () {
    this.setActions()
    CategoryService.getCategories().then(categories => {
      this.categories = categories
      this.categories.push({ name: 'Все' })
    })
  },
  props: {
    drawer: {
      type: Boolean,
      required: true
    }
  },
  methods: {
    switchCategory (categoryObject) {
      console.log(categoryObject)
      console.log(this.$router.currentRoute)
      if (!this.$router.currentRoute.path.includes(categoryObject.name)) {
        console.log('Switched category')
        this.$emit('category-change', categoryObject.name)
      }
    },
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
  }
}
</script>

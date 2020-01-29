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
      <v-list>
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
    }
  },
  mounted () {
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
    }
  }
}
</script>

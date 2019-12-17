<template>
  <div class='lots-list'>
    <header-menu active-panel="lotsList"/>
    <p>Список лотов</p>
    <ul class="lots-list">
      <li v-for="lot in listOfLots" v-bind:key="lot.id" @click="onLotClick" class="lots-list-item">
        <router-link v-bind:to="lot.link">
          <placeholder width="200" height="200" text="Лот"/>
          {{ lot.name }} за {{ lot.price }} рублей
          Выставлено пользователем с номером {{ lot.ownerID }}
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
import HeaderMenu from './HeaderMenu'
import Placeholder from './Placeholder'
import axios from 'axios'

export default {
  name: 'lots-list',
  components: {
    HeaderMenu,
    Placeholder
  },
  data () {
    return {
      listOfLots: [{
        id: 0,
        link: '/lot/0',
        name: 'Лот',
        price: 0,
        ownerID: 0
      }]
    }
  },
  methods: {
    getLots () {
      axios.get('/api/lots').then(response => {
        // Parse the JSON
        let array = JSON.parse(response.data['lots'])
        // Push all the lots
        for (const lot in array) {
          array[lot].link = '/lot/' + array[lot].id
          this.listOfLots.push(array[lot])
        }
      })
    },
    onLotClick () {

    }
  },
  mounted () {
    this.getLots()
  }
}
</script>

<style>
.lots-list{
  list-style-type: none;
}
.lots-list-item{
  text-align: left;
  display: block;
}
</style>

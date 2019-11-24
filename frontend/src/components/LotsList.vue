<template>
  <div class='lots-list'>
    <header-menu active-panel="lotsList"></header-menu>
    <p>Список лотов</p>
    <ul class="lots-list">
      <li v-for="lot in listOfLots" v-bind:key="lot.id" class="lots">
        {{ lot.name }} за {{ lot.price }} рублей
        Выставлено пользователем с номером {{ lot.ownerID }}
      </li>
    </ul>
  </div>
</template>

<script>
import HeaderMenu from './HeaderMenu'
import axios from 'axios'

export default {
  name: 'lots-list',
  components: {
    HeaderMenu
  },
  data () {
    return {
      listOfLots: []
    }
  },
  methods: {
    getLots () {
      axios.get('/api/lots').then(response => {
        // Parse the JSON
        let array = JSON.parse(response.data['lots'])
        // Push all the lots
        for (const lot in array) {
          this.listOfLots.push(array[lot])
        }
      })
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
.lots{
  text-align: left;
}
</style>

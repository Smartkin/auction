<template>
  <div class='lots-list'>
    <header-menu active-panel="lotsList"/>
    <p>Список лотов</p>
    <ul class="lots-list">
      <li v-for="lot in listOfLots" v-bind:key="lot.id" class="lots-list-item">
        <router-link v-bind:to="lot.link">
          <placeholder width="200" height="200" text="Лот"/>
          {{ lot.name }} за {{ lot.price }} рублей
          <p>Выставлено пользователем {{ lot.owner.username }}</p>
          <p v-if="lot.bidder !== null">Выкупается пользователем {{ lot.bidder.username }}</p>
        </router-link>
        <form @submit.prevent="handleBid(lot.id)">
          <input type="number" name="priceIncrease" v-model="bid.priceIncrease" v-validate="'required|min:1|max:5'"/>
          <button>Повысить</button>
        </form>
      </li>
    </ul>
  </div>
</template>

<script>
import HeaderMenu from './HeaderMenu'
import Placeholder from './Placeholder'
import Lot from '../models/lot'
import User from '../models/user'
import Bid from '../models/bid'
import LotsService from '../services/lots.service'
import UserService from '../services/user.service'

export default {
  name: 'lots-list',
  components: {
    HeaderMenu,
    Placeholder
  },
  data () {
    return {
      listOfLots: [],
      startId: 0,
      loadCount: 5,
      bid: new Bid(0, 0)
    }
  },
  mounted () {
    // Get lots information
    LotsService.getLots().then(
      response => {
        let array = response.data['lots']
        console.log(array)
        for (const lot in array) {
          let parsedLot = JSON.parse(array[lot])
          // Get the user information
          UserService.getUserById(parsedLot.ownerID).then(
            response => {
              console.log(response.data.user)
              let owner = JSON.parse(response.data.user)
              // Get bidder information if a bidder exists
              if (parsedLot.bidderID !== null) {
                UserService.getUserById(parsedLot.bidderID).then(
                  response => {
                    let bidder = JSON.parse(response.data.user)
                    this.listOfLots.push(new Lot(parsedLot.id, '/lot/' + parsedLot.id,
                              parsedLot.name, new User(owner.name, owner.surname, owner.username, owner.email),
                              parsedLot.price, new User(bidder.name, bidder.surname, bidder.username, bidder.email)))
                  }
                )
              } else {
                this.listOfLots.push(new Lot(parsedLot.id, '/lot/' + parsedLot.id, parsedLot.name, new User(owner.name, owner.surname, owner.username, owner.email), parsedLot.price))
              }
              console.log(this.listOfLots)
            }
          )

        }
        console.log(this.listOfLots)
      },
      error => {
        this.listOfLots.push(error.response.data.message)
      }
    )
  },
  methods: {
    handleBid (lotId) {
      this.$validator.validateAll()
      console.log('Tried to bid')
      console.log(this.bid)
      this.bid.lotId = lotId
      if (this.bid.priceIncrease) {
        this.$store.dispatch('lots/bid', this.bid)
      }
    }
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
  padding-bottom: 30px;
}
</style>

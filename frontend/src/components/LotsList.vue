<template>
  <div class='lots-list'>
    <b-container fluid>
      <b-list-group>
        <b-list-group-item v-for="lot in listOfLots" v-bind:key="lot.id" variant="dark">
          <router-link v-bind:to="lot.link">
            <b-col>
              <b-col>
                <placeholder width="200" height="200" text="Лот"/>
              </b-col>
              {{ lot.name }} за {{ lot.price }} рублей
              <p>Выставлено пользователем {{ lot.owner.username }}</p>
              <p v-if="lot.bidder !== null">Выкупается пользователем {{ lot.bidder.username }}</p>
            </b-col>
          </router-link>
          <b-form @submit.prevent="handleBid(lot.id)">
            <b-form-input placeholder="Введите значение шага ставки" type="number" :id="'повышение' + lot.id"/>
            <b-alert :show="showInputError[lot.id]" variant="danger" dismissible>{{formMessage}}</b-alert>
            <b-button class="mt-3" block type="submit" variant="info">Повысить</b-button>
          </b-form>
          <b-alert variant="danger" v-if="errorMessage">{{errorMessage}}</b-alert>
        </b-list-group-item>
      </b-list-group>
    </b-container>
  </div>
</template>

<script>
import HeaderMenu from './HeaderMenu'
import Placeholder from './Placeholder'
import { ValidationProvider } from 'vee-validate'
import Lot from '../models/lot'
import User from '../models/user'
import Bid from '../models/bid'
import LotsService from '../services/lots.service'
import UserService from '../services/user.service'

export default {
  name: 'lots-list',
  components: {
    HeaderMenu,
    Placeholder,
    ValidationProvider
  },
  data () {
    return {
      listOfLots: [],
      startId: 0,
      loadCount: 5,
      bid: new Bid(0, 0),
      submitted: false,
      submitId: -1,
      errorMessage: '',
      formMessage: 'Число должно быть больше единицы',
      showInputError: [false]
    }
  },
  mounted () {
    // Get lots information
    LotsService.getLots().then(
      response => {
        let array = response.data['lots']
        console.log(array)
        let parsedArray = []
        for (const lot in array) {
          parsedArray.push(JSON.parse(array[lot]))
        }
        console.log(parsedArray)
        for (const parsedLot in parsedArray) {
          let newLot = new Lot(parsedArray[parsedLot].id, '/lot/' + parsedArray[parsedLot].id,
            parsedArray[parsedLot].name, '', parsedArray[parsedLot].price, new Bid(parsedArray[parsedLot].id, 0))
          UserService.getUserById(parsedArray[parsedLot].ownerID).then(
            response => {
              let owner = JSON.parse(response.data.user)
              // Get bidder information if a bidder exists
              if (parsedArray[parsedLot].bidderID !== null) {
                UserService.getUserById(parsedArray[parsedLot].bidderID).then(
                  response => {
                    let bidder = JSON.parse(response.data.user)
                    newLot.setBidder(new User(bidder.name, bidder.surname, bidder.username, bidder.email))
                  }
                )
              }
              newLot.setOwner(new User(owner.name, owner.surname, owner.username, owner.email))
            }
          )
          this.listOfLots.push(newLot)
        }
      },
      error => {
        this.listOfLots.push(error.response.data.message)
      }
    )
  },
  methods: {
    handleBid (lotId) {
      this.submitted = true
      this.submitId = lotId
      let bidValue = document.getElementById('повышение' + lotId).value
      console.log(bidValue)
      this.showInputError[lotId] = lotId !== this.submitId || bidValue <= 0
      console.log(this.showInputError[lotId])
      if (this.showInputError[lotId]) {

      } else {
        console.log('Tried to bid')
        console.log(this.bid)
        this.bid.priceIncrease = bidValue
        this.bid.lotId = lotId
        if (this.bid.priceIncrease) {
          this.$store.dispatch('lots/bid', this.bid).then(
            () => {
              this.$router.go(0)
            },
            error => {
              this.errorMessage = error.message
            }
          )
        }
      }
    }
  }
}
</script>

<style>
/*.lots-list{*/
/*  list-style-type: none;*/
/*}*/
/*.lots-list-item{*/
/*  text-align: left;*/
/*  display: block;*/
/*  padding-bottom: 30px;*/
/*}*/
</style>

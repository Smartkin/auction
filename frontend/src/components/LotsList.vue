<template>
  <div class='lots-list'>
    <LotCard
      v-for="lot in listOfLots"
      :key="lot.id"
      variant="dark"
      :lot-id="lot.id"
      :lot-name="lot.name"
      :lot-owner="lot.owner ? lot.owner.username : ''"
      :price="lot.price.toString()"
      :stomp-client="stompClient"
      :server-connected="connectionEstablished"
      :lot-bidder="lot.bidder ? lot.bidder.username : ''">
    </LotCard>
  </div>
</template>

<script>
import HeaderMenu from './HeaderMenu'
import Placeholder from './Placeholder'
import LotCard from './LotCard'
import Lot from '../models/lot'
import User from '../models/user'
import Bid from '../models/bid'
import LotsService from '../services/lots.service'
import UserService from '../services/user.service'
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'

export default {
  name: 'lots-list',
  components: {
    HeaderMenu,
    Placeholder,
    LotCard
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
      showInputError: [false],
      stompClient: null,
      connectionEstablished: false
    }
  },
  mounted () {
    let socket = new SockJS('/auction-websocket')
    console.log(socket)
    this.stompClient = Stomp.over(socket)
    console.log(this.stompClient)
    this.stompClient.connect({}, frame => {
      console.log(frame)
      for (let i in this.listOfLots) {
        this.stompClient.subscribe('/lots/priceChange/' + this.listOfLots[i].id, newPrice => {
          console.log(newPrice)
          this.listOfLots[i].price = JSON.parse(newPrice.body).newPrice
        })
      }
      this.connectionEstablished = true
    })
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
  beforeDestroy () {
    console.log('Disconnecting from the server')
    for (let i in this.listOfLots) {
      this.stompClient.unsubscribe('/lots/priceChange/' + this.listOfLots[i].id)
    }
    this.stompClient.disconnect()
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

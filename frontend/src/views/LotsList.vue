<template>
  <div class='lots-list'>
    <v-container fluid>
      <v-row dense>
        <v-col cols="2">
          <v-label>Количество лотов на странице</v-label>
          <v-overflow-btn
            label="Количество лотов на странице"
            :items="lotsAmt"
            v-model="loadAmount"
            @input="switchPage"
            auto-select-first
            dense
          />
        </v-col>
        <v-col cols="10">
          <LotCard
            class="mx-auto mb-3"
            v-for="lot in listOfLots"
            :key="lot.id"
            variant="dark"
            :lot-id="lot.id"
            :lot-name="lot.name"
            :lot-owner="lot.owner"
            :lot-description="lot.description"
            :price="lot.price.toString()"
            :server-connected="serverConnected"
            :lot-bidder="lot.bidder === '' ? 'никем' : lot.bidder">
          </LotCard>
          <v-pagination @input="switchPage" v-model="curPage" color="primary" total-visible="10" :length="pages"/>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import Placeholder from '../components/Placeholder'
import LotCard from '../components/LotCard'
import Lot from '../models/lot'
import Bid from '../models/bid'
import WebsockSubLink from '../models/WebsockSubLink'
import LotsService from '../services/lots.service'

export default {
  name: 'lots-list',
  components: {
    Placeholder,
    LotCard
  },
  data () {
    return {
      lotsAmt: [5, 10, 15, 20],
      listOfLots: [],
      startId: 0,
      loadAmount: 5,
      bid: new Bid(0, 0),
      submitted: false,
      submitId: -1,
      errorMessage: '',
      formMessage: 'Число должно быть больше единицы',
      showInputError: [false],
      pages: 1,
      curPage: 1
    }
  },
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    },
    serverConnected () {
      if (this.$store.state.lots.status.serverConnected) {
        this.subscribeToLots(true)
      }
      return this.$store.state.lots.status.serverConnected
    }
  },
  mounted () {
    // Get lots information
    this.switchPage()
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
            () => {},
            error => {
              this.errorMessage = error.message
            }
          )
        }
      }
    },
    lotBidder (lotId) {
      if (this.listOfLots[lotId]) {
        if (this.listOfLots[lotId].bidder && this.currentUser) {
          return this.listOfLots[lotId].bidder === this.currentUser ? 'Вами' : this.listOfLots[lotId].bidder
        }
        if (this.listOfLots[lotId].bidder) {
          return this.listOfLots[lotId].bidder
        }
      }
      return 'никем'
    },
    subscribeToLots (connectStatus) {
      if (connectStatus) {
        console.log('Subscribing to things')
        for (let i = 0; i < this.listOfLots.length; ++i) {
          this.$store.dispatch('lots/subscribe',
            new WebsockSubLink('/lots/dataChange/' + this.listOfLots[i].id,
              newLotData => {
                console.log(newLotData)
                let lotData = JSON.parse(newLotData.body)
                console.log(lotData)
                this.listOfLots[i].bidder = lotData.bidder
                this.listOfLots[i].price = lotData.price
                console.log(this.listOfLots[i].bidder)
              }))
        }
      }
    },
    switchPage () {
      this.listOfLots.length = 0
      this.pages = 1
      LotsService.getLots(this.curPage, 'Букинистические антикварные книги', this.loadAmount).then(
        response => {
          console.log(response)
          let array = JSON.parse(response.data.lots)
          this.pages = response.data.pages
          console.log(array)
          for (const arrayKey in array) {
            let bidderName = ''
            if (array[arrayKey].bidder !== null) {
              // Since dependencies are bidirectional instead of an object we can get an index
              // Find the required object
              console.log(array[arrayKey])
              console.log(array[arrayKey].bidder)
              if (Number.isInteger(array[arrayKey].bidder)) {
                let neededLot = array.find(obj => {
                  if (obj.bidder !== null) {
                    return obj.bidder.id === array[arrayKey].bidder
                  }
                  return false
                })
                console.log(array)
                console.log(neededLot)
                bidderName = neededLot.bidder.username
              } else {
                // Otherwise just fetch the username
                bidderName = array[arrayKey].bidder.username
              }
            }
            let newLot = new Lot(array[arrayKey].id, '/lot/' + array[arrayKey].id,
              array[arrayKey].name, array[arrayKey].owner.username, array[arrayKey].price,
              new Bid(array[arrayKey].id, 0), bidderName,
              array[arrayKey].description)
            this.listOfLots.push(newLot)
          }
          this.subscribeToLots(this.serverConnected)
        },
        error => {
          this.listOfLots.push(error.response.data.message)
        }
      )
    }
  }
}
</script>

<template>
  <div class='lots-list'>
    <v-container fluid>
      <v-row dense>
        <v-col md="2" cols="12">
          <v-label>Количество лотов на странице</v-label>
          <v-overflow-btn
            label="Количество лотов на странице"
            :items="lotsAmt"
            v-model="loadAmount"
            @input="onLotAmtOnPage"
            auto-select-first
            dense
          />
        </v-col>
        <v-col md="10" cols="12">
          <v-list style="max-height: 700px" class="overflow-y-auto">
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
              :lot-bidder="lot.bidder === '' ? 'никем' : lot.bidder"
              :page-loading="loading"
              :start-date="lot.startDate"
              :end-date="lot.endDate"
              :images="lot.images"
            />
          </v-list>
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
      curPage: 1,
      loading: false
    }
  },
  props: {
    category: {
      type: String,
      default: 'all'
    },
    parentParams: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  watch: {
    category: function () {
      console.log('Switched lots list category')
      this.switchPage()
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
    },
    resultCategory: {
      get: function () {
        return this.category
      },
      set: function (newCategory) {
        console.log('Received new category: ' + newCategory)
        this.category = newCategory
      }
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
    onLotAmtOnPage (newLotAmt) {
      this.switchPage()
    },
    switchPage (toPage = 1) {
      console.log(toPage)
      console.log(this.category)
      this.loading = true
      this.curPage = toPage
      let multipleType = 'all'
      if (this.category !== 'all') {
        multipleType = 'categories'
      }
      let params = {
        page: this.parentParams.toPage ? this.parentParams.toPage : toPage,
        category: this.parentParams.category ? this.parentParams.category : this.category,
        amount: this.parentParams.amount ? this.parentParams.amount : this.loadAmount,
        multipleType: this.parentParams.multipleType ? this.parentParams.multipleType : multipleType,
        owner: this.parentParams.owner,
        bidder: this.parentParams.bidder
      }
      if (params.category !== 'all' && params.category !== 'Все') {
        if (params.owner) {
          params.multipleType = 'categories&owner'
        } else if (params.bidder) {
          params.multipleType = 'categories&bidder'
        }
      } else {
        params.category = this.parentParams.hasOwnProperty('multipleType') ? null : 'Все'
      }
      console.log(params.category)
      LotsService.getLots(params).then(
        response => {
          this.listOfLots.length = 0
          console.log(response)
          let array = JSON.parse(response.data.lots)
          this.pages = response.data.pages
          console.log(array)
          for (const arrayKey in array) {
            let bidderName = ''
            if (array[arrayKey].bidder !== null) {
              bidderName = array[arrayKey].bidder.username
            }
            let newLot = new Lot(array[arrayKey].id, '/lot/' + array[arrayKey].id,
              array[arrayKey].name, array[arrayKey].owner.username, array[arrayKey].price,
              new Bid(array[arrayKey].id, 0), bidderName,
              array[arrayKey].description, array[arrayKey].startDate,
              array[arrayKey].endDate, array[arrayKey].categories,
              array[arrayKey].lotImages)
            this.listOfLots.push(newLot)
          }
          this.subscribeToLots(this.serverConnected)
          this.loading = false
        },
        error => {
          this.listOfLots.push(error.response.data.message)
        }
      )
    }
  }
}
</script>

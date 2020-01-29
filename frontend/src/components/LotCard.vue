<template>
  <v-card :loading="!serverConnected || pageLoading" :disabled="!serverConnected || pageLoading" min-width="200" color="white">
    <v-progress-linear slot="progress" indeterminate height="10" color="primary"/>
    <v-img height="200px"
           alt="placeholder"
           src="../assets/placeholder.png"
           class="align-end"
           v-if="images ? images.length === 0 : !images"
    >
      <v-card-title>{{ lotName }}</v-card-title>
    </v-img>
    <v-card
      color="black"
      dark
      flat
      tile
      v-if="images && images.length !== 0"
    >
      <v-window continuous v-model="onboarding">
        <v-window-item
          v-for="(image, n) in images"
          :key="n"
        >
          <v-img
            min-height="500px"
            max-height="500px"
            aspect-ratio="720/1280"
            contain
            class="align-end"
            lazy-src="../assets/placeholder.png"
            :src="'/api/images?id=' + image.id"
          >
            <v-card-title>{{ lotName }}</v-card-title>
          </v-img>
        </v-window-item>
      </v-window>
      <v-card-actions class="justify-space-between">
        <v-btn
          text
          @click="prev"
        >
          <v-icon>mdi-chevron-left</v-icon>
        </v-btn>
        <v-item-group
          v-model="onboarding"
          class="text-center"
          mandatory
        >
          <v-item
            v-for="n in length"
            :key="`btn-${n}`"
            v-slot:default="{ active, toggle }"
          >
            <v-btn
              :input-value="active"
              icon
              @click="toggle"
            >
              <v-icon>mdi-record</v-icon>
            </v-btn>
          </v-item>
        </v-item-group>
        <v-btn
          text
          @click="next"
        >
          <v-icon>mdi-chevron-right</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
    <v-card-subtitle>
      Выставлено пользователем <router-link style="text-decoration: none; color: blue" to="/lots_list">{{ lotOwner }}</router-link>
    </v-card-subtitle>
    <v-card-text>
      <div class="text--primary mb-3" style="font-size: 18px">{{ lotDescription }}</div>
      <div class="text--primary headline">Оставшееся время: <span class="warning--text">{{ getTimeLeft }}</span></div>
      <div class="text--primary headline">Выкупается <span class="primary--text"> {{ lotBidder ? lotBidder : 'никем' }}</span></div>
      <div class="text--primary headline">Цена лота: <span class="accent--text"> {{ newPrice ? newPrice : price }} ₽</span></div>
    </v-card-text>
    <v-card-actions>
      <validation-provider name="Шаг" rules="required|min_value:1" v-slot="{ errors, valid }">
        <v-container fluid>
          <v-row>
            <v-text-field
              type="number"
              label="Шаг повышения"
              outlined
              color="primary"
              v-model="priceIncrease"
              :success="valid"
              :error-messages="errors"
              required
              />
            <v-btn block color="primary" class="mb-3" @click="handleBid" :loading="loading" :disabled="!valid">
              Повысить цену
            </v-btn>
          </v-row>
        </v-container>
      </validation-provider>
    </v-card-actions>
    <v-alert v-if="bidMessage !== ''" type="info" dismissible>
      {{ bidMessage }}
    </v-alert>
    <v-alert v-if="errorAlert" type="error" dense dismissible>
      {{ errorMessage }}
    </v-alert>
  </v-card>
</template>

<script>
import Bid from '../models/bid'
import { ValidationProvider } from 'vee-validate'

export default {
  components: {
    ValidationProvider
  },
  props: {
    lotId: {
      type: Number,
      required: true
    },
    lotName: {
      type: String,
      required: true
    },
    lotDescription: {
      type: String,
      required: false,
      default: 'Здесь описание лота.'
    },
    lotOwner: {
      type: String,
      required: true
    },
    images: {
      type: Array,
      required: false
    },
    price: {
      type: String,
      required: true
    },
    lotBidder: {
      type: String,
      required: false
    },
    serverConnected: {
      type: Boolean,
      required: true
    },
    pageLoading: {
      type: Boolean,
      default: false
    },
    startDate: {
      type: Date,
      required: true
    },
    endDate: {
      type: Date,
      required: true
    }
  },
  data () {
    return {
      priceIncrease: null,
      loading: false,
      errorMessage: '',
      bidMessage: '',
      errorAlert: false,
      newPrice: 0,
      timeLeft: 0,
      interval: null,
      onboarding: 0,
      length: 0
    }
  },
  methods: {
    handleBid () {
      this.loading = true
      this.errorAlert = false
      this.bidMessage = ''
      let bid = new Bid(this.lotId, this.priceIncrease)
      this.$store.dispatch('lots/bid', bid).then(
        response => {
          this.loading = false
          console.log(response)
          this.$store.dispatch('lots/send', {
            link: '/auction/lotPrice/' + this.lotId,
            payloadString: JSON.stringify({}),
            headers: {}
          })
          this.bidMessage = response.message
        },
        error => {
          this.errorMessage = error.message
          this.errorAlert = true
          this.loading = false
        }
      )
    },
    setTimeLeft (endDate) {
      let now = new Date().getTime()
      let epoch = endDate.getTime() - now
      let leftSeconds = new Date(epoch).getTime() / 1000
      let hh = '' + Math.floor(leftSeconds / 3600)
      let dd = '' + Math.floor(hh / 24)
      let mm = '' + Math.floor(leftSeconds / 60 % 60)
      let ss = '' + Math.floor(leftSeconds % 60)
      let hhStr = '' + (hh % 24)
      if (mm.length === 1) {
        mm = '0' + mm
      }
      if (ss.length === 1) {
        ss = '0' + ss
      }
      if (hhStr.length === 1) {
        hhStr = '0' + hhStr
      }
      this.timeLeft = (dd !== '0' ? dd + ' дней ' : '') + hhStr + ':' + mm + ':' + ss
    },
    next () {
      this.onboarding = this.onboarding + 1 === this.length
        ? 0
        : this.onboarding + 1
    },
    prev () {
      this.onboarding = this.onboarding - 1 < 0
        ? this.length - 1
        : this.onboarding - 1
    }
  },
  mounted () {
    if (this.images) {
      this.length = this.images.length
    }
    this.setTimeLeft(this.endDate)
    this.interval = setInterval(() => {
      this.setTimeLeft(this.endDate)
    }, 1000)
  },
  computed: {
    getTimeLeft () {
      return this.timeLeft
    }
  }
}
</script>

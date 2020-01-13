<template>
  <v-card :loading="!serverConnected" :disabled="!serverConnected" min-width="200" max-width="600" color="white">
    <v-progress-linear slot="progress" indeterminate height="10" color="primary"/>
    <v-img height="200px"
           alt="placeholder"
           src="../assets/placeholder.png"
           class="align-end">
      <v-card-title>{{ lotName }}</v-card-title>
    </v-img>
    <v-card-subtitle>
      Выставлено пользователем <router-link style="text-decoration: none; color: blue" to="/lots_list">{{ lotOwner }}</router-link>
    </v-card-subtitle>
    <v-card-text>
      <div class="text--primary mb-3">{{ lotDescription }}</div>
      <div class="text--primary">Выкупается {{ lotBidder ? lotBidder : 'никем' }}</div>
      <div class="text--primary headline">Цена лота: {{ newPrice ? newPrice : price }} ₽</div>
    </v-card-text>
    <v-card-actions>
      <validation-provider name="Шаг" rules="required|min_value:1" v-slot="{ errors, valid }">
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
        <v-btn color="primary" class="mb-3" @click="handleBid" :loading="loading" :disabled="!valid">
          Повысить цену
        </v-btn>
        <v-alert v-if="bidMessage !== ''" type="info" dismissible>
          {{ bidMessage }}
        </v-alert>
        <v-alert v-if="errorAlert" type="error" dense dismissible>
          {{ errorMessage }}
        </v-alert>
      </validation-provider>
    </v-card-actions>
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
    imagePath: {
      type: String,
      required: false,
      default: 'placeholder.png'
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
    }
  },
  data () {
    return {
      priceIncrease: null,
      loading: false,
      errorMessage: '',
      bidMessage: '',
      errorAlert: false,
      newPrice: 0
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
    }
  }
}
</script>

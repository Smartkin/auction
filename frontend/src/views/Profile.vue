<template>
  <v-container fluid class="profile">
    <v-row align="center">
      <v-col md="3" cols="12">
        <v-row align="stretch" justify="center" class="mb-3">
          <placeholder type="user" v-if="avatarUrl === null"/>
          <v-img contain height="500px" :src="avatarUrl" v-if="avatarUrl !== null" alt=""/>
        </v-row>
        <v-row>
          <v-file-input
            single-line
            show-size
            filled
            counter
            dense
            full-width
            placeholder="Изменить аватар..."
            accept=".jpeg,.png"
            prepend-icon="mdi-account-box"
            @change="uploadAvatar"
            v-model="uploadedAvatar"
          />
        </v-row>
        <v-row justify="center" v-if="avatarChangedMessage !== ''">
          <v-alert
            type="info"
            dismissible
          >{{ avatarChangedMessage }}</v-alert>
        </v-row>
      </v-col>
      <v-col md="9" cols="12">
        <v-row>
          <v-col>
            <v-card class="ml-9" tile>
              <v-card-text class="text-right">
                <v-row>
                  <p>
                    <strong>Полное имя: </strong>
                    {{ currentUser.data.surname + ' ' + currentUser.data.name }}
                  </p>
                </v-row>
                <v-row>
                  <p>
                    <strong>Логин: </strong>
                    {{ currentUser.data.username }}
                  </p>
                </v-row>
                <v-row>
                  <p>
                    <strong>Адрес: </strong>
                    {{ currentUser.addr ? currentUser.addr : 'Не указан' }}
                  </p>
                </v-row>
                <v-row>
                  <p>
                    <strong>E-Mail: </strong>
                    {{ currentUser.data.email }}
                  </p>
                </v-row>
              </v-card-text>
            </v-card>
            <v-row justify="start" class="mx-auto mt-3 ml-9">
              <v-btn small block :color="color" depressed>
                <v-icon>mdi-account</v-icon>
                Редактировать данные
              </v-btn>
            </v-row>
          </v-col>
        </v-row>
      </v-col>
      <v-col>
        <v-tabs
          grow
          centered
          v-model="tab"
        >
          <v-tabs-slider/>
          <v-tab>
            <v-icon class="mr-3">mdi-briefcase</v-icon>
            Мои лоты
          </v-tab>
          <v-tab>
            <v-icon class="mr-3">mdi-coin</v-icon>
            Мои ставки
          </v-tab>
          <v-tabs-items v-model="tab">
            <v-tab-item>
              <v-list style="max-height: 800px" class="overflow-y-auto">
                <lots-list
                  :category="category"
                  :parent-params="{ owner: this.currentUser.data.username, multipleType: 'owner' }"
                />
              </v-list>
            </v-tab-item>
            <v-tab-item>
              <v-list style="max-height: 800px" class="overflow-y-auto">
                <lots-list
                  :category="category"
                  :parent-params="{ bidder: this.currentUser.data.username, multipleType: 'bidder' }"
                />
              </v-list>
            </v-tab-item>
          </v-tabs-items>
        </v-tabs>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Placeholder from '../components/Placeholder'
import UserService from '../services/user.service'
import LotCard from '../components/LotCard'
import LotsList from './LotsList'

export default {
  name: 'profile',
  components: {LotsList, LotCard, Placeholder},
  data () {
    return {
      color: 'primary',
      tab: 0,
      ownerLots: [],
      bidLots: [],
      avatarUrl: null,
      uploadedAvatar: null,
      avatarChangedMessage: ''
    }
  },
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    }
  },
  mounted () {
    if (!this.currentUser) {
      this.$router.push('/login')
      return
    }
    // Avoid requesting placeholder images
    if (this.currentUser.data.avatar.id !== 1 && this.currentUser.data.avatar.id !== 2) {
      this.avatarUrl = '/api/images?id=' + this.currentUser.data.avatar.id
    }
  },
  props: {
    category: {
      type: String,
      default: 'all'
    }
  },
  methods: {
    uploadAvatar (image) {
      console.log(image)
      this.avatarChangedMessage = ''
      if (image !== null) {
        UserService.uploadAvatar(image).then(response => {
          this.avatarChangedMessage = response.message
          this.$store.dispatch('auth/fetchUserData', this.currentUser)
        })
      }
    }
  }
}
</script>

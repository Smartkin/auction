<template>
  <v-container fluid class="profile">
    <v-row>
      <v-col md="5" cols="12">
        <v-row align="stretch" justify="center" class="mb-3">
          <placeholder/>
        </v-row>
        <v-row align="stretch" justify="space-between">
          <v-btn small :color="color" depressed>
            <v-icon>mdi-account-box</v-icon>
            Изменить аватар
          </v-btn>
          <v-btn small :color="color" depressed>
            <v-icon>mdi-briefcase</v-icon>
            Мои лоты
          </v-btn>
          <v-btn small :color="color" depressed>
            <v-icon>mdi-coin</v-icon>
            Мои ставки
          </v-btn>
        </v-row>
      </v-col>
      <v-col align-self="center">
        <v-row>
          <v-col cols="3">
            <v-card>
              <v-card-text class="text-right">
                <p>
                  <strong>Полное имя</strong>
                </p>
                <p>
                  <strong>Логин</strong>
                </p>
                <p>
                  <strong>Адрес</strong>
                </p>
                <p>
                  <strong>Баланс</strong>
                </p>
                <p>
                  <strong>E-Mail</strong>
                </p>
              </v-card-text>
            </v-card>
          </v-col>
          <v-col cols="9">
            <v-card max-width="300px">
              <v-card-text class="text--primary">
                <p>
                  {{ currentUser.surname + ' ' + currentUser.name }}
                </p>
                <p>
                  {{ currentUser.username }}
                </p>
                <p>
                  {{ currentUser.addr ? currentUser.addr : 'Не указан' }}
                </p>
                <v-row justify="start">
                  <p class="ml-3 mr-3">
                    {{ currentUser.balance ? currentUser.balance : '0.00 ₽' }}
                  </p>
                  <v-btn small depressed :color="color">
                    <v-icon>mdi-wallet</v-icon>
                    Пополнить баланс
                  </v-btn>
                </v-row>
                <p>
                  {{ currentUser.email }}
                </p>
              </v-card-text>
            </v-card>
            <v-row justify="start" class="mx-auto mt-3">
              <v-btn small :color="color" depressed>
                <v-icon>mdi-account</v-icon>
                Редактировать профиль
              </v-btn>
            </v-row>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Placeholder from './Placeholder'
export default {
  name: 'profile',
  components: {Placeholder},
  data () {
    return {
      color: 'primary'
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
    }
  }
}
</script>

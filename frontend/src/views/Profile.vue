<template>
  <v-container fluid class="profile">
    <v-row align="center">
      <v-col md="5" cols="12">
        <v-row align="stretch" justify="center" class="mb-3">
          <placeholder type="user"/>
        </v-row>
        <v-row align="stretch" justify="space-between">
          <v-col>
            <v-btn block small :color="color" depressed>
              <v-icon>mdi-account-box</v-icon>
              Изменить аватар
            </v-btn>
          </v-col>
          <v-col>
            <v-btn block small :color="color" depressed>
              <v-icon>mdi-briefcase</v-icon>
              Мои лоты
            </v-btn>
          </v-col>
          <v-col>
            <v-btn block small :color="color" depressed>
              <v-icon>mdi-coin</v-icon>
              Мои ставки
            </v-btn>
          </v-col>
        </v-row>
      </v-col>
      <v-col>
        <v-row>
          <v-col>
            <v-card tile>
              <v-card-text class="text-right">
                <v-row>
                  <p>
                    <strong>Полное имя: </strong>
                    {{ currentUser.surname + ' ' + currentUser.name }}
                  </p>
                </v-row>
                <v-row>
                  <p>
                    <strong>Логин: </strong>
                    {{ currentUser.username }}
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
                    {{ currentUser.email }}
                  </p>
                </v-row>
              </v-card-text>
            </v-card>
            <v-row justify="start" class="mx-auto mt-3">
              <v-btn small block :color="color" depressed>
                <v-icon>mdi-account</v-icon>
                Редактировать данные
              </v-btn>
            </v-row>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Placeholder from '../components/Placeholder'
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

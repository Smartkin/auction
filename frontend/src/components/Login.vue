<template>
  <div class="login">
    <v-card :loading="loading" :disabled="loading" class="elevation-5 mx-auto" color="blue-grey lighten-3" min-width="200" max-width="500">
      <v-progress-linear slot="progress" indeterminate height="10" color="info"/>
      <div class="mx-auto text-center title">Вход в личный кабинет</div>
      <v-card-text>
        <validation-observer ref="loginForm" v-slot="{ invalid, validated, passes, validate }">
          <v-form name="form" @submit.prevent="handleLogin">
            <validation-provider name="Логин" rules="required" v-slot="{ errors, valid }">
              <v-text-field
                type="text"
                class="mx-auto"
                label="Логин"
                v-model="user.username"
                :error-messages="errors"
                :success="valid"
              />
            </validation-provider>
            <validation-provider name="Пароль" rules="required" v-slot="{ errors, valid }">
              <v-text-field
                type="password"
                name="password"
                label="Пароль"
                v-model="user.password"
                :error-messages="errors"
                :success="valid"
              />
            </validation-provider>
            <v-card-actions>
              <v-btn type="submit" color="primary" :disabled="invalid">
                Войти
              </v-btn>
              <v-btn color="info" to="/register">
                Регистрация
              </v-btn>
            </v-card-actions>
            <div class="alert alert-danger" role="alert" v-if="message">{{message}}</div>
          </v-form>
        </validation-observer>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import User from '../models/user'
import { ValidationProvider, ValidationObserver } from 'vee-validate'

export default {
  name: 'login',
  components: {
    ValidationProvider,
    ValidationObserver
  },
  data () {
    return {
      user: new User('', ''),
      loading: false,
      message: ''
    }
  },
  computed: {
    loggedIn () {
      return this.$store.state.auth.status.loggedIn
    }
  },
  mounted () {
    if (this.loggedIn) {
      this.$router.push('/profile')
    }
  },
  methods: {
    handleLogin () {
      this.loading = true
      this.$store.dispatch('auth/login', this.user).then(
        () => {
          this.$router.push('/')
        },
        error => {
          this.loading = false
          console.log(error)
          this.message = error.message
        }
      )
    }
  }
}
</script>

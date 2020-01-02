<template>
  <div class="login">
    <header-menu active-panel="login"/>
    <div class="card card-container">
      <validation-observer ref="loginForm" v-slot="{ invalid, validated, passes, validate }">
        <v-form name="form" @submit.prevent="handleLogin">
          <validation-provider name="Логин" rules="required" v-slot="{ errors, valid }">
            <v-text-field
              type="text"
              class="form-control"
              label="Логин"
              v-model="user.username"
              :error-messages="errors"
              :success="valid"
              required
            />
          </validation-provider>
          <validation-provider name="Пароль" rules="required" v-slot="{ errors, valid }">
            <v-text-field
              type="password"
              class="form-control"
              name="password"
              label="Пароль"
              v-model="user.password"
              :error-messages="errors"
              :success="valid"
              required
            />
          </validation-provider>
          <v-btn type="submit" color="primary" :disabled="invalid" :loading="loading">
            Войти
          </v-btn>
          <div class="alert alert-danger" role="alert" v-if="message">{{message}}</div>
        </v-form>
      </validation-observer>
      <v-btn color="info" class="btn btn-primary btn-block" to="/register" :loading="loading">
        Регистрация
      </v-btn>
    </div>
  </div>
</template>

<script>
import HeaderMenu from './HeaderMenu'
import User from '../models/user'
import { ValidationProvider, ValidationObserver } from 'vee-validate'

export default {
  name: 'login',
  components: {
    HeaderMenu,
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
      this.$router.push('/')
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

<style scoped>
/*label {*/
/*  display: block;*/
/*  margin-top: 10px;*/
/*}*/

/*.card-container.card {*/
/*  max-width: 350px !important;*/
/*  padding: 40px 40px;*/
/*}*/

/*.card {*/
/*  background-color: #e1e1e1;*/
/*  padding: 20px 25px 30px;*/
/*  margin: 50px auto 25px;*/
/*  -moz-border-radius: 2px;*/
/*  -webkit-border-radius: 2px;*/
/*  border-radius: 2px;*/
/*  -moz-box-shadow: 0px 2px 2px rgba(255, 255, 255, 0.3);*/
/*  -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);*/
/*  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);*/
/*}*/
</style>

<template>
  <div class="registration">
    <v-card :loading="loading" :disabled="loading" class="elevation-5 mx-auto" color="blue-grey lighten-3" min-width="200" max-width="500">
      <v-progress-linear slot="progress" indeterminate height="10" color="info"/>
      <div class="text-center title">Регистрация</div>

        <validation-observer ref="registerForm" v-slot="{ invalid, validated, passes, validate }">
          <v-form>
            <v-card-text>
              <validated-text-field
                v-model="user.name"
                label="Имя*"
                field-name="имя"
                rules="required|min:3|max:32"
              />
              <validated-text-field
                v-model="user.surname"
                label="Фамилия*"
                field-name="фамилия"
                rules="required|min:3|max:32"
              />
              <validated-text-field
                v-model="user.username"
                label="Логин*"
                field-name="логин"
                rules="required|min:3|max:32"
              />
              <validated-text-field
                v-model="user.password"
                type="password"
                label="Пароль*"
                field-name="пароль"
                vid="passwordField"
                rules="required|min:6|max:128"
              />
              <validated-text-field
                v-model="repeatPassword"
                type="password"
                label="Повтор пароля*"
                field-name="повтор пароля"
                :rules="{ required: true, equalTo: ['@passwordField', 'пароль'] }"
              />
              <validated-text-field
                v-model="user.email"
                type="email"
                label="E-Mail*"
                field-name="E-Mail"
                rules="required|email|max:256"
              />
              <validation-provider name="" rules="agreeRequired" v-slot="{ errors, valid }">
                <v-checkbox
                  label="Я согласен с условиями пользовательского соглашения.*"
                  :error-messages="errors"
                  :success="valid"
                  v-model="agreed"
                  type="checkbox"
                  value="1"
                />
              </validation-provider>
              <p>* - поле обязательно для заполнения</p>
            </v-card-text>
            <v-card-actions>
              <v-btn color="primary" @click="passes(handleRegister)" :disabled="invalid">
                Зарегистрироваться
              </v-btn>
            </v-card-actions>
            <v-alert
              :type="successful ? 'success' : 'error'"
              v-model="showAlert"
              dismissible
            >{{message}}</v-alert>
          </v-form>
        </validation-observer>
    </v-card>
  </div>
</template>
<script>
import {ValidationObserver, ValidationProvider} from 'vee-validate'
import User from '../models/user'
import ValidatedTextField from './ValidatedTextField'

export default {
  name: 'register',
  computed: {
    loggedIn () {
      return this.$store.state.auth.status.loggedIn
    }
  },
  components: {
    ValidatedTextField,
    ValidationProvider,
    ValidationObserver
  },
  data () {
    return {
      user: new User('', '', '', '', ''),
      repeatPassword: '',
      submitted: false,
      successful: false,
      loading: false,
      agreed: '',
      message: 'Error test',
      showAlert: false
    }
  },
  mounted () {
    if (this.loggedIn) {
      this.$router.push('/profile')
    }
  },
  watch: {
    showAlert: function (newAlert, oldAlert) {
      if (!newAlert) {
        this.message = ''
      }
    }
  },
  methods: {
    handleRegister () {
      console.log(this.user)
      this.$store.dispatch('auth/register', this.user).then(
        data => {
          console.log(data)
          this.$store.dispatch('auth/login', this.user).then(
            () => {
              this.$router.push('/profile')
            },
            error => {
              this.loading = false
              console.log(error)
              this.message = error.message
            }
          )
          this.message = data.message
          this.showAlert = true
          this.successful = true
        },
        error => {
          console.log(error)
          this.message = error.message
          this.showAlert = true
          this.successful = false
        }
      )
    }
  }
}
</script>

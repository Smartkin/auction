<template>
  <div class="login">
    <header-menu active-panel="login"/>
    <div class="card card-container">
      <form name="form" @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="username">Username</label>
          <input
            type="text"
            class="form-control"
            name="username"
            v-model="user.username"
            v-validate="'required'"
          />
          <div
            class="alert alert-danger"
            role="alert"
            v-if="errors.has('username')"
          >Username is required!</div>
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input
            type="password"
            class="form-control"
            name="password"
            v-model="user.password"
            v-validate="'required'"
          />
          <div
            class="alert alert-danger"
            role="alert"
            v-if="errors.has('password')"
          >Password is required!</div>
        </div>
        <div class="form-group">
          <button class="btn btn-primary btn-block" :disabled="loading">
            <span class="spinner-border spinner-border-sm" v-show="loading"/>
            <span>Login</span>
          </button>
        </div>
        <div class="form-group">
          <div class="alert alert-danger" role="alert" v-if="message">{{message}}</div>
        </div>
      </form>
      <router-link to="/register">
        <button class="btn btn-primary btn-block" :disabled="loading">
          <span class="spinner-border spinner-border-sm" v-show="loading"/>
          <span>Регистрация</span>
        </button>
      </router-link>
    </div>
  </div>
</template>

<script>
import HeaderMenu from './HeaderMenu'
import User from '../models/user'

export default {
  name: 'login',
  components: {
    HeaderMenu
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
      this.$validator.validateAll()

      if (this.errors.any()) {
        this.loading = false
        return
      }

      if (this.user.username && this.user.password) {
        this.$store.dispatch('auth/login', this.user).then(
          () => {
            this.$router.push('/')
          },
          error => {
            this.loading = false
            this.message = error.message
          }
        )
      }
    }
  }
}
</script>

<style scoped>
label {
  display: block;
  margin-top: 10px;
}

.card-container.card {
  max-width: 350px !important;
  padding: 40px 40px;
}

.card {
  background-color: #f7f7f7;
  padding: 20px 25px 30px;
  margin: 50px auto 25px;
  -moz-border-radius: 2px;
  -webkit-border-radius: 2px;
  border-radius: 2px;
  -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
}
</style>

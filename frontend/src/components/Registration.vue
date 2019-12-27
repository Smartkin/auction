<template>
  <div class="registration">
    <header-menu active-panel="login"/>
    <div class="card card-container">
      <form name="form" @submit.prevent="handleRegister">
        <div v-if="!successful">
          <div class="form-group">
            <label for="name">Имя</label>
            <input
              type="text"
              class="form-control"
              name="имя"
              v-model="user.name"
              v-validate="'required|min:3|max:20'"
            />
            <div
              class="alert-danger"
              v-if="submitted && errors.has('имя')"
            >{{errors.first('имя')}}</div>
          </div>
          <div class="form-group">
            <label for="surname">Фамилия</label>
            <input
              type="text"
              class="form-control"
              name="фамилия"
              v-model="user.surname"
              v-validate="'required|min:3|max:20'"
            />
            <div
              class="alert-danger"
              v-if="submitted && errors.has('фамилия')"
            >{{errors.first('фамилия')}}</div>
          </div>
          <div class="form-group">
            <label for="username">Логин</label>
            <input
              type="text"
              class="form-control"
              name="логин"
              v-model="user.username"
              v-validate="'required|min:3|max:20'"
            />
            <div
              class="alert-danger"
              v-if="submitted && errors.has('логин')"
            >{{errors.first('логин')}}</div>
          </div>
          <div class="form-group">
            <label for="email">Email</label>
            <input
              type="email"
              class="form-control"
              name="email"
              v-model="user.email"
              v-validate="'required|email|max:50'"
            />
            <div
              class="alert-danger"
              v-if="submitted && errors.has('email')"
            >{{errors.first('email')}}</div>
          </div>
          <div class="form-group">
            <label for="password">Пароль</label>
            <input
              type="password"
              class="form-control"
              name="пароль"
              v-model="user.password"
              v-validate="'required|min:6|max:40'"
            />
            <div
              class="alert-danger"
              v-if="submitted && errors.has('пароль')"
            >{{errors.first('пароль')}}</div>
          </div>
          <div class="form-group">
            <button class="btn btn-primary btn-block">Зарегистрироваться</button>
          </div>
        </div>
      </form>

      <div
        class="alert"
        :class="successful ? 'alert-success' : 'alert-danger'"
        v-if="message"
      >{{message}}</div>
    </div>
  </div>
</template>
<script>
import HeaderMenu from './HeaderMenu'
import User from '../models/user'

export default {
  name: 'register',
  computed: {
    loggedIn () {
      return this.$store.state.auth.status.loggedIn
    }
  },
  components: { HeaderMenu },
  data () {
    return {
      user: new User('', '', '', '', ''),
      submitted: false,
      successful: false,
      message: ''
    }
  },
  mounted () {
    if (this.loggedIn) {
      this.$router.push('/profile')
    }
  },
  methods: {
    handleRegister () {
      this.message = ''
      this.submitted = true
      this.$validator.validate().then(valid => {
        if (valid) {
          this.$store.dispatch('auth/register', this.user).then(
            data => {
              this.message = data.message
              this.successful = true
            },
            error => {
              this.message = error.message
              this.successful = false
            }
          )
        }
      })
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
  background-color: #555555;
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

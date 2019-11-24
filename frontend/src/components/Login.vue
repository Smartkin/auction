<template>
  <div class="login">
    <header-menu active-panel="login"></header-menu>
    <p>Вход в профиль</p>
    <div class="success-login">
      <p v-if="success">Вход успешно выполнен!</p>
    </div>
    <div class="error-login">
      <p v-if="errorMsg !== ''">{{ errorMsg }}</p>
    </div>
    <form @submit.prevent="login">
      Email:<input v-model="email" type="email" id="emailInput">
      Пароль:<input v-model="password" type="password" id="passwordInput">
      <button type="submit">Войти</button>
    </form>
  </div>
</template>

<script>
import HeaderMenu from './HeaderMenu'
import axios from 'axios'

export default {
  data () {
    return {
      email: '',
      password: '',
      success: false,
      errorMsg: ''
    }
  },
  components: { HeaderMenu },
  methods: {
    login () {
      const mail = this.email
      const pass = this.password
      this.errorMsg = ''
      console.log('Entered mail: ' + mail)
      console.log('Entered password: ' + pass)
      axios.get('/api/login', {
        params: {
          email: mail,
          password: pass
        }
      }).then(response => {
        console.log(response.data)
        let data = response.data
        this.success = data['success']
        if (!this.success) {
          this.errorMsg = data['error']
        } else {
          this.$cookies.set('SESSION', data['sessionID'])
            .set('name', data['name'])
            .set('surname', data['surname'])
            .set('userID', data['userID'])
          this.$router.push('/')
        }
      })
    }
  }
}
</script>

<style>
input{
  border: none;
  outline: none;
  border-bottom: 2px solid red;
  background-color: #333333;
  transition: background-color 0.4s ease-in-out, border-bottom-color 0.4s ease-in-out;
  color: white;
}
input:hover{
  background-color: #111111;
}
input:focus{
  background-color: #519099;
  border-bottom: 2px solid #519099;
}
button{
  border: none;
  background-color: transparent;
  color: inherit;
  transition: background-color 0.1s ease-in-out;
  padding: 5px 5px;
}
button:hover{
  background-color: #111111;
}
</style>

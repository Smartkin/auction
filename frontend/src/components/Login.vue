<template>
  <div class="login">
    <header-menu active-panel="login"></header-menu>
    <p>Вход в профиль</p>
    <form>
      Email:<input type="email" id="emailInput">
      Пароль:<input type="password" id="passwordInput">
      <button @click="login">Войти</button>
    </form>
  </div>
</template>

<script>
import HeaderMenu from './HeaderMenu'
import axios from 'axios'

export default {
  components: { HeaderMenu },
  methods: {
    login () {
      
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

<template>
  <div class="contacts">
    <header-menu active-panel="profile"></header-menu>
    <p>
      Профиль
    </p>
    <ul>
      <li v-for="value in userData" v-bind:key="value.fullName">
        {{ value }}
      </li>
    </ul>
  </div>
</template>

<script>
import HeaderMenu from './HeaderMenu'

export default {
  name: 'profile',
  data () {
    return {
      userData: {
        fullName: this.$cookies.get('name') + ' ' + this.$cookies.get('surname')
      }
    }
  },
  components: {
    HeaderMenu
  }
}
</script>

<style>

</style>

<template>
  <div class="profile">
    <header-menu active-panel="profile"/>
    <header class="jumbotron">
      <h3>
        <strong>{{currentUser.username}}</strong> Профиль
      </h3>
    </header>
    <p>
      <strong>Email:</strong>
      {{currentUser.email}}
    </p>
  </div>
</template>

<script>
import HeaderMenu from './HeaderMenu'

export default {
  name: 'profile',
  components: {
    HeaderMenu
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

<style scoped>

</style>

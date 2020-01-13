<template>
  <div></div>
</template>

<script>
export default {
  data () {
    return {
      currentUser: this.$store.state.auth.user
    }
  },
  mounted () {
    this.$store.dispatch('auth/logout').then(
      () => {
        this.$router.push('/')
      }
    )
  }
}
</script>

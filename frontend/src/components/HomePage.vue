<template>
  <div class="hello">
    <header-menu active-panel="homePage"/>
    <p>Домашняя страница</p>
  </div>
</template>

<script>
import HeaderMenu from './HeaderMenu'

export default {
  name: 'HomePage',
  data () {
    return {
      msg: '',
      response: []
    }
  },
  components: {
    HeaderMenu
  }
}

</script>

<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>

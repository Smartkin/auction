<template>
  <div class="hello">
    <header-menu active-panel="homePage"/>
    <p>Домашняя страница</p>
  </div>
</template>

<script>
import axios from 'axios'
import HeaderMenu from './HeaderMenu'

export default {
  name: 'HomePage',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      response: []
    }
  },
  components: {
    HeaderMenu
  },
  methods: {
    callRestService () {
      axios.get('/api/hello')
        .then(response => {
          this.response = response.data
          this.msg = this.response['message']
        })
        .catch(e => {
          this.errors.push(e)
        })
    }
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
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

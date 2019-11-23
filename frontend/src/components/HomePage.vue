<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <button class="Search__button" @click="callRestService()">Say Hi!</button>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      response: [],
      errors: []
    }
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

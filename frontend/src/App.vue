<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
body {
  background-color: #333333;
}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #8bb4bb;
  margin-top: 60px;
}
a:link, a:visited {
  color: #8bb4bb;
  text-decoration: none;
}
a:hover, a:active {
  color: red;
}
img {
  width: 10%;
  height: auto;
}
</style>

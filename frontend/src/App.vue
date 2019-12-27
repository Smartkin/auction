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
  background-color: #d9d4d7;
}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #475c63;
  margin-top: 60px;
}
a:link, a:visited {
  color: #191e22;
  text-decoration: none;
}
a:hover, a:active {
  color: #920000;
}
img {
  width: 10%;
  height: auto;
}
</style>

<template>
  <v-img alt="placeholder" min-width="100" max-width="500" :src="imgSrc"/>
</template>

<script>
export default {
  name: 'placeholder',
  props: {
    type: {
      type: String,
      required: false,
      default: 'lot'
    }
  },
  data () {
    return {
      imgSrc: require('../assets/placeholder.png')
    }
  },
  mounted () {
    switch (this.type) {
      case 'lot':
        this.imgSrc = require('../assets/placeholder.png')
        break
      case 'user':
        this.imgSrc = require('../assets/avatars/placeholder.png')
        break
    }
  }
}
</script>

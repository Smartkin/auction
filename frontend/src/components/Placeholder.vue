<template>
  <img alt="placeholder" :src="url">
</template>

<script>
export default {
  name: 'placeholder',
  data () {
    return {
    }
  },
  computed: {
    url: function () {
      let theUrl = 'http://via.placeholder.com/' + this.width + 'x' + this.height
      if (this.bgcolor) theUrl += '/' + this.bgcolor
      if (this.textcolor) theUrl += '/' + this.textcolor
      if (this.text) theUrl += '?text=' + encodeURIComponent(this.text)
      return theUrl
    }
  },
  props: {
    height: {
      default: 400
    },
    width: {
      default: 400
    },
    text: {
      type: String,
      required: false
    },
    bgcolor: {
      type: String,
      default: 'cccccc',
      required: false
    },
    textcolor: {
      type: String,
      required: false
    }
  }
}
</script>

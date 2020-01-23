<template>
  <div class="lot">
    <placeholder width="200" height="200" text="Лот"/>
    <p>Лот с ИД: {{id}}</p>
  </div>
</template>

<script>
import Placeholder from '../components/Placeholder'

export default {
  components: {
    Placeholder
  },
  props: ['id'],
  data () {
    return {
    }
  }
}
</script>

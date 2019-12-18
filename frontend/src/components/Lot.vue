<template>
  <div class="lot">
    <HeaderMenu active-panel="lotsList"/>
    <placeholder width="200" height="200" text="Лот"/>
    <p>Лот с ИД: {{id}}</p>
  </div>
</template>

<script>
import HeaderMenu from './HeaderMenu'
import Placeholder from './Placeholder'

export default {
  components: {
    HeaderMenu,
    Placeholder
  },
  props: ['id'],
  data () {
    return {
    }
  }
}
</script>

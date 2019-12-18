<template>
  <div class="contacts">
    <header-menu active-panel="contacts"/>
    <h2>Работу выполняли</h2>
    <ul>
      <li>Смышляев Владислав - Ведущий Разработчик</li>
      <li>Кузнецов Александр - Разработчик</li>
      <li>Простомолотов Олег - Тестировщик</li>
      <li>Чернюк Александр - Аналитик</li>
    </ul>
  </div>
</template>

<script>
import HeaderMenu from './HeaderMenu'

export default {
  name: 'contacts',
  components: {
    HeaderMenu
  }
}
</script>

<style scoped>
ul {
  list-style-type: none;
}

li {
  font-size: 2em;
}
</style>

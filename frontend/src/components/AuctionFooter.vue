<template>
  <v-footer color="white--text transparent" app padless inset height="40px">
    <v-col class="text-center">
      <v-btn color="info" class="mt-n1" to="/help" block>
        Помощь
      </v-btn>
    </v-col>
    <v-col class="text-center black--text" v-if="$vuetify.breakpoint.smAndUp">
      2019-{{ new Date().getFullYear() }} — <strong>Античный Торговец</strong>
    </v-col>
    <v-col class="text-center" v-if="$vuetify.breakpoint.mdAndUp">
      <v-btn color="info" class="mt-n1" block to="/use_policy">
        Условия использования сервиса
      </v-btn>
    </v-col>
  </v-footer>
</template>

<script>
export default {}
</script>

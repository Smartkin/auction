<template>
  <div class="home">
    <v-card
      color="black"
      dark
      flat
      tile
    >
      <v-window v-model="onboarding">
        <v-window-item
          v-for="(item, n) in items"
          :key="n"
        >
          <v-card
            height="700"
            img="../assets/home_page_back.png"
          >
            <v-card
              style="background-color: rgba(0, 0, 0, 0.5)"
              class="fill-height"
            >
              <v-row
                align="center"
                justify="center"
                style="font-size: 4em;"
                tag="v-card-title"
              >
                {{ item.title_name }}
              </v-row>
              <v-row
                class="fill-height"
                align="center"
                justify="center"
                tag="v-card-text"
                style="font-size: 24px;"
              >
                {{ item.description }}
              </v-row>
            </v-card>
          </v-card>
        </v-window-item>
      </v-window>
      <v-card-actions class="justify-space-between">
        <v-btn
          text
          @click="prev"
        >
          <v-icon>mdi-chevron-left</v-icon>
        </v-btn>
        <v-item-group
          v-model="onboarding"
          class="text-center"
          mandatory
        >
          <v-item
            v-for="n in length"
            :key="`btn-${n}`"
            v-slot:default="{ active, toggle }"
          >
            <v-btn
              :input-value="active"
              icon
              @click="toggle"
            >
              <v-icon>mdi-record</v-icon>
            </v-btn>
          </v-item>
        </v-item-group>
        <v-btn
          text
          @click="next"
        >
          <v-icon>mdi-chevron-right</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
export default {
  name: 'HomePage',
  data () {
    return {
      onboarding: 0,
      length: 3,
      items: [
        {
          title_name: 'Античный торговец',
          description: 'Аукцион антикварных вещей, где Вы и пользователи по всей России могут выставлять исторические вещи на продажу!'
        },
        {
          title_name: 'В кратце о нас',
          description: 'В нашем аукционе Вы можете безопасно совершать сделки и выстовлять свои товары. Просто заполните форму и после подтверждения нашими оценщиками Ваш лот уже будет выставлен!'
        },
        {
          title_name: 'Начните уже сейчас!',
          description: 'Пройдите быструю регистрацию и начинайте участвовать!'
        }
      ]
    }
  },
  methods: {
    next () {
      this.onboarding = this.onboarding + 1 === this.length
        ? 0
        : this.onboarding + 1
    },
    prev () {
      this.onboarding = this.onboarding - 1 < 0
        ? this.length - 1
        : this.onboarding - 1
    }
  }
}
</script>

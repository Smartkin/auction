<template>
  <ValidationObserver v-slot="{ invalid, validated, passes, validate }">
    <v-form>
      <v-container fluid class="lot-create">
        <v-row>
          <v-col cols="4">
            <v-row align="stretch" justify="center">
              <placeholder v-if="images.length === 0"/>
              <v-carousel
                v-if="images.length !== 0"
                continuous
              >
                <v-carousel-item
                  v-for="(image, i) in images"
                  :key="i"
                  :src="image.src"
                />
              </v-carousel>
            </v-row>
            <v-row class="mt-3 mr-3">
<!--              <validation-provider name="фотографии" rules="required|image" v-slot="{ errors, valid }">-->
                <v-file-input
                  single-line
                  show-size
                  filled
                  multiple
                  counter
                  dense
                  placeholder="Фотографии лота..."
                  accept=".jpeg,.png"
                  prepend-icon="mdi-camera"
                  @change="imagesUploaded"
                />
<!--              </validation-provider>-->
            </v-row>
          </v-col>
          <v-col>
            <validated-text-field
              label="Имя лота"
              field-name="имя лота"
              rules="required|min:10|max:50"
              v-model="newLot.name"
            />
            <validated-text-field
              label="Цена"
              field-name="цена"
              rules="required|numeric|min:4"
              type="numeric"
              v-model="newLot.startPrice"
            />
            <v-row align="center" justify="space-around">
              <v-col cols="4">
                <p class="title">Дата окончания выставления</p>
                <validation-provider name="дата окончания" rules="required"  v-slot="{ errors, valid }">
                  <v-date-picker
                    show-current
                    locale="ru"
                    v-model="newLot.endDate"
                  />
                </validation-provider>
              </v-col>
              <v-col>
                <validation-provider name="описание" rules="max:1024" v-slot="{ errors, valid }">
                  <v-textarea
                    label="Описание лота"
                    rows="24"
                    row-height="15"
                    auto-grow
                    filled
                    counter="1024"
                    v-model="newLot.description"
                    placeholder="(до 1024 символов)"
                    :error-messages="errors"
                  />
                </validation-provider>
                <v-row>
                  <v-checkbox label="Возможность выкупа" v-model="newLot.isBuyout"/>
                  <v-alert dense dismissible v-model="showAlert">
                    {{ message }}
                  </v-alert>
                </v-row>
              </v-col>
            </v-row>
            <v-btn :disabled="invalid" color="primary" depressed block @click="passes(createLot)">
              Создать
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
  </ValidationObserver>
</template>

<script>
import Placeholder from './Placeholder'
import { ValidationProvider, ValidationObserver } from 'vee-validate'
import ValidatedTextField from './ValidatedTextField'

export default {
  data () {
    return {
      newLot: {
        isBuyout: false
      },
      images: [],
      files: [],
      message: '',
      showAlert: false
    }
  },
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    }
  },
  components: {
    ValidatedTextField,
    Placeholder,
    ValidationProvider,
    ValidationObserver
  },
  mounted () {
    if (!this.currentUser) {
      this.$router.push('/login')
    }
  },
  methods: {
    createLot () {
      console.log('create lot: ' + this.newLot)
      this.$store.dispatch('lots/create', this.newLot).then(
        response => {
          this.message = response.message
          this.showAlert = true
        },
        error => {
          this.message = error.data.message
          this.showAlert = true
        }
      )
    },
    imagesUploaded (files) {
      console.log(files)
      // Free all images
      for (let i in this.images) {
        this.images.pop()
      }
      this.images.length = 0

      this.files = files
      this.files.forEach((file) => {
        this.images.push({src: URL.createObjectURL(file)})
      })
    }
  }
}
</script>

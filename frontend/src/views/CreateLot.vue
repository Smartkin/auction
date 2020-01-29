<template>
  <ValidationObserver v-slot="{ invalid, validated, passes, validate }">
    <v-form>
      <v-container fluid class="lot-create">
        <v-row>
          <v-col md="4" cols="12">
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
            <validation-provider name="фотографии" rules="required|size:2000|image" v-slot="{ errors, valid }">
              <v-file-input
                class="mt-3"
                single-line
                show-size
                filled
                multiple
                counter
                dense
                full-width
                placeholder="Фотографии лота..."
                accept=".jpeg,.png,.gif"
                prepend-icon="mdi-camera"
                :error-messages="errors"
                @change="imagesUploaded"
                v-model="images_"
              />
            </validation-provider>
          </v-col>
          <v-col>
            <validated-text-field
              label="Имя лота"
              field-name="имя лота"
              rules="required|min:10|max:50"
              v-model="newLot.name"
            />
            <validated-text-field
              label="Стартовая цена"
              field-name="стартовая цена"
              rules="required|numeric|min_value:1000"
              type="numeric"
              v-model="newLot.startPrice"
              suffix=".00 ₽"
            />
            <validated-text-field
              label="Количество дней выставления"
              field-name="количество дней"
              rules="required|numeric|min_value:1"
              type="numeric"
              v-model="newLot.days"
            />
            <v-row>
              <v-col>
                <validation-provider name="описание" rules="required|max:1024" v-slot="{ errors, valid }">
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
                <v-checkbox label="Возможность выкупа" v-model="newLot.isBuyout"/>
                <v-expand-transition>
                  <v-card class="transparent" flat v-show="newLot.isBuyout">
                    <validated-text-field
                      label="Цена выкупа"
                      field-name="цена выкупа"
                      :rules="newLot.isBuyout ? 'required|' : '' + 'numeric|min_value:' + newLot.startPrice"
                      type="numeric"
                      v-model="newLot.buyoutPrice"
                      suffix=".00 ₽"
                    />
                  </v-card>
                </v-expand-transition>
                <v-alert dense dismissible v-model="showAlert">
                  {{ message }}
                </v-alert>
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
import Placeholder from '../components/Placeholder'
import LotsService from '../services/lots.service'
import { ValidationProvider, ValidationObserver } from 'vee-validate'
import ValidatedTextField from '../components/ValidatedTextField'

export default {
  data () {
    return {
      newLot: {
        name: '',
        startPrice: 1000,
        days: 1,
        isBuyout: false,
        buyoutPrice: null
      },
      images: [],
      files: [],
      images_: [],
      message: '',
      showAlert: false,
      today: new Date().toISOString()
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
      this.newLot.images = this.images
      this.$store.dispatch('lots/create', this.newLot).then(
        response => {
          console.log(response.message)
          console.log(this.files)
          LotsService.uploadImages(this.files, response.lotId).then(response => {
            this.message = response.message
          })
          this.message = response.message
          this.showAlert = true
        },
        error => {
          console.log(error.data.message)
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

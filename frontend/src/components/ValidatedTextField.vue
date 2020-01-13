<template>
    <validation-provider :vid="vid" :name="fieldName" :rules="rules" v-slot="{ errors, valid }">
        <v-text-field
                :type="type"
                :label="label"
                v-model="innerValue"
                v-bind="$attrs"
                v-on="$listeners"
                :error-messages="errors"
                :success="valid"
        />
    </validation-provider>
</template>
<script>
import { ValidationProvider } from 'vee-validate'
export default {
  data: () => ({
    innerValue: ''
  }),
  components: {
    ValidationProvider
  },
  name: 'ValidatedTextField',
  watch: {
    innerValue (newVal) {
      this.$emit('input', newVal)
    },
    value (newVal) {
      this.innerValue = newVal
    }
  },
  props: {
    type: {
      type: String,
      default: 'text'
    },
    label: {
      type: String,
      required: true
    },
    vid: {
      type: String
    },
    rules: {
      type: [Object, String],
      required: true
    },
    value: {
      type: null,
      required: true
    },
    fieldName: {
      type: String,
      required: true
    }
  }
}
</script>

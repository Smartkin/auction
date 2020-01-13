import { extend } from 'vee-validate'
import * as rules from 'vee-validate/dist/rules'
import { messages } from 'vee-validate/dist/locale/ru'

Object.keys(rules).forEach(rule => {
  extend(rule, {
    ...rules[rule],
    message: messages[rule]
  })
})

extend('agreeRequired', {
  ...rules['required'],
  message: 'Требуется согласие с пользовательским соглашением'
})

extend('equalTo', {
  validate (value, { otherValue, fieldName }) {
    return value === otherValue
  },
  params: ['otherValue', 'fieldName'],
  message: 'Поле {_field_} должно быть равным полю {fieldName}'
})

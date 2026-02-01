import {createI18n} from 'vue-i18n'
import vi from './vi'
import en from './en'

export const i18n = createI18n({
    legacy: false,
    locale: 'vi',
    fallbackLocale: 'vi',
    messages: {vi, en}
})
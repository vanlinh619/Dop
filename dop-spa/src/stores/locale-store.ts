import {defineStore} from "pinia";
import {i18n} from "../locales/i18n";

export const useLocaleStore = defineStore('locale', {
    state: () => ({
        locale: 'vi'
    }),
    actions: {
        changeLocale(locale: 'vi' | 'en'): void {
            this.locale = locale
            i18n.global.locale.value = locale
        }
    }
})
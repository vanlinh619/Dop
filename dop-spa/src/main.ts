import {createApp} from 'vue'
import {createPinia} from 'pinia'
import piniaPersistedState from 'pinia-plugin-persistedstate'
import './style.css'
import router from "./router/index";
import {i18n} from "./locales/i18n";
import App from "./App.vue";

const pinia = createPinia()
    .use(piniaPersistedState)

createApp(App)
    .use(pinia)
    .use(router)
    .use(i18n)
    .mount('#app')

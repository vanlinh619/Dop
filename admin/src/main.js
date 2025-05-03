import {createApp} from 'vue'
import {createPinia} from 'pinia'
import piniaPersistedState from 'pinia-plugin-persistedstate'
import './style.css'
import App from './App.vue'
import router from "./router/index.js";

const pinia = createPinia()
    .use(piniaPersistedState)

createApp(App)
    .use(pinia)
    .use(router)
    .mount('#app')

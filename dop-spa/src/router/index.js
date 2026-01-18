import {createRouter, createWebHistory} from 'vue-router'
import Home from "../templates/pages/Home.vue";
import OidcCode from "../templates/pages/OidcCode.vue";
import Login from "../templates/pages/Login.vue";
import Test from "../templates/pages/Test.vue";

const routes = [
  {path: '/', name: 'Home', component: Home},
  {path: '/login', name: 'Login', component: Login},
  {path: '/login/oauth2/code', name: 'OidcCode', component: OidcCode},
  {path: '/test', name: 'Test', component: Test}
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
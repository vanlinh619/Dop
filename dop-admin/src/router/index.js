import { createRouter, createWebHistory } from 'vue-router'
import Home from "../views/Home.vue";
import OidcCallback from "../views/OidcCallback.vue";
import Login from "../views/Login.vue";
import Test from "../views/Test.vue";

const routes = [
    { path: '/', name: 'Home', component: Home },
    { path: '/login', name: 'Login', component: Login},
    { path: '/login/oauth2/code', name: 'OidcCallback', component: OidcCallback },
    {path: '/Test', name: 'Test', component: Test}
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
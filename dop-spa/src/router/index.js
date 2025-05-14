import { createRouter, createWebHistory } from 'vue-router'
import Home from "../pages/Home.vue";
import OidcCode from "../pages/OidcCode.vue";
import Login from "../pages/Login.vue";
import Test from "../pages/Test.vue";

const routes = [
    { path: '/', name: 'Home', component: Home },
    { path: '/login', name: 'Login', component: Login},
    { path: '/login/oauth2/code', name: 'OidcCode', component: OidcCode },
    {path: '/test', name: 'Test', component: Test}
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
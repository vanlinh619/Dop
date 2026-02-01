import {createRouter, createWebHistory} from 'vue-router'
import HomePage from "../templates/pages/HomePage.vue";
import OidcLoginPage from "../templates/pages/OidcLoginPage.vue";
import Login from "../templates/pages/Login.vue";
import AccessDeniedPage from "../templates/pages/AccessDeniedPage.vue";
import {routerPage} from "./router-page";

const routes = [
    {path: '/', name: routerPage.home, component: HomePage},
    {path: '/login', name: routerPage.login, component: Login},
    {path: '/login/oauth2/code', name: routerPage.oidcLogin, component: OidcLoginPage},
    {path: '/access-denied', name: routerPage.accessDenied, component: AccessDeniedPage},
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
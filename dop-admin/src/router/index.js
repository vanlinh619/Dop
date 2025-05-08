import { createRouter, createWebHistory } from 'vue-router'
import Home from "../views/Home.vue";
import OidcCode from "../views/OidcCode.vue";
import Login from "../views/Login.vue";
import Test from "../views/Test.vue";
import tenantProperties from "../properties/tenant-properties.js";

const routes = [
    { path: '/:tenant', name: 'Home', component: Home },
    { path: '/:tenant/login', name: 'Login', component: Login},
    { path: '/login/oauth2/code', name: 'OidcCode', component: OidcCode },
    {path: '/:tenant/test', name: 'Test', component: Test}
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

router.beforeEach((to, from, next) => {
    if (!to.params.tenant) {
        next({path: `/${tenantProperties.tenantDefault}/`})
    } else {
        next()
    }
})

export default router
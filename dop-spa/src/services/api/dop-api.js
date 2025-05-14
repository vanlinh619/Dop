import axios from 'axios'
import tenantService from "../tenant/tenant-service.js";
import auth from "../oauth-2-client/user-manager.js";
import router from "../../router/index.js";

const api = axios.create({baseURL: `http://localhost:8080/`})

api.interceptors.request.use(async config => {
    const tenant = tenantService.getCurrentTenant()
    config.url = `/${tenant}${config.url}`
    const user = await auth.getUser()
    if (!user || !user.access_token || user.access_token.length === 0) {
        await router.push({name: 'Login'})
    }
    const token = user.access_token
    config.headers.Authorization = `Bearer ${token}`
    return config
})

api.interceptors.response.use(response => {
    console.log(`Response Success: `, response)
    return response
}, async error => {
    console.log(`Response Error: `, error)
    if (error.response && error.response.status === 401) {
        const currentUser = auth.getCurrentUserManager()
        await currentUser.clearStaleState()
        return  router.push({name: 'Login'})
    }
    return Promise.reject(error)
})

export default api
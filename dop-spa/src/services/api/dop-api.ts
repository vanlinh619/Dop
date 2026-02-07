import axios from 'axios'
import router from "../../router";
import auth from "../oauth-2-client/user-manager";
import {useErrorStore} from "../../stores/error-store";

const api = axios.create({baseURL: `http://localhost:8080/`})

api.interceptors.request.use(async config => {
  config.url = `${config.url}`
  const user = await auth.getUser()
  const token = user!.access_token
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
    return router.push({name: 'Login'})
  }
  let errorStore = useErrorStore()
  errorStore.addError(error.response.data.code, error.response.data.path, error.response.data)
  return Promise.reject(error)
})

export default api
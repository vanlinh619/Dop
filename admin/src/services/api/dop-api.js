import axios from 'axios'

const api = axios.create({ baseURL: 'https://localhost:8080' })

export default api
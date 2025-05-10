import {defineStore} from "pinia";

export const authStore = defineStore("auth", {
    state: () => ({
        accessToken: '',
        user: {},
    }),
    actions: {
        setAccessToken: (accessToken) => state => {state.accessToken = accessToken},
        setUser: (user) => state => {state.user = user},
    },
    getters: {
        getAccessToken: () => state => state.accessToken,
        getUser: () => state => state.user,
    },
    persist: true,
})
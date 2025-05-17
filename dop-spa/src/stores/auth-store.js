import {defineStore} from "pinia";

export const authStore = defineStore("auth", {
    state: () => ({
        accessToken: '',
        user: {},
    }),
    actions: {
        setAccessToken(accessToken) {
            this.accessToken = accessToken
        },
        setUser(user) {
            this.user = user
        },
    },
    persist: true,
})
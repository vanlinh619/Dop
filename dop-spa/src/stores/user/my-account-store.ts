import {defineStore} from "pinia";

export const useMyAccountStore = defineStore('myAccount', {
  state: () => ({
    fullName: '',
    identifier: ''
  }),
  actions: {
    setAccount(account: MyAccount) {
      this.fullName = account.fullName;
      this.identifier = account.identifier;
    }
  },
  getters: {
    getFullName(state) {
      return state.fullName;
    },
    getIdentifier(state) {
      return state.identifier;
    }
  }
})

export interface MyAccount {
  fullName: string,
  identifier: string,
}
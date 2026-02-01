import {defineStore} from "pinia";

export const useErrorStore = defineStore('error', {
    state: () => ({
        errors: [] as ErrorStore[]
    }),
    actions: {
        addError(title: string, message: string, paramError: unknown) {
            let error: ErrorStore = {
                title: title,
                message: message,
                paramError: paramError
            }
            this.errors.push(error);
            if (this.errors.length > 5) {
                this.errors.shift();
            }

            setTimeout(() => {
                this.removeError(error);
            }, 20000);
        },
        removeError(error: ErrorStore) {
            const index = this.errors.indexOf(error);
            if (index > -1) {
                this.errors.splice(index, 1);
            }
        },
        clearErrors() {
            this.errors = []
        }
    },
});

export interface ErrorStore {
    title: string,
    message: string,
    paramError: unknown
}
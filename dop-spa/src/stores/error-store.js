import {defineStore} from "pinia";

export const useErrorStore = defineStore('error', {
    state: () => ({
        errors: []
    }),
    actions: {
        addError(title, message, paramError) {
            let error = {
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
        removeError(error) {
            const index = this.errors.indexOf(error);
            if (index > -1) {
                this.errors.splice(index, 1);
            }
        },
        clearErrors() {
            this.message = '';
            this.paramError = {};
        }
    },
});
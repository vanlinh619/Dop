import {defineStore} from "pinia";
import {adminViewProperties} from "../properties/admin-view-properties.js";

export const useAdminViewStore = defineStore('adminView', {
    state: () => ({
        currentView: adminViewProperties.userView,
    }),
    actions: {
        switchView(viewName) {
            this.currentView = viewName;
        },
    },
    getters: {
        getCurrentView(state) {
            return state.currentView;
        }
    }
})
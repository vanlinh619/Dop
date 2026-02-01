import {defineStore} from "pinia";
import {adminViewProperties} from "../properties/admin-view-properties";

export const useManageUserItemMenuViewStore = defineStore('adminView', {
    state: () => ({
        currentView: adminViewProperties.userView,
    }),
    actions: {
        switchView(viewName: string) {
            this.currentView = viewName;
        },
    },
    getters: {
        getCurrentView(state) {
            return state.currentView;
        }
    }
})
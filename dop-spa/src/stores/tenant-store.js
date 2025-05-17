import {defineStore} from "pinia";
import tenantProperties from "../properties/tenant-properties.js";

export const useTenantStore = defineStore('tenant', {
    state: () => ({
        tenantSelected: tenantProperties.tenantDefault,
        tenants: [tenantProperties.tenantDefault]
    }),
    actions: {
        setTenantSelected(tenant) {
            this.tenantSelected = tenant
        },
        setTenants(tenants) {
            this.tenants = tenants
        }
    },
    getters: {
        getTenantSelected(state) {
            return state.tenantSelected;
        },
        getTenants(state) {
            return state.tenants;
        }
    },
    persist: true,
})
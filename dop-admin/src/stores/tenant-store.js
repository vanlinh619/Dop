import {defineStore} from "pinia";
import tenantProperties from "../properties/tenant-properties.js";

export const useTenantStore = defineStore('tenant', {
    state: () => ({
        tenantSelected: tenantProperties.tenantDefault,
        tenants: [tenantProperties.tenantDefault]
    }),
    actions: {
        setTenantSelected: (tenant) => state => {
            state.tenantSelected = tenant
        },
        setTenants: (tenants) => state => {
            state.tenants = tenants
        }
    },
    getters: {
        getTenantSelected: () => state => state.tenantSelected,
        getTenants: () => state => state.tenants,
    },
    persist: true,
})
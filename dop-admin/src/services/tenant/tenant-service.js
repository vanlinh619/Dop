import tenantProperties from "../../properties/tenant-properties.js";
import {useTenantStore} from "../../stores/tenant-store.js";

const getCurrentTenant = () => {
    const store = useTenantStore()
    const tenant = store.tenantSelected
    return store.tenants.some(tn => tn === tenant) ? tenant : tenantProperties.tenantDefault
}

const tenantService = {
    getCurrentTenant: () => getCurrentTenant()
}

export default tenantService

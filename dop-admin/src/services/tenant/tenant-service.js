import router from "../../router/index.js";
import tenantProperties from "../../properties/tenant-properties.js";

const getCurrentTenant = () => {
    return router.currentRoute.value.params.tenant ?? tenantProperties.tenantDefault
}

export default getCurrentTenant

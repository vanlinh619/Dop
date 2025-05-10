import {UserManager} from 'oidc-client-ts'
import oidcProperties from '../../properties/oidc-properties.js'
import tenantService from "../tenant/tenant-service.js";
import {authStore} from "../../stores/auth-store.js";

const createUserManager = (tenant) => {
    const oidcConfig = {
        authority: `${oidcProperties.serverBaseURL}/${tenant}/`,
        client_id: oidcProperties.clientId,
        redirect_uri: `${oidcProperties.baseURL}/login/oauth2/code`,
        // post_logout_redirect_uri: 'http://localhost:3000',
        response_type: 'code',
        scope: 'address phone openid profile email master',
        // state: '1234567890',
        // code_challenge: '1234567890',
        // code_challenge_method: 'S256',

    }
    return new UserManager(oidcConfig)
}

const getCurrentUserManager = () => {
    return createUserManager(tenantService.getCurrentTenant())
}

const getUser = () => {
    return  getCurrentUserManager().getUser()
}

const auth = {
    createUserManager: createUserManager,
    getCurrentUserManager: getCurrentUserManager,
    getUser: getUser,
}

export default auth
import {UserManager} from 'oidc-client-ts'
import oidcProperties from '../../properties/oidc-properties.js'

const self = {
    userManager: null,
}

export const createUserManager = (tenant) => {
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
    self.userManager = new UserManager(oidcConfig)
    return self.userManager
}

export const getCurrentUserManager = () => {
    return self.userManager
}
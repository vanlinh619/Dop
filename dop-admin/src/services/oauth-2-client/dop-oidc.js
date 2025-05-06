import {UserManager} from 'oidc-client-ts'
import oidcProperties from '../../properties/oidc-properties.js'

const oidcConfig = {
    authority: `${oidcProperties.baseURL}/dop/oauth2/authorize`,
    client_id: oidcProperties.clientId,
    redirect_uri: 'http://localhost:3000/login/oauth2/code',
    // post_logout_redirect_uri: 'http://localhost:3000',
    response_type: 'code',
    scope: 'address phone openid profile email master',
    // state: '1234567890',
    // code_challenge: '1234567890',
    // code_challenge_method: 'S256',
}

export const oidc = new UserManager(oidcConfig)
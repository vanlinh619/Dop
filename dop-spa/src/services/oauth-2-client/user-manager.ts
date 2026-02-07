import {User, UserManager} from 'oidc-client-ts'
import oidcProperties from "../../properties/oidc-properties";
import router from "../../router";
import {routerPage} from "../../router/router-page";

let userManager: UserManager;

const createUserManager = () => {
  const oidcConfig = {
    authority: `${oidcProperties.serverBaseURL}/`,
    client_id: oidcProperties.clientId,
    redirect_uri: `${oidcProperties.baseURL}${oidcProperties.redirectUri}`,
    post_logout_redirect_uri: oidcProperties.baseURL,
    response_type: 'code',
    scope: 'address phone openid profile email master',
  }
  return new UserManager(oidcConfig)
}

const getCurrentUserManager = () => {
  if (!userManager) {
    userManager = createUserManager()
  }
  return userManager
}

const getUser = async (): Promise<User> => {
  let user = await getCurrentUserManager().getUser()
  if (!user) {
    await router.push({name: routerPage.login});
  }
  return user!;
}

const auth = {
  getCurrentUserManager: getCurrentUserManager,
  getUser: getUser,
}

export default auth
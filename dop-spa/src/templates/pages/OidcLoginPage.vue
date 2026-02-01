<!--
  page: /login/oauth2/code
-->
<script setup lang="ts">
import auth from "../../services/oauth-2-client/user-manager.js";
import router from "../../router";
import {onMounted} from "vue";
import oidcProperties from "../../properties/oidc-properties.js";
import {routerPage} from "../../router/router-page";

onMounted(async () => {
  try {
    const userManager = auth.getCurrentUserManager()
    const user = await userManager.signinCallback()
    console.log('Login callback success', user)
    await router.push({name: routerPage.home})
  } catch (errorResponse: any) {
    if (errorResponse?.error === oidcProperties.singingErrorResponse) {
      await router.push({name: routerPage.accessDenied})
    } else {
      console.log(errorResponse)
    }
  }
})
</script>
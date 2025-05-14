<script setup>
import auth from "../services/oauth-2-client/user-manager.js";
import router from "../router/index.js";
import {authStore} from "../stores/auth-store.js";
import {onMounted} from "vue";

onMounted(async () => {
  const userManager = auth.getCurrentUserManager()
  const user = await userManager.signinCallback()
  console.log('Login callback success', user)
  console.log(user.access_token)
  await router.push({name: 'Home'})
})
</script>
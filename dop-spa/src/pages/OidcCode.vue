<script setup>
import auth from "../services/oauth-2-client/user-manager.js";
import router from "../router/index.js";
import {onMounted} from "vue";

onMounted(async () => {
  const userManager = auth.getCurrentUserManager()
  const user = await userManager.signinCallback()
  console.log('Login callback success', user)
  await router.push({name: 'Home'})
})
</script>
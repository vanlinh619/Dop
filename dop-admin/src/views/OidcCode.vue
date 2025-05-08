<script setup>
import {getCurrentUserManager} from "../services/oauth-2-client/user-manager.js";
import router from "../router/index.js";
import {onMounted} from "vue";

onMounted(async () => {
  try {
    const userManager = getCurrentUserManager()
    const user = await userManager.signinCallback()
    console.log('Login callback success', user)
    await router.push({name: 'Home'})
  } catch (err) {
    console.error('Login callback failed', err)
  }
})
</script>
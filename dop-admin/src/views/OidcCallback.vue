<script setup>
import {oidc} from "../services/oauth-2-client/dop-oidc.js";
import router from "../router/index.js";
import {onMounted} from "vue";

onMounted(async () => {
  try {
    const user = await oidc.signinCallback()
    console.log('Login callback success', user)
    await router.push({name: 'Home'})
  } catch (err) {
    console.error('Login callback failed', err)
  }
})
</script>
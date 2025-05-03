<script setup>
import {oidc} from "../services/oauth-2-client/dop-oidc.js";
import {onMounted} from "vue";

onMounted(async () => {
  try {
    const user = await oidc.signinRedirectCallback()
    // useAuthStore().setUser(user)
    // ✅ Sau khi login, redirect về home hoặc trang yêu cầu
    console.log('Login callback success', user)
    window.location.href = '/'
  } catch (err) {
    console.error('Login callback failed', err)
  }
})
</script>

<template>
</template>
<script setup>
import {createUserManager} from "../services/oauth-2-client/user-manager.js";
import {onMounted} from "vue";
import getCurrentTenant from "../services/tenant/tenant-service.js";

onMounted(async () => {
  const tenant = getCurrentTenant()
  const userManager = createUserManager(tenant)
  await userManager.signinRedirect()
})

</script>

<template>

</template>

<style scoped>

</style>
<script setup>
import {onMounted} from "vue";
import tenantService from "../services/tenant/tenant-service.js";
import auth from "../services/oauth-2-client/user-manager.js";

onMounted(async () => {
  const tenant = tenantService.getCurrentTenant()
  const userManager = auth.createUserManager(tenant)
  await userManager.signinRedirect()
})

</script>

<template>

</template>

<style scoped>

</style>
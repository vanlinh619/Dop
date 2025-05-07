<script setup>
import {oidc} from "../services/oauth-2-client/dop-oidc.js";
import {onMounted} from "vue";

onMounted(async () => {
  await oidc.signinRedirect()
})

</script>

<template>

</template>

<style scoped>

</style>
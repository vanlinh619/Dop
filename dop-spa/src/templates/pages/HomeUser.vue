<script setup>
import auth from "../../services/oauth-2-client/user-manager.js";

const userManager = auth.getCurrentUserManager()

const logout = () => {
  userManager.signoutRedirect()
}

</script>

<template>
  <a @click="logout">Logout</a>
User view
</template>

<style scoped>

</style>
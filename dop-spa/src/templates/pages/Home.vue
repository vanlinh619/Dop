<script setup>
import HomeAdmin from "./HomeAdmin.vue";
import auth from "../../services/oauth-2-client/user-manager.js";
import {jwtDecode} from "jwt-decode";
import {onMounted, ref} from "vue";
import {roleProperties} from "../../properties/role-properties.js";
import HomeUser from "./HomeUser.vue";
import router from "../../router/index.js";

const role = ref([])

onMounted(async () => {
  const user = await auth.getUser()
  if (!user) {
    await router.push({name: 'Login'})
  }
  const token = user.access_token

  let payload = jwtDecode(token)
  role.value = payload.role
})
</script>

<template>
  <HomeAdmin v-if="role.some(item => item === roleProperties.roleSuper)"/>
  <HomeUser v-else/>
</template>
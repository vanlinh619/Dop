<!--
  page: /
-->
<script setup lang="ts">
import ManageUserView from "../views/ManageUserView.vue";
import auth from "../../services/oauth-2-client/user-manager";
import {jwtDecode} from "jwt-decode";
import {onMounted, ref} from "vue";
import {roleProperties} from "../../properties/role-properties";
import UserProfileView from "../views/UserProfileView.vue";
import router from "../../router";
import {routerPage} from "../../router/router-page";

const role = ref([])

onMounted(async () => {
  const user = await auth.getUser()
  if (!user) {
    await router.push({name: routerPage.login})
  }
  const token = user.access_token

  let payload: any = jwtDecode(token)
  role.value = payload.role
})
</script>

<template>
  <ManageUserView v-if="role.some((item: string) => item === roleProperties.roleSuper)"/>
  <UserProfileView v-else/>
</template>
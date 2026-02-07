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
import api from "../../services/api/dop-api";
import {useMyAccountStore} from "../../stores/user/my-account-store";

const role = ref([])
const myAccountStore = useMyAccountStore()


const loadMyAccount = () => {
  api.get('api/v1/user/my-account')
      .then((response) => {
        myAccountStore.setAccount(response.data);
      })
      .catch((error) => {
        console.error('Error loading my account data', error)
      })
}

onMounted(async () => {
  const user = await auth.getUser()

  loadMyAccount();

  const token = user!.access_token
  let payload: any = jwtDecode(token)
  role.value = payload.role
})
</script>

<template>
  <ManageUserView v-if="role.some((item: string) => item === roleProperties.roleSuper)"/>
  <UserProfileView v-else/>
</template>
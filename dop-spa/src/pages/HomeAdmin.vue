<script setup type="ts">

import {adminViewProperties} from "../properties/admin-view-properties.js";
import AsideMenu from "../views/AsideMenu.vue";
import Header from "../views/Header.vue";
import ClientView from "../views/ClientView.vue";
import UserView from "../views/UserView.vue";
import RoleView from "../views/RoleView.vue";
import SessionView from "../views/SessionView.vue";
import {useTenantStore} from "../stores/tenant-store.js";
import {useAdminViewStore} from "../stores/admin-view-store.js";
import {onMounted} from "vue";
import api from "../services/api/dop-api.js";

let tenantsStore = useTenantStore()
let adminViewStore = useAdminViewStore()

onMounted(() => {
  api.get("/api/v1/manage/tenant")
      .then(response => {
        let tenants = response.data
        tenantsStore.setTenants(tenants)
      })
      .catch(error => {
      })
})
</script>

<template>
  <div class="flex text-slate-700 h-screen overflow-auto bg-gray-100">

    <AsideMenu/>

    <!-- Main -->
    <main class="flex-1 flex flex-col">

      <Header/>

      <UserView v-if="adminViewStore.currentView === adminViewProperties.userView"/>
      <ClientView v-if="adminViewStore.currentView === adminViewProperties.clientView"/>
      <RoleView v-if="adminViewStore.currentView === adminViewProperties.roleView"/>
      <SessionView v-if="adminViewStore.currentView === adminViewProperties.sessionView"/>
    </main>
  </div>
</template>

<style scoped>

</style>
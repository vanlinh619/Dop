<script setup type="ts">

import {adminViewProperties} from "../../properties/admin-view-properties.js";
import AsideMenu from "../views/AsideMenu.vue";
import Header from "../views/Header.vue";
import ClientView from "../views/ClientView.vue";
import UserView from "../views/UserView.vue";
import RoleView from "../views/RoleView.vue";
import SessionView from "../views/SessionView.vue";
import {useAdminViewStore} from "../../stores/admin-view-store.js";

let adminViewStore = useAdminViewStore()
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
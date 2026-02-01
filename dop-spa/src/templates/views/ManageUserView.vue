<script setup lang="ts">

import {adminViewProperties} from "../../properties/admin-view-properties.js";
import AsideMenuView from "./AsideMenuView.vue";
import HeaderView from "./HeaderView.vue";
import ClientView from "./ClientView.vue";
import UserView from "./UserView.vue";
import RoleView from "./RoleView.vue";
import SessionView from "./SessionView.vue";
import {useManageUserItemMenuViewStore} from "../../stores/admin-view-store.js";

let adminViewStore = useManageUserItemMenuViewStore()
</script>

<template>
  <div class="flex text-slate-700 h-screen overflow-auto bg-gray-100">

    <AsideMenuView/>

    <!-- Main -->
    <main class="flex-1 flex flex-col">

      <HeaderView/>

      <UserView v-if="adminViewStore.currentView === adminViewProperties.userView"/>
      <ClientView v-if="adminViewStore.currentView === adminViewProperties.clientView"/>
      <RoleView v-if="adminViewStore.currentView === adminViewProperties.roleView"/>
      <SessionView v-if="adminViewStore.currentView === adminViewProperties.sessionView"/>
    </main>
  </div>
</template>

<style scoped>

</style>
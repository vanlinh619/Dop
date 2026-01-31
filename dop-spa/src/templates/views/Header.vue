<script setup>
import auth from "../../services/oauth-2-client/user-manager.js";
import {onBeforeUnmount, ref, watch} from "vue";
import UserIcon from "../icon/UserIcon.vue";
import ProfileIcon from "../icon/ProfileIcon.vue";
import SettingIcon from "../icon/SettingIcon.vue";
import LogoutIcon from "../icon/LogoutIcon.vue";

const userManager = auth.getCurrentUserManager()
const openMenu = ref(false);
const menuRef = ref();


const logout = async () => {
  await userManager.signoutRedirect()
}

const toggleMenu = () => {
  openMenu.value = !openMenu.value;
};

const handleClickOutside = (e) => {
  if (!menuRef.value) return;
  if (!menuRef.value.contains(e.target)) {
    openMenu.value = false;
  }
};

watch(openMenu, (value) => {
  if (value) {
    document.addEventListener("pointerdown", handleClickOutside);
  } else {
    document.removeEventListener("pointerdown", handleClickOutside);
  }
});

onBeforeUnmount(() => {
  document.removeEventListener("click", handleClickOutside);
});
</script>

<template>
  <!-- Header -->
  <header class="h-16 bg-white shadow px-6 flex items-center justify-between">
    <div>Dop</div>
    <!-- User area -->
    <div class="relative" ref="menuRef">
      <div @click="toggleMenu" class="flex items-center gap-2 cursor-pointer select-none">
        <div class="w-8 h-8 rounded-full bg-emerald-500 text-white flex items-center justify-center">
          <UserIcon/>
        </div>
      </div>

      <!-- Dropdown -->
      <div v-if="openMenu"
           class="absolute border-[1.5px] right-0 mt-2 w-52 bg-white rounded-lg shadow-md border-slate-200 z-50">

        <!-- User info -->
        <div class="px-2 pt-3">
          <div class="px-2 pb-2 border-b-[1.5px] border-slate-200">
            <div class="text-sm font-medium text-slate-800">user name</div>
            <div class="text-xs text-slate-500">user</div>
          </div>
        </div>

        <!-- Menu -->
        <div class="px-2">
          <ul class="py-1 border-b-[1.5px] border-slate-200 text-sm text-slate-700">
            <li class="px-2 py-2 rounded-lg hover:bg-slate-50 cursor-pointer flex items-center gap-2">
              <!-- Profile icon -->
              <ProfileIcon/>
              Profile
            </li>

            <li class="px-2 py-2 rounded-lg hover:bg-slate-50 cursor-pointer flex items-center gap-2">
              <!-- Settings icon -->
              <SettingIcon/>
              Settings
            </li>
          </ul>
        </div>

        <!-- Sign out -->
        <div class="px-2 pt-1 pb-2">
          <button
              @click="logout"
              class="w-full flex items-center gap-2 text-left text-rose-700 px-2 py-2 rounded-lg text-sm hover:bg-slate-50"
          >
            <!-- Logout icon -->
            <LogoutIcon/>
            Sign out
          </button>
        </div>
      </div>
    </div>
  </header>
</template>
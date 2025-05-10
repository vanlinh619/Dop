<script setup>
import {ref} from "vue";

const isCollapsed = ref(false);

const toggleSidebar = () => {
  isCollapsed.value = !isCollapsed.value;
}
</script>

<template>
  <aside
      :class="['bg-white text-slate-700 flex flex-col shadow-md transition-all duration-300', isCollapsed ? 'lg:w-20' : 'lg:w-64', 'w-full']">
    <!-- Mobile toggle button -->
    <div class="flex justify-between items-center py-5 bg-white shadow">
      <div
          :class="['text-2xl font-bold transition-all duration-300 ease-in-out overflow-hidden whitespace-nowrap', isCollapsed ? 'w-0' : 'w-40 px-4']">
        Dop Admin
      </div>
      <!-- Button to toggle sidebar -->
      <button
          :class="['px-4 focus:outline-none transition-transform duration-300', isCollapsed ? 'w-full flex justify-center' : '']"
          @click="toggleSidebar">
        <svg v-if="!isCollapsed" class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"
             xmlns="http://www.w3.org/2000/svg">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
        </svg>
        <svg v-else class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"
             xmlns="http://www.w3.org/2000/svg">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16"></path>
        </svg>
      </button>
    </div>

    <!-- Menu -->
    <nav :class="['flex-1 py-4 space-y-2 transition-all duration-300', isCollapsed ? 'px-2' : 'px-4']">
      <button
          :class="['w-full h-10 text-left py-2 px-3 rounded hover:bg-emerald-600 active:hover:bg-emerald-700 hover:text-white transition-all duration-300 inline-flex', isCollapsed ? 'justify-center' : 'items-center']">
        <div class="w-5 h-5 flex-shrink-0 flex items-center justify-center">
          <svg xmlns="http://www.w3.org/2000/svg"
               class="w-5 h-5"
               fill="none"
               viewBox="0 0 24 24"
               stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
          </svg>
        </div>
        <span
            :class="['transition-all duration-300 whitespace-nowrap', isCollapsed ? 'w-0 opacity-0' : 'w-auto opacity-100 ml-2']">Người dùng</span>
      </button>
      <button
          :class="['w-full h-10 text-left py-2 px-3 rounded hover:bg-emerald-600 active:hover:bg-emerald-700 hover:text-white transition-all duration-300 inline-flex', isCollapsed ? 'justify-center' : 'items-center']">
        <div class="w-5 h-5 flex-shrink-0 flex items-center justify-center">
          <svg xmlns="http://www.w3.org/2000/svg"
               class="w-5 h-5"
               fill="none"
               viewBox="0 0 24 24"
               stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M9.75 17L9 20l-1 1h8l-1-1-.75-3M3 13h18M5 17h14a2 2 0 002-2V5a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"/>
          </svg>
        </div>
        <span
            :class="['transition-all duration-300 whitespace-nowrap', isCollapsed ? 'w-0 opacity-0' : 'w-auto opacity-100 ml-2']">Clients</span>
      </button>
      <button
          :class="['w-full h-10 text-left py-2 px-3 rounded hover:bg-emerald-600 active:hover:bg-emerald-700 hover:text-white transition-all duration-300 inline-flex', isCollapsed ? 'justify-center' : 'items-center']">
        <div class="w-5 h-5 flex-shrink-0 flex items-center justify-center">
          <svg xmlns="http://www.w3.org/2000/svg"
               class="w-5 h-5"
               fill="none"
               viewBox="0 0 24 24"
               stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z"/>
          </svg>
        </div>
        <span
            :class="['transition-all duration-300 whitespace-nowrap', isCollapsed ? 'w-0 opacity-0' : 'w-auto opacity-100 ml-2']">Roles</span>
      </button>
      <button
          :class="['w-full h-10 text-left py-2 px-3 rounded hover:bg-emerald-600 active:hover:bg-emerald-700 hover:text-white transition-all duration-300 inline-flex', isCollapsed ? 'justify-center' : 'items-center']">
        <div class="w-5 h-5 flex-shrink-0 flex items-center justify-center">
          <svg xmlns="http://www.w3.org/2000/svg"
               class="w-5 h-5"
               fill="none"
               viewBox="0 0 24 24"
               stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"/>
          </svg>
        </div>
        <span
            :class="['transition-all duration-300 whitespace-nowrap', isCollapsed ? 'w-0 opacity-0' : 'w-auto opacity-100 ml-2']">Group</span>
      </button>
      <button
          :class="['w-full h-10 text-left py-2 px-3 rounded hover:bg-emerald-600 active:hover:bg-emerald-700 hover:text-white transition-all duration-300 inline-flex', isCollapsed ? 'justify-center' : 'items-center']">
        <div class="w-5 h-5 flex-shrink-0 flex items-center justify-center">
          <svg xmlns="http://www.w3.org/2000/svg"
               class="w-5 h-5"
               fill="none"
               viewBox="0 0 24 24"
               stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"/>
          </svg>
        </div>
        <span
            :class="['transition-all duration-300 whitespace-nowrap', isCollapsed ? 'w-0 opacity-0' : 'w-auto opacity-100 ml-2']">Identity Providers</span>
      </button>
      <button
          :class="['w-full h-10 text-left py-2 px-3 rounded hover:bg-emerald-600 active:hover:bg-emerald-700 hover:text-white transition-all duration-300 inline-flex', isCollapsed ? 'justify-center' : 'items-center']">
        <div class="w-5 h-5 flex-shrink-0 flex items-center justify-center">
          <svg xmlns="http://www.w3.org/2000/svg"
               class="w-5 h-5"
               fill="none"
               viewBox="0 0 24 24"
               stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"/>
          </svg>
        </div>
        <span
            :class="['transition-all duration-300 whitespace-nowrap', isCollapsed ? 'w-0 opacity-0' : 'w-auto opacity-100 ml-2']">Sessions</span>
      </button>
    </nav>
    <div
        :class="['text-xs border-t border-gray-300 transition-opacity duration-300 ease-in-out', isCollapsed ? 'opacity-0 h-0' : 'opacity-100 p-4']">
      © 2025 Dop
    </div>
  </aside>
</template>
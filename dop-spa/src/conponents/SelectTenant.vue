<script setup lang="ts">
import {Listbox, ListboxButton, ListboxOption, ListboxOptions} from "@headlessui/vue";
import {ref} from "vue";

const selectedTenant = ref(null)
const tenants = [
  {id: 'master', name: 'master'},
  {id: 'tenant1', name: 'tenant1'},
  {id: 'tenant2', name: 'tenant2'}
]
</script>

<template>
  <div class="flex items-center relative">
    <Listbox v-model="selectedTenant">
      <ListboxButton
          class="border border-slate-300 p-2 rounded-md w-full text-slate-700 hover:border-emerald-600 focus:outline-none focus:border-emerald-500 focus:ring-1 focus:ring-emerald-500 bg-white flex justify-between items-center"
      >
        {{ selectedTenant?.name || 'Chọn tenant...' }}
        <svg class="w-4 h-4 ml-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"/>
        </svg>
      </ListboxButton>

      <ListboxOptions
          class="absolute top-full left-0 mt-1 w-full bg-white border border-slate-200 rounded-md shadow-lg z-10"
      >
        <ListboxOption
            v-for="tenant in tenants"
            :key="tenant.id"
            :value="tenant"
            v-slot="{ active }"
            as="template"
        >
          <li :class="['px-4 py-2 cursor-pointer', active ? 'bg-emerald-50' : '']">
            {{ tenant.name }}
          </li>
        </ListboxOption>
        <li class="px-4 py-2 text-emerald-600 font-medium hover:bg-emerald-50 cursor-pointer">
          <div class="flex items-center">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 mr-2" viewBox="0 0 20 20" fill="currentColor">
              <path fill-rule="evenodd"
                    d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z"
                    clip-rule="evenodd"/>
            </svg>
            Thêm tenant mới
          </div>
        </li>
      </ListboxOptions>
    </Listbox>
  </div>
</template>

<style scoped>

</style>
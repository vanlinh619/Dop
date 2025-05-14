<script setup lang="ts">
import {Listbox, ListboxButton, ListboxOption, ListboxOptions} from "@headlessui/vue";
import {useTenantStore} from "../stores/tenant-store.js";
import {ref} from "vue";
import api from "../services/api/dop-api";

let tenantsStore = useTenantStore()

let isAddingNew = ref(false)
let newTenantName = ref('')

const addTenant = () => {
  if (!newTenantName.value || newTenantName.value.trim() === '') {
    console.log('Tenant name is required')
    return
  }
  let tenant = newTenantName.value.trim()

  api.post('/api/v1/manage/tenant', {
    name: tenant
  })
      .then(res => {
        tenantsStore.tenants.push(newTenantName.value)
        newTenantName.value = ''
        isAddingNew.value = false
      })
      .catch(err => {
        console.log(err)
      })
}
</script>

<template>
  <div class="flex items-center relative">
    <Listbox v-model="tenantsStore.tenantSelected">
      <ListboxButton
          class="border border-slate-300 p-2 rounded-md w-full text-slate-700 hover:border-emerald-600 focus:outline-none focus:border-emerald-500 bg-white flex justify-between items-center"
      >
        {{ tenantsStore.tenantSelected || 'Chọn tenant...' }}
        <svg class="w-4 h-4 ml-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"/>
        </svg>
      </ListboxButton>

      <ListboxOptions
          class="absolute top-full left-0 mt-1 w-full bg-white border border-slate-200 rounded-md shadow-lg z-10"
      >
        <ListboxOption
            v-for="tenant in tenantsStore.tenants"
            :key="tenant"
            :value="tenant"
            v-slot="{ active }"
            as="template"
        >
          <li :class="['px-4 py-2 cursor-pointer border-b border-slate-100', active ? 'bg-emerald-50' : '', tenantsStore.tenantSelected === tenant ? 'bg-emerald-500 text-white font-medium' : '']">
            {{ tenant }}
          </li>
        </ListboxOption>
        <li v-if="!isAddingNew" @click="isAddingNew = true"
            class="px-4 py-2 text-slate-500 font-medium hover:bg-emerald-50 cursor-pointer border-t border-slate-200">
          <div class="flex items-center">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 mr-2" viewBox="0 0 20 20" fill="currentColor">
              <path fill-rule="evenodd"
                    d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z"
                    clip-rule="evenodd"/>
            </svg>
            Thêm tenant
          </div>
        </li>
        <li v-else class="w-full border-t border-slate-200">
          <div class="p-3">
            <label class="block text-sm font-medium text-gray-700 mb-1">Tên tenant mới</label>
            <input type="text" v-model="newTenantName"
                   class="w-full px-2 py-1 text-sm border border-gray-300 rounded focus:outline-none focus:border-emerald-500">
            <div class="flex justify-end gap-2 mt-3">
              <button @click="isAddingNew = false"
                      class="px-2 py-1 text-xs text-gray-600 bg-gray-100 rounded hover:bg-gray-200">Hủy
              </button>
              <button @click="addTenant"
                      class="px-2 py-1 text-xs text-white bg-emerald-600 rounded hover:bg-emerald-700">Thêm
              </button>
            </div>
          </div>
        </li>
      </ListboxOptions>
    </Listbox>
  </div>
</template>

<style scoped>

</style>
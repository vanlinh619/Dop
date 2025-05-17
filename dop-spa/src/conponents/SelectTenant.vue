<script setup>
import {Listbox, ListboxButton, ListboxOption, ListboxOptions} from "@headlessui/vue";
import {useTenantStore} from "../stores/tenant-store.js";
import {computed, ref} from "vue";
import api from "../services/api/dop-api";

const STATE_ADD_TENANT = {
  CLOSED: 'CLOSED',
  ADDING: 'ADDING',
  WAITING: 'WAITING',
}

const tenantsStore = useTenantStore()
let state = ref(STATE_ADD_TENANT.CLOSED)
let newTenantName = ref('')

const tenantSelected = computed({
  get: () => tenantsStore.getTenantSelected,
  set: (tenant) => tenantsStore.setTenantSelected(tenant)
})
const tenants = computed({
  get: () => tenantsStore.getTenants,
  set: (tenants) => tenantsStore.setTenants(tenants)
})

const addTenant = async () => {
  let tenant = newTenantName.value ? newTenantName.value.trim() : null
  if (!tenant) {
    return
  }
  state.value = STATE_ADD_TENANT.WAITING

  try {
    await api.post('/api/v1/manage/tenant', {
      name: tenant
    })
    tenantsStore.tenants.push(newTenantName.value)

  } catch (err) {
  } finally {
    newTenantName.value = ''
    state.value = STATE_ADD_TENANT.CLOSED
  }
}
</script>

<template>
  <div class="flex items-center relative">
    <Listbox v-model="tenantSelected">
      <ListboxButton
          class="border border-slate-300 p-2 rounded-md w-full text-slate-700 hover:border-emerald-600 focus:outline-none focus:border-emerald-500 bg-white flex justify-between items-center">
        {{ tenantSelected || '' }}
        <svg class="w-4 h-4 ml-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"/>
        </svg>
      </ListboxButton>

      <transition
          enter-active-class="transition duration-100 ease-out"
          enter-from-class="transform scale-95 opacity-0"
          enter-to-class="transform scale-100 opacity-100"
          leave-active-class="transition duration-75 ease-in"
          leave-from-class="transform scale-100 opacity-100"
          leave-to-class="transform scale-95 opacity-0"
      >
        <ListboxOptions
            class="absolute top-full left-0 mt-1 w-full bg-white border border-slate-200 rounded-md shadow-lg z-10"
        >
          <ListboxOption
              v-for="tenant in tenants"
              :key="tenant"
              :value="tenant"
              v-slot="{ active }"
              as="template"
          >
            <li :class="['px-4 py-2 cursor-pointer border-b border-slate-100',
              active ? 'bg-emerald-600 text-white font-medium' : '',
              tenantSelected === tenant ? 'bg-emerald-600 hover:bg-emerald-700 text-white font-medium' : '']">
              {{ tenant }}
            </li>
          </ListboxOption>
          <li v-if="state === STATE_ADD_TENANT.CLOSED" @click="state = STATE_ADD_TENANT.ADDING"
              class="px-4 py-2 text-slate-400 font-medium hover:bg-emerald-600 hover:text-white cursor-pointer border-t border-slate-200">
            <div class="flex justify-center">
              Thêm tenant
            </div>
          </li>
          <li v-if="state === STATE_ADD_TENANT.ADDING" class="w-full border-t border-slate-200">
            <div class="p-4">
              <input type="text" v-model="newTenantName" placeholder="Tên"
                     class="w-full px-2 py-1 text-sm border border-gray-300 rounded focus:outline-none focus:border-emerald-500">
              <div class="flex justify-end gap-2 mt-3">
                <button @click="state = STATE_ADD_TENANT.CLOSED"
                        class="px-2 py-1 text-xs text-gray-600 bg-gray-100 rounded hover:bg-gray-200">
                  Hủy
                </button>
                <button @click="addTenant" :disabled="!(state === STATE_ADD_TENANT.ADDING)"
                        class="px-2 py-1 text-xs text-white bg-emerald-600 rounded hover:bg-emerald-700 disabled:opacity-50 flex items-center">
                  Thêm
                </button>
              </div>
            </div>
          </li>
          <li v-if="state === STATE_ADD_TENANT.WAITING"
              class="px-4 py-2 text-slate-500 font-medium hover:bg-emerald-50 cursor-pointer border-t border-slate-200">
            <div class="flex items-center justify-center">
              <svg class="animate-spin -ml-1 mr-2 h-3 w-3 text-slate-500"
                   xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor"
                      d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
              </svg>
              <span>Đang khởi tạo...</span>
            </div>
          </li>
        </ListboxOptions>
      </transition>
    </Listbox>
  </div>
</template>

<style scoped>

</style>
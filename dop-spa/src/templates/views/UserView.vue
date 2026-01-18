<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import api from "../../services/api/dop-api";

const page = ref(1)
const size = 25
const sortName = ref('fullName')
const sortDirection = ref('ASC')
const search = ref('')
const total = ref(0)

const users = ref([])

const loading = ref(false)

const listPage = () => {
  loading.value = true
  api.get('/api/v1/manage/user-info', {
    params: {
      page: page.value,
      size: size,
      sortName: sortName.value,
      direction: sortDirection.value,
      search: search.value,
    }
  })
      .then(response => {
        users.value = response.data.content
        total.value = response.data.total
      })
      .catch(error => {
      })
      .finally(() => {
        loading.value = false
      })
}

onMounted(() => {
  listPage()
})
</script>

<template>
  <!-- Content -->
  <section class="overflow-y-auto p-4">
    <div class="bg-white shadow rounded-xl p-4">
      <h2 class="text-emerald-700 text-lg font-semibold mb-4">
        Quản lý Người Dùng
      </h2>
      <p class="mb-6">Danh sách người dùng trong hệ thống của bạn.</p>
      <div>
        <div v-if="loading">
          <div class="w-full h-full bg-white bg-opacity-75 flex items-center justify-center">
            <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-emerald-700"></div>
          </div>
        </div>
        <!-- Table for users without border -->
        <table v-else
               class="min-w-full">
          <thead class="bg-gray-50">
          <tr>
            <th class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Tên</th>
            <th class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Email</th>
            <th class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Vai Trò</th>
            <th class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Trạng Thái</th>
            <th class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Cài Đặt</th>
          </tr>
          </thead>
          <tbody class="bg-white relative">
          <tr v-for="user in users"
              class="hover:bg-gray-50">
            <td class="px-6 py-4 whitespace-nowrap text-sm">{{ user.fullName }}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">{{ user.email.value }}</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">
              <div class="flex gap-1 flex-wrap">
                  <span v-for="role in user.roles" :key="role"
                        class="px-2 py-0.5 bg-gray-100 text-gray-800 rounded-full text-xs">
                    {{ role }}
                  </span>
              </div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">
              <span v-if="user.status === 'ENABLED'" class="px-2 py-0.5 bg-emerald-100 text-emerald-800 rounded-full">
                Hoạt động
              </span>
              <span v-else class="px-2 py-0.5 bg-red-100 text-red-800 rounded-full">
                Vô hiệu
              </span>
            </td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">
              <div class="flex gap-2">
                <button class="text-emerald-600 hover:text-emerald-800">
                  <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24"
                       stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                          d="M12 6V4m0 2a2 2 0 100 4m0-4a2 2 0 110 4m-6 8a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4m6 6v10m6-2a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4"/>
                  </svg>
                </button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </section>
</template>

<style scoped>

</style>
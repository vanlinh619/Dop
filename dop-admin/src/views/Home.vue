<template>
  <div class="flex h-screen bg-gray-100 flex-col lg:flex-row">
    <!-- Sidebar (collapsible on small screens) -->
    <aside
        class="lg:w-64 w-full bg-white text-gray-700 flex flex-col shadow-md"
        :class="{ hidden: !sidebarOpen, 'lg:flex': true }"
    >
      <div class="p-6 text-2xl font-bold border-b border-gray-300">
        Dop Admin
      </div>

      <!-- Realm Selector & Create Combined -->
      <div class="p-4">
        <div class="flex space-x-2 items-center">
          <div class="relative w-full">
            <select class="w-full py-2 px-3 bg-emerald-600 text-white rounded-lg hover:bg-emerald-700">
              <option>Dop Realm 1</option>
              <option>Dop Realm 2</option>
              <option>Dop Realm 3</option>
            </select>
          </div>
          <button class="px-3 py-2 bg-emerald-600 text-white rounded-lg hover:bg-emerald-700">
            +
          </button>
        </div>
      </div>

      <!-- Menu -->
      <nav class="flex-1 px-4 py-4 space-y-2">
        <button class="w-full text-left py-2 px-3 rounded hover:bg-emerald-600 hover:text-white">
          Người dùng
        </button>
        <button class="w-full text-left py-2 px-3 rounded hover:bg-emerald-600 hover:text-white">
          Clients
        </button>
        <button class="w-full text-left py-2 px-3 rounded hover:bg-emerald-600 hover:text-white">
          Roles
        </button>
        <button class="w-full text-left py-2 px-3 rounded hover:bg-emerald-600 hover:text-white">
          Group
        </button>
        <button class="w-full text-left py-2 px-3 rounded hover:bg-emerald-600 hover:text-white">
          Identity Providers
        </button>
        <button class="w-full text-left py-2 px-3 rounded hover:bg-emerald-600 hover:text-white">
          Sessions
        </button>
      </nav>
      <div class="p-4 text-xs border-t border-gray-300">
        © 2025 dop Auth
      </div>
    </aside>

    <!-- Mobile toggle button -->
    <div class="lg:hidden flex justify-between items-center px-4 py-3 bg-white shadow">
      <div class="flex-1 text-xl font-semibold">Dop Admin</div>
      <!-- Button to toggle sidebar -->
      <button @click="sidebarOpen = !sidebarOpen" class="text-emerald-600 focus:outline-none">
        <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16"></path>
        </svg>
      </button>
    </div>

    <!-- Main -->
    <main class="flex-1 flex flex-col">
      <!-- Header -->
      <header class="h-16 bg-white shadow px-6 flex items-center justify-between">
        <div class="flex items-center space-x-6">
          <div class="text-sm text-gray-600">Chào, Admin</div>
        </div>
        <!-- User icon on the top right -->
        <div class="flex items-center space-x-4">
          <div class="w-8 h-8 rounded-full bg-emerald-600 text-white flex items-center justify-center">
            <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 20h14v-2a4 4 0 00-4-4H9a4 4 0 00-4 4v2zM12 2a4 4 0 00-4 4h8a4 4 0 00-4-4z" />
            </svg>
          </div>
        </div>
      </header>

      <!-- Content -->
      <section class="flex-1 overflow-y-auto p-4 sm:p-6">
        <div class="bg-white shadow rounded-xl p-4 sm:p-6">
          <h2 class="text-emerald-700 text-lg font-semibold mb-4">
            Quản lý Người Dùng
          </h2>
          <p class="text-gray-700 mb-6">Danh sách người dùng trong hệ thống của bạn.</p>

          <!-- Table for users without border -->
          <div class="overflow-x-auto">
            <table class="min-w-full">
              <thead class="bg-gray-50">
              <tr>
                <th class="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Tên</th>
                <th class="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Email</th>
                <th class="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Vai trò</th>
                <th class="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Hành động</th>
              </tr>
              </thead>
              <tbody class="bg-white">
              <tr class="hover:bg-gray-50">
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">Nguyễn Văn A</td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">nguyenvana@dop.com</td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">Admin</td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">
                  <div class="flex gap-2">
                    <button class="inline-flex items-center px-3 py-1.5 border border-transparent text-xs font-medium rounded-full shadow-sm text-white bg-blue-600 hover:bg-blue-700">Sửa</button>
                    <button class="inline-flex items-center px-3 py-1.5 border border-transparent text-xs font-medium rounded-full shadow-sm text-white bg-red-600 hover:bg-red-700">Xóa</button>
                  </div>
                </td>
              </tr>
              <tr class="hover:bg-gray-50">
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">Trần Thị B</td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">tranthib@dop.com</td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">User</td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-800">
                  <div class="flex gap-2">
                    <button class="inline-flex items-center px-3 py-1.5 border border-transparent text-xs font-medium rounded-full shadow-sm text-white bg-blue-600 hover:bg-blue-700">Sửa</button>
                    <button class="inline-flex items-center px-3 py-1.5 border border-transparent text-xs font-medium rounded-full shadow-sm text-white bg-red-600 hover:bg-red-700">Xóa</button>
                  </div>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const sidebarOpen = ref(false)
</script>
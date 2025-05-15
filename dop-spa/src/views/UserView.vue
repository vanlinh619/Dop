<script setup lang="ts">

</script>

<template>
  <!-- Content -->
  <section class="overflow-y-auto p-4">
    <div class="bg-white shadow rounded-xl p-4">
      <h2 class="text-emerald-700 text-lg font-semibold mb-4">
        Quản lý Người Dùng
      </h2>
      <p class="mb-6">Danh sách người dùng trong hệ thống của bạn.</p>

      <!-- Table for users without border -->
      <div class="overflow-x-auto">
        <table class="min-w-full">
          <thead class="bg-gray-50">
          <tr>
            <th class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Tên</th>
            <th class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Email</th>
            <th class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Vai trò
            </th>
            <th class="px-6 py-3 text-left text-xs font-semibold uppercase tracking-wider">Hành động
            </th>
          </tr>
          </thead>
          <tbody class="bg-white">
          <tr class="hover:bg-gray-50">
            <td class="px-6 py-4 whitespace-nowrap text-sm">Nguyễn Văn A</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">nguyenvana@dop.com</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">Admin</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">
              <div class="flex gap-2">
                <button
                    class="inline-flex items-center px-3 py-1.5 border border-transparent text-xs font-medium rounded-full shadow-sm text-white bg-blue-600 hover:bg-blue-700">
                  Sửa
                </button>
                <button
                    class="inline-flex items-center px-3 py-1.5 border border-transparent text-xs font-medium rounded-full shadow-sm text-white bg-red-600 hover:bg-red-700">
                  Xóa
                </button>
              </div>
            </td>
          </tr>
          <tr class="hover:bg-gray-50">
            <td class="px-6 py-4 whitespace-nowrap text-sm">Trần Thị B</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">tranthib@dop.com</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">User</td>
            <td class="px-6 py-4 whitespace-nowrap text-sm">
              <div class="flex gap-2">
                <button
                    class="inline-flex items-center px-3 py-1.5 border border-transparent text-xs font-medium rounded-full shadow-sm text-white bg-blue-600 hover:bg-blue-700">
                  Sửa
                </button>
                <button
                    class="inline-flex items-center px-3 py-1.5 border border-transparent text-xs font-medium rounded-full shadow-sm text-white bg-red-600 hover:bg-red-700">
                  Xóa
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
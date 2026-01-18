<script setup>
import {useErrorStore} from "../../stores/error-store.js";
import {onMounted, onBeforeUnmount} from "vue";

const errorStore = useErrorStore()

const copyToClipboard = (paramError) => {
  let text = JSON.stringify(paramError, null, 2)
  navigator.clipboard.writeText(text)
}

onBeforeUnmount(() => {
  errorStore.clearErrors()
})
</script>

<template>
  <div v-if="errorStore.errors.length" class="fixed bottom-4 right-4 flex flex-col gap-2">
    <div v-for="(error, index) in errorStore.errors" :key="index"
         class="flex items-center p-4 rounded-lg shadow-lg bg-gradient-to-r from-red-500 to-red-600 text-white transform transition-all duration-300 hover:scale-105 translate-y-0"
         :style="{transform: `translateY(${(errorStore.errors.length - index - 1) * -10}px)`}">
      <svg class="flex-shrink-0 w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor"
           viewBox="0 0 20 20">
        <path
            d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM10 15a1 1 0 1 1 0-2 1 1 0 0 1 0 2Zm1-4a1 1 0 0 1-2 0V6a1 1 0 0 1 2 0v5Z"/>
      </svg>
      <div class="ml-3 mr-6 flex flex-col max-w-[300px]">
        <span class="text-sm font-bold truncate break-words">{{ error.title }}</span>
        <span class="text-xs font-medium line-clamp-2 break-words">{{ error.message }}</span>
      </div>
      <div class="flex flex-col gap-2 ml-auto">
        <button class="text-white hover:text-red-200 focus:outline-none"
                @click="errorStore.removeError(error)">
          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
          </svg>
        </button>
        <button class="text-white hover:text-red-200 focus:outline-none"
                @click="copyToClipboard(error.paramError)">
          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 012-2h2a2 2 0 012 2M9 5a2 2 0 002 2h2a2 2 0 002-2"/>
          </svg>
        </button>
      </div>
    </div>
  </div>
</template>
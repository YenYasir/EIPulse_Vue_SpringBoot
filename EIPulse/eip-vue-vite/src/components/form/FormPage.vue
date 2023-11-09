<template>
  <ul class="pagination">
    <li class="page-item" @click="onPageChange(page)" v-for="page in totalPages" :key="page">
      <a :class="{ 'page-link': true, 'active': currentPage === page }">
        {{ page }}
      </a>
    </li>
  </ul>
</template>

<script setup>
import { ref, computed } from 'vue'

const props = defineProps({
  totalPages: {
    type: Number,
    required: true
  },
  currentPage: {
    type: Number,
    required: true
  }
})

const emit = defineEmits(['page-change'])

const onPageChange = (page) => {
  emit('page-change', page)
}

</script>

<style scoped></style>
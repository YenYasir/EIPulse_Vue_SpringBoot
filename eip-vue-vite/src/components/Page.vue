
<template>
  <nav aria-label="Page navigation">
    <ul class="pagination">
      <li class="page-item" :class="{ 'disabled': currentPage <= 1 }">
        <a class="page-link" @click="currentPage <= 1 ? null : selectPage(currentPage - 1)">Previous</a>
      </li>
      <li class="page-item" v-for="page in totalPages" :key="page">
        <a class="page-link" @click="selectPage(page)">{{ page }}</a>
      </li>
      <li class="page-item" :class="{ 'disabled': currentPage >= totalPages }">
        <a class="page-link" @click="currentPage >= totalPages ? null : selectPage(currentPage + 1)">Next</a>
      </li>
    </ul>
  </nav>
</template>
<script setup>
import { ref, defineProps, defineEmits } from "vue";

const { totalPages, currentPage } = defineProps(['totalPages', 'currentPage']);
const emit = defineEmits(['selectPage']);

const selectPage = (page) => {
  emit('selectPage', page);
};
</script>


<style scoped>
/* 你的樣式 */
</style>

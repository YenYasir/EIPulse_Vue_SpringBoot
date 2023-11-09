<script setup>
import { ref, defineProps, defineEmits } from "vue";
const { totalPages, currentPage } = defineProps(['totalPages', 'currentPage'])


const emit = defineEmits(['selectPage'])

const selectPage = (page) => {
    emit("selectPage", page)
}

</script>
<template>
    <ul class="pagination pagination-sm">
        <li class="page-item"><a class="page-link" @click="selectPage(currentPage - 1)">Previous</a></li>
        <li class="page-item" v-for="page in totalPages" :key="page">
            <a class="page-link" @click="selectPage(page)">{{ page }}</a>
        </li>

        <li class="page-item" :class="{
            'disabled': currentPage >= totalPages
        }" @click="currentPage >= totalPages ? null : selectPage(currentPage + 1)"><a class="page-link"
                href="#">Next</a>
        </li>
    </ul>
</template>
    
    
<style scoped>
.page-link{
  cursor: pointer;
}
</style>
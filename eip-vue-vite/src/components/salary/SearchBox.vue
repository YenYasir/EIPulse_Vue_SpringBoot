<script setup>
import { ref, defineProps, defineEmits } from "vue";
const keyword = ref(" ")
const emit = defineEmits(["searchInput"])
const inputHandler = () => {
    emit("searchInput", keyword.value)
}
</script>

<template>
    <div class="input-group text-right">
        <input type="text" class="form-control " @input="inputHandler" v-model="keyword" placeholder="姓名/員工編號">
        <p>{{ keyword }}</p>
        <button type="submit" class="btn btn-secondary"><i class="bi bi-search"></i></button>
    </div>
</template>
    
<style></style>
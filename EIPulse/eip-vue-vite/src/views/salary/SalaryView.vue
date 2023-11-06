<script setup>
import SalaryBar from "@/components/salary/SalaryBar.vue";
import { useRouter, useRoute } from 'vue-router';
const route = useRoute()
const router = useRouter()


</script>
<template>
    <SalaryBar
        :items="[{ name: '薪資設定', path: '/info' }, { name: '薪資科目', path: '/subject' }, { name: '薪資結算', path: '/salary/calculate' }]">
    </SalaryBar>
    <router-view></router-view>
</template>
    
    
<style></style>
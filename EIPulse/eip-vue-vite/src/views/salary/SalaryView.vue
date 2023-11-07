<script setup>
import SalaryBar from "@/components/salary/SalaryBar.vue";
import { useRouter, useRoute } from 'vue-router';
const route = useRoute()
const router = useRouter()


</script>
<template>
    <!-- <SalaryBar
        :items="[{ name: '薪資設定', path: '/info' }, { name: '薪資科目', path: '/subject' }, { name: '薪資結算', path: '/salary/calculate' }]">
    </SalaryBar> -->

    <nav>
        <div class="nav nav-tabs" id="nav-tab" role="tablist">
            <router-link :to="{ name: 'salaryInfo' }"> <button class="nav-link active" id="nav-home-tab"
                    data-bs-toggle="tab" data-bs-target="#nav-home" type="button" role="tab" aria-controls="nav-home"
                    aria-selected="true">薪資設定</button></router-link>
            <router-link :to="{ name: 'subject' }"><button class="nav-link" id="nav-profile-tab" data-bs-toggle="tab"
                    data-bs-target="#nav-profile" type="button" role="tab" aria-controls="nav-profile"
                    aria-selected="false">薪資科目</button></router-link>
            <router-link :to="{ name: 'trial' }"> <button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab"
                    data-bs-target="#nav-contact" type="button" role="tab" aria-controls="nav-contact"
                    aria-selected="false">薪資結算</button></router-link>
        </div>
    </nav>
    <!-- <div class="tab-content" id="nav-tabContent">
        <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab"></div>
        <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab"></div>
        <div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab"></div>
    </div> -->
    <router-view></router-view>
</template>
    
    
<style scoped>
a {
    text-decoration: none;
}
</style>
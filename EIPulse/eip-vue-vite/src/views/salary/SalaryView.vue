<script setup>
import SalaryBar from "@/components/salary/SalaryBar.vue";
import { useRouter, useRoute } from 'vue-router';
const route = useRoute()
const router = useRouter()


</script>
<template>
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
                    aria-selected="false">薪資試算</button></router-link>
        </div>
    </nav>
    <section>
        <router-view></router-view>
    </section>
</template>
    
    
<style scoped>
section {
    max-height: 800px;
}

a {
    text-decoration: none;
}

.nav-link {
    color: #000;
}
</style>
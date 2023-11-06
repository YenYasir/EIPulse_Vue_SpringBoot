<script setup >
import { defineProps } from 'vue'
const props = defineProps({
    items: {
        type: Array,
        requried: true,
        default: () => []
    },

})
</script>

<template>
    <nav>
        <div class="nav nav-tabs" id="nav-tab" role="tablist">
            <router-link :to="item.path" v-for="item in items" :key="item.name"><button class="nav-link active"
                    id="nav-home-tab" data-bs-toggle="tab" type="button" role="tab" aria-controls="nav-home"
                    aria-selected="true">{{
                        item.name }}</button> </router-link>
            <!-- <router-link :to="item[1].path"><button class="nav-link active" id="nav-home-tab" data-bs-toggle="tab"
                    data-bs-target="#nav-home" type="button" role="tab" aria-controls="nav-home" aria-selected="true">{{
                        item[1].name }}</button> </router-link> -->

            <!-- <router-link :to="{ name: 'subject' }"><button class="nav-link" id="nav-profile-tab" data-bs-toggle="tab"
                    data-bs-target="#nav-profile" type="button" role="tab" aria-controls="nav-profile"
                    aria-selected="false">{{titleName2}}</button></router-link> -->
            <!-- <button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-contact" type="button"
                role="tab" aria-controls="nav-contact" aria-selected="false">{{titleName3}}</button>
            <button class="nav-link" id="nav-disabled-tab" data-bs-toggle="tab" data-bs-target="#nav-disabled" type="button"
                role="tab" aria-controls="nav-disabled" aria-selected="false" disabled>{{titleName3}}</button> -->
        </div>
    </nav>
    <div class="tab-content" id="nav-tabContent">
        <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab" tabindex="0">
            <!-- <router-view>123</router-view> -->
        </div>
        <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab" tabindex="0">...</div>
        <div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab" tabindex="0">...</div>
        <div class="tab-pane fade" id="nav-disabled" role="tabpanel" aria-labelledby="nav-disabled-tab" tabindex="0">...
        </div>
    </div>
</template>   
    
<style scoped>
a {
    text-decoration: none;
}
</style>
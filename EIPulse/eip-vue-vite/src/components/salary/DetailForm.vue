<script setup>
import axios from "axios";
import { onMounted } from 'vue';
import { ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
const subject = ref([])
const subjectSelect = ref([])
const props = defineProps({
    data: Object,
})
const emit = defineEmits(['selectType'])

const selectType = (type) => {
    emit('selectType', type)
    console.log(props.data);
}

const getSubject = async (type) => {
    const API_URL = `${import.meta.env.VITE_API_JAVAURL}payroll/subject/open`
    const { data } = await axios.get(API_URL)
    // console.log(data);
    subject.value = data;
    // console.log(`output->`, subject.value.length)

    // subject.value = data.filter(item => item.calculateType === type);
    for (let i = 0; i < subject.value.length; i++) {
        // console.log(`output->`, subject.value[i])

        if (type === subject.value[i].calculateType)

            subjectSelect.value.push(subject.value[i])
        // console.log("!" + subjectSelect.value)
    }
    return subjectSelect;

}


onMounted(() => {
    // loadSalaryInfos()
    getSubject('type')
});
</script>
    
<template>
    <div class="row">
        <div class="col">
            <select class="form-control" v-model="subjectSelect.subjectName">
                <option value="" disabled>科目名稱</option>
                <option v-for="s in subjectSelect" :value="s.subjectId" :key="s.subjectId">{{ s.subjectName }}</option>

            </select>

        </div>
        <div class="col">

            <select class="form-control" v-model="subjectSelect.calculateType" @input="productSave">
                <option value="" disabled>計算類型</option>
                <option v-for="s in subjectSelect" :value="s.calculateType" :key="s.subjectId">{{ s.calculateType }}
                </option>
            </select>

        </div>

        <div class="col">
            <input v-model="subjectSelect.amountDefault" @input="productSave" type="text" class="form-control mb-2"
                id="price" placeholder="金額">
            <label for="price">單價</label>
        </div>
        <button @click="selectType"></button>
    </div>

    <!-- <div class="col">
            <div class="form-floating">
                <input v-model="product.stockQuantity" @input="productSave" type="text" class="form-control mb-2"
                    id="stockQuantity" placeholder="庫存量">
                <label for="stockQuantity">庫存量</label>
            </div>
        </div> -->
    <!-- <div class="col">
            <div class="form-floating mb-3">
                <input v-model="product.productName" @input="productSave" type="text" class="form-control" id="productName"
                    placeholder="產品名稱">
                <label for="productName">產品名稱</label>
            </div>
        </div> -->

    <!-- </div> -->
</template>
    
<style></style>

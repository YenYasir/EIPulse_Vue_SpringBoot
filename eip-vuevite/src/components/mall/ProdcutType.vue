<template>
  <window-modal @submit="saveType" class="modal-m" titleName="新增類別" >
<!--    子類別新增-->
    <form  v-if="isChangeSub">
      <div class="col">
        <div class="form-floating mb-3">
          <select class="form-control" v-model="selectType">
            <option value="" disabled>選擇主類別</option>
            <option v-for="type in productTypes" :value="type.id" :key="type.id">{{type.typeName}}</option>
          </select>
        </div>
      </div>
      <div class="form-floating mb-3">
        <input v-model="subTypeName" type="text" class="form-control"  placeholder="子類別名稱">
        <label for="productName">子類別名稱</label>
      </div>
    </form>

    <!--    主類別新增-->
    <form v-else>
      <div class="form-floating mb-3">
        <input v-model="typeName" type="text" class="form-control"  placeholder="主類別名稱">
        <label for="productName">主類別名稱</label>
      </div>
    </form>
    <div class="row mb-4">
      <button @click.prevent="changeSubType" class="btn btn-outline-primary ">{{typeChangeButton}}</button>
    </div>
  </window-modal>
</template>
<script setup>

import WindowModal from "./WindowModal.vue";
import axios from "axios";
import Swal from "sweetalert2";
import {ref} from "vue";

const isChangeSub = ref(false);
const typeName = ref('');
const subTypeName = ref('');
const typeChangeButton=ref('子類別新增')
const productTypes =ref([]);
const selectType = ref('')
// const selectTypeId =ref(null);
const saveType = async () => {
  if (typeName.value !== null && typeName.value !== '') {
    try {
      await axios.post('http://localhost:8090/eipulse/productType', {typeName: typeName.value})
      Swal.fire({
        title: '新增類別成功',
        timer: 1000,
        timerProgressBar: true,
        icon: 'success'
      })
    } catch (e) {
      Swal.fire({
        title: '新增失敗',
        icon: 'error'
      })
      console.log(e)
    }
  } else if (subTypeName.value !== null && subTypeName.value !== ''){
    try {
      await axios.post('http://localhost:8090/eipulse/subType', {productTypeId:selectType.value ,subName:subTypeName.value})
      Swal.fire({
        title: '新增子類別成功',
        timer: 1000,
        timerProgressBar: true,
        icon: 'success'
      })
    }catch (e){
      Swal.fire({
        title: '子類別新增失敗',
        icon: 'error'
      })
      console.log(e)
    }
  }else {
    Swal.fire({
      title: '不得為空',
      icon: 'error'
    })
  }
}

const changeSubType =()=>{
  isChangeSub.value =!isChangeSub.value;
  if(isChangeSub.value){
    typeChangeButton.value='主類別新增'
    axios.get('http://localhost:8090/eipulse/productTypes').then(response=>{
      productTypes.value = response.data;
      typeName.value=''
      console.log(selectType.value)
    }).catch((e)=>{
      console.log(e)
    })
  }else {
    selectType.value=''
    subTypeName.value=''
    typeChangeButton.value='子類別新增'
  }
}
</script>



<style scoped>

</style>
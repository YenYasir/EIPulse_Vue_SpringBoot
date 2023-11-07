
<template>
  <div class="container d-flex justify-content-center">
    <div class="form-group">
      <div class="row mb-3">
        <div class="col-md-6">
          <div class="form-floating">
            <!-- <input type="text" class="form-control " id="nowTitle" placeholder="現在職位">{{ empEdit.titleName }} -->
            <input type="text" :value="empEdit.titleName" readonly id="nowTitle" class="form-control">
            <label for="nowTitle">現在職位</label>
          </div>
        </div>
        <div class="col-md-6">
          <div class="form-floating">
            <input type="date" class="form-control" @input="saveEditedData" id="date" v-model="sendTitle.effectDate"
              required>
            <label for="date">起效日</label>
          </div>
        </div>
      </div>
      <div class="row mb-3">
        <div class="col-md-6">
          <div class="form-floating">
            <select class="form-select" id="deptSelect" aria-label="Floating label select example" v-model="selectedDept">
              <option v-for="dept in depts" :key="dept.deptId" :value="dept.deptId">
                {{ dept.deptName }}
              </option>

            </select>
            <label for="price">新部門</label>
          </div>
        </div>
        <div class="col-md-6">
          <div class="form-floating">
            <select class="form-select" id="titleSelect" aria-label="Floating label select example"
              v-model="sendTitle.titleId" @input="saveEditedData">
              <template v-if="selectedDept != null">
                <option v-for="title in depts[selectedDept].titles" :key="title.id" :value="title.id">
                  {{ title.titleName }}
                </option>
              </template>
            </select>
            <label for="price">新職位</label>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col ">
          <div class="form-floating">
            <textarea class="form-control fs-5" placeholder="Leave a comment here" id="floatingTextarea"
              v-model="sendTitle.reason" @input="saveEditedData" required></textarea>
            <label for="floatingTextarea">更動原因</label>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import axios from "axios";
import Swal from 'sweetalert2';
import { ref, reactive, watch, defineEmits } from "vue";
const emits = defineEmits(['update-send-title']);

const depts = ref([]);
const selectedDept = ref(null);
const selectedTitle = ref(null);
const titles = ref([]);
// const isChangeSub = ref(false);
const selectTitle = ref('');
// const afterTtile = ref();
const props = defineProps(['emp-edit']);
const sendTitle = reactive({
  empId: '',
  titleId: '',
  reason: '',
  effectDate: ''
})

const loadDept = async () => {
  // get api of dept datas 
  const api = `http://localhost:8090/eipulse/dapt/findAll`
  try {
    const response = await axios.get(api);
    depts.value = response.data; // 假设API返回的是一个数组

  } catch (error) {
  }
}
const loadTitle = async () => {
  // get api of title datas 
  const api = `http://localhost:8090/eipulse/title/findAll`
  try {
    const response = await axios.get(api);
    titles.value = response.data; // 假设API返回的是一个数组

  } catch (error) {
  }
}


// 根據部門顯示出對應的職位
const changeSubType = () => {
  Object.assign(selectedTitle, selectedDept.value)

}

const saveEditedData = () => {
  sendTitle.empId = props.empEdit.empId;
  emits('update-send-title', sendTitle);
}

loadDept();
loadTitle();

</script>

  
<style scoped>
.form-group {
  width: 50%;
  /* 或者是您希望的寬度 */
}

.form-control {
  max-width: 250px;
  /* 控制輸入欄位的最大寬度 */
}
</style>
  
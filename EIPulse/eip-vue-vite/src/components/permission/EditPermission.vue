<script setup>
import WindowModal from "@/components/mall/WindowModal.vue";
import {computed, onMounted, reactive, ref, watch} from "vue";
import axios from "axios";
import Swal from "sweetalert2";
import {empStore} from "../../stores/employee";
const store = empStore();
const emit =defineEmits(['closeModal'])

const URL = import.meta.env.VITE_API_JAVAURL;
const selectedPermission = ref({});
const props = defineProps({
  editEmp: Object,
});

const editedEmp = reactive({
  empId: '',
  permissionId: '',
  reason:'',
  effectDate:'',
  approver:store.empId
});

async function editedPermission() {
  editedEmp.empId =props.editEmp.empId
  editedEmp.permissionId = selectedPermission.value.permissionId;
  const result = await Swal.fire({
    title: '請確認變動無誤',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: '確定',
    cancelButtonText: '取消'
  })
  if (result.isConfirmed) {
    try {
      await axios.put(`${URL}permissionInfo`, editedEmp)
      Swal.fire({
        title: "修改成功",
        timer: 1500,
        timerProgressBar: true,
        icon: "success",
      })
      editedEmp.reason=''
      editedEmp.effectDate=''
      selectedPermission.value=''
      emit('closeModal')
    } catch (e) {
      Swal.fire('錯誤!', '更新失敗。', 'error');
    }
  }
}


const allPermission = reactive({});
const getAllPermission = () => {
  axios.get(`${URL}permission/findAll`).then((res) => {
    Object.assign(allPermission, res.data);
  })
}
onMounted(() => {
  getAllPermission();
})
</script>

<template>
  <window-modal button-name="更新" title-name="權限變動" class=" modal-lg" @submit="editedPermission">
    <div class="row">
      <!-- 左側列 -->
      <div class="col-sm-6">
        <p class="text-center">目前權限</p>
        <div class="input-group input-group-sm mb-3">
          <span class="input-group-text">員工姓名</span>
          <input
              class="form-control"
              readonly
              :value="props.editEmp.empName"
          />
        </div>
        <div class="input-group input-group-sm mb-3">
          <span class="input-group-text">目前可用功能</span>
          <input
              class="form-control"
              readonly
              :value="props.editEmp.permissionStatement"
          />
        </div>
        <div class="input-group input-group-sm mb-3">
          <span class="input-group-text">更改前權限</span>
          <input
              class="form-control"
              readonly
              :value="props.editEmp.permissionName"
          />
        </div>
      </div>

      <!-- 右側列 -->
      <div class="col-sm-6">
        <div class="col-sm-12">
          <p class="text-center">更改後權限</p>
          <div class="input-group input-group-sm mb-3">
            <span class="input-group-text">員工姓名</span>
            <input
                type="text"
                class="form-control"
                readonly
                :value="props.editEmp.empName"
            />
          </div>
          <div class="input-group input-group-sm mb-3">
            <span class="input-group-text">更改後功能</span>
            <input
                type="text"
                class="form-control"
                readonly
                :value="selectedPermission.permissionName"
            />
          </div>
          <div class="input-group input-group-sm mb-3">
            <span class="input-group-text">更改後權限</span>
            <select class="form-control" v-model="selectedPermission" required>
              <option v-for="permission in allPermission" :value="permission">{{ permission.permissionStatement }}
              </option>
            </select>
          </div>
        </div>
      </div>
    </div>
    <div class="input-group input-group-sm mb-3">
      <span class="input-group-text">更動原因</span>
      <input
          type="date"
          class="form-control"
          v-model="editedEmp.effectDate"
      />
    </div>
    <div class="input-group input-group-sm mb-3">
      <span class="input-group-text">更動原因</span>
      <input
          type="text"
          required
          class="form-control"
          v-model="editedEmp.reason"
      />
    </div>
    <div class="input-group input-group-sm mb-3">
      <span class="input-group-text">簽核人</span>
      <input
          type="text"
          class="form-control"
          :value="store.empName"
          readonly
      />
    </div>
  </window-modal>

</template>
<style></style>

<template>
  <button type="button" class="btn btn-primary" data-bs-toggle="modal" :data-bs-target="'#exampleModal'+datas.form.formId" data-bs-whatever="@mdo">詳細資料</button>

  <div class="modal fade" :id="'exampleModal'+datas.form.formId" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">

        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">表單資訊</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>

        <div class="modal-body">
          <template v-if="datas.endDate != null">
            <span v-if="datas.statusId == 2">
                主管{{datas.auditor}}已於{{formatStartDate(datas.endDate)}}核准<br><br>
              </span>
            <span v-else-if="datas.statusId == 3">
                主管{{datas.auditor}}已於{{formatStartDate(datas.endDate)}}拒絕<br><br>
              </span>
            <span v-else-if="datas.statusId == 4">
                已於{{formatStartDate(datas.endDate)}}撤回<br><br>
            </span>
            <span v-if="datas.message!=''&datas.message!=null">
                給員工的訊息:{{datas.message}}<br><br>
              </span>
          </template>
          <div v-if="props.formType==1">
            請假類別:<span> {{ datas.form.typeName }} </span><br>
            請假緣由:<span> {{ datas.form.reason }} </span><br>
            從<span> {{ datas.form.startTime }} </span>開始請假<br>
            總共請<span> {{ datas.form.days }} </span>天
            <span> {{ datas.form.hours }} </span>小時<br>
          </div>
          <div v-if="props.formType==2">
            離職日期:<span> {{ datas.form.leaveDate }} </span><br>
            離職原因:<span> {{ datas.form.reason }} </span><br>
            <template v-if="datas.form.quit==true || datas.form.transferForm == true">
              申請了:
              <span v-if="datas.form.quit==true"> 離職證明 </span>
              <span v-if="datas.form.transferForm==true"><br> 勞健保轉出單 </span>
            </template>
          </div>
          <div v-if="props.formType==3">

          </div>


          <div v-if="datas.form.file!=null">
                <img v-for="(image, index) in images" :key="index" :src="getImageUrl(image)" alt="Image">
          </div>
        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>

      </div>
    </div>
  </div>
</template>
<script setup>

import {onMounted, reactive, ref} from "vue";
import axios from "axios";
const URL = import.meta.env.VITE_API_JAVAURL;
const props = defineProps({
  datas: Object,
  formType: Number,
})
const images = ref([]);
const formatStartDate = (dateString) => {
  const options = {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  };
  const date = new Date(dateString);
  return date.toLocaleDateString('zh-TW', options);
};

const downloadFile = async () => {
  const URLAPI = `${URL}download/${props.datas.empId}/${props.datas.form.file}`;
  const response = await axios.get(URLAPI)
  images.value = response.data;

}
const getImageUrl = (imageData) => {
  return `data:image/jpeg;base64,${imageData.body}`;
}
if (props.datas.form.file != null) {
  downloadFile();
}


</script>



<style scoped>
.modal-body img {
  max-width: 100%;
  height: auto;
  margin: 10px 0;
  object-fit: contain;
}
</style>
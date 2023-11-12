<script setup>

import { ref } from "vue";
import Swal from "sweetalert2";
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


if (props.datas.form.file != '' && props.datas.form.file != null) {
  images.value = props.datas.form.file.split(",");
}

const  calculateDaysDifference = () => {
  let sestartDate = new Date(props.datas.form.startTime);
  const allHours = props.datas.form.days*24+props.datas.form.hours;
  // const allHours = 90;
  let day1 = 0;
  if(sestartDate.getHours()>13){
    day1 = 17-sestartDate.getHours();
  }else{
    day1 = 17-sestartDate.getHours()-1;
  }

  const daysDiff = Math.ceil((allHours-day1)/8);
  const dayLast = (allHours-day1)%8;

  for(let i = 0 ;i<daysDiff;i++){
    sestartDate.setDate(sestartDate.getDate()+1);
    if(sestartDate.getDay()==6||sestartDate.getDay()==0) {
      i--;
    }
  }
  if(dayLast!=0){
    sestartDate.setHours(8+dayLast);
  }
  console.log(sestartDate)
  return sestartDate;
}


calculateDaysDifference()
</script>

<template>
  <button type="button" class="btn btn-secondary" data-bs-toggle="modal"
    :data-bs-target="'#exampleModal' + datas.form.formId" data-bs-whatever="@mdo" style="margin-right: 15px;">詳細資料</button>

  <div class="modal fade" :id="'exampleModal' + datas.form.formId" tabindex="-1" aria-labelledby="exampleModalLabel"
    aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">

        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">表單資訊</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>

        <div class="modal-body">
          <template v-if="datas.endDate != null">
            <span v-if="datas.statusId == 2">
              主管{{ datas.auditor }}已於{{ formatStartDate(datas.endDate) }}核准<br><br>
            </span>
            <span v-else-if="datas.statusId == 3">
              主管{{ datas.auditor }}已於{{ formatStartDate(datas.endDate) }}拒絕<br><br>
            </span>
            <span v-else-if="datas.statusId == 4">
              已於{{ formatStartDate(datas.endDate) }}撤回<br><br>
            </span>
            <span v-else-if="datas.statusId == 5">
              主管{{ datas.auditor }}未在7天內審核，此表單已關閉<br><br>
            </span>
            <span v-if="datas.message != '' & datas.message != null">
              給員工的訊息:{{ datas.message }}<br><br>
            </span>
          </template>
          <div v-if="props.formType == 1">
            請假類別:<span> {{ datas.form.typeName }} </span><br>
            請假緣由:<span> {{ datas.form.reason }} </span><br>
            從<span> {{ formatStartDate(datas.form.startTime) }} </span>開始請假<br>
            到<span>{{ formatStartDate(calculateDaysDifference()) }}</span>
            總共請<span> {{ datas.form.days }} </span>天
            <span> {{ datas.form.hours }} </span>小時<br>
          </div>
          <div v-if="props.formType == 2">
            加班日期:<span> {{ datas.form.date }} ({{ datas.form.typeName }})</span><br>
            從:<span> {{ datas.form.startTime.split(":")[0] }}點 </span><br>
            到:<span> {{ datas.form.endTime.split(":")[0] }}點 </span><br>
            加班緣由:<span> {{ datas.form.reason }} </span><br>
          </div>
          <div v-if="props.formType == 3">
            離職日期:<span> {{ datas.form.leaveDate }} </span><br>
            離職原因:<span> {{ datas.form.reason }} </span><br>
            <template v-if="datas.form.quit == true || datas.form.transferForm == true">
              申請了:
              <span v-if="datas.form.quit == true"> 離職證明書 </span>
              <span v-if="datas.form.transferForm == true"><br> 勞健保轉出單 </span>
            </template>
          </div>


          <div v-if="datas.form.file != null">
            <img v-for="(image, index) in images" :key="index"
              :src="'https://eipulseimages.blob.core.windows.net/images/form/' + image" alt="Image">
          </div>
        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>

      </div>
    </div>
  </div>
</template>

<style scoped>
.modal-body img {
  max-width: 100%;
  height: auto;
  margin: 10px 0;
  object-fit: contain;
}
.no-active-style:active {
  background-color: inherit !important;
}
.btn.btn-secondary{
  color: black;
}
</style>
<script setup>
import WindowModal from "../mall/WindowModal.vue";
import {nextTick, onBeforeMount, onMounted, reactive, ref} from "vue";
import {empStore} from "../../stores/employee.js";
import axios from "axios";
import Swal from "sweetalert2";
const URL = import.meta.env.VITE_API_JAVAURL;
//預設地圖經緯度
const center = reactive({lat: 22.99297785113601, lng: 120.18681223016014})
const emp = empStore();
const userLocation = navigator.geolocation;
const timeInfo = reactive({
  empId: emp.empId,
  empName: emp.empName,
  lastTime: '今日無紀錄',
  type:'上班',
  latitude: null,
  longitude: null,
})

//打卡資料新增
const clockTimeSave = async () => {
  try {
    const response = await axios.post(`${URL}clockTime`, {
      empId: timeInfo.empId,
      type: timeInfo.type,
      latitude: timeInfo.latitude,
      longitude: timeInfo.longitude
    })
    getLastTime();
    await Swal.fire({
      title: '打卡成功',
      timer: 2000,
      timerProgressBar: true,
      icon: 'success'
    })
  } catch (e) {
    console.log(e)
    Swal.fire({
      title: '打卡失敗',
      text: '請確認是否在廠區內',
      icon: 'error',
    })
  }
}
//獲得用戶位置
const getUserMaps = async () => {
  try {
    await userLocation.getCurrentPosition(position => {
      timeInfo.latitude = position.coords.latitude;
      timeInfo.longitude = position.coords.longitude;
      center.lat = timeInfo.latitude
      center.lng = timeInfo.longitude
    })
  } catch (e) {
    console.log(e)
  }
}
//獲得用戶最後時間，用於前端按鈕渲染
const getLastTime = () => {
  axios.get(`${URL}clockTime/last/${timeInfo.empId}`).then((res)=>{
    let formattedTime = res.data.time.replace("T"," ");
    if(res.data.type!='上班'){
      timeInfo.type = '上班'
    }else {
      timeInfo.type='下班'
    }
    timeInfo.lastTime = formattedTime
  })
}

onBeforeMount(() => {
  getUserMaps();
  getLastTime();
})

</script>

<template>
  <window-modal :button-name="timeInfo.type" title-name="出勤" @submit="clockTimeSave" class="modal-m">
    <h3>{{ emp.empId }}{{ emp.empName }}，您好</h3>
    <!-- 調整 Google Maps 的高度和寬度 -->
    <GMapMap
        :center="center"
        :zoom="18"
        map-type-id="terrain"
        :options="{ draggable: false, disableDefaultUI: true,clickableIcons:false }"
        style="height: 300px; width: 100%;">
      <GMapCluster>
        <GMapMarker/>
      </GMapCluster>
    </GMapMap>
    <br/>
    <p>最近一次打卡時間：{{timeInfo.lastTime}}</p>
    <br/>
  </window-modal>
</template>
<style scoped>

</style>
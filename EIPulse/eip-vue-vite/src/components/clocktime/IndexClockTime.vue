<template>
  <div class="row mx-2">
    <div class="card row h-auto p-3 border-0 shadow-sm" style="width: 24rem">
      <h5>{{ emp.empId }}{{ emp.empName }}，您好</h5>
      <p class="text-center">現在時間:{{ clock }}</p>
      <!-- 調整 Google Maps 的高度和寬度 -->
      <GMapMap
        :center="center"
        :zoom="18"
        map-type-id="terrain"
        :options="{
          draggable: false,
          disableDefaultUI: true,
          clickableIcons: false,
        }"
        style="height: 300px; width: 100%"
      >
        <GMapCluster>
          <GMapMarker />
        </GMapCluster>
      </GMapMap>
      <br />
      <p>最近一次打卡時間：{{ timeInfo.lastTime }}</p>
      <br />
      <button href="#" class="btn btn-primary mt-2" @click="clockTimeSave">
        {{ timeInfo.type }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { onBeforeMount, reactive, ref } from "vue";
import { empStore } from "../../stores/employee.js";
import axios from "axios";
import Swal from "sweetalert2";

const URL = import.meta.env.VITE_API_JAVAURL;
const center = reactive({ lat: 22.99297785113601, lng: 120.18681223016014 });
const emp = empStore();
const userLocation = navigator.geolocation;
const timeInfo = reactive({
  empId: emp.empId,
  empName: emp.empName,
  lastTime: "今日無紀錄",
  type: "上班",
  latitude: null,
  longitude: null,
});

const clock = ref(new Date().toLocaleTimeString());

//打卡資料新增
const clockTimeSave = async () => {
  try {
    const response = await axios.post(`${URL}clockTime`, {
      empId: timeInfo.empId,
      type: timeInfo.type,
      latitude: timeInfo.latitude,
      longitude: timeInfo.longitude,
    });
    getLastTime();
    await Swal.fire({
      title: "打卡成功",
      timer: 2000,
      timerProgressBar: true,
      icon: "success",
    });
  } catch (e) {
    console.log(e);
    Swal.fire({
      title: "打卡失敗",
      text: "請確認是否在廠區內",
      icon: "error",
    });
  }
};
//獲得用戶位置
const getUserMaps = () => {
  userLocation.getCurrentPosition(
    (position) => {
      timeInfo.latitude = position.coords.latitude;
      timeInfo.longitude = position.coords.longitude;
      center.lat = timeInfo.latitude;
      center.lng = timeInfo.longitude;
    },
    () => {},
    {
      //高精度定位， wi-fi with maps
      enableHighAccuracy: true,
      timeout: 10000,
      maximumAge: 0,
    }
  );
};
//獲得用戶最後時間，用於前端按鈕渲染
const getLastTime = () => {
  axios
    .get(`http://localhost:8090/eipulse/clockTime/last/${timeInfo.empId}`)
    .then((res) => {
      let formattedTime = res.data.time.replace("T", " ");
      if (res.data.type != "上班") {
        timeInfo.type = "上班";
      } else {
        timeInfo.type = "下班";
      }
      timeInfo.lastTime = formattedTime;
    });
};

onBeforeMount(() => {
  getUserMaps();
  getLastTime();
  const interval = setInterval(() => {
    clock.value = new Date().toLocaleTimeString();
  }, 1000);
});
</script>

<style scoped></style>

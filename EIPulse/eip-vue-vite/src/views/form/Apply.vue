<template>
  <div class="card text-center border-0 " style="background-color: #f0f0f0">
    <div class="a1">
      <label>表單名稱</label>
      <select v-model="form.name" @change="onReset">
        <option value="加班">加班申請</option>
        <option value="請假">請假申請</option>
        <option value="離職">離職申請</option>
      </select>
      <div v-if="form.name === '請假'">
        <select
            v-model="form.type"
            placeholder="請選擇請假類別"
            @change="getRemaining(form.type)"
        >
          <option value="-1" disabled>請選擇請假類別</option>
          <option value="1">半年特休</option>
          <option value="2">一年特休</option>
          <option value="3">半薪病假</option>
          <option value="4">生理假</option>
          <option value="5">事假</option>
          <option value="6">婚假</option>
          <option value="7">喪假</option>
          <option value="8">產假</option>
        </select>
        <span v-if="form.type >= 1"
        >目前還可以請 : {{ Math.floor(remainingDays / 24) }} 天
          {{ remainingDays % 24 }} 小時</span
        ><br />

        <label for="startDateTime">開始日期時間：</label>
        <input type="date" v-model="startDate" @change="dayCount" /><input
          type="time"
          v-model="startTime"
          @change="dayCount"
          step="3600000"
      />
        <label for="endDateTime">結束日期時間：</label>
        <input type="date" v-model="endDate" @change="dayCount" /><input
          type="time"
          v-model="endTime"
          @change="dayCount"
      />
        <span v-if="difference != ''">
          共:{{ difference.days }}天{{ difference.hours }}小時
        </span>
        <label>請假原因</label>
        <textarea rows="5" v-model="form.reason" placeholder="請填寫請假原因"></textarea>
      </div>
      <div v-if="form.name === '加班'">
        <label>加班日期</label>
        <input type="date" v-model="date" @change="isWeekend(date)"/>
        <label>加班時段</label>
        <input type="time" v-model="startDateTime" />
        到
        <input type="time" v-model="endDateTime" />
        <select v-model="form.type" placeholder="請選擇加班日別">
          <option value="-1" disabled>請選擇加班類別</option>
          <option value="1" v-if="weekend!=true">平日</option>
          <option value="2" v-else>休息日</option>
          <option value="3">國定假日或特別休假</option>
          <option value="4">例假</option>
        </select>
        <label>加班理由</label>
        <textarea rows="5" v-model="form.reason" placeholder="請填寫加班理由"></textarea>
      </div>
      <div v-if="form.name === '離職'">
        <label>離職日期</label>
        <input type="date" v-model="date" />
        <label>離職原因</label>
        <textarea rows="5"  v-model="form.reason"></textarea><br />
        是否需要申請<br />
        離職證明書:<input type="checkbox" v-model="fileRes.file1" />
        勞健保轉出單:<input type="checkbox" v-model="fileRes.file2" />
      </div>
      附檔:
      <div class="input-group">
        <input type="file" @change="fileChange"
               multiple
               :accept="fileType" class="form-control" aria-describedby="inputGroupFileAddon04" placeholder="" aria-label="Upload" ref="fileInput">
      </div>
      <label>審核者ID
        <select v-model="aduit">
          <option value="" disabled>請選擇審核人</option>
          <template v-for="deptEmp in sameDeptEmp">
          <option v-if="deptEmp.empId!=user" :value="deptEmp.empId">
            {{deptEmp.empName}}
          </option>
          </template>
        </select></label>
      <button @click="applyForm" class="btn btn-secondary mx-2">提交</button>
      <button @click="onReset" class="btn btn-secondary mx-2">重置</button>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import {ref, reactive, watch, onMounted} from "vue";
import Swal from "sweetalert2";
import {empStore} from "../../stores/employee.js";
let toDay = new Date();
let formattedDate = toDay.toISOString().split('T')[0]; // 這將給你 "2023-10-20" 這樣的格式
const forms = ref([]);
const emp = empStore();
const user = ref(emp.empId);
const sameDeptEmp=reactive({});
const aduit = ref("");
const startDate = ref(formattedDate);
const endDate = ref(formattedDate);
const startTime = ref("08:00");
const endTime = ref("17:00");
const fileRes = ref({
  file1: false,
  file2: false,
});
const updateTime = (value, target) => {
  const newHour = value.split(":")[0];
  target.value = `${newHour}:00`;
};
const startDateTime = ref("17:00");
const endDateTime = ref();
watch(startTime, (newValue) => updateTime(newValue, startTime));
watch(endTime, (newValue) => updateTime(newValue, endTime));
watch(startDateTime, (newValue) => updateTime(newValue, startDateTime));
watch(endDateTime, (newValue) => updateTime(newValue, endDateTime));

const date = ref(formattedDate);

//檔案部分
const file = ref([]);
const fileInput = ref(null);
const fileType = ref(
  ".txt, .doc, .docx, .pdf, .jpg, .jpeg, .png, .gif, .xlsx, .csv"
);

const fileChange = (e) => {
  const selectedFiles = e.target.files;
  const maxSizeInBytes = 1024 * 1024 * 5; // 5MB
  // 檢查每個選擇的文件
  file.value = [];
  for (let i = 0; i < selectedFiles.length; i++) {
    if (selectedFiles[i].size > maxSizeInBytes) {
      Swal.fire({
        icon: "error",
        title: "檔案超過大小",
        text: `${selectedFiles[i].name}超過5MB`,
      });
      fileInput.value = "";
      return;
    } else {
      // 如果文件大小符合要求，添加到陣列
      file.value.push(selectedFiles[i]);
    }
  }
};
function getFileName(files) {
  const fileNames = [];
  files.forEach((file) => {
    fileNames.push(file.name);
  });
  return fileNames.join(",");
}



//資料設定
const form = ref({
  name: "加班",
  date: "",
  time: "",
  reason: "",
  type: "-1",
});
const datas = reactive({
  typeId: "",
  days: "",
  hours: "",
  reason: "",
  file: [],
  startime: "",
  endTime: "",
});
const URL = import.meta.env.VITE_API_JAVAURL;

//算剩餘天數
const remainingDays = ref("");
const getRemaining = async (id) => {
  if (user.value == "") {
    return;
  }
  const URLAPI = `${URL}form/findRemainingLeaveDays?typeId=${id}&empId=${user.value}`;
  const response = await axios.get(URLAPI);
  if (response.data == "") {
    switch (parseInt(id)) {
      case 1:
        remainingDays.value = 3 * 24;
        break;
      case 2:
        remainingDays.value = 4 * 24;
        break;
      case 3:
        remainingDays.value = 3 * 24;
        break;
      case 4:
        remainingDays.value = 12 * 24;
        break;
      case 5:
        remainingDays.value = 30 * 24;
        break;
      case 6:
        remainingDays.value = 7 * 24;
        break;
      case 7:
        remainingDays.value = 7 * 24;
        break;
      case 8:
        remainingDays.value = 30 * 24;
        break;
    }
  } else if (response.data < 0) {
    remainingDays.value = "本月已無天數";
  } else remainingDays.value = response.data;
};

// 計算天數
const difference = ref({ hours: 0, days: 0 });
const dayCount = () => {
  startDateTime.value = startDate.value + " " + startTime.value;
  endDateTime.value = endDate.value + " " + endTime.value;
  if (
    startTime.value != "" &&
    startDate.value != "" &&
    endTime.value != "" &&
    endDate.value != ""
  )
    difference.value = calculateTimeDifference(
      startDateTime.value,
      endDateTime.value
    );
};
//送出
const applyForm = async () => {
  if(!checkform()){
    onReset();
    return;
  }
  let applyType = "";
  const formData = new FormData();
  if (file.value) {
    file.value.forEach((fileItem) => {
      formData.append("files", fileItem);
    });
  }
  if (form.value.name == "請假") {
    let dateTime = difference.value.days * 24 + difference.value.hours;
    if (
      dateTime > remainingDays.value ||
      remainingDays.value == "本月已無天數"
    ) {
      Swal.fire({
        icon: "error",
        title: "天數異常",
        text: "已超過可請的天數",
      });
      return;
    }
    applyType = "applyForLeave";
    datas.value = {
      typeId: form.value.type,
      days: dateTime / 24,
      hours: dateTime % 24,
      file: getFileName(file.value),
      reason: form.value.reason,
      startTime: startDateTime.value.replace(" ", "T") + ":00",
    };
    console.log(datas.value);
  } else if (form.value.name == "加班") {

    applyType = "applyForOvertime";
    datas.value = {
      typeId: form.value.type,
      reason: form.value.reason,
      date: date.value,
      file: getFileName(file.value),
      startTime: startDateTime.value + ":00",
      endTime: endDateTime.value + ":00",
    };
    console.log(getFileName(file.value));
  } else if (form.value.name == "離職") {
    applyType = "applyResign";
    datas.value = {
      reason: form.value.reason,
      leaveDate: date.value,
      file: getFileName(file.value),
      quit: fileRes.value.file1,
      transferForm: fileRes.value.file2,
    };
  }
  const json = JSON.stringify(datas.value);
  const blob = new Blob([json], {
    type: "application/json",
  });
  formData.append("data", blob);
  console.log(formData);

  const URLAPI = `${URL}form/${applyType}?empId=${user.value}&audit=${aduit.value}`;

  const response = await axios.post(URLAPI, formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
  datas.value = {
    typeId: "",
    days: "",
    hours: "",
    file: [],
    reason: "",
    startTime: "",
    endTime: "",
  };
  if ((response.data == false) & (form.value.name == "離職")) {
    Swal.fire({
      icon: "error",
      title: "錯誤",
      text: "你的離職單已通過,或者正在審核中",
    });
  } else if (response.status == 200)
    Swal.fire({
      title: "成功",
      text: "送出成功！",
      icon: "success",
    });
  if (form.value.name == "請假") getRemaining(form.value.type);
  onReset();
};


const checkform = () => {
  if (form.value.name == "請假") {
    if(form.value.type==-1){
      Swal.fire({
        icon: "error",
        title: "請選擇請假種類",
      });
      return false;
    }else if(form.value.reason==null){
      Swal.fire({
        icon: "error",
        title: "請填寫請假緣由",
      });
      return false;
    }

  }else if(form.value.name == "加班"){

    const start = new Date(date.value+"T"+startDateTime.value)
    const end = new Date(date.value+"T"+endDateTime.value)
    if(start > end) {
      Swal.fire({
        icon: 'error',
        title: '時間錯誤',
        text: '開始時間不能大於結束時間'
      })
      return false
    }else if(form.value.type==-1){
      Swal.fire({
        icon: "error",
        title: "請選擇加班類別",
      });
      return false;
    }else if(form.value.reason==null){
      Swal.fire({
        icon: "error",
        title: "請填寫加班緣由",
      });
      return false;
    }

  }else {
    console.log(new Date(date.value))
    const selectDay = new Date();
    if(new Date(date.value)<selectDay.setDate(selectDay.getDate()+9)){
      Swal.fire({
        icon: "error",
        title: "離職日必須提前10天申請",
        text: `離職日請選${selectDay.getMonth() + 1}月${selectDay.getDate()}日之後`,
      });
      return false;
    }
  }
  if(aduit.value==""){
    Swal.fire({
      icon: "error",
      title: "請選擇審核者",
    });
    return false;
  }
  return true;
};

//計算
function calculateTimeDifference(startDateTime, endDateTime) {
  const sestartDate = new Date(startDateTime);
  const seendDate = new Date(endDateTime);

  const daysDiff = Math.ceil((seendDate - sestartDate) / (1000 * 60 * 60 * 24));
  // daysDiff = 5
  let totalHours = 0;
  // 迴圈計算每天的時數
  for (let i = 0; i < daysDiff; i++) {
    // 獲取當天日期
    const day = new Date(sestartDate.getTime() + i * 1000 * 60 * 60 * 24);
    // 定義當天的工作時間範圍
    let startHour = 8; // 開始時間
    let startMinute = 0;
    let endHour = 17; // 結束時間
    let endMinute = 0;
    const startWork = 8;
    const endWork = 17;
    console.log(day.getDay);
    // 略過周末
    if (day.getDay() === 0 || day.getDay() === 6) {
      continue;
    } else if (daysDiff == 1) {
      if (sestartDate.getHours() < startWork) startHour = startWork;
      else startHour = sestartDate.getHours();
      if (seendDate.getHours() > endWork) endHour = endWork;
      else endHour = seendDate.getHours();
    } else if (i == 0) {
      if (sestartDate.getHours() < startWork) startHour = startWork;
      else startHour = sestartDate.getHours();
      endHour = endWork;
    } else if (i == daysDiff - 1) {
      startHour = startWork;
      if (seendDate.getHours() > endWork) endHour = endWork;
      else endHour = seendDate.getHours();
    }
    // 建立開始和結束時間日期物件
    const start = new Date(
      day.getFullYear(),
      day.getMonth(),
      day.getDate(),
      startHour,
      startMinute
    );
    const end = new Date(
      day.getFullYear(),
      day.getMonth(),
      day.getDate(),
      endHour,
      endMinute
    );
    // 計算當天工作時數
    let dayHours = (end - start) / (1000 * 60 * 60);
    // 累加時數
    if (startHour <= 12 && endHour >= 13) {
      dayHours--;
    }
    if (dayHours > 0) {
      totalHours += dayHours;
    }
  }
  console.log(totalHours);
  if (totalHours <= 0) {
    Swal.fire({
      icon: "error",
      title: "時數異常",
      text: "請重新選擇時間",
    });
    return { days: "--", hours: "--" };
  }
  return { days: parseInt(totalHours / 24), hours: totalHours % 24 };
}

onMounted(()=>{
  axios.get(`http://localhost:8090/eipulse/employee/dept/${emp.empId}`).then((res)=>{
    Object.assign(sameDeptEmp,res.data);
  })
})

const onReset = () => {
  date.value = formattedDate;
  file.value = [];
  fileInput.value.type = "";
  fileInput.value.type = "file";
  form.value.reason = "";
  aduit.value = "";
if(form.value.name === '請假'){
  form.value.type = -1;
  startDate.value = formattedDate;
  startTime.value = "08:00";
  endTime.value = "17:00";
  difference.value = { hours: 0, days: 0 };
  dayCount();
}else if(form.value.name === '加班'){
  startDateTime.value = "17:00";
  endDateTime.value = "";
  form.value.type = -1;
}else{
  fileRes.value = {
    file1: false,
    file2: false,
  };
  const selectDay = new Date();
  selectDay.setDate(selectDay.getDate()+10)
  date.value = selectDay.toISOString().split('T')[0];
}

}


const weekend = ref();
const isWeekend = (day) => {
  const aDay = new Date(day).getDay();
  if(aDay!=0&&aDay!=6){
    weekend.value=false;
  }else{
    weekend.value=true;
  }
}

</script>

<style scoped>
.a1 {
  max-width: 100%;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: white;
  overflow: auto;
  height: 90vh;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input,
select,
textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  resize: none;
}

input[type="date"] {
  appearance: none; /* 清除默认样式 */
}

</style>

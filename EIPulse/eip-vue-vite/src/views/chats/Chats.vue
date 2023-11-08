<template>

  <div class="chat-container">

    <div class="chat-header">
<!--      設定聊天室:<input v-model="roomid"><br>-->
<!--      you name: <input v-model="userid"><br>-->
<!--      <button @click="connect">連線</button>-->
      <select v-model="roomid" @change="connect">
        <option value="1">一般聊天室1</option>
        <option value="2">一般聊天室2</option>
        <option value="3">一般聊天室3</option>
        <template v-if="emp.permissionId==6">
        <option value="4">主管聊天室1</option>
        <option value="5">主管聊天室2</option>
        </template>
      </select>
      <h2>留言板</h2>
      目前在線人數:{{ connectedUsers }}
    </div>
    <div class="chat-messages" ref="scrollContainer">
      <div v-for="messagea in messages">
        <div v-if="messagea.mymsg==false" class="message left">
          <img v-if="findUser(messagea.empId)[2]!=null && findUser(messagea.empId)[2]!=''" :src="findUser(messagea.empId)[2]" class="avatar">
          <img v-else src="https://eipulseimages.blob.core.windows.net/images/ce2cc2511903a619a519d801b61e1d9d.jpg" class="avatar">
          <div class="message-content">
            <div class="name">{{ findUser(messagea.empId)[1] }}</div>
            <img v-if="messagea.file!=''" :src="messagea.file" :alt="messagea.file" style="max-width: 300px; max-height: 200px;">
            <div v-else class="text">{{messagea.message}}</div>
            <div class="timestamp">{{formatStartDate(messagea.createdAt)}}</div>
          </div>
        </div>

        <div v-else-if="messagea.mymsg==true" class="message right" style="text-align: right">
          <div class="message-content">
            <img v-if="messagea.file!=''" :src="messagea.file" :alt="messagea.file" style="max-width: 300px; max-height: 200px;">
            <div v-else class="text">{{messagea.message}}</div>
            <div class="timestamp">{{formatStartDate(messagea.createdAt)}}</div>
          </div>
        </div>
      </div>
    </div>

    <div class="chat-input">
      <textarea v-model="newMessage" style="resize: none;" class="msg" @keydown.enter.prevent="sendmsg"></textarea>
      <input type="file" id="selectedFile" accept=".jpg, .jpeg, .png, .gif" style="display: none;"  @change="fileChange" ref="fileInput">
      <input type="button" value="傳圖檔" onclick="document.getElementById('selectedFile').click();" class="button"/>
      <button @click="sendmsg" class="button">Send</button>
    </div>

  </div>

</template>
<script setup>
import {nextTick, onMounted, ref, onUnmounted} from 'vue';
import axios from "axios";
import { Client } from '@stomp/stompjs';
import Swal from "sweetalert2";
import {empStore} from "../../stores/employee.js";
const emp = empStore();
const userid = ref(emp.empId);
const roomid = ref('1');
const messages = ref([]);
const newMessage = ref('');
const connectedUsers = ref('');
const URL = import.meta.env.VITE_API_JAVAURL;
const stompClient = new Client({
  brokerURL: 'ws://localhost:8090/eipulse/ws',
});
const sendmsg = async() => {
  if(newMessage.value!="" || file.value!=""){
    // const clientIP = await getClientIP()
    const formData = new FormData();
    const chats = ref({
      "empId": userid.value,
      "roomId": roomid.value,
      "message": "",
      "file": "",
      "createdAt": new Date().toISOString(),
      // "userIp": clientIP,
    });
    if(file.value!=""){
      formData.append('file', file.value);
      chats.value.file = file.value.name;
    }else{
      chats.value.message = newMessage.value;
    }
    const json = JSON.stringify(chats.value);
    const blob = new Blob([json], {
      type: 'application/json'
    });
    formData.append('data', blob);
  
    const URLAPI = `${URL}Chats`;
    console.log(URLAPI);
    await axios.post(URLAPI, formData);
    newMessage.value="";
  }
}

const allUser = ref();
const exceptForMyself = async () => {
  const URLAPI = `${URL}exceptForMyself?empId=${userid.value}`;
  const response = await axios.get(URLAPI)
  allUser.value = response.data;
}
exceptForMyself();
const findUser = (id) => {
  return allUser.value.find(obj => obj[0] == id);
}

const fileInput = ref(null);
const file = ref("");
const fileChange = (e) => {
  file.value = '';
  const selectedFile = e.target.files[0]; // 獲取第一個選擇的檔案
  const maxSizeInBytes = 1024 * 1024 * 5; // 5MB

  if (selectedFile) {
    if (selectedFile.size > maxSizeInBytes) {
      Swal.fire({
        icon: 'error',
        title: '檔案超過大小',
        text: `${selectedFile.name}超過5MB`,
      });
      fileInput.value = ''; // 清空輸入框
      return;
    }else{
      file.value = selectedFile;
      sendmsg();
    }
  }
  file.value = '';
};


stompClient.onConnect =  (frame) => {
  setConnected(true);

  stompClient.publish({
      destination: '/app/login',
      body: JSON.stringify({
    userId: userid.value
  })})

  console.log('Connected: ' + frame);
  stompClient.subscribe('/topic/'+roomid.value+'/chats', async (message) => {
    const newMessage = JSON.parse(message.body)
    if(newMessage.empId == userid.value){
      newMessage.mymsg = true;
    }else{
      newMessage.mymsg = false;
    }
    // if(newMessage.file!=""){
    //   await downloadFile(newMessage.file,newMessage.empId).then((result => {
    //     newMessage.message = result;
    //   }))
    // }
    messages.value.push(newMessage)
    newMessage.value=""
    if (messages.value.length > 10) {
      messages.value.shift();
    }
  })
  console.log('connectedUsers未連接');
  stompClient.subscribe('/topic/'+roomid.value+'/connectedUsers', (data) => {
    if(JSON.parse(data.body)!=-1)
      connectedUsers.value = JSON.parse(data.body);
    else
      connectedUsers.value --;

    console.log('connectedUsers連接');
  })

};

const isConnected = ref(false);
const connect = () => {
  if(stompClient.connected){
    disconnect();
  }
  stompClient.activate();
  chataa();
};
const disconnect = () => {
  stompClient.deactivate();
  setConnected(false);
};
const setConnected = (connected) => {
  isConnected.value = connected;
};

const chataa = async () =>{
  const URLAPI = `${URL}Chats?roomId=${roomid.value}&pageNumber=0&pageSize=10`;
  const responsePage = await axios.get(URLAPI);
  const response = responsePage.data.content
  for(let i = 0 ; i<response.length;i++){
    if(response[i].empId == userid.value){
      response[i].mymsg = true;
    }else{
      response[i].mymsg = false;
    }
    // if(response[i].file!=""){
    //   await downloadFile(response[i].file,response[i].empId).then((result => {
    //     response[i].message = result;
    //   }))
    // }
  }
  messages.value = response.slice().reverse();
  await scrollToBottom();
}


const scrollContainer = ref(null);
const scrollToBottom = () => {
  nextTick(() => {
    scrollContainer.value.scrollTop = scrollContainer.value.scrollHeight;
  })
};
onUnmounted( ()=>{
  stompClient.deactivate()
})
onMounted(()=>{
  connect()
  scrollContainer.value.addEventListener('scroll', () => {
    if(isConnected){
      if(!totalPages.value==true){
        if(scrollContainer.value.scrollTop === 0) {
          getUpPage()
          page.value++
          scrollContainer.value.scrollTop = 300
        }
      }
    }
  })
})
const page = ref(1);
const totalPages = ref();
const getUpPage = async () => {
  const URLAPI = `${URL}Chats?roomId=${roomid.value}&pageNumber=${page.value}&pageSize=10`;
  const responsePage = await axios.get(URLAPI);
  console.log(URLAPI)
  console.log(responsePage)
  if(page.value>= responsePage.data.totalPages)
    totalPages.value = true
  const response = responsePage.data.content
  for(let i = 0 ; i<response.length;i++){
    if(response[i].empId == userid.value){
      response[i].mymsg = true;
    }else{
      response[i].mymsg = false;
    }
    // if(response[i].file!=""){
    //   await downloadFile(response[i].file,response[i].empId).then((result => {
    //     response[i].message = result;
    //   }))
    // }
  }
  console.log(response.slice().reverse())
  messages.value = response.slice().reverse().concat(messages.value);
}

async function getClientIP() {
  const res = await fetch('https://api.ipify.org?format=json')
  return res.json().then(data => data.ip)
}

const formatStartDate = (dateString) => {
  const options = { month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit", };
  const date = new Date(dateString);
  return date.toLocaleDateString('zh-TW', options);
};

</script>



<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  height: 40%;
}

.chat-header {
  padding: 10px;
  border-bottom: 1px solid #eee;
  font-weight: bold;
}

.chat-messages {
  flex: 1;
  overflow: auto;
  padding: 10px;
}

.message {
  display: flex;
  margin-bottom: 10px;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.message-content {
  max-width: 80%;
}

.name {
  font-weight: bold;
  margin-bottom: 4px;
}

.text {
  background: #eee;
  padding: 8px;
  border-radius: 4px;
}

.timestamp {
  font-size: 12px;
  color: #999;
  margin-top: 5px;
}

.left {
  justify-content: flex-start;
}

.right {
  justify-content: flex-end;
}

.chat-input {
  display: flex;
  align-items: center;
  padding: 10px;
  border-top: 1px solid #ddd;
}

.msg {
  flex: 1;
  margin-right: 10px;
  padding: 8px;
  border-radius: 4px;
  border: 1px solid #ddd;
}

.button {
  padding: 8px 12px;
  border-radius: 4px;
  background: #007bff;
  color: #fff;
  border: none;
  cursor: pointer;
}

.file{

}
</style>
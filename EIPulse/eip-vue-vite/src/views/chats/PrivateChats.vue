<template>
  <!--  使用者:<input type="text" v-model="userId" @change="getUser">-->

  <div class=" position-fixed"
       style="width: 250px; height: 100vh;overflow-y: auto;">
    跟誰聊天:
    <select v-model="inputText" @change="ck(inputText)">
      <option v-for="(user,index) in users" :value="user[0]">{{ user[0] }}{{ user[1] }}</option>
    </select>
    <ul class="nav nav-pills flex-column mb-auto">
      <li class="nav-item" v-for="personnel in personnels" @click="ck(personnel.sender)">
        <div style="height: 50px;margin: 10px auto">
          員工:<span>{{personnel.sender}}</span>
          訊息:<span v-if="personnel.chat != ''">{{personnel.chat}}</span>
          <span v-else>上傳了一張圖片</span><br>
          時間:<span>{{formatStartDate(personnel.createdAt)}}</span>
        </div>
      </li>
    </ul>

  </div>
  <div v-if="login==true" style="margin-left: 20%;">
<!--    <PrivateChats1 @sendMsg="sendMsg" @loadMsg="getMsg" :messages="filterMessages(presentTarget)" :user="parseInt(userId)"/>-->
    <div class="chat-container">

      <div class="chat-messages" ref="scrollContainer">
        <div v-for="message in filterMessages(presentTarget)">

          <div v-if="message.receiver == parseInt(userId)" class="message left">
            <img src="#" class="avatar">
            <div class="message-content">
              <div class="name">{{ message.user2 == parseInt(userId) ? message.user1 : message.user2 }}</div>
              <img v-if="message.file!=null" :src="getImageUrl(message.chat)" :alt="message.file" style="width: 300px; height: 200px;">
              <div v-else class="text">{{message.chat}}</div>
              <div class="timestamp">{{formatStartDate(message.createdAt)}}</div>
            </div>
          </div>

          <div v-else class="message right">
            <img src="#" class="avatar">
            <div class="message-content">
              <div class="name">{{ message.user1 == parseInt(userId) ? message.user1 : message.user2 }}</div>
              <img v-if="message.file!=null" :src="getImageUrl(message.chat)" :alt="message.file" style="width: 300px; height: 200px;">
              <div v-else class="text">{{message.chat}}</div>
              <div class="timestamp">{{formatStartDate(message.createdAt)}}</div>
            </div>
          </div>
        </div>
      </div>

      <div class="chat-input">
        <textarea v-model="msg" class="msg"></textarea>
        <input type="file" id="selectedFile" accept=".jpg, .jpeg, .png, .gif" style="display: none;" @change="fileChange" ref="fileInput">
        <input type="button" value="傳圖檔" onclick="document.getElementById('selectedFile').click();" class="file"/>
        <button @click="sendMsg(msg)" class="button">Send</button>
      </div>

    </div>
  </div>
</template>

<script setup>
import {nextTick, onMounted, ref, onUnmounted } from 'vue';
import axios from "axios";
import { Client } from '@stomp/stompjs';
import {empStore} from "../../stores/employee.js";
import Swal from "sweetalert2";
const emp = empStore();
const userId = ref(emp.empId);
const login = ref(false);
const presentTarget = ref();
const stompClient = new Client({
  brokerURL: `ws://localhost:8090/eipulse/ws/${userId.value}`,
});
const URL = import.meta.env.VITE_API_JAVAURL;
const personnels = ref([])


const messages = ref([{
  chat:"",
  createdAt:"",
  file:"",
  receiver:"",
  sender:"",
  user1:"",
  user2:"",
}]);
const getUser = async () => {
  let URLAPI = `${URL}getUsers?my=${userId.value}`;
  let response = await axios.get(URLAPI)
  for(let a of response.data){
    let user = {
      createdAt: a.createdAt,
    }
    if(a.chat==null){
      user.chat = a.file;
    }else{
      user.chat = a.chat;
    }
    if(a.user1 == userId.value){
      user.sender = a.user2;
    }else{
      user.sender = a.user1;
    }
    personnels.value.unshift(user)
  }
}
getUser()
const sendMsg = async (msg1,file) => {
  if(file!=true && msg.value==""){
    return
  }
  let user = userComparing(userId.value,presentTarget.value)
  const formData = new FormData();
  let data = {
    chat:"",
    createdAt:new Date().toISOString(),
    file:msg1.name,
    receiver:presentTarget.value,
    sender:userId.value,
    user1:user.user1,
    user2:user.user2,
  };
  if(file == true){
    formData.append('file', msg1);
    data.file = msg1.name
  }else{
    data.chat = msg1
  }
  const json = JSON.stringify(data);
  const blob = new Blob([json], {
    type: 'application/json'
  });
  formData.append('data', blob);
  const URLAPI = `${URL}sendMsg`;
  await axios.post(URLAPI, formData);


  updateObjects(data,true)
  if(data.file!=null){
    await downloadFile(data.user1+""+data.user2,data.sender,data.file).then((result => {
      data.chat = result;
    }))
  }

  messages.value.push(data)
  msg.value = "";

}
onUnmounted( ()=>{
  stompClient.deactivate()
})
stompClient.activate();
stompClient.onConnect =  (frame) => {
  console.log('Connected: ' + frame);
  stompClient.subscribe(`/user/chat/contact`, async function (frame) {
    console.log(123)
    console.log('stompClient.onConnect')
    const entity = JSON.parse(frame.body)
    console.log(entity.sender)
    console.log(presentTarget.value)
    if(parseInt(entity.sender)==parseInt(presentTarget.value)) {
      console.log(1)
      if (messages.value == null) {
        console.log('messages.value == null')
        messages.value = entity
      } else {
        console.log('messages.value != null')
        if(entity.file!=null){
          console.log('entity.file!=null')
          await downloadFile(entity.user1+""+entity.user2,entity.sender,entity.file).then((result => {
            entity.chat = result;
          }))
        }
        messages.value.push(entity)
      }
    }else{
      console.log("entity,false")
      console.log(entity)
    }
    updateObjects(entity,false)
  })
}
const ck =async (id) => {
  page.value = 1;
  totalPages.value = false;
  login.value=true;
  presentTarget.value = id;
  await getMsg(0)
  scrollContainer.value.addEventListener('scroll', () => {
    if(totalPages.value!=true){
      if(scrollContainer.value.scrollTop === 0) {
        getMsg(page.value)
        page.value++;
        scrollContainer.value.scrollTop = 300
      }
    }
  })
}

const getMsg = async (index) => {
  let user = userComparing(userId.value,presentTarget.value)
  let URLAPI = `${URL}getMsg?pageNumber=${index}&pageSize=10`;
  const data = {
    user1: user.user1,
    user2: user.user2,
  }
  let responsePage = await axios.post(URLAPI,data)
  const response = responsePage.data.content
  if(page.value>= responsePage.data.totalPages)
    totalPages.value = true
  for(let i = 0 ; i<response.length;i++){
    if(response[i].file!=null){
      await downloadFile(response[i].user1+""+response[i].user2,response[i].sender,response[i].file).then((result => {
        response[i].chat = result;
      }))
    }
  }
  if(messages.value.length == 1 )
  messages.value = response.slice().reverse();
  else{
    messages.value = response.slice().reverse().concat(messages.value)
  }

  await scrollToBottom();
};
const downloadFile = async (folder,sender,fileName) => {
  const URLAPI = `${URL}privateChats/${folder}/${sender}/${fileName}`;
  const response = await axios.get(URLAPI)
  return response.data;
}

//收到訊息時排序user
const  updateObjects = (Object,ismy) => {
  let index;
  let newObject = {
    chat:Object.chat,
    createdAt:Object.createdAt,
  };
  if(ismy==false){
    console.log(789)
    index = personnels.value.findIndex(person => person.sender == Object.sender);
    newObject.sender = Object.sender
    console.log(newObject)
  }else{
    console.log(456)
    index = personnels.value.findIndex(person => person.sender == Object.receiver);
    newObject.sender = Object.receiver
    console.log(newObject)
  }
  if (index !== -1) {
    personnels.value.splice(index, 1);
  }
  personnels.value.unshift(newObject)
}

const userComparing = (user1,user2) => {
  let user
  if(user1>user2)
    user = {user1:user2,user2:user1}
  else
    user = {user1:user1,user2:user2}
  return user
}
const inputText =ref("");

const from = ref();
const filterMessages = (presentTarget) => {
  return messages.value.filter(m => m.user1 == presentTarget || m.user2 == presentTarget)}

const formatStartDate = (dateString) => {
  const options = { month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit", };
  const date = new Date(dateString);
  return date.toLocaleDateString('zh-TW', options);
};

const msg = ref();

//圖片上傳

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
      sendMsg(file.value,true);
    }
  }
  file.value = ''
  fileInput.value.file = ''
  fileInput.value.type = ''
  fileInput.value.type = 'file'
};

const getImageUrl = (imageData) => {
  return `data:image/jpeg;base64,${imageData}`;
}

const scrollContainer = ref(null);
const scrollToBottom = () => {
  if(page.value!=1){
    nextTick(() => {
      scrollContainer.value.scrollTop = 300;
    })
  }else{
    nextTick(() => {
      scrollContainer.value.scrollTop = scrollContainer.value.scrollHeight;
    })
  }

};


const page = ref(1);
const totalPages = ref();
const users = ref();
const exceptForMyself = async () => {
  const URLAPI = `${URL}exceptForMyself?empId=${userId.value}`;
  const response = await axios.get(URLAPI)
  users.value = response.data;
}
onMounted(()=>{
  exceptForMyself()
})
</script>


<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  height: 500px;
}

.chat-header {
  padding: 10px;
  border-bottom: 1px solid #eee;
  font-weight: bold;
}

.chat-messages {
  flex: 1;
  overflow: scroll;
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
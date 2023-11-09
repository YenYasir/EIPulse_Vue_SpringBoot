<template>
  <div class="chat-container">
    <!-- 左側聯絡人列表 -->
    <div class="contacts">
      <div class="contacts-header">
        跟誰聊天:
        <select v-model="inputText" @change="ck(inputText)">
          <option v-for="user in users" :value="user[0]">
            {{ user[0] }}{{ user[1] }}
          </option>
        </select>
      </div>
      <div class="a">
        <div class="contact" v-for="personnel in personnels" @click="ck(personnel.sender)">
          <img v-if="findPhoto(personnel.sender)[2] != null && findPhoto(personnel.sender)[2] != ''"
            :src="findPhoto(personnel.sender)[2]" class="avatar">
          <img v-else src="https://eipulseimages.blob.core.windows.net/images/ce2cc2511903a619a519d801b61e1d9d.jpg"
            class="avatar">
          <div class="personnel-info">
            <span>{{ findPhoto(personnel.sender)[1] }}</span>
            <div v-if="personnel.chat" style="font-size: 15px">訊息:{{ personnel.chat }}</div>
            <div v-else style="font-size: 15px">訊息:上傳了一張圖片</div>
            <div>
              <span style="font-size: 12px">時間:{{ formatStartDate(personnel.createdAt) }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 右側聊天視窗 -->
    <div class="chat-window" v-if="login">
      <div class="chat-header">
        <img v-if="presentTarget[2] != null && presentTarget[2] != ''" :src="presentTarget[2]" class="avatar">
        <img v-else src="https://eipulseimages.blob.core.windows.net/images/ce2cc2511903a619a519d801b61e1d9d.jpg"
          class="avatar">
        {{ presentTarget[1] }}
      </div>
      <div class="chat-messages">
        <div class="chat-messages">
          <div class="messages" ref="scrollContainer">
            <div v-for="message in messages">
              <!-- 左邊(對方發送) -->
              <div v-if="message.sender != userId" class="message left">
                <img v-if="presentTarget[2] != null && presentTarget[2] != ''" :src="presentTarget[2]" class="avatar">
                <img v-else src="https://eipulseimages.blob.core.windows.net/images/ce2cc2511903a619a519d801b61e1d9d.jpg"
                  class="avatar">
                <div class="message-content">
                  <!--                  <div class="name">{{ message.user2 == parseInt(userId) ? message.user1 : message.user2 }}</div>-->
                  <img v-if="message.file" :src="message.file" style="max-width: 300px; max-height: 200px;">
                  <div v-else class="text">
                    {{ message.chat }}
                  </div>
                  <div class="timestamp" style="font-size: 12px">
                    {{ formatStartDate(message.createdAt) }}
                  </div>
                </div>
              </div>
              <!-- 右邊(自己發送) -->
              <div v-else class="message right">
                <!--                <img src="#" class="avatar">-->
                <div class="message-content">
                  <!--                  <div class="name">{{ message.user1 == parseInt(userId) ? message.user1 : message.user2 }}</div>-->
                  <img v-if="message.file" :src="message.file" style="width: 300px; height: 200px;">
                  <div v-else class="text">
                    {{ message.chat }}
                  </div>
                  <div class="timestamp" style="font-size: 12px">
                    {{ formatStartDate(message.createdAt) }}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="chat-input">
        <textarea v-model="msg" class="msg" @keydown.enter.prevent="sendMsg(msg)" />
        <input type="file" id="selectedFile" @change="fileChange" style="display: none;" ref="fileInput">
        <button onclick="document.getElementById('selectedFile').click();">傳圖檔</button>
        <button @click="sendMsg(msg)" class="button">Send</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { nextTick, onMounted, ref, onUnmounted } from 'vue';
import axios from "axios";
import { Client } from '@stomp/stompjs';
import { empStore } from "../../stores/employee.js";
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


const messages = ref([]);
const getUser = async () => {
  let URLAPI = `${URL}getUsers?my=${userId.value}`;
  let response = await axios.get(URLAPI)
  for (let a of response.data) {
    let user = {
      createdAt: a.createdAt,
    }
    if (a.chat == null) {
      user.chat = a.file;
    } else {
      user.chat = a.chat;
    }
    if (a.user1 == userId.value) {
      user.sender = a.user2;
    } else {
      user.sender = a.user1;
    }
    personnels.value.unshift(user)
  }
}
getUser()
const sendMsg = async (msg1, file) => {
  if (file != true && msg.value == "") {
    return
  }
  let user = userComparing(userId.value, presentTarget.value[0])
  const formData = new FormData();
  let data = {
    chat: "",
    createdAt: new Date().toISOString(),
    file: "",
    receiver: presentTarget.value[0],
    sender: userId.value,
    user1: user.user1,
    user2: user.user2,
  };
  if (file == true) {
    formData.append('file', msg1);
    data.file = msg1.name
  } else {
    data.chat = msg1
  }
  const json = JSON.stringify(data);
  const blob = new Blob([json], {
    type: 'application/json'
  });
  formData.append('data', blob);
  const URLAPI = `${URL}sendMsg`;
  await axios.post(URLAPI, formData);

  if (file == true) {
    data.file = 'https://eipulseimages.blob.core.windows.net/images/' + msg1.name;
  }
  updateObjects(data, true)
  messages.value.push(data)
  msg.value = "";
}

onUnmounted(() => {
  stompClient.deactivate()
})
stompClient.activate();
stompClient.onConnect = (frame) => {
  console.log('Connected: ' + frame);
  stompClient.subscribe(`/user/chat/contact`, async function (frame) {
    const entity = JSON.parse(frame.body)
    if (parseInt(entity.sender) == parseInt(presentTarget.value[0])) {
      if (messages.value == null) {
        messages.value = entity
      } else {
        messages.value.push(entity)
      }
    }
    updateObjects(entity, false)
  })
}
const ck = async (id) => {
  if (scrollContainer.value != null)
    scrollContainer.value.removeEventListener('scroll', onScroll);
  presentTarget.value = users.value.find(obj => obj[0] == id);
  totalPages.value = false;
  login.value = true;
  page.value = 0;
  await getMsg(0)
  scrollContainer.value.scrollTop = scrollContainer.value.scrollHeight;
  scrollContainer.value.addEventListener('scroll', onScroll);

}

const onScroll = () => {
  if (totalPages.value != true) {
    if (scrollContainer.value.scrollTop == 0) {
      getMsg(page.value)
      page.value++;
      scrollContainer.value.scrollTop = 300
    }
  }
}

const getMsg = async (index) => {
  if (page.value == 0) {
    messages.value = null;
  }

  let user = userComparing(userId.value, presentTarget.value[0])
  let URLAPI = `${URL}getMsg?pageNumber=${index}&pageSize=10`;
  const data = {
    user1: user.user1,
    user2: user.user2,
  }
  let responsePage = await axios.post(URLAPI, data)
  const response = responsePage.data.content
  if (page.value >= responsePage.data.totalPages)
    totalPages.value = true
  if (messages.value == null) {
    messages.value = response.slice().reverse();
  } else {
    messages.value = messages.value.concat(response.slice().reverse().concat())
  }

  await scrollToBottom();

};

//收到訊息時排序user
const updateObjects = (Object, ismy) => {
  let index;
  let newObject = {
    chat: Object.chat,
    createdAt: Object.createdAt,
  };
  if (ismy == false) {
    index = personnels.value.findIndex(person => person.sender == Object.sender);
    newObject.sender = Object.sender
  } else {
    index = personnels.value.findIndex(person => person.sender == Object.receiver);
    newObject.sender = Object.receiver
  }
  if (index !== -1) {
    personnels.value.splice(index, 1);
  }
  personnels.value.unshift(newObject)
}

const userComparing = (user1, user2) => {
  let user
  if (user1 > user2)
    user = { user1: user2, user2: user1 }
  else
    user = { user1: user1, user2: user2 }
  return user
}
const inputText = ref("");

const from = ref();
const filterMessages = (presentTarget) => {
  return messages.value.filter(m => m.user1 == presentTarget || m.user2 == presentTarget)
}

const formatStartDate = (dateString) => {
  const options = {
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
  };
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
    } else {
      file.value = selectedFile;
      sendMsg(file.value, true);
    }
  }
  file.value = ''
  fileInput.value.file = ''
  fileInput.value.type = ''
  fileInput.value.type = 'file'
};
const scrollContainer = ref(null);
const scrollToBottom = () => {
  if (page.value != 0) {
    nextTick(() => {
      scrollContainer.value.scrollTop = 300;
    })
  } else {
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

const findPhoto = (id) => {
  return users.value.find(obj => obj[0] == id);
}
onMounted(() => {
  exceptForMyself()
})
</script>

<style scoped>
/* 聊天室樣式 */

.chat-container {
  display: flex;
}

.contacts {
  width: 30%;
  border-right: 1px solid #ddd;
}

.contacts-header {
  padding: 10px;
  border-bottom: 1px solid #eee;
}

.a {
  overflow: auto;
  height: 700px;
}

.contact {
  display: flex;
  align-items: center;
  padding: 10px;
  cursor: pointer;
}

.contact:hover {
  background-color: gray;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.personnel-info {
  flex: 1;
}

/* 聊天視窗 */
.chat-window {
  width: 100%;
}

.chat-header {
  padding: 10px;
  border-bottom: 1px solid #eee;
  font-weight: bold;
}

/* 訊息區塊 */
.chat-messages {
  height: 500px;
  display: flex;
  flex-direction: column;
}

.messages {
  flex: 1;
  overflow: auto;
  padding: 10px;
}

/* 訊息氣泡 */
.message {
  display: flex;
  margin-bottom: 10px;
}

.message-content {
  max-width: 80%;
  background-color: #babbbc;
}

/* 左右區分 */
.right {
  text-align: right;
  justify-content: flex-end;
}

.left {
  justify-content: flex-start;
}

.message-content {
  padding: 8px;
  border-radius: 4px;
  max-width: 80%;
}

.right {
  align-self: flex-end;
}

.left {
  align-self: flex-start;
}

/* 輸入框 */

.chat-input {
  padding: 10px;
  border-top: 1px solid #ddd;
}

.msg {
  width: 100%;
  resize: none;
  border-radius: 4px;
  border: 1px solid #ddd;
  padding: 10px;
}

button {
  float: right;
  padding: 6px 10px;
  border-radius: 4px;
  background: blue;
  color: white;
  border: none;
}</style>
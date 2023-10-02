import { createApp } from 'vue'
// import './style.css'
import '@/assets/bootstrap.min.css'
import '@/assets/bootstrap.min.js'
import App from './App.vue'

import Login from './components/Login.vue'
import Index from "./components/Index.vue";
import NewPassword from "./components/NewPassword.vue";
const app = createApp(App)

app.component('login',Login)
app.component('index',Index)
app.component('new-password',NewPassword)
app.mount('#app')

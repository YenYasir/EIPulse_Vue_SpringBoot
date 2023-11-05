import { createApp } from 'vue'
import { createPinia } from "pinia";
import VueGoogleMaps from '@fawmi/vue-google-maps'
import { someFunction } from 'fast-deep-equal'



import 'bootstrap-icons/font/bootstrap-icons.css'
import '@/assets/bootstrap.min.css'
import '@/assets/bootstrap.min.js'
import router from './router'
import App from './App.vue'
import Login from './views/Login.vue'
import Index from "./views/manage/Index.vue";
import NewPassword from "./views/NewPassword.vue";
const app = createApp(App)
//ref如果裝陣列跟物件只要屬性值一改變會變成整個物件都更新，較耗性能
//recitive則是只會更新物件裡的值，所以用這個裝物件跟陣列較好


app.component('login',Login)
app.component('index',Index)
app.component('new-password',NewPassword)
app.use(router)
app.use(createPinia())

// googleMaps API Key
app.use(VueGoogleMaps,{
    load: {
        key: 'AIzaSyDxntkw27-7sWJpO1fm3kWQnrx_5rCkznc'
    }
})
app.mount('#app')

import { createApp } from 'vue'

import Cookies from 'js-cookie'

import ElementPlus from 'element-plus'
import locale from 'element-plus/lib/locale/lang/zh-tw' // 中文語言

import '@/assets/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'
import directive from './directive' // directive

// 註冊指令
import plugins from './plugins' // plugins
import { download } from '@/utils/request'

// svg圖標
import 'virtual:svg-icons-register'
import SvgIcon from '@/components/SvgIcon'
import elementIcons from '@/components/SvgIcon/svgicon'

import './permission' // permission control

import { useDict } from '@/utils/dict'
import { parseTime, resetForm, addDateRange, handleTree, selectDictLabel, selectDictLabels } from '@/utils/ruoyi'

// 分頁組件
import Pagination from '@/components/Pagination'
// 自定義表格工具組件
import RightToolbar from '@/components/RightToolbar'
// 富文本組件
import Editor from "@/components/Editor"
// 文件上傳組件
import FileUpload from "@/components/FileUpload"
// 圖片上傳組件
import ImageUpload from "@/components/ImageUpload"
// 圖片預覽組件
import ImagePreview from "@/components/ImagePreview"
// 自定義樹選擇組件
import TreeSelect from '@/components/TreeSelect'
// 字典標簽組件
import DictTag from '@/components/DictTag'

const app = createApp(App)

// 全域方法掛載
app.config.globalProperties.useDict = useDict
app.config.globalProperties.download = download
app.config.globalProperties.parseTime = parseTime
app.config.globalProperties.resetForm = resetForm
app.config.globalProperties.handleTree = handleTree
app.config.globalProperties.addDateRange = addDateRange
app.config.globalProperties.selectDictLabel = selectDictLabel
app.config.globalProperties.selectDictLabels = selectDictLabels

// 全域組件掛載
app.component('DictTag', DictTag)
app.component('Pagination', Pagination)
app.component('TreeSelect', TreeSelect)
app.component('FileUpload', FileUpload)
app.component('ImageUpload', ImageUpload)
app.component('ImagePreview', ImagePreview)
app.component('RightToolbar', RightToolbar)
app.component('Editor', Editor)

app.use(router)
app.use(store)
app.use(plugins)
app.use(elementIcons)
app.component('svg-icon', SvgIcon)

directive(app)

// 使用element-plus 並且設置全域的大小
app.use(ElementPlus, {
  locale: locale,
  // 支持 large、default、small
  size: Cookies.get('size') || 'default'
})

app.mount('#app')

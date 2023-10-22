import tab from './tab'
import auth from './auth'
import cache from './cache'
import modal from './modal'
import download from './download'

export default function installPlugins(app){
  // 標籤操作
  app.config.globalProperties.$tab = tab
  // 認證物件
  app.config.globalProperties.$auth = auth
  // 緩存物件
  app.config.globalProperties.$cache = cache
  // 模態框物件
  app.config.globalProperties.$modal = modal
  // 下載文件
  app.config.globalProperties.$download = download
}

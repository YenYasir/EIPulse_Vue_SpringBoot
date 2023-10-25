import auth from './auth'
import cache from './cache'
import modal from './modal'
import download from './download'

export default {
  install(Vue) {
    // 認證對象
    Vue.prototype.$auth = auth
    // 緩存對象
    Vue.prototype.$cache = cache
    // 模組對象
    Vue.prototype.$modal = modal
    // 下載文件
    Vue.prototype.$download = download
  }
}

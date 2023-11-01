import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  resolve:{
    alias:{
      '@':'/src',
    }
  },
  server:{
    proxy:{
      '/eipulse':{
        target:'http://localhost:8090',
        changeOrigin:true,
        rewrite:(path)=>path.replace(/^\/eipulse/, '')
      }
    }
  }
})

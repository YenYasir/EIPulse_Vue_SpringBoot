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
    open: true, 
    proxy:{
      '/eipulse':{
        target:'http://localhost:8090',
        changeOrigin:true,
        rewrite:(path)=>path.replace(/^\/eipulse/, ''),
        onProxyReq: (proxyReq) => {
     
          proxyReq.setHeader('host', proxyReq.getHeader('host').replace('127.0.0.1', 'localhost'));
        },
      }
    }
  }
})

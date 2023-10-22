# EIPulseinSpringboot
EEIT71 final project with spring boot
更新日誌:

## 1022
更新EIPulse_Vue3 
VIte+Vue3 模板 
VSCODE開啟，終端-> 新終端
安裝依賴:終端輸入 npm install
執行web : 終端輸入 npm run dev，或bin資料夾中，run-web.bat

vite.config.js中 vite 相關配置註解那，設置後端server
預設:
 http://localhost:8090/eipulse/

功能綁死API 尚未有登入帳號情況下
src/permission.js 中const whiteList = ['/login', '/register']; 加入,'/index' ,可以繞過登入頁
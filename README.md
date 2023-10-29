# EIPulseinSpringboot

EEIT71 final project with spring boot
更新日誌:

## 10/22
更新EIPulse_Vue3 
VIte+Vue3 模板 
##
VSCODE開啟，終端-> 新終端

安裝依賴:終端輸入 npm install

執行web : 終端輸入 npm run dev，或bin資料夾中，run-web.bat
##
vite.config.js中 vite 相關配置註解那，設置後端server
預設:
 http://localhost:8090/eipulse/

功能綁死API 尚未有登入帳號情況下
src/permission.js 中const whiteList = ['/login', '/register']; 加入,'/index' ,可以繞過登入頁


## 10/28
後端: eclipse -> import existing maven project - Eipulse資料夾(需解壓縮)

eipulse-admin中src/resources的 application-druid.yml 改mysql配置 sql放在sql資料夾裡 先匯入eipulsedemo再匯入quartz

需安裝redis https://github.com/MicrosoftArchive/redis/releases 載Redis-x64-3.0.504.msi

安裝不要改端口 用預設的

前端Eipulse-Vue3用VSCODE開啟 npm install

執行專案 後端:eipulse-admin eipulse中的EipulseApplication

前端:npm run dev
## 10/30 
換回cli 
修復部門 無法正常顯示BUG
圖片 個人中心出現新BUG待修復
新增一些功能

# EIPulseinSpringboot

#EEIT71 final project with spring boot


## 日誌

EIPulse 前後端 整合功能
請務必每日檢查main分支取最新版本下來做修正


11/7 晚: 整合 彥宇 權限功能 | 1+ 薪資一般員工查詢 |  承員 聊天室優化 頭像顯示 | 裕凱 個人中心 
11/10 :  整合11/9內容  1+|前端  旭凱|版面 名字查詢 映君|行事曆   裕凱|更新上傳頭像 優化美術 操作  加強路由配置 NAVIBAR路由重寫
## TODOLIST:
### BUG:
- 1+ 版面皆無熱處理 須重啟server才會更新
- 上傳頭像 無法及時擷取圖片 需重新登入才會顯示，圖片上傳後增加cropper再送出
- 登入後登出 短時間內一般員工端登入會導向管理端
- router 需設beforeeach 不然資安近乎為0 改網址就能更動可視內容

### 功能待補:
- 主要色彩 
標題 text-bg-dark
表格 淺橘 rgb(255, 204, 0)
按鈕(橘) btn-warning |(深灰) btn-secondary
文字(橘) text-warninig |(深灰) text-secondary

快速輸入按鈕 新增
部門 緊急聯絡人

- 可擴充
全局 RWD響應設置
較嚴重的
find-emp.vue 

hamburger(縮小sidebar跟著浮動 縮小剩圖示)

profile.vue
click 頭像(V) 並用cropper裁圖上傳 即時顯示更新






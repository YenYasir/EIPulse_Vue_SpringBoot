# EIPulseinSpringboot

#EEIT71 final project with spring boot


## 日誌

EIPulse 前後端 整合功能
請務必每日檢查main分支取最新版本下來做修正


11/7 晚: 整合 彥宇 權限功能 | 1+ 薪資一般員工查詢 |  承員 聊天室優化 頭像顯示 | 裕凱 個人中心 
</br>
11/10 :  整合11/9內容  1+|前端  旭凱|版面 名字查詢 映君|行事曆   裕凱|更新上傳頭像 優化美術 操作  加強路由配置 NAVIBAR路由重寫
</br>
11/13: 裕凱 路由加強 新增員工申請表單 版面修正 | 承員 修改表單 聊天室
</br>
11/13: 1+ 薪資 VUE | 員 表單 | 裕凱 版面
</br> SQL 地端總表 放在sql_script裡面    1+ 版面 | 映君 布告欄 | 承員 版面 | 裕凱 路由 上傳頭像

## TODOLIST:
### BUG:
- 1+ 版面皆無熱處理 須重啟server才會更新
- 上傳頭像 無法及時擷取圖片 需重新登入才會顯示，圖片上傳後增加cropper再送出

- router 需設beforeeach 不然資安近乎為0 改網址就能更動可視內容

### 功能待補:
- 主要色彩 
標題 text-bg-dark
表格 淺橘 rgb(255, 204, 0)
按鈕(橘) btn-warning |(深灰) btn-secondary
文字(橘) text-warninig |(深灰) text-secondary


- 可擴充
全局 RWD響應設置
較嚴重的
find-emp.vue 



profile.vue
click 頭像(V) 並用cropper裁圖上傳 即時顯示更新(V)






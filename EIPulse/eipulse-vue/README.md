## 開發

```bash
# 克隆項目
git clone https://github.com/YenYasir/EIPulseinSpringboot

# 進入項目目錄
cd ruoyi-ui

# 安裝依賴
npm install

# 建議不要直接使用 cnpm 安裝依賴，會有各種詭異的 bug。可以通過如下操作解決 npm 下載速度慢的問題
npm install --registry=https://registry.npm.taobao.org

# 啟動服務
npm run dev
```

瀏覽器訪問 http://localhost:80

## 發布

```bash
# 構建測試環境
npm run build:stage

# 構建生產環境
npm run build:prod
```
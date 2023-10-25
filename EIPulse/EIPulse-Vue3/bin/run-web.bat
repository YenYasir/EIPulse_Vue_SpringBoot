@echo off
echo.
echo [訊息] 使用 Vite 命令運行 Web 。
echo.

%~d0
cd %~dp0

cd ..
npm run dev

pause
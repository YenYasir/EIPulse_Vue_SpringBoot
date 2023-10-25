@echo off
echo.
echo [訊息] 打包Web工程，生成dist文件。
echo.

%~d0
cd %~dp0

cd ..
npm build:prod

pause
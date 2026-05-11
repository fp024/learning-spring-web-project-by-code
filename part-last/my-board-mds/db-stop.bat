@echo off
echo Stopping Oracle Docker container...
cd /d "%~dp0"
docker compose stop
echo.
echo Done. Data is preserved. Run db-start.bat to restart.
pause


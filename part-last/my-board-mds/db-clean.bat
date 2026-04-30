@echo off
echo [WARNING] This will permanently delete the Oracle XE container and ALL data!
echo.
set /p confirm=Are you sure you want to proceed? (y/N):
if /i not "%confirm%"=="y" (
    echo Cancelled.
    pause
    exit /b 0
)

echo.
echo Stopping and removing Oracle XE container and volumes...
cd /d "%~dp0"
docker compose down -v
echo.
echo Done. The init-scripts will be re-executed on the next startup.
pause


@echo off
echo Starting Oracle Docker container...
cd /d "%~dp0"
docker compose up -d
echo.
echo Oracle is starting up. It may take 1-2 minutes to be fully ready.
echo   - JDBC URL : jdbc:oracle:thin:@//localhost:1521/FREEPDB1
echo   - Username : book_ex
echo   - Password : book_ex
echo.
echo Showing container logs now. Press Ctrl+C to stop watching logs.
docker compose logs -f oracle-free
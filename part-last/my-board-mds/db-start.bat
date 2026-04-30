@echo off
echo Starting Oracle XE Docker container...
cd /d "%~dp0"
docker compose up -d
echo.
echo Oracle XE is starting up. It may take 1-2 minutes to be fully ready.
echo   - JDBC URL : jdbc:oracle:thin:@//localhost:1521/FREEPDB1
echo   - Username : book_ex
echo   - Password : book_ex
echo.
echo To check logs: docker compose logs -f oracle-xe
pause
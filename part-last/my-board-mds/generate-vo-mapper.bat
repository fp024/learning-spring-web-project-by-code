@ECHO OFF
SETLOCAL
ECHO [Generate VO, Mapper...]
CALL ..\..\set-jdk-21-env.bat
CALL .\mvnw.cmd mybatis-generator:generate

echo.
echo Build log checked? Press Enter to close...
set /p dummyVar=""
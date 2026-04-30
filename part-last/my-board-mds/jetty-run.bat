@ECHO OFF
SETLOCAL
ECHO [Jetty Run...]
CALL ..\..\set-jdk-21-env.bat
CALL .\mvnw.cmd clean -Pjetty-run jetty:run
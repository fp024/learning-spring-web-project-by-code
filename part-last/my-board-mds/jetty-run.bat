@ECHO OFF
SETLOCAL
ECHO [Jetty Run...]
CALL ..\..\set-jdk-env.bat
mvnw clean -Pjetty-run jetty:run
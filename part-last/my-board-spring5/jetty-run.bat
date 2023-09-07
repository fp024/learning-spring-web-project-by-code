@ECHO OFF
SETLOCAL
CALL ..\..\set-jdk-env.bat
mvnw clean -Pjetty-run jetty:run
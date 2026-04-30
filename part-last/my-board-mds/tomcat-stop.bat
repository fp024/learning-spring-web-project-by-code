@ECHO OFF
SETLOCAL
ECHO [Tomcat Stop...]
CALL ..\..\set-jdk-21-env.bat
mvnw -Ptomcat-run cargo:stop
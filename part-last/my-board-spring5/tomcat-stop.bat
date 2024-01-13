@ECHO OFF
SETLOCAL
ECHO [Tomcat Stop...]
CALL ..\..\set-jdk-env.bat
mvnw -Ptomcat-run cargo:stop
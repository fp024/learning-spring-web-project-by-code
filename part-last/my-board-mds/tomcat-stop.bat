@ECHO OFF
SETLOCAL
ECHO [Tomcat Stop...]
CALL ..\..\set-jdk-21-env.bat
CALL .\mvnw -Ptomcat-run cargo:stop
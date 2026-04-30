@ECHO OFF
SETLOCAL
ECHO [Tomcat Run...]
CALL ..\..\set-jdk-21-env.bat
CALL .\mvnw.cmd clean package -Ptomcat-run -DskipTests cargo:run
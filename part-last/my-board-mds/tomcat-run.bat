@ECHO OFF
SETLOCAL
ECHO [Tomcat Run...]
CALL ..\..\set-jdk-21-env.bat
mvnw clean package -Ptomcat-run -DskipTests cargo:run
@ECHO OFF
SETLOCAL
ECHO [Tomcat Run...]
CALL ..\..\set-jdk-env.bat
mvnw clean package -Ptomcat-run -DskipTests cargo:run
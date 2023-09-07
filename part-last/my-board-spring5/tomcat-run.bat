@ECHO OFF
SETLOCAL
CALL ..\..\set-jdk-env.bat
mvnw clean package -Ptomcat-run -DskipTests cargo:run
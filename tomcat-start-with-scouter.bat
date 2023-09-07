@SETLOCAL
@CALL .\set-jdk-env.bat
@CALL .\set-scouter-env.bat
@SET MAVEN_OPTS=-Dscouter.agent.lib=%SCOUTER_JAVA_AGENT_LIB% -Dscouter.config.file=%SCOUTER_JAVA_AGENT_CONF%
@%LATEST_PROJECT_HOME%\mvnw -f %LATEST_PROJECT_HOME% clean package -DskipTests cargo:run -Ptomcat-run-with-scouter

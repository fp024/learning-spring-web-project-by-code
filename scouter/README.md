# Scouter 적용 테스트

> ✨ Scouter를 적용해보자! 



## Host Agent 실행 문제

#### 개인 사용자 커스터마이징 DLL 수정 파일

> 어떤 분이 컨버전을 한 것 같아서 받아서 DLL을 바꿔봤는데, Windows 10 + Java 8, 11, 17 환경에서는 여전히 안된다.

* https://github.com/cnstar9988/sigar/commit/9e5dba8

* sigar-amd64-winnt.dll
  * https://www.virustotal.com/gui/file/8713ce1cb22fb06af7148ea973809f66259d16cdb3f1666b4594f00f73c2a264

* 그래서 HostAgent 실행할 때에 한해서 Java 버전을 `7 Update 80`, `6 Update 45`로 낮춰봤는데.. 잘 동작한다.

  * `%Scouter_홈경로%\agent.host\host.bat `

    ```bat
    @call ..\..\setenv.bat
    @%JAVA_7_HOME%\bin\java -classpath ./scouter.host.jar   scouter.boot.Boot ./lib
    ```

    * setenv.bat에는 Java 7에 대한 경로 환경변수 설정을 해둠.



#### 그외 [Sigar](https://github.com/hyperic/sigar) 관련 이슈

* 시스템에 ODD 드라이브에 대해서 디스크 사용량 정보를 잘못처리해서 오류가 나는가보다. 😅
  * https://github.com/scouter-project/scouter/issues/740
    * 대안방법: ODD 드라이브 문자 제거



## Serlvet Container에 적용하기

Windows 환경에서 cargo 플러그인을 통한 Tomcat 9.x에는 적용함

* Scouter 수집 서버는 Rocky Linux 8 환경
* 웹 애플리케이션과 Host Agent는 Windows 10 환경
  * cargo로 tomcat 9.x 실행



### Scouter Java Agent 설정 파일

* `scouter/conf/board-webapp.conf`

  ```properties
  ### scouter java agent configuration sample
  obj_name=board-webapp
  net_collector_ip=lvm.scouter-server
  net_collector_udp_port=6100
  net_collector_tcp_port=6100
  #hook_method_patterns=sample.mybiz.*Biz.*,sample.service.*Service.*
  #trace_http_client_ip_header_key=X-Forwarded-For
  #profile_spring_controller_method_parameter_enabled=false
  #hook_exception_class_patterns=my.exception.TypedException
  #profile_fullstack_hooked_exception_enabled=true
  #hook_exception_handler_method_patterns=my.AbstractAPIController.fallbackHandler,my.ApiExceptionLoggingFilter.handleNotFoundErrorResponse
  #hook_exception_hanlder_exclude_class_patterns=exception.BizException
  
  ```

  * net_collector_ip 에는 Scouter 서버를 실행중인 Rocky Linxu 8 호스트명 넣었음.

### 환경 변수 정의  배치 파일 

* `setenv.bat`

  ```bat
  @SET SCOUTER_JAVA_AGENT_CONF=scouter\conf\board-webapp.conf
  @SET SCOUTER_JAVA_AGENT_LIB=C:\scouter\scouter\agent.java\scouter.agent.jar
  @SET LATEST_PROJECT_HOME=part07\ex06-board
  @echo SCOUTER_JAVA_AGENT_CONF=%SCOUTER_JAVA_AGENT_CONF%
  @echo SCOUTER_JAVA_AGENT_LIB=%SCOUTER_JAVA_AGENT_LIB%
  @echo LATEST_PROJECT_HOME=%LATEST_PROJECT_HOME%
  ```

  * SCOUTER_JAVA_AGENT_CONF: Java Agent 설정파일 경로
  * SCOUTER_JAVA_AGENT_LIB: Java Agent 라이브러리 파일 경로
  * LATEST_PROJECT_HOME: 적용할 프로젝트 홈 경로



### pom.xml에서 cargo 설정

* `study-dependencies-parent/pom.xml`

  ```xml
  <!-- 실제 Tomcat 9 배포 실행 테스트 -->
        <!-- mvn clean package -DskipTests cargo:run -->
        <plugin>
          <groupId>org.codehaus.cargo</groupId>
          <artifactId>cargo-maven3-plugin</artifactId>
          <version>${cargo.version}</version>
          
          <configuration>
            <container>
              <containerId>tomcat9x</containerId>
              <systemProperties>
                <file.encoding>UTF-8</file.encoding>
                <scouter.config>${project.basedir}/../../${scouter.config.file}</scouter.config>
              </systemProperties>
              <zipUrlInstaller>
                <url>https://repo.maven.apache.org/maven2/org/apache/tomcat/tomcat/${cargo-tomcat9x.version}/tomcat-${cargo-tomcat9x.version}.zip</url>
                <downloadDir>${project.build.directory}/downloads</downloadDir>
                <extractDir>${project.build.directory}/extracts</extractDir>
              </zipUrlInstaller>
            </container>
            <configuration>
              <type>standalone</type>
              <properties>
                <cargo.start.jvmargs><!-- 
                -->-javaagent:${scouter.agent.lib} <!--
                -->--illegal-access=warn <!--
                -->--add-opens=java.base/java.lang=ALL-UNNAMED <!--
                -->-Djdk.attach.allowAttachSelf=true <!--
                --></cargo.start.jvmargs>
                <cargo.servlet.port>${cargo-server-port}</cargo.servlet.port>
              </properties>
            </configuration>
            <deployables>
              <deployable>
                <groupId>${project.groupId}</groupId>
                <artifactId>${project.artifactId}</artifactId>
                <type>war</type>
                <properties>
                  <context>${cargo-context-path}</context>
                </properties>
              </deployable>
            </deployables>
          </configuration>
        </plugin>
  ```
  
  * cargo.start.jvmargs
  
    * `-javaagent`: Scouter Java Agent 라이브러리 지정
    * `--add-opens=java.base/java.lang=ALL-UNNAMED`는 Java 17 실행시 unnamed module 관련 오류 해결 목적으로 추가
  
  * scouter.config
  
    * Scouter Java Agent  설정 파일 경로 지정
  
    

### Cargo  Maven Plugin으로 웹 애플리케이션 실행 배치 파일

* start-server-with-scouter.bat

  ```bat
  @call .\setenv.bat
  @SET MAVEN_OPTS=-Dscouter.agent.lib=%SCOUTER_JAVA_AGENT_LIB% -Dscouter.config.file=%SCOUTER_JAVA_AGENT_CONF%
  @%LATEST_PROJECT_HOME%\mvnw -f %LATEST_PROJECT_HOME% clean package -DskipTests cargo:run
  ```

  * `cargo:run`으로 웹 애플리케이션 실행



## Scouter Client (Eclipse 기반 프로그램)로 성능 모니터링

![scouter-client](doc-resources/scouter-viewer.png)

게시판 프로젝트 켜서 F5 또는 연속 클릭으로 잘 나오는지 확인해보았음.

상태를 볼수는 있지만 역시나... 부하를 주는 프로그램에 대해 학습이 필요할 것 같다. ([게틀링](https://gatling.io/) 같은...)



## 🎇 추가로 해야할 일  (아래 것 들은... 천천히 하자~)

- [x] Linux 환경용 스크립트 파일 만들기

- [ ] https://gatling.io/ 으로 부하 시나리오 만들어보기

- [x] Maven 웹 애플리케이션 실행 프로필 분리

  [study-dependencies-parent/pom.xml](../study-dependencies-parent/pom.xml)

  * 기본 상태에서는 Scouter를 연동할 필요가 없어서 Scouter를 연동하지 않은 기본 프로필을 기본 값으로 분리했다. (`-P{프로필_ID}`로 프로필을 주지않고 mvn을 실행하면 Scouter 연동없이 실행한다.)

    * `default-webapp-run`: 기본 웹 애플리케이션 실행 : jetty 또는 tomcat

    * `webapp-run-with-scouter`: Scouter를 활성화하여 웹 애플리케이션 실행 : jetty 또는 tomcat



- [x] Jetty로도 어떻게 할 수 없는지 더 알아보기. 

  * 프로젝트 루트에 jetty와 tomcat용 배치파일을 만들었다.
    * [jetty-start-with-scouter.bat](../jetty-start-with-scouter.bat)
    * [tomcat-start-with-scouter.bat](../tomcat-start-with-scouter.bat)
  * FORK모드로 Jetty를 실행하면 jvmArgs를 잘 전달 할 수 있었다.



## 의견

* 윈도우 환경에서 게시판 스터디 프로젝트를 간편하게 Scouter와 연동할 수 있게 구성할 수 있어서 좋았다.

* Jetty 연동은 조금 막혔었는데, 해결되서 다행이다. 🎉




## 추가 사항

### Attach API cannot be used to attach to the current VM by default 	

* Scouter [개발자님 답글](https://github.com/scouter-project/scouter/issues/866#issuecomment-1236034288)중에 `--add-opens` 설정 외에도 `jdk.attach.allowAttachSelf=true` 옵션이 있어서 추가함. 

  ```
  --add-opens=java.base/java.lang=ALL-UNNAMED -Djdk.attach.allowAttachSelf=true
  ```

* https://www.oracle.com/java/technologies/javase/9-notes.html#JDK-8178380

* Java 9 부터 현재 VM에 연결하기 위한 Attach API를 기본으로 사용할 수 없게 설정되어있어서, 호환성을 위해 허용하도록 추가해주는 설정 같다.



### `cargo:run` 으로 프로젝트를  nohup로 백그라운드 실행한 상태에서 `cargo:stop`으로 Tomcat종료시 프로세스가 남는 문제

* https://codehaus-cargo.github.io/cargo/Maven+3+Plugin.html

* `cargo:run`으로 프로젝트를 시작하면 이런 메시지가 남음

  ```
  ...
  [INFO] [talledLocalContainer] Tomcat 9.x started on port [8080]
  [INFO] Press Ctrl-C to stop the container...
  ...
  ```

  * Ctrl+C 입력을 받기 위해서 Maven 부모프로세스가 대기하고 있고, 자식으로 Tomcat을 실행하는 것으로 보임.

    ```
    
    # Maven Wrapper 부모 프로세스 1850
    0 S fp024       1850    1845  9  80   0 - 843381 -     06:07 pts/0    00:00:11 ... org.apache.maven.wrapper.MavenWrapperMain clean package -DskipTests cargo:run -Pwebapp-run-with-scouter
    
    # Cargo가 실행한 Tomcat 9.x 프로세스 (2256)
    0 S fp024       2256    1850 12  80   0 - 1086730 -    06:08 pts/0    00:00:13 ... org.apache.catalina.startup.Bootstrap start
    
    ```

  * 이때 다른 bash창을 열어  `cargo:stop`를 실행 하면 Tomcat(자식 프로세스:2256)만 종료시키기 때문에 Maven (부모 프로세스:1850)가 제대로 종료되지 않음. 🎃

  stop을 하면 전부 종료시켜주면 좋은데... 좋은 방법을 찾을 때까지... cargo는 백그라운드로 실행하지 말아야겠다.





###  scouter의   jakarta  servlet 을 지원하는 새로운 버전(`2.2.0`)이 나왔다. 

새로운 버전을 적용하면  Spring 6 + Hibernate 6 + Tomcat 10.1 을 적용한  [my-board](../part-last/my-board) 프로젝트에 모니터링을 적용할 수 있다. 😊👍

🎇 설치는 잘 하였다. [my-board](../part-last/my-board) 프로젝트의 유입 모니터링도 잘 처리함을 확인하였음 🎉🎊✨

* agent.host

  *  런타임 환경을  Java 11로 실행하던 것 17로 올림 (20까지 지원 하신다고 함.)

* 수집 서버 

  * 수집 서버는 아직은 `Java 8`로 써야하는 것 같다. 
    * 11에서도 동작하지만, 수집서버가 사용하는 라이브러리들이 구버전이라  Java 8  버전을 쓰는게 나을 것 같음.
    * sun.misc.Unsafe::defineClass 를 사용하는 부분이 있는데.. JDK 17에서는 제거되서 그런 것 같음. JDK 11에서는 잘됨.
    * `jaxb-impl-2.3.0.1.jar` 가 최신  Java  버전과 호환되지 않는 것 같음.
    * https://github.com/scouter-project/scouter/issues/901
    * https://stackoverflow.com/questions/55918972/unable-to-find-method-sun-misc-unsafe-defineclass


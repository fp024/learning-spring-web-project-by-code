## log4j 에서 log4j2로 전환



log4j가 1.2.17 를 마지막으로 수명이 끝났고, 보안 위협도 존재하여 log4j2로 전환한다.

* 참조: https://logging.apache.org/log4j/1.2/

  > **End of Life**
  >
  > 2015년 8월  5일 로깅 서비스 프로젝트 관리 위원회는 Log4j 1.x의 수명이 종료되었다고 발표했습니다. 발표의 전체 텍스트는 [Apache 블로그](http://blogs.apache.org/foundation/entry/apache_logging_services_project_announces)를 참조하세요. Log4j 1 사용자는 [Apache Log4j 2](http://logging.apache.org/log4j/2.x/index.html)로 업그레이드 하는 것을 권장합니다.
  >
  >  **보안 취약점**
  >
  > Log4j 1에 대해 보안 취약점인 [CVE-2019-17571](https://www.cvedetails.com/cve/CVE-2019-17571/)이 식별되었습니다. Log4j에는 직렬화된 로그이벤트를 수락하고 객체의 허용여부를 확인하지 않고 이를 역직렬화 하는 SockerServer가 포함되어있습니다. 이것은 악용될 수 있는 공격 벡터를 제공할 수 있습니다. Log4j 1은 더이상 유지 관리되지 않으므로 이 문제는 수정되지 않습니다. 사용자는 Log4j로 업그레이드 해야합니다.

현재 스터디 프로젝트에서는 Eclipse의 New Spring Legacy Project의  Spring MVC Project 템플릿을 이용해서 프로젝트 템플릿을 만들어서 그것을 수정해서 사용하고 있는데, log4j 1.2.x기반이다.

이미 Spring 버전을 5.2.x 대로 올려서 쓰고 있고, 그외 라이브러리도 최신으로 사용하는데다, log4j.xml 의 설정도 단순 콘솔 출력 수준이라서 변경은 어렵지 않을 것 같다.

#### test 리소스의 log4j.xml - Log4j 1 변경전

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE log4j:configuration PUBLIC "-//APACHE//DTD LOG4J 1.2//EN" "log4j.dtd">
<log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">

	<!-- Appenders -->
	<appender name="console" class="org.apache.log4j.ConsoleAppender">
		<param name="Target" value="System.out" />
		<layout class="org.apache.log4j.PatternLayout">
			<param name="ConversionPattern" value="%-5p: %c - %m%n" />
		</layout>
	</appender>
	
	<!-- Application Loggers -->
	<logger name="org.fp024">
		<level value="info" />
	</logger>
	
	<!-- mapper 인터페이스 경로 또는 이름을 지정해줘도 어느정도 로그 보는데는 지장이 없어보임. -->
	<!-- https://hashcode.co.kr/questions/1715/spring4-mybatis-%EC%BF%BC%EB%A6%AC-%EB%A1%9C%EA%B7%B8-%EB%82%A8%EA%B8%B0%EB%8A%94-%EB%B0%A9%EB%B2%95%EC%97%90-%EB%8C%80%ED%95%B4%EC%84%9C-%EB%AC%B8%EC%9D%98%EB%93%9C%EB%A6%BD%EB%8B%88%EB%8B%A4 -->
	<!--  MyBatis 3.2부터는 myBatis에서 지정하는 namespace별로 로그레벨을 지정할 수 있음. -->
	<logger name="org.fp024.mapper">
		<level value="trace" />
	</logger>
	
	<!-- 3rdparty Loggers -->
	<logger name="org.springframework.core">
		<level value="info" />
	</logger>	
	
	<logger name="org.springframework.beans">
		<level value="info" />
	</logger>
	
	<logger name="org.springframework.context">
		<level value="info" />
	</logger>

	<logger name="org.springframework.web">
		<level value="info" />
	</logger>

	<!-- Root Logger -->
	<root>
		<priority value="info" />
		<appender-ref ref="console" />
	</root>
	
</log4j:configuration>

```



#### test 리소스의  log4j2.xml - Log4j2 변경 후

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <!-- Appenders -->    
    <Appenders>
        <Console name="STDOUT" target="SYSTEM_OUT">
            <PatternLayout pattern="%-5p: %c - %m%n"/>
        </Console>
    </Appenders>
    
    <!-- Application Loggers -->
    <Loggers>
        <Logger name="org.fp024" level="info"/>

        <!-- mapper 인터페이스 경로 또는 이름을 지정해줘도 어느정도 로그 보는데는 지장이 없어보임. -->
        <!-- https://hashcode.co.kr/questions/1715/spring4-mybatis-%EC%BF%BC%EB%A6%AC-%EB%A1%9C%EA%B7%B8-%EB%82%A8%EA%B8%B0%EB%8A%94-%EB%B0%A9%EB%B2%95%EC%97%90-%EB%8C%80%ED%95%B4%EC%84%9C-%EB%AC%B8%EC%9D%98%EB%93%9C%EB%A6%BD%EB%8B%88%EB%8B%A4 -->
        <!--  MyBatis 3.2부터는 myBatis에서 지정하는 namespace별로 로그레벨을 지정할 수 있음. -->
        <Logger name="org.fp024.mapper" level="trace"/>
        
        <!-- 3rdparty Loggers -->
        <Logger name="org.springframework.core" level="info"/>      
        <Logger name="org.springframework.beans" level="info"/>     
        <Logger name="org.springframework.context" level="info"/>           
        <Logger name="org.springframework.web" level="info"/>

        <!-- Root Logger -->
        <Root level="info">
            <Appender-Ref ref="STDOUT" />
        </Root>
    </Loggers>
</Configuration>
```



#### pom.xml 의 변경, 제거해야할 부분

* commons-logging을 제외설정한 부분을 **제거**한다. Spring 5.2.x에서는 commons-logging에 대한 디펜던시가 없다.

    ```xml
    <!-- Spring -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>${org.springframework-version}</version>
        <exclusions>
            <!-- Exclude Commons Logging in favor of SLF4j -->
            <exclusion>
                <groupId>commons-logging</groupId>
                <artifactId>commons-logging</artifactId>
                </exclusion>
        </exclusions>
    </dependency>
    ```



* log4j 관련 디펜던시 **제거**

  ```xml
  <!-- // spring-jcl-5.2.17.RELEASE 에 같은 패키지로 포함이 되어있다.
  <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>jcl-over-slf4j</artifactId>
      <version>${org.slf4j-version}</version>
      <scope>runtime</scope>
  </dependency>
  -->		
  <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-log4j12</artifactId>
      <version>${org.slf4j-version}</version>
      <scope>runtime</scope>
  </dependency>
      <dependency>
      <groupId>log4j</groupId>
      <artifactId>log4j</artifactId>
      <version>1.2.17</version>
      <exclusions>
          <exclusion>
              <groupId>javax.mail</groupId>
              <artifactId>mail</artifactId>
          </exclusion>
          <exclusion>
              <groupId>javax.jms</groupId>
              <artifactId>jms</artifactId>
          </exclusion>
          <exclusion>
              <groupId>com.sun.jdmk</groupId>
              <artifactId>jmxtools</artifactId>
          </exclusion>
          <exclusion>
              <groupId>com.sun.jmx</groupId>
              <artifactId>jmxri</artifactId>
          </exclusion>
      </exclusions>
      <scope>runtime</scope>
  </dependency>
  ```

  

* 새로 추가해야할 내용

  * 라이브러리 버전은 log4j-bom으로 관리하는 것이 나을 것 같고, slf4j 사용을 위해 log4j-slf4j-impl을 추가했다.
    * slf4j에 익숙해서 이렇게 쓰는데, 나중에는 log4j2 그대로 쓰는게 나을 것 같다.

  ```xml
  <properties>
      ...
      <log4j2.version>2.15.0</log4j2.version>
      ...
  </properties>
  ...
  <dependencyManagement>
      <dependencies>
          <dependency>
              <groupId>org.apache.logging.log4j</groupId>
              <artifactId>log4j-bom</artifactId>
              <version>${log4j2.version}</version>
              <scope>import</scope>
              <type>pom</type>
          </dependency>
          ....   
      </dependencies>
  </dependencyManagement> 
  ...
  <!-- Apache Log4j SLF4J Binding  -->
  <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-slf4j-impl</artifactId>
  </dependency>
  ```

  

## Log4j2 에서도 보안 이슈가 있어 2.15.0 이상으로 버전업 해야한다.

* Apache Log4j 2 취약점 주의 및 업데이트 권고(CVE-2021-44228)
  https://asec.ahnlab.com/ko/29479/

* Huntress Log4Shell Testing Application
  https://github.com/huntresslabs/log4shell-tester

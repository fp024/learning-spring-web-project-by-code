# IntelliJ와 Gradle을 이용하는 스프링 환경



부록에서는 IntelliJ와 Gradle을 이용해서 스프링 프로젝트를 만드는 법을 설명하고 있다.

그런데, 나는 아래처럼 초기 gradle 프로젝트를 만든다음 IntelliJ 나 Visual Studio Code등으로 불러와서 개발했다.

1. `gradle init`으로 커맨드 라인에서 초기 프로젝트 만듬

2. init으로 만들 때, 루트 프로젝트 안에 app이 붙는 구조로 되어있는데, 단일 구조로 바꾸는게 편한 프로젝트라면 단일로 바꿈.

3. build.gradle 에서 필요한 디펜던시 추가

4. 라이브러리 버전 관리는 프로젝트 루트에 `gradle.properties` 파일 만들어서 정리

5. lombok은 https://plugins.gradle.org/plugin/io.freefair.lombok 플러그인 사용

   ```groovy
   plugins {
     id "io.freefair.lombok" version "${lombokPluginVersion}"
   }
   ```

6. log4j는 디펜던시는 `log4j-slf4j-impl` 만 추가하고 설정파일은 log4j2.xml 파일 만들어서 사용

   ```groovy
   implementation "org.apache.logging.log4j:log4j-slf4j-impl:${log4jVersion}"
   ```

7. WAS 설정은 IDE에서 연동하는 것보다 Embedded Jetty나 Tomcat 사용하는 것이 편하여 gretty 사용

   ```groovy
   plugins {
       id 'java'
       id 'war'
       id "org.gretty" version "${grettyVersion}" // 3.0.7 버전, 최신버전은 Servlet 버전이 5.x여서 현재 프로젝트 환경에서는 실행할 수가 없다.
       // (...) 
   }
   
   // (...)
   gretty {
       httpPort = 8080
       contextPath = "/"
       servletContainer = "jetty9.4"  // tomcat9 도 가능한데, Jetty가 안정적인 것 같다.
   }
   ```

   * WAS 실행할 때는 커맨드라인에서 아래 명령으로 입력

     ```bash
     $ gradle appRun
     ```

     

## 의견

최종 프로젝트를 Gralde 로 전환하기는 좀 어렵고 😅 (linux 프로파일 나눈거나 cargo 안썼으면... 했을지도..) 초반의 단순 프로젝트를 위의 내용대로 해볼까? 말까했는데.. 모르는 내용이 아니니... 안해도 되겠다.



### 🎇🎇🎇 책이 드디어 끝났다.. 저자님들이 많을 것을 알려주시려고 노력을 쏟아부은 책이란 생각이 들었습니다.  잘보았습니다. 감사합니다. 🎇🎇🎇
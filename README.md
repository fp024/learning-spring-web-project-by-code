# learning-spring-web-project-by-code

## 코드로 배우는 스프링 웹 프로젝트 스터디  (저자: 구멍가게코딩단)

* 도서 판매처

    * yes24
        * http://www.yes24.com/Product/Goods/64340061
    * 교보문고
        * https://www.kyobobook.co.kr/product/detailViewKor.laf?barcode=9791189184018

    

* 실습 진행 환경
  
    * 개발도구
        * STS 3, Eclipse, IntelliJ, VS Code
    * JDK
        * [Temurin OpenJDK 17](https://adoptium.net/temurin/archive)
        * 책 환경: Oracle JDK 1.8
        
    * Database
        * Oracle 18c Express Edition
            * 책 환경: Oracle 11c Express Edition
    
        * [HyperSQL DB 2.6.x](http://hsqldb.org/)
            * 일부 단순 프로젝트, 최종 프로젝트
                * Java 기반 설정과 [Mybatis Dynamic SQL](https://mybatis.org/mybatis-dynamic-sql/docs/introduction.html)를 적용한 ~~최종장 프로젝트는 Oracle이 없더라도 Pull 만 받아서 바로 실행가능하도록 HyperSQL DB Embedded 적용 목표.~~
                  * DB만 HyperSQL DB로 바꾸는 것은 의미가 없는 것 같다. Oracle로 쓰기로 했음. 완료 후에 여유있을 때, jex 최종장 프로젝트를 JPA로 바꾸는 것이 나을 것 같다.
        
    * WAS
        * [Jetty 10.x](https://mvnrepository.com/artifact/org.eclipse.jetty/jetty-maven-plugin) (Maven Jetty 플러그인 실행)
        * [Tomcat 8.5.x](https://tomcat.apache.org/download-80.cgi)
    * 빌드 도구
        * [Maven 3.8.x](https://maven.apache.org/index.html)
    

## 스터디 진행 목차

* [Part 1. 스프링 개발환경 구축](part01)
  * [데이터베이스 설정](part01/ex00/database)
  * [데이터베이스 스키마 변경과정](db-schema.md)
* [Part 2. 스프링 MVC설정](part02)
* [Part 3. 기본적인 웹 게시물 관리](part03)
* [Part 4. REST 방식과 Ajax를 이용하는 댓글 처리](part04)
* [Part 5. AOP와 트랜젝션](part05)
* [Part 6. 파일 업로드 처리](part06)
* [Part 7. Spring Web Security를 이용한 로그인 처리](part07)
* 부록



## 라이브러리 버전 관리 목적의 Maven Parent 프로젝트

디펜던시하고 있는 라이브러리들의 버전 관리를 하나의 POM에서 하기 위해서, 부모 POM으로 분리해두었음.

* [study-dependencies-parent](study-dependencies-parent)



## 의견

* Oracle JDK는 개인 용도 사용이 아닌 경우라면 라이선스 문제가 있을 수 있어, OpenJDK 설치로
  소개해주시면 좋을 것 같습니다.
    * https://adoptium.net/temurin/archive
    * Oracle의 Java SE TCK 테스트 통과
        * https://blog.adoptopenjdk.net/2021/08/goodbye-adoptopenjdk-hello-adoptium/

* Part1에서 lombok의 @Setter를 통해 @Autowired를 붙이는 것을 가이드 해주셨는데,
  클래스의 멤버 변수에 직접 @Autowired를 붙일 수 있으므로 반드시 필요한 부분이 아닐 것 같습니다.
  JDK버전에 따라 코드를 변경해야할 수도 있으므로 필드에 붙이는게 나아보입니다.

* 보통 JUnit 테스트 클래스명은 Test로 끝나는 것이 일반적으로 보였는데, Tests로 하셔서 뭔가 특이하긴했습니다.

* p92. RootConfig 클래스의 일부에서 sqlSessionFactory.getObject() 반환 부분
    * getObject() 메서드가 SqlSessionFactory를 반환하기 때문에 명시적 형변환이 필요없습니다.

* @Slf4j를 사용하면 로그 구현체를 바꾸더라도 코드 수정이 없어서, @Log4j 보다 나을 것 같긴한데,
  연습할 때는 @Slf4j 규칙에 맞게 로깅 부분을 수정했습니다.

* Eclipse 나 STS 사용시  MyBatipse 플러그인이 Mapper XML 파일 생성 및 작성시 도움이 되었습니다.

* Part1 - log4jdbc-log4j2 설정관련..
  적용을 하려다, 아래 사유로 적용을 하지 않았습니다.
    * JDK 11, ojdbc8 실행환경
  
    * 2013년 이후로 라이브러리 업데이트가 되지 않고 있음.

    * 데이터 소스 생성시 드라이버 클래스로 로깅 라이브러리의 Spy클래스를 쓰는 것에 거부감이 듬.
  
	  * MyBatis에서 처리해주는 로깅 방식 사용  
	    test 리소스의 log4j.xml 에다 mapper에 대한 TRACE 로거를 설정하였습니다.
	    ```xml
		<Logger name="org.fp024.mapper" level="trace"/>
		```
  	
	  * 결과
	    ```
	    INFO : org.fp024.persistence.TimeMapperTest - getTime2
	    DEBUG: org.fp024.mapper.TimeMapper.getTime2 - ==>  Preparing: SELECT sysdate FROM dual
	    DEBUG: org.fp024.mapper.TimeMapper.getTime2 - ==> Parameters: 
	    TRACE: org.fp024.mapper.TimeMapper.getTime2 - <==    Columns: SYSDATE
	    TRACE: org.fp024.mapper.TimeMapper.getTime2 - <==        Row: 2021-03-19 01:02:47
	    DEBUG: org.fp024.mapper.TimeMapper.getTime2 - <==      Total: 1
	    INFO : org.fp024.persistence.TimeMapperTest - 2021-03-19 01:02:47
      ```
      MyBatis 3.2 부터 이렇게 할 수 있다고 합니다.
  
  * **Log4j 1이 End of Life 상태이고 보안취약점이 있어, 개정판에는 Log4j 2로 변경되야할 것 같습니다.**
    
    * [Spring MVC Template 생성 프로젝트의 Log4j 1에서 Log4j 2 전환](./migrate-log4j-1-to-log4j-2.md)



## 서버 실행 / 정지/ 로그 보기 스크립트

가상 머신에 띄어두기위해서 서버 실행/정지 스크립트를 만들고 크론탭으로 서버 시작시 실행되게 하였다.

* 최신 테스트할 프로젝트에 대해 latest 란 심볼릭 링크를 우선 만든다.

  ```bash
  cd /home/fp024/git-fp024/learning-spring-web-project-by-code
  ln -s part04/jex03 latest
  ```
  
* [start-server.sh](start-server.sh) : 서버 시작

* [stop-server.sh](stop-server.sh) : 서버 종료

* [show-log.sh](show-log.sh): 실행 로그 보기 (최초 시작후 로그만 남김, log rotate등은 적용하지 않음, 재부팅 때마다 새로 갱신)

* `crontab -e` 설정

  ```bash
  # 코드로 배우는 스프링 웹프로젝트 스터디 게시판 서버 시작
  @reboot /home/fp024/git-fp024/learning-spring-web-project-by-code/start-server.sh
  ```


### Linux  환경 스크립트에서 사용하는  환경변수  설정 파일

스크립트에서 사용하는 환경 설정 파일 정의

* env.properties

  ```properties
  JAVA_HOME=/home/fp024/.sdkman/candidates/java/current
  MAVEN_HOME=/home/fp024/.sdkman/candidates/maven/current
  
  # 최종 진행 완료한 프로젝트를 명시, Linux 스크립트로 시작, 종료시 사용
  LATEST_HOME=part04/jex03
  ```



## Visual Studio Code 에서 Java Project 인식 시간이 너무 느릴 때... 😥

* `Ctrl` + `<Shift>` + `P` 로 모든 명령 연 다음..
* java clean 이라고 입력하면...
* `Java: Clean Java Language Server Workspace` 명령이 뜨는데 이것을 실행해주자! 😄





## 정오표

* 각 진행 문서에 작성


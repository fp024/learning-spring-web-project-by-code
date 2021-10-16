# Part 3. 기본적인 웹 게시물 관리

## 07. 스프링 MVC 프로젝트의 기본 구성

### 7.1 각 영역의 Naming Convention(명명 규칙)

* 이름 규칙
  * 컨트롤러
    * xxxController
  * 서비스
    * xxxService (인터페이스), xxxServiceImpl (구현 클래스)
  *  레파지토리
    * DAO (Data-Access-Object)
    * Repository
    * 이 책에서는 MyBatis Mapper 인터페이스로 사용함
  * 도메인
    * VO: Read Only 목적이 강함, 데이터 자체도 불편하게 설계하는 것이 정석
    * DTO: 데이터 수집의 용도가 강함
    * 이 책에서 테이블과 관련된 데이터는 VO란 이름을 사용함.

### 7.2 프로젝트를 위한 요구사항

* 게시판의 요구사항

  * 고객은 새로운 게시물을 등록할 수 있어야한다.
  * 고객은 특정한 게시물을 조회할 수 있어야한다.
  * 고객은 작성한 게시물을 삭제할 수 있어야한다.
  * ...

  

### 7.3 예제 프로젝트 구성

 *   프로젝트: ex02

     * Oracle 드라이버는 메이븐 레파지토리로 받을 수 있다.

       ```xml
       <dependency>
           <groupId>com.oracle.database.jdbc</groupId>
           <artifactId>ojdbc8</artifactId>
           <version>18.3.0.0</version>
       </dependency>
       ```

     * 그외 몇몇 라이브러리는 최신으로 수정. 

       * Java 11

       * Spring 5.2.17

       * Junit5 5.8.1

       * ...

         ```xml
         <properties>
             <java-version>11</java-version>
             <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
             <maven.compiler.source>${java-version}</maven.compiler.source>
             <maven.compiler.target>${java-version}</maven.compiler.target>
             <org.springframework-version>5.2.17.RELEASE</org.springframework-version>
             <org.aspectj-version>1.9.7</org.aspectj-version>
             <org.slf4j-version>1.7.32</org.slf4j-version>
         </properties>
         ```

* 테이블 생성

  ```sql
  CREATE SEQUENCE seq_board;
  
  CREATE TABLE tbl_board (
      bno         NUMBER(10,0),
      title       VARCHAR2(200)   NOT NULL,
      content	    VARCHAR2(2000)  NOT NULL,
      writer      VARCHAR2(50)    NOT NULL,
      regdate     DATE            DEFAULT SYSDATE,
      updatedate  DATE            DEFAULT SYSDATE
  );
  
  ALTER TABLE tbl_board ADD CONSTRAINT pk_board
      PRIMARY KEY (bno);
  ```

* Dummy 데이터 생성

  ```sql
  INSERT INTO tbl_board (bno, title, content, writer)
    VALUES (seq_board.nextval, '테스트 제목', '테스트 내용', 'user00'); 
  ```

* book_ex 계정에 대해 권한은 줬는데, 공간할당을 정의해주지 않아서 INSERT시 오류가 발생했는데...

  * 나만 사용하는 테스트 DB여서 무제한으로 설정해주었다.
      ```sql
      ALTER USER book_ex DEFAULT TABLESPACE SPRING_TEST QUOTA UNLIMITED ON SPRING_TEST;
      -- 일단 무제한으로 주었다. 500M제한을 준다면 아래처럼 하자
      -- ALTER USER book_ex QUOTA 500M ON SPRING_TEST;
      ```
      



### 7.4 데이터베이스 관련 설정 및 테스트

* 이전과 마찬가지로 log4jdbc 설정없이 mapper 패키지 로깅방식으로 쿼리 로그를 출력하게 했다. test resource의 log4j.xml 참고

* JUnit 5로 설정한 테스트 케이스를 돌리려면  maven-surefire-plugin 을 버전업 해두자!

  ```xml
  <pluginManagement>
      <plugins>
          <!-- JUnit 5 사용을 위해서 maven-surefire-plugin 버전을 올려두자! -->
          <plugin>
              <artifactId>maven-surefire-plugin</artifactId>
              <version>2.22.2</version>
          </plugin>
          ...
  ```

* mvnw 로 명령 프롬프트에서 실행하면 경고가 남는 부분이 있다. IDE에서도 남던 경고인데, 그동안 신경을 안써서 몰랐다.

  ```
  [WARNING] [path] bad path element "C:\Users\사용자_이름\.m2\repository\com\oracle\database\jdbc\ojdbc8\18.3.0.0\oraclepki.jar": no such file or directory
  [WARNING] [path] bad path element "C:\Users\사용자_이름\.m2\repository\com\oracle\database\security\oraclepki\18.3.0.0\osdt_core.jar": no such file or directory
  [WARNING] [path] bad path element "C:\Users\사용자_이름\.m2\repository\com\oracle\database\security\oraclepki\18.3.0.0\osdt_cert.jar": no such file or directory
  [WARNING] [path] bad path element "C:\Users\사용자_이름\.m2\repository\com\oracle\database\security\oraclepki\oracle.osdt\osdt_core.jar": no such file or directory
  [WARNING] [path] bad path element "C:\Users\사용자_이름\.m2\repository\com\oracle\database\security\oraclepki\oracle.osdt\osdt_cert.jar": no such file or directory
  [WARNING] No processor claimed any of these annotations: /org.junit.jupiter.api.Test,/org.junit.jupiter.api.extension.ExtendWith,/org.springframework.test.context.ContextConfiguration,/org.springframework.beans.factory.annotation.Autowired
  ```

  * 위에 파일 없다는 것은 실제로 없긴한데, 디펜던시 트리상으로 다른 경로에 정상적으로 설정이 되어있음. Oracle 쿼리 동작에도 문제가 없는 상태인데... 뭔가 이상하긴 하지만 무시해도 될 경고 같다.

  * No processor claimed any of these annotations 는 어노테이션을 요구한 프로세서가 없다는데, 실제로 mvnw에서 test 골이 잘 동작하고, Spring 도 정상적으로 동작했다. 이것도 무시해도 될 것 같다.

    ```xml
    <!--
    	명시적으로 compilerArgs 나, showWarnings를 설정하지 않으면 경고가 안보여서 그동안 잘 몰랐던 것 같다.
        이 프로젝트의 경우는 아래 설정이 있어서 경고가 노출되었다.
    -->
    
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.8.1</version>
        <configuration>
            <source>${java-version}</source>
            <target>${java-version}</target>
            <compilerArgs>
                <arg>-Xlint:all</arg>
                <arg>-Xlint:-processing</arg>  <!-- No processor claimed any of these annotations 경고는 노출되지않게 설정 -->
            </compilerArgs>
            <showWarnings>true</showWarnings>  <!-- 오라클 드라이버관련 경고관련해서는 이걸 false로 하면 될태지만, 냅두자 -->
            <showDeprecation>true</showDeprecation>
        </configuration>
    </plugin>
    ```

  * Spring Legacy 프로젝트 생성시 자동 추가된 jcl-over-slf4j 는 제거 했다. spring-jcl-5.2.17.RELEASE 에 같은 패키지로 포함이 되어있다.

    ```xml
    <!-- // spring-jcl-5.2.17.RELEASE 에 같은 패키지로 포함이 되어있다.
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>jcl-over-slf4j</artifactId>
        <version>${org.slf4j-version}</version>
        <scope>runtime</scope>
    </dependency>
    -->	
    ```

    


### 7.5 Java 설정을 이용하는 경우의 프로젝트 구성

* jex02 프로젝트 생성

* mvnw 추가

  `mvn -N io.takari:maven:wrapper -Dmaven=3.8.2`

* Maven tool chain 설정 적용

  * 명령프롬프트 환경에서 Java 8 환경에서 Maven 을 사용해서 프로젝트 설정이 Java 11로 되어있는 프로젝트 빌드를 할 수 없다. 
  * 아래 tool chain 설정을 적용뒤에, Java 8 환경의 명령 프롬프트에서 Java 11을 찾아 정상 빌드함을 확인했다.
    * https://maven.apache.org/guides/mini/guide-using-toolchains.html

* **jetty-maven-plugin**을 사용할 때, tool chain을 쓰더라도, 빌드는 JDK 11로 하지만  Jetty는 toolchain 설정으로 처리되지 않고,  Maven을 구동하는 JDK 버전으로 동작하기 때문에, JDK버전을 맞춰줄 수 밖에 없다.

  * Maven을 구동하는 버전이 JDK 8이고, toolchain으로 설정한 JDK 버전이 11이면, toolchain에 의해 11버전으로 빌드된 Java 클래스파일을 Jetty가 처리할 수 없어 오류가 난다. (Unsupported major.minor version XX.X)


## 08. 영속/비즈니스 계층의 CRUD 구현
* 영속 계층의 작업 순서
  1. 테이블 컬럼 구조를 반영하는 VO(Value Object) 클래스의 생성
  2. MyBatis의 Mapper 인터페이스의 작성/XML 처리
  3. 작성한 Mapper 인터페이스 테스트

### 8.1 영속 계층의 구현 준비

### 8.2 영속 영역의 CRUD 구현

ex02 프로젝트는 책의 설명 그대로 따라가고 jex02 프로젝트는 `MyBatis Dynamic SQL` 모듈을 사용해서 매퍼를 자동으로 만들고, QueryDSL 처럼 메서드를 연이어 호출해가며 실행하는 방식으로 해봤다.

* https://mybatis.org/mybatis-dynamic-sql/docs/introduction.html
* jex02에서 주목해야할 코드들..
  * generatorConfig.xml
  * BoardMapper
  * BoardVODynamicSqlSupport
  * BoardMapperTest
  * BoardVO (DB 테이블명과 generatorConfig.xml에 오버라이드 설정 참고해서 POJO 클래스를 만들어준다. 주석이 많긴한데, lombok으로 줄여서 써도 된다.)

## 09. 비즈니스 계층

### 9.1 비즈니스 계층의 설정

### 9.2 비즈니스 계층의 구현과 테스트

* jex02의경우 MyBatis Dynamic SQL 라이브러리로 진행하고 ex02는 책내용대로 진행

  * https://mybatis.org/mybatis-dynamic-sql/docs/delete.html
  * 특별한 문제가 없는한 jexXX 프로젝트는 이 라이브러리 기반으로 구현할 것 같다.

* google-java-format을 적용했다.

  * 탭사이즈는 공백 2

    

## 10. 프리젠테이션(웹) 계층의 CRUD 구현

### 10.1 Controller의 작성

### 10.2 BoardController의 작성



## 11. 화면 처리

### 11.1 목록 페이지 작업과 includes

### 11.2 목록 화면 처리

### 11.3 등록 입력 페이지와 등록 처리

### 11.4 조회 페이지와 이동

### 11.5 게시물의 수정/삭제 처리



## 12. 오라클 데이터베이스 페이징 처리

### 12.1 order by의 문제

### 12.2 order by 보다는 인덱스

### 12.3 인덱스를 이용하는 정렬

### 12.3 ROWNUM과 인라인뷰



## 13. MyBatis와 스프링에서 페이징 처리

### 13.1 MyBatis 처리와 테스트 

### 13.2 BoardController와 BoardService 수정



## 14. 페이징 화면 처리

### 14.1 페이징 처리할 때 필요한 정보들

### 14.2 페이징 처리를 위한 클래스 설계

### 14.3 JSP에서 페이지 번호 출력

### 14.4 조회 페이지로 이동

### 14.5 수정과 삭제 처리

### 14.6 MyBatis에서 전체 데이터의 개수 처리



## 15.  검색처리

### 15.1 검색 기능과 SQL

### 15.2 MyBatis의 동적 SQL

### 15.3 검색 조건 처리를 위한 Criteria의 변화

### 15.4 화면에서 검색 조건 처리







---

## 기타

### web.xml의 xml검증 오류: cvc-id.3: A field of identity constraint 'web-common-servlet-name-uniqueness' matched element 'web-app', but this element does not have a simple type.

```xml
<!-- <web-app version="2.5" xmlns="http://java.sun.com/xml/ns/javaee"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee https://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	>  -->
 <!--
 	생성된 web.xml의 코드로 검증오류가 발생해서...
 	
 	cvc-id.3: A field of identity constraint 'web-common-servlet-name-uniqueness' matched element 'web-app', but this element does not have a simple type.
  
	https://stackoverflow.com/questions/3219639/cvc-id-3-error-in-web-xml
	배포 기술자의 버전 및 형식을 다른 버전들로 바꿔봤다.	
 -->
<web-app    xmlns="http://xmlns.jcp.org/xml/ns/javaee"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
                                 http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
    version="3.1">
 
```

그동안의 답변이 스키마의 주소를 바꿔주는 방법으로 오류를 회피했던 답변들이 많았는데, 이렇게 하는게 그거보단 나은 것 같다.  

  * 참고
    * [Tomcat 6, 7, 8, 9, 10 의 예제 페이지의 web.xml 스키마 헤더 선언부](https://github.com/fp024/etc/blob/main/tomcat/Tomcat_6%EC%97%90%EC%84%9C_10%EA%B9%8C%EC%A7%80%EC%9D%98_%EC%98%88%EC%A0%9C_web.xml_%EC%8A%A4%ED%82%A4%EB%A7%88_%ED%97%A4%EB%8D%94.md)


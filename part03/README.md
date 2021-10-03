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






### 7.5 Java 설정을 이용하는 경우의 프로젝트 구성



## 08. 영속/비즈니스 계층의 CRUD 구현

### 8.1 영속 계층의 구현 준비

### 8.2 영속 영역의 CRUD 구현



## 09. 비즈니스 계층

### 9.1 비즈니스 계층의 설정

### 9.2 비즈니스 계층의 구현과 테스트



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


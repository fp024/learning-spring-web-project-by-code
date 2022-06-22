# Part 7. Spring Web Security를 이용한 로그인 처리

권한관리는 항상 중요한 부분인데, 잘 모르는 부분이 많다. 이번 기회에 기초를 쌓고 더 공부할 수 있는 발판을 만들어보자!



## 30. Spring Web Security 소개

* 스프링 시큐리티의 기본 동작은 여러 종류의 `Filter`와 `Intercepter`를 통해서 처리됨
  * Filter: Servlet에서의 Filter, 스프링과는 무관한 서블릿 자원
  * Intercepter: 스프링에서 필터와 유사한 역활을 함, 스프링의 빈으로 관리되며, 스프링 컨텍스트 안에 속함



### 30.1 Spring Web Security의 설정

* 단순 프로젝트인 Part02 의 예제 프로젝트를 수정해서 ex06, jex07프로젝트를 만들자.

* 라이브러리 추가

  ```xml
  <properties>
    <spring-security.version>스프링 시큐리티 버전</spring-security.version>
  </properties>
  
  <!-- Spring Security -->
  <dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-web</artifactId>
    <version>${spring-security.version}</version>
  </dependency>
  
  <dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-config</artifactId>
    <version>${spring-security.version}</version>
  </dependency>
  
  <dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-core</artifactId>
    <version>${spring-security.version}</version>
  </dependency>
  
  <dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-taglibs</artifactId>
    <version>${spring-security.version}</version>
  </dependency>
  
  
  ```



#### 30.1.1 security-context.xml 생성

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:security="http://www.springframework.org/schema/security"
  xsi:schemaLocation="http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/security https://www.springframework.org/schema/security/spring-security.xsd">

</beans>

```



#### 30.1.2 web.xml 설정



Java Config 기반 설정은 이후에 따로 설명해주시니, 지금 고려하지 않아도 되겠다. 😄😄😄



### 30.2 시큐리티가 필요한 URI 설계

* 예제 URI 설계
  * `/sample/all` > 로그인을 하지 않은 사용자도 접근 가능
  * `/sample/member` > 로그인 한 사용자들만 접근 가능
  * `/sample/admin` > 로그인 사용자 중 관리자 권한을 가진 사용자만 접근 가능



### 30.3 인증(Authentication)과 권한 부여(Authorization - 인가)

* 인증

  * 자기자신을 증명
  * 본인이 무엇인가를 증명

* 권한 부여

  * 남에 의해서 자격이 부여됨

    

* AuthenticationManager
  * 다양한 방식의 인증을 처리할 수 있도록 설계됨.
* ProviderManager
  * 인증에 대한 처리를 AuthenticationProvider라는 타입의 객체를 이용해서 처리를 위임함.
* AuthenticationProvider
  * 실제 인증 작업 진행
  * 인증된 정보에는 권한에 대한 정보를 같이 전달하게 되는데, 이 처리는 UserDetailService와 관련이 있음.



개발자가 스프링 시큐리티를 커스터마이징하는 방식은 AuthenticationManager을 직접 구현하는 방식과 실제 처리를 담당하는 UserDetailService를 구현하는 방식으로 나누어짐.



## 31. 로그인과 로그아웃 처리



### 31.1 접근제한 설정



### 31.2 단순로그인 처리

* 주의사항
  * 일반 시스템에서의 `userid`는 스프링 시큐리티에서 `username` 에 해당함, 사용자의 이름으로 혼동하지 말것!
  * 스프링 시큐리티의 User
    * 인증 정보와 권한을 가진 객체, 일반적인 사용자 정보와는 다름.

* PasswordEncoder 없이 로그인을 하려할 때.. 다음과 같은 예외가 나올 수 있음.

  ```
  java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
  ```

  * Spring Security 5 부터 암호에 대해 PasswordEncoder를 기본으로 사용하도록 설정되었기 때문이고, 연습용으로서 필요없다면 아래처럼 패스워드 문자열 앞부분에 `{noop}` 문자열을 삽입한다.

    ```xml
     <security:user name="member" password="{noop}member" authorities="ROLE_MEMBER" />
    ```

    

#### 31.2.1 로그아웃 확인

* 개발 학습을 하면서 로그인 로그아웃을 자주해야하는데, 로그아웃을 위해서 기능 구현을 아직 하지 않았다면, 브라우저에서 세션과 관련된 정보를 지우는 것이 확실함.

  * 로그인 상태에서 Cookie Editor로 보았을 때, `JSESSIONID` 쿠키에 값이 할당 되어있는 것을 확인했다. 해당 쿠키를 지우면 로그인이 풀림.

    ![JSESSIONID](doc-resources/cookie-editor.png)



### 31.2.2 여러 권한을 가지는 사용자 설정

* admin 관련 설정추가하고, member로 로그인후 admin 페이지 접근시 403 응답 확인했음.



### 31.2.3 접근 제한 메시지의 처리

접근 제한에 대해서 AccessDeniedHandler를 직접 구현 또는 특정 URI를 지정할 수 있음.

* Access Denied의 경우 403 에러 메시지가 발생함.
* JSP에서는 HttpServletRequest 안에 `SPRING_SECURITY_403_EXCEPTION`이라는 이름으로 `AccessDeniedException` 객체가 전달됨



### 31.2.4 AccessDeniedHandler 인터페이스를 구현하는 경우

* 접근이 제한 되었을 때, 쿠키나 세션에 특정한 작업을 하거나 HttpServletResponse에 특정한 헤더정보를 추가하는 등의 행위를 할 경우는 직접 구현하는 방식이 권장됨.

* CustomAccessDeniedHandler 에서 리다이렉트를 하긴했는데...

  accessDeniedException 에 대한 정보를 리다이렉트 페이지로 전달하지 않았으므로... SPRING_SECURITY_403_EXCEPTION 관련 메시지는 표시되지 않는다.

  * 기본 내장 AccessDeniedHandlerImpl 에서는  예외정보를 request에 속성으로 설정하고 포워딩을 한다.

    ```java
    // Put exception into request scope (perhaps of use to a view)
    request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);
    // Set the 403 status code.
    response.setStatus(HttpStatus.FORBIDDEN.value());
    // forward to error page.
    if (logger.isDebugEnabled()) {
        logger.debug(LogMessage.format("Forwarding to %s with status code 403", this.errorPage));
    }
    request.getRequestDispatcher(this.errorPage).forward(request, response);
    ```

    


### 31.3 커스텀 로그인 페이지

* CSRF 토큰 

  ```html
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
  ```

  * 브라우저에서 확인
    ```html
    <input type="hidden" name="_csrf" value="363f36a0-4bd4-42b2-a100-ad021c217135">
    <!-- 로그아웃 후 다시 확인하니 값이 바뀌어 있다. -->
    <input type="hidden" name="_csrf" value="b2901297-935d-4b22-a8b3-5392da1d26ef">
    ```

보안 처리를 위해 넣어둔 것 같은데 아직은 잘 모르겠다.



### 31.4 CSRF(Cross-site request forgery) 공격과 토큰

* 사이트간 위조 방지를 목적으로 특정한 값의 토큰을 사용하는 방식
* CSRF 공격
  * 서버에서 받아들이는 요청을 해석하고 처리할 때 어떤 출처에서 호출이 진행 되었는지는 따지지 않기 때문에 생기는 허점을 노리는 공격방식
* 대응 방법
  * referer 헤더 체크
  * REST 방식에서 사용되는 PUT 또는 DELETE 사용



#### 31.4.1 CRSF 토큰

1. 서버에서 브라우저에 데이터를 전송할 때, CSRF 토큰을 전송
2. 사용자가 POST등으로 요청할 때, 요청내용의 CSRF값을 검사
   * 제공한 CSRF 토큰과 다르다면 처리하지 않음.



#### 31.4.2 스프링 시큐리티의 CSRF 설정

* CSRF 토큰은 세션을 통해 보관하고 브라우저에서 전송된 CSRF 토큰 값을 검사하는 방식으로 처리.
* 비활성화 할 수도 있음.



### 31.5 로그인 성공과 AuthenticationSuccessHandler

* 브라우저에서 단지 뒤로가기해서 CSRF 토큰이 갱신되지 않은 상태라면 로그인이 안된다, 새로고침을 눌러줘야함.



### 31.6 로그아웃의 처리와 LogoutSuccessHandler

* POST 방식으로 처리되기 때문에, CSRF 토큰값을 지정함.
* 추가적인 작업을 원한다면 logoutSucessHandler을 정의해야함.





## 32. JDBC를 이용하는 간편 인증/권한 처리



* UserDetailsService는 다음 구현 클래스를 제공함
  * CachingUserDetailsService
  * InMemoryUserDetailsManager
  * JdbcDaoImpl
  * JdbcUserDetailsManager
  * LdapUserDetailsManager
  * LdapUserDetailsService



### 32.1 JDBC를 이용하기 위한 테이블 설정

* JdbcUserDetailsManager

  * https://github.com/spring-projects/spring-security/blob/5.7.1/core/src/main/java/org/springframework/security/provisioning/JdbcUserDetailsManager.java

* 스프링 시큐리티에서 지정된 SQL을 그대로 사용하고 싶을 때, 아래와 같이 테이블을 생성해줌.

  ```sql
  -- 기본 User 스키마
  create table users(
  	username varchar_ignorecase(50) not null primary key,
  	password varchar_ignorecase(500) not null,
  	enabled boolean not null
  );
  
  create table authorities (
  	username varchar_ignorecase(50) not null,
  	authority varchar_ignorecase(50) not null,
  	constraint fk_authorities_users foreign key(username) references users(username)
  );
  create unique index ix_auth_username on authorities (username,authority);
  
  
  
  -- Oracle DB를 위한 기본 User 스키마
  CREATE TABLE USERS (
      USERNAME NVARCHAR2(128) PRIMARY KEY,
      PASSWORD NVARCHAR2(128) NOT NULL,
      ENABLED CHAR(1) CHECK (ENABLED IN ('Y','N') ) NOT NULL
  );
  
  
  CREATE TABLE AUTHORITIES (
      USERNAME NVARCHAR2(128) NOT NULL,
      AUTHORITY NVARCHAR2(128) NOT NULL
  );
  ALTER TABLE AUTHORITIES ADD CONSTRAINT AUTHORITIES_UNIQUE UNIQUE (USERNAME, AUTHORITY);
  ALTER TABLE AUTHORITIES ADD CONSTRAINT AUTHORITIES_FK1 FOREIGN KEY (USERNAME) REFERENCES USERS (USERNAME) ENABLE;
  
  
  -- ORACLE 기준 테스트 유저 데이터 입력
  INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES ('user00', 'pw00', 'Y');
  INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES ('member00', 'pw00', 'Y');
  INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES ('admin00', 'pw00', 'Y');
  
  INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('user00', 'ROLE_USER');
  INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('member00', 'ROLE_MANAGER');
  INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('admin00', 'ROLE_MANAGER');
  INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('admin00', 'ROLE_ADMIN');
  ```

  * 클래스 리소스로도 사용할 수 있음.
    * `org/springframework/security/core/userdetails/jdbc/users.ddl`
  * 가이드 문서
    * `5.7.1 버전`
      * https://github.com/spring-projects/spring-security/blob/5.7.1/docs/modules/ROOT/pages/servlet/authentication/passwords/jdbc.adoc
    * `main` 브랜치
      * https://github.com/spring-projects/spring-security/blob/main/docs/modules/ROOT/pages/servlet/authentication/passwords/jdbc.adoc

#### ex06 프로젝트에 datasource 추가

 *  spring-jdbc만 써서 mybatis 설정을 할필요가 없다.

    

#### 32.1.1 PasswordEncoder 문제 해결

`/sample/admin` 에 접근하여 로그인 페이지에 ID와 암호를 정상 입력하고 접근해보면, 아래와 같은 예외가 발생한다.

```
java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
	at org.springframework.security.crypto.password.DelegatingPasswordEncoder$UnmappedIdPasswordEncoder.matches(DelegatingPasswordEncoder.java:289)
	...
```

* Database를 사용할 때는 PasswordEncoder를 사용해야함.
* 현재 예제에서는 암호화 없이 처리하도록 PasswordEncoder를 구현해서 사용하기로함.
* CustomNoOpPasswordEncoder 는 security-context.xml에 등록

이후 다시 로그인 시도해보면 잘 수행됨.





### 32.2 기존 테이블을 이용하는 경우

기존 회원관련 데이터 베이스가 구축되어있을 때, 스프링 시큐리티의 기본 테이블 구조를 섞어 사용하는 것은 오히려 복잡해질 수 있음.

* JDBC를 이용하고 기존에 테이블이 있다면 지정된 결과를 반환하는 쿼리를 작성해주는 작업으로도 처리가 가능함.

* `<security:jdbc-user-service> 의 아래 속성에 적당한 쿼리 지정`
  * `users-by-username-query`
  * `authorities-by-user-name-query`



#### 32.2.1 인증/권한을 위한 테이블 설계

책에서 제시한 테이블이 스프링 시큐리티 기본 제공 테이블 스키마와 크게 차이는 없지만, 사용자 테이블에 이름(username), 등록일시(regdate), 업데이트일시(updatedate) 등의 추가정보가 들어가 있다.

* 스프링 시큐리티의 기본 스키마의 username은 ID개념임.

* 사용여부(enabled)에 대해서...

  스프링 시큐리티의 기본 스키마는 `ENABLED CHAR(1) CHECK (ENABLED IN ('Y','N') ) NOT NULL` 이렇게 되어있는데... 들어오는 값을 Y, N으로 고정하고 있다. 

  그런데 책에서는 boolean으로 처리하려고 한 것인지 기본 값을 '1'로 하고 있다. 이부분은 스프링 시큐리티 기본 스키마 정의를 따르고, 내가 잘 조정해서 바꿔야겠다.

  

* 일반적인 회원 테이블과 권한 테이블

  ```sql
  CREATE TABLE TBL_MEMBER(
    USERID        VARCHAR2(50)    NOT NULL PRIMARY KEY,
    USERPW        VARCHAR2(100)   NOT NULL,
    USERNAME      VARCHAR2(100)   NOT NULL,
    REGDATE       DATE            DEFAULT SYSDATE,
    UPDATEDATE    DATE            DEFAULT SYSDATE,
    ENABLED       CHAR(1) CHECK (ENABLED IN ('Y','N') ) NOT NULL 
    /* ENABLED       CHAR(1)         DEFAULT '1' 대신 위처럼 사용해보자., 유저 추가시 활성화/비활성화 여부는 서비스에서 결정하자. */
  );
  
  CREATE TABLE TBL_MEMBER_AUTH (
    USERID        VARCHAR2(50)    NOT NULL,
    AUTH          VARCHAR2(50)    NOT NULL,
    CONSTRAINT    FK_MEMBER_AUTH  FOREIGN KEY(USERID) REFERENCES TBL_MEMBER(USERID)
  );
  ```



#### 32.2.2 BCryptPasswordEncoder 클래스를 이용한 패스워드 보호

* bcrypt
  * 패스워드를 저장하는 용도로 설계된 해시함수로 특정 문자열을 암호화하고, 체크하는 쪽에서는 암호화된 패스워드가 유효한 패스워드인지만 확인하고 다시 원문으로 되돌리지는 못함.
* Password Encoder로 BCryptPasswordEncoder로 사용



##### 인코딩된 패스워드를 가지는 사용자 추가

* 태스트 클래스를 만들어서 인코딩된 사용자를 DB에 넣어주자!
* `spring-test`는 이미 디펜던시에 추가되어있음.
* 저자님께서는 `Connection`, `PreparedStatement` 등등 사용하셨는데, jdbc 템플릿 사용해보자!
  * **MemberTest**
    * `NamedParameterJdbcTemplate` 로 사용했고, 회원과 회원 권한에 대해 별도 도메인 클래스 만들어서 거기에 값을 설정하고 `BeanPropertySqlParameterSource` 사용해서 매핑이 쉽도록 작성했다.




#### 사용자에 권한 추가하기

* 권한을 직접 문자열로 사용하기 싫어서 enum으로 정의했는데, `BeanPropertySqlParameterSource` 를 그대로 사용할 수가 없었다. 다음과 같이 getValue()를 오버라이드 해주면 정상 입력할 수 있었다.

  ```java
  public class CustomBeanPropertySqlParameterSource extends BeanPropertySqlParameterSource {
    public CustomBeanPropertySqlParameterSource(Object object) {
      super(object);
    }
  
    /** enum 인식을 위해 getValue() 메서드를 오버라이드 */
    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
      Object value = super.getValue(paramName);
      if (value instanceof Enum) {
        return value.toString();
      }
      return value;
    }
  }
  ```

  

### 32.2.3 쿼리를 이용하는 인증

* security-context.xml에 다음과 같이 지정

  ```xml
  <!-- SELECT 컬럼 순서만 맞으면, 컬럼명이 기본과 다른 것은 상관 없는 것 같다. -->
  <security:jdbc-user-service data-source-ref="dataSource"
    users-by-username-query="SELECT USERID, USERPW, ENABLED FROM TBL_MEMBER WHERE USERID = ?"
    authorities-by-username-query="SELECT USERID, AUTH FROM TBL_MEMBER_AUTH WHERE USERID = ?"/>
  ```

 *  특정 유저의 `ENABLED` 컬럼 값을 N으로 변경했을 때, 로그인이 되지 않는 것도 확인했다.

    ```sql
    UPDATE TBL_MEMBER 
       SET ENABLED = 'N'
    WHERE USERID = 'admin90'
    ```

    

















---

## jex06-board 프로젝트 진행 특이사항

* 



---

## 의견

* 

  


## 정오표

* p628 
  * Custom`e`AccessDeniedHandler -> CustomAccessDeniedHandler




## 기타


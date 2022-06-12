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


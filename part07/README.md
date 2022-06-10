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





---

## jex06-board 프로젝트 진행 특이사항

* 



---

## 의견

* 

  


## 정오표

* 



## 기타


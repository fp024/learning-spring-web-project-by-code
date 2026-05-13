# Part Last . 내맘대로 게시판

> 책은 이미 완료한지 좀 되었지만..., 그전부터 하고싶었던 것을 해보자~
>
> - [x] JPA 전환
> - [ ] Github OAuth 로그인 적용



## 1. JPA 전환

* 적용 내용

  1. JPA 3.0
     * 프로젝트에서 mybatis는 완전히 겉어냈다. [NestedIOException](https://github.com/mybatis/spring/pull/663)도 일부러 만들어둘 필요없어졌고...
  2. HSQL DB (완료되면 메모리 DB설정으로 바꿈)

  3. Spring 6
  4. WAS
     * Jetty 12 (Jetty Maven 플러그인)
     * Tomcat 10.1.x  (Cargo Maven 플러그인)





## 2. GitHub OAuth 로그인 붙이기

* 아직 시작안함. 어떻게 해야할지 감이 안 옴.





## 특이사항

* ...



## 의견

* 내가 `mybatis-dynamic-sql`을 제대로 활용 못해서 쿼리 호출 코드가 엄청 복잡한 부분들이 많았는데...(분명 잘했으면 괜찮았을지도...)
  *  `Querydsl`, `Spring Data JPA` 적용 후, 코드가 엄청나게 간단해졌다. 👍👍👍
* HSQLDB로 데이터 넣고 진행해버리니까.. 테스트가 엄청 빠르게 되었다. 🎉



---

# Servlet 4.0 + Spring 5 + Hibernate 5 프로젝트 분기 추가

>  Scouter의 구버전([v2.20.0 이전 버전](https://github.com/scouter-project/scouter/releases/tag/v2.20.0))이 Servlet 5.0 부터는 지원을 안해서 `Servlet 4.0 + Spring 5 + Hibernate 5` 환경 기반으로도 프로젝트를 만들어보기로 했었다.

* 프로젝트
  * [my-board-spring5](my-board-spring5)

* ✨ Servlet 4.0 으로 전환되어 Scouter가 잘 동작한다.
  * JPA로 전환한 프로젝트가 기존 설정 그대로 잘 붙는지 보고 싶었다.



## 특이사항

* [특정 환경에서 QueryDSL로 insert시 오류 문제 - (Spring 5.3.24 + Spring Data 2.7.6 + Hibernate 5.6.14 + QueryDSL 5.0) · Issue #21](https://github.com/fp024/learning-spring-web-project-by-code/issues/21)
  * 버전환경을 내리고보니 Querydsl 의 insert문 수행시 오류가 난다. 🎃
  * Spring Data JPA의 save()로 바꾸고 관련 엔티티에는 `@DynamicInsert`를 붙혔다.





---

# mybatis-dynamic-sql 적용한 프로젝트를 Spring 7로 마이그레이션

* 프로젝트
  * [my-board-mds](my-board-mds)

part07의 jex06-board 프로젝트를 Spring 7 + Spring Security 7 + Java 21 환경으로 마이그레이션해보았다.



#### 변경내용

* Spring 5 -> Spring 7
* Spring Security 5.8 -> Spring Security 7
* Tomcat 9 -> Tomcat 11



### 특이사항

* [프로젝트 README 참조..](my-board-mds/README.md) 

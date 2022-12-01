# Part Last . 내맘대로 게시판

> 책은 이미 완료한지 좀 되었지만..., 그전부터 하고싶었던 것을 해보자~
>
> - [x] JPA 전환
> - [ ] Github OAuth 로그인 적용



## 1. JPA 전환

* 적용 내용

  1. JPA 3.0
     * 프로젝트에서 mybatis는 완전히 겉어냈다. NestedIOException도 일부러 만들어둘 필요없어졌고...
  2. HSQL DB (완료되면 메모리 DB설정으로 바꿈)

  3. Spring 6
  4. WAS
     * Jetty 11 (12버전을 사용하는게 좋은데 아직 정식버전이 안나옴)
     * Tomcat 10.1.x  (Cargo Maven 플러그인)





## 2. GitHub OAuth 로그인 붙이기

* 아직 시작안함. 어떻게 해야할지 감이 안 옴.





## 특이사항





## 의견

* 내가 `mybatis-dynamic-sql`을 제대로 활용 못해서 쿼리 호출 코드가 엄청 복잡한 부분들이 많았는데...(분명 잘했으면 괜찮았을지도...)
  *  `Querydsl`, `Spring Data JPA` 적용 후, 코드가 엄청나게 간단해졌다. 👍👍👍
* HSQLDB로 데이터 넣고 진행해버리니까.. 테스트가 엄청 빠르게 되었다. 🎉




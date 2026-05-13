# mybatis-dynamic-sql 적용한 프로젝트를 Spring 7로 마이그레이션

* 프로젝트
  * [my-board-mds](my-board-mds)

part07의 jex06-board 프로젝트를 Spring 7 + Spring Security 7 + Java 21 환경으로 마이그레이션해보았다.



## 변경내용

* Spring 5 -> Spring 7
* Spring Security 5.8 -> Spring Security 7
* Tomcat 9 -> Tomcat 11



## 특이사항

1. 패스워드 인코더를 DelegatingPasswordEncoder()로 사용했었는데...

   ```java
     @Bean
     public PasswordEncoder passwordEncoder() {
       return PasswordEncoderFactories.createDelegatingPasswordEncoder();
     }
   ```

   스프링 시큐리티 7에서는 BCrypt 형식으로 암호화되어있더라도 `{bcrypt}`를 붙여줘야하나보다.

   ```java
     // 💡 PasswordEncoder를 BCryptPasswordEncoder로 고정하여 사용
     //    Spring Security 6 까지는 prefix가 없어도 기본으로 BCrypt로 처리가 되었었는데,
     //    Spring Security 7부터는 prefix가 필요하다. 그래서 일단 BCryptPasswordEncoder로 고정해서 사용해보자!
     @Bean
     public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
     }
   ```
   
   DB에다가 일괄 업데이트를 해도되긴 되는데.. 일단은 이 프로젝트는 `BCryptPasswordEncoder`를 사용하도록 고정해둠.
   ==> 💡 근데.. DB를 Docker Compose로 OracleFREE 23c로 전환하고 나서, 회원암호에 전부 `{bcrypt}`를 붙여서, 이부분은 다시 DelegatingPasswordEncoder 사용하는 것으로 돌아갔다. 😅 

2. JSON 메시지 컨버터 재설정 방법 변경

   원래는 자동 생성된 GSON 컨버터를 제거하고 내가 사용자정의한것 쓰도록 바꿨었는데...

   ```java
     @Override
     public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
       converters.removeIf(
           httpMessageConverter -> httpMessageConverter.getClass() == GsonHttpMessageConverter.class);
       converters.add(GsonHelper.gsonHttpMessageConverter());
     }
   ```

   configureMessageConverters를 대신 쓰라고 해서 바꿨다.

   ```java
     @Override
     public void configureMessageConverters(HttpMessageConverters.ServerBuilder builder) {
       builder.withJsonConverter(GsonHelper.gsonHttpMessageConverter());
     }
   ```

   



## TODO:

- [ ] 이 프로젝트에 먼저 Github 로그인을 먼저 붙여볼까?
  - [x] DB 테이블 수정이 필요한데, Oracle을 Docker로 실행해서 이전 챕터에서 부터 쭈욱 써왔던 DB와 분리되야할 것 같다.
    - DB를 Docker Compose로 전환해서, `db-start.bat`를 실행하면 OracleFREE 23c가 실행되며 테이블/데이터도 초기화된다.
      - `db-start.bat`: Oracle DB 컨테이너 생성 및 실행 (이미 있다면 DB 컨테이너 실행만 함)
      - `db-stop.bat`: Oracle DB 컨테이너만 종료, 데이터는 유지됨
      - `db-clean.bat`: Oracle DB 컨테이너 종료 및 데이터 삭제
- [x] 다른 프로젝트엔 tui.editor를 붙여보긴 했는데... 여기도 에디터를 붙여보면 좋을 것 같다.
  - 동일한 방식으로 tui.editor를 그대로 붙였다. 먼저 적용한 코드에서 약간의 코드 정리만 추가로 했다.


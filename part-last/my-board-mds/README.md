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
- [ ] 이 프로젝트는 아직 Oracle이긴한데... 다른 Part Last 의 프로젝트처럼 간편한 확인 테스트를 위해 HSQLDB로 바꾸는게 좋을 것 같다.
  - [ ] 다른 프로젝트엔 tui.editor를 붙여보긴 했는데... 여기도 에디터를 붙여보면 좋을 것 같다.


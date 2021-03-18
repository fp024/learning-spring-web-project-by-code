# learning-spring-web-project-by-code

## 코드로 배우는 스프링 웹 프로젝트 (저자: 구멍가게코딩단)

* [yes24 도서 판매 페이지 링크](http://www.yes24.com/Product/Goods/64340061)
* 실습 진행 환경
    * STS 3 3.9.14.RELEASE 또는 Eclipse 2021-03
    * AdoptOpenJDK 11.0.10
        * 책 환경: Oracle JDK 1.8
    * Oracle 18c Express Edition
        * 책 환경: Oracle 11c Express Edition
    * Tomcat 9.0.44
    * Maven 3.6.3


## 의견
### 개정판이 나온다면..
* Oracle JDK는 개인 용도 사용이 아닌 경우라면 라이선스 문제가 있을 수 있어, OpenJDK 설치로
소개해주시면 좋을 것 같습니다.
    * https://adoptopenjdk.net/archive.html?variant=openjdk11&jvmVariant=hotspot

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
	<logger name="org.fp024.mapper">
		<level value="trace" />
	</logger>
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


## 정오표

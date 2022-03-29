# Part 5. AOP와 트랜젝션



## 18. AOP라는 패러다임

* 관심사 (Aspect) 와 비즈니스 로직을 분리 후, 실행할 때 결합하는 방식으로 접근

### 18.1 AOP 용어

* Target: 순수한 비지니스 로직, 순수한 코어
* Proxy: `Target`을 감싸고 있는 존재, 내부적으로 Target 호출
* JointPoint: `Target` 객체가 가진 여러 메서드
* PointCut: 어떤 메서드에 관심사를 결합할 것인지 결정, 관심사와 비즈니스 로직이 결합되는 지점을 결정
* 관심사
  * Aspect: 관심사 자체를 의미
  * Advice: `Aspect`를 구현한 코드



#### 동작 위치에 따른 분리

| 구분                   | 설명                                                         |
| ---------------------- | ------------------------------------------------------------ |
| Before Advice          | Target의 JoinPoint를 호출하기 전에 실행되는 코드             |
| After Returning Advice | 모든 실행이 정상적으로 이루어진 후에 동작하는 코드           |
| After Throwing Advice  | 예외가 발생한뒤에 동작하는 코드                              |
| After Advice           | 정상적 실행이후 또는 예외가 발생했을 때, 구분없이 실행되는 코드 |
| Around Advice          | 메서드의 실행 자체를 제어할 수 있는 강력한 코드<br />직접 대상 메서드를 호출하고 결과나 예외를 처리 할 수 있음 |



### 18.2 AOP 실습

* 프로젝트: 
  * XML기반설정: [ex04](ex04)
  * Java 기반설정: [jex04](jex04)
    

* `acpectjweaver`를 디펜던시하면 `aspectrt`를 디펜던시하지 않아도 되는 것 같다.
  * 둘다 선언하면 클래스 중복이 일어남.



### 18.3 AOP 설정

* IntelliJ에서 Spring 네임스페이스 추가
  * https://stackoverflow.com/questions/9862808/does-intellij-have-spring-namespaces-wizard
  * Eclipse처럼 별도로 네임스페이스를 추가하는 부분은 없고, 사용할 태그를 입력해주면 자동으로 입력해준다.



### 18.4 AOP 테스트



### 18.5 @Around와 ProceedingJoinPoint





---

## 19. 스프링에서 트랜잭션 관리

#### ACID 원칙

| 이름                 | 설명                                                         |
| -------------------- | ------------------------------------------------------------ |
| 원자성 (Atomicity)   | 하나의 트랜잭션은 하나의 단위로 처리되야함                   |
| 일관성 (Consistency) | 트랜잭션이 성공했다면 데이터베이스의 모든 데이터는 일관성을 유지해야함 |
| 격리 (Isolation)     | 트랜잭션으로 처리되는 중간에 외부의 간섭은 없어야함          |
| 영속성 (Durability)  | 트랜잭션이 성공적으로 처리되면 그 결과는 영속적으로 보관되야함 |



### 19.1 데이터베이스 설계와 트랜잭션

#### 정규화

중복된 데이터를 제거하여 데이터 저장의 효율을 높이는 작업



#### 정규화를 진행하면서 원칙적으로 컬럼으로 처리되지 않는데이터

* 시간이 흐르면 변경되는 데이터
  * 일반적으로 생년월일은 기록하지만 나이 값을 저장하진 않음
* 계산이 가능한 데이터
  * 주문과 주문상세가 별도의 테이블로 분리되어있다면 사용자가 한번에 몇개의 상품을 주문했는지 등은 기록안함
* 누구에게나 정해진 값을 이용하는 경우 데이터베이스에서 취급하지 않음

정규화가 잘 된 환경일 수록 `트랜잭션`이 많이 일어나지 않음

조인이나 서브쿼리 사용으로 `반정규화`를 해야하는 경우도 있음

* 게시물과 댓글 관계에서 댓글 카운트를 댓글에 대한 조인이나 서브쿼리로 카운트하지 않고 게시물 테이블에 댓글 수 컬럼 생성
  * 이때 댓글 추가와 댓글 카운트 수정에 트랜젝션이 필요하다.



### 19.2 트랜잭션 설정 실습

지금까지 진행한대로 [ex04](ex04) 는 mybatis XML설정으로 사용하고  [jex04](jex04)는 mybatis-dynamic-sql로 사용하면 되겠다.

log4jdbc-logj2-jdbc4 부분은 mapper 인터페이스 로그를 trace 레벨로 설정하면 쿼리가 나오므로 아래처럼 로그에 설정해.서 사용하도록 하자.

```xml
<!--  MyBatis 3.2부터는 myBatis에서 지정하는 namespace별로 로그레벨을 지정할 수 있음. -->
<Logger name="org.fp024.mapper" level="trace"/>
```



### 19.2.2 예제 테이블 생성

ex04는 Oracle을 사용하고, jex04는 HyperSQL DB로 설정해도 되겠다.

```sql
CREATE table tbl_sample1 (col1 VARCHAR2(500));
CREATE table tbl_sample2 (col2 VARCHAR2(50));
```

* HyperSQL DB에서는 VARCHAR2를 Oracle 호환성 모드로 사용하지 않으면 오류가 발생하니 일반 VARCHAR로 생성한다. (비표준 구문을 인식하는 설정으로 테이블을 생성해도 알아서 VARCHAR로 변경해 줌.)

  ```sql
  SET DATABASE SQL SYNTAX ORA TRUE
  ```
  
  위와 의미가 동등한 url 프로퍼티를 아래처럼 설정 해주면 Oracle의 비표준 구문을 사용할 수 있다.

  * `sql.syntax_ora=true`의 사용 예

    ```xml
     <property name="url" value="jdbc:hsqldb:mem:PUBLIC;sql.syntax_ora=true" />
    ```
  



### 19.2.3 비즈니스 계층과 트랜잭션 설정



### 19.2.4 @Transactional 어노테이션

* mybatis의 mapper로그만 볼때는 쿼리와 전달 값, 예외발생시 예외 내용등을 볼 수 있긴한데... 롤백이 어떻게 되었는지, AutoCommit 상태가 어떤 지 등의 상세로그는 볼 수 없었다.

  * 추가 설정으로 `org.springframework.jdbc`의 로그레벨을 debug로 추가했을 때 정보가 더 보이긴함.

    ```xml
    <Logger name="org.springframework.jdbc" level="debug"/>
    ```

    * 오류 코드라던지, 롤백이 되었다는지 등..

      ```
      DEBUG: org.springframework.jdbc.support.SQLErrorCodesFactory - Looking up default SQLErrorCodes for DataSource [com.zaxxer.hikari.HikariDataSource@588f63c]
      DEBUG: org.springframework.jdbc.support.SQLErrorCodesFactory - SQL error codes for 'Oracle' found
      DEBUG: org.springframework.jdbc.support.SQLErrorCodesFactory - Caching SQL error codes for DataSource [com.zaxxer.hikari.HikariDataSource@588f63c]: database product name is 'Oracle'
      DEBUG: org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator - Unable to translate SQLException with Error code '12899', will now try the fallback translator
      DEBUG: org.springframework.jdbc.support.SQLStateSQLExceptionTranslator - Extracted SQL state class '72' from value '72000'
      DEBUG: org.springframework.jdbc.datasource.DataSourceTransactionManager - Initiating transaction rollback
      DEBUG: org.springframework.jdbc.datasource.DataSourceTransactionManager - Rolling back JDBC transaction on Connection [HikariProxyConnection@1607458416 wrapping oracle.jdbc.driver.T4CConnection@3cec79d3]
      DEBUG: org.springframework.jdbc.datasource.DataSourceTransactionManager - Releasing JDBC Connection [HikariProxyConnection@1607458416 wrapping oracle.jdbc.driver.T4CConnection@3cec79d3] after transaction
      ```



### 19.2.5 @Transactional 어노테이션 속성들



### 19.2.6 @Transactional 적용 순서

* @Transaciontal 어노테이션의 우선 순위

  메서드 > 클래스 > 인터페이스



---

## 20. 댓글과 댓글 수에 대한 처리



​                                                                                                                              



---

##  jex04 프로젝트 진행 특이사항

### HyperSQL DB 사용

* HyperSQL DB 적용시 문제없이 잘 동작하였다.

### mybatis generator로 생성

```bash
$ mvnw mybatis-generator:generate
```

제네레이터로 생성하면 도메인은 무조건 만들게 되었다. 괜히 복잡해진 면이 있긴함... 😅😅😅





---

## 의견

* 




## 정오표

* 




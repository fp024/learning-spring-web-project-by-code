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



​                                                                        



---

## 20. 댓글과 댓글 수에 대한 처리



​                                                                                                                              



---

## ex04 / jex04 프로젝트 진행 특이사항







---

## 의견

* 




## 정오표

* 




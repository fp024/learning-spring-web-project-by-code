# Part 4. REST 방식과 Ajax를 이용하는 댓글 처리



## 16. REST 방식으로 전환

* 모바일 시대가 되면서 서버의 역활이 변화됨
  * 기존 브라우저 대상을 위해 HTML형태로 전달을 해왔다면, 이제는 순수한 데이터를 전달하는 형태로 변화됨 (API 서버)
* URI (Uniform Resource Identifier)
  * 이전에는 페이지를 이동하더라도 브라우저의 주소는 변화하지 않는 방식을 선호했음
  * 현재의 대부분은 페이지를 이동하면 브라우저의 주소도 같이 이동하는 방식을 사용함

* URL와 URI(자원의 식별자)의 상징적인 의미
  * URL : 이 곳에 가면 당신이 원하는 것을 찾을 수 있습니다.
  * URI: 당신이 원하는 곳의 주소는 여기입니다.
    * URI의 I는 DB의 PK와 같은 의미로 생각할 수 있음.



##### REST는...

* URI는 하나의 고유한 리소스(Resource)를 대표하도록 설계된다는 개념에 전송방식을 결합해서 원하는 작업을 지정함.

* REST 방식의 구성
  * `URI` + `GET/POST/PUT/DELETE/...`



##### Spring에서 제공하는 REST 관련 어노테이션

| 어노테이션      | 기능                                                         |
| --------------- | ------------------------------------------------------------ |
| @RestController | Controller가 REST 방식을 처리위한 것임을 명시합니다.         |
| @ResponseBody   | 일반적인 JSP와 같은 뷰로 전달된는 것이 아니라 데이터 자체를 전달하기 위한 용도 |
| @PathVariable   | URL 경로에 있는 값을 파라미터로 추출하려고 할 때 사용        |
| @CrossOrigin    | Ajax의 크로스 도메인 문제를 해결해주는 어노테이션            |
| @RequestBody    | JSON 데이터를 원하는 타입으로 바인딩 처리                    |





### 16.1 @RestController

* 서버에서 존송하는 것이 순수한 데이터이므로, 모든 메서드의 리턴타입을 기존과 다르게 처리함을 명시해야 함.
  * 스프링 4부터는 @RestController를 붙일 수 있음.
  * 이전 버전에서는 클래스 또는 메서드 위에 @ResponseBody를 붙여줬었음.

##### 16.1.1 예제 프로젝트 준비

* ex03 프로젝트
  * jackson 을 사용한다.
  * 테스트 환경에서 gson을 사용하는 부분이 있다고 하여, 추가했다.
* jex03 프로젝트
  * gson을 사용해보자!





### 16.2 @RestController의 반환타입

* SampleController 클래스 확인 주소
  * `http://localhost:8080/sample/getText`

##### 16.2.1 단순문자열 반환

* @GetMapping에 사용된 produces 속성은 해당 메서드가 생산하는 MIME 타입을 의미함.

##### 16.2.2 객체의 반환

* MediaType.APPLICATION_JSON_UTF8_VALUE 를 사용할  때 Deprecated 경고

  * 현시점에서 크롬과 같은 주요 브라우저들이 charset=UTF-8 파라미터 없이 UTF-8 특수 문자들을 올바르게 해석하므로, 스프링 5.2부터 APPLICATION_JSON_VALUE를 선호한다고 함.

      ```
      A String equivalent of APPLICATION_JSON_UTF8.
      Deprecated
      as of 5.2 in favor of APPLICATION_JSON_VALUE since major browsers like Chrome now comply with the specification  and interpret correctly UTF-8 special characters without requiring a charset=UTF-8 parameter.
      ```

      * 괜히 Deprecated 경고 나오니, APPLICATION_JSON_VALUE 으로 사용하자!

* getSample 테스트주소

  * 브라우저에서 다음과 요청할 떄 `http://localhost:8080/sample/getSample`  
    * XML로 결과를 받음
    * `http://localhost:8080/sample/getSample.xml` 도 동일하게 xml로 결과를 받음

  * 그런데 끝에 json 확장자를 붙이면 json 결과를 받음
    * `http://localhost:8080/sample/getSample.json`  






### 16.3 @RestController에서 파라미터





### 16.4 REST 방식의 테스트





### 16.5 다양한 전송방식







## 17. Ajax 댓글 처리





### 17.1 프로젝트의 구성





### 17.2 댓글 처리를 위한 영속 영역





### 17.3 서비스 영역과 Controller 처리





### 17.4 JavaScript 준비





### 17.5 이벤트 처리와 HTML 처리





### 17.6 댓글의 페이징 처리





### 17.7 댓글 페이지의 화면 처리









---

## 기타

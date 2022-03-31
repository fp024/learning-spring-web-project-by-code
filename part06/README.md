# Part 6. 파일 업로드 처리

기억해보면 특별히 파일업로드를 전문적으로 할일이 없었던 것 같긴하다. 해본 것은 엑셀파일업로드 / 다운로드 그리고 빌드 결과물 업로드 정도인데, 거의 존재하는 시스템을 수정하거나 간단한 것을 새로만든 정도라서, 이 Part를 잘 봐보자!



## 21. 파일 업로드 방식

* `<form>`태그를 이용하는 방식
  * 브라우저 제한이 없어야하는 경우
  * `<iframe>`을 이용해서 화면의 이동없이 첨부파일 처리
* Ajax를 이용하는 방식
  * `<input type='file'>`을 이용하고 Ajax로 처리
  * HTML5의 Drag And Drop 기능이나 jQuery 라이브러리를 이용



#### 서버에서 첨부파일을 처리할 때 사용하는 API

* [commons-fileupload](https://commons.apache.org/proper/commons-fileupload/): 가장 일반적으로 사용됨, Servlet 3.0 이전에도 사용가능
* Servlet 3.0 이상: 자체적으로 파일 업로드 처리 API 지원



#### 업로드 디렉토리 경로 구성

* `C:\upload\temp`
  * 필요하다면 mklink /J 로 정션 링크를 구성하자!



### 21.1 스프링 첨부파일을 위한 설정

* 프로젝트
  * XML설정기반: [ex05](ex05)
  * Java 설정기반: [jex05](jex05)

#### 21.1.1 web.xml을 이용하는 경우의 첨부파일 설정

서블릿 2.5, 3.1, 4.0 혼재되어있던 코드를 4.0으로 통일하고 Maven 버전 프로퍼티를 정리하였다.

* web.xml의 상위 스키마 선언 부는 4.0 버전으로 전부 변경

  ```xml
  <web-app
    xmlns="http://xmlns.jcp.org/xml/ns/javaee"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
    version="4.0">
  ```

* `<multipart-config>` 설정

  ```xml
      <multipart-config>
        <!-- 파일을 저장할 공간 -->
        <location>C:\\upload\\temp</location>
        <!-- 업로드되는 파일의 최대 크기 -->
        <max-file-size>20971520</max-file-size> <!-- 1MB * 20 -->
        <!-- 한번에 올릴 수 있는 최대 크기 -->
        <max-request-size>41943040</max-request-size> <!-- 40MB -->
        <!-- 파일이 메모리에 기록되는 임계값  -->
        <file-size-threshold>20971520</file-size-threshold> <!-- 20MB -->
      </multipart-config>
  ```

* 스프링에서의 업로드 처리는 MultipartResolver 타입의 빈을 등록해야 가능하다. (servlet-context.xml)

  ```xml
  	<!-- commons-fileupload를 사용할 때와는 다르게, 이 빈에 상세 속성을 적지 않고, web.xml에 정의된 값을 따르는 것 같다. -->
  	<beans:bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver" />
  
  ```

  

#### 21.1.2 Java 설정을 이용하는 경우









## 22. 파일 업로드 상세 처리





## 23. 브라우저에서 섬네일 처리





## 24. 첨부파일의 다운로드 혹은 원본 보여주기





## 25. 프로젝트의 첨부파일 - 등록





## 26. 게시물의 조회와 첨부파일





## 27. 게시물의 삭제와 첨부파일





## 28. 게시물의 수정과 첨부파일





## 29. 잘못 업로드된 파일 삭제







---

## jex05-board 프로젝트 진행 특이사항







---

## 의견

* 




## 정오표

* 


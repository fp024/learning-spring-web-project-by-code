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
        <!-- 파일이 디스크에 기록될 때까지의 크기 임계값 -->
        <file-size-threshold>20971520</file-size-threshold> <!-- 20MB -->
      </multipart-config>
  ```

* 스프링에서의 업로드 처리는 MultipartResolver 타입의 빈을 등록해야 가능하다. (servlet-context.xml)

  ```xml
  	<!-- commons-fileupload를 사용할 때와는 다르게, 이 빈에 상세 속성을 적지 않고, web.xml에 정의된 값을 따르는 것 같다. -->
  	<beans:bean id="multipartResolver" class="org.springframework.web.multipart.support.StandardServletMultipartResolver" />
  
  ```

  

#### 21.1.2 Java 설정을 이용하는 경우

* MultipartConfigElement 클래스 이용
  * https://docs.oracle.com/javaee/7/api/javax/servlet/MultipartConfigElement.html

* WebConfig 클래스

  ```java
    /** 처리할 수 있는 핸들러를 찾을 수 없을 때, 404를 예외로 처리하는 사용자 정의 설정 서블릿 3.0 이상에서 설정 가능. */
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
      registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
  
      MultipartConfigElement multipartConfig =
          new MultipartConfigElement("C:\\upload\\temp", 20971520, 41943040, 20971520);
      registration.setMultipartConfig(multipartConfig);
    }
  ```

* ServletConfig 클래스

  ```java
    @Bean
    public MultipartResolver multipartResolver() {
      return new StandardServletMultipartResolver();
    }
  ```

  

### 21.2 \<form> 방식의 업로드

#### 21.2.1 MultipartFile 타입

* getOriginalFilename() 로 파일 명을 얻을 때, IE에서의 업로드는 파일경로 + 파일이름의 전체 경로가 나오고, Chrome에서는 파일이름만 받을 수 있었다.

* 책에서는 아직 한글 파일 입력이 안된다고 하였는데, 현재 설정한 프로젝트에서는 CharacterEncodingFilter 으로 강제 UTF-8 설정을 하고 있어서 한글파일 인식에 문제는 없었다.

* **특이사항**

  * MultipartFile을 사용한 컨트롤러 메서드 종료 후, 파일이 자동 정리 되는 (clean up) 현상이 나타났다.

    ```java
    @PostMapping("/uploadFormAction")
      public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
        for (MultipartFile multipartFile : uploadFile) {
          LOGGER.info("------------------------------------");
          LOGGER.info("Upload File Name: {}", multipartFile.getOriginalFilename());
          LOGGER.info("Upload File Size: {}", multipartFile.getSize());
    
          File saveFile = new File(uploadFolder, "tmp_" + multipartFile.getOriginalFilename());
    
          try {
            multipartFile.transferTo(saveFile);
            // transferTo()로 처음 생성한 파일은 (메모리 -> 파일저장) 메서드가 끝날때 자동 정리되는 것 같다.
            //
            // StandardServletMultipartResolver 클래스의 cleanupMultipart 메서드 참조바람!!!
            //
            // 파일이 업로드 된 이후 뭔가 다른 처리를 해줘야하는 것을 권고하는 것 같은데..
            // 이 프로젝트는 테스트 동작 확인용이여서, 최초 생성할 때는 tmp_파일명으로 생성후 이후 tmp_를 제거하는 식으로
            // 이름만 바꾸어줬다.
            //
            // web.xml의 <multipart-config> 이하 <location> 설정은...
            // <file-size-threshold> 를 초과했을 때, 임시로 사용할 디스크 경로라고 하는데,
            // 동시에 2MB씩 10개 이상 파일이 업로드 시도 되었을 때에 한해서, 해당 디렉토리에 임시 파일이 생성되는 것을
            // 볼 수 있을 것 같긴하다.
            //
            saveFile.renameTo(new File(uploadFolder, multipartFile.getOriginalFilename()));
          } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
          }
        }
      }
    ```

    일단 uploadFormPost 메서드는 약간의 파일 처리를 추가해서 자동 삭제를 막았다.

  * **linux 환경에서도 실행 테스트를 하기 때문에 Maven에서 프로필 환경을 분리했다.**

    ```xml
      <!-- web.xml에서 경로 변화를 주기위해, profile을 윈도우와 리눅스 환경으로 나눠보았다. -->
      <profiles>
        <profile>
          <id>win</id>
          <activation>
            <activeByDefault>true</activeByDefault>
            <os>
              <family>windows</family>
            </os>
          </activation>
          <properties>
            <env>win</env>
            <web-xml-location>${project.basedir}/src/main/webapp/WEB-INF/web.xml</web-xml-location>
          </properties>
        </profile>
    
        <profile>
          <id>linux</id>
          <activation>
            <os>
              <family>linux</family>
            </os>
          </activation>
          <properties>
            <env>linux</env>
            <web-xml-location>${project.basedir}/src/main/webapp/WEB-INF/web-${env}.xml</web-xml-location>
          </properties>
    
          <build>
            <resources>
              <resource>
                <directory>src/main/resources/${env}</directory>
              </resource>
              <resource>
                <directory>src/main/resources</directory>
                <excludes>
                  <exclude>project-data.properties</exclude>
                </excludes>
              </resource>
            </resources>
          </build>
        </profile>
      </profiles>
    ```

    web.xml 과 project-data.properties 파일이 윈도우, 리눅스 환경으로 각각 구분될 필요가 있는데, Maven의 profile 기능으로 Maven 실행 환경에 따라 필요한 파일이 사용되도록 하였다.




### 21.3 Ajax 방식의 업로드

이부분은 ex05프로젝트에서는 책의 내용대로 jQuery로 진행하고 jex05 프로젝트에서는 최신 자바스크립트의 내장 메서드를 사용하는 쪽으로 구현해보자!

* jex06 프로젝트의 uploadAjax.jsp에서는 jQuery 대신 최신 자바스크립트로 로직을 구성

  ```javascript
    document.addEventListener('DOMContentLoaded', () => {
      document.querySelector('button').addEventListener('click', () => {
        const formData = new FormData()
        const inputFile = document.querySelector('input[name="uploadFile"]')
        const files = inputFile.files // input이 1개가 명백하니, querySelector를 사용했고, 이것은 배열을 반환하지 않으므로, 배열참조를 제거하자!
  
        console.log(files)
  
        // 파일 데이터를 폼 데이터에 추가
        for (let i = 0; i < files.length; i++) {
          formData.append("uploadFile", files[i])
        }
  
        fetch("/uploadAjaxAction", {
          method: "POST",
          body: formData
        }).then(response => { // https://developer.mozilla.org/en-US/docs/Web/API/Response/text
          return response.text().then(text => {
            alert("Uploaded: " + text)
          })
        })
      })
    })
  ```

당장 404 콘솔 오류로그 뜨는것 보기싫어서, 해당 컨트롤러 메서드에 `@ResponseBody`를 붙이고, `ResponseEntity<String>`로 결과를 반환했다.



## 22. 파일 업로드 상세 처리

### 22.1 파일의 확장자나 크기의 사전처리

* https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Statements/for...of

### 🎇Internet Explorer 11이 설치된 환경에서 확인해보았을 때.. 안되는  부분이 있다.

* const, let이 처리되긴하는데 정상적으로 잘 되는지 확신이 안듬.

  ```javascript
  const test = "11"
  console.log(test)
  'test'이(가) 정의되지 않았습니다.
  var test = "111"
  undefined
  console.log(test)
  ```

  콘솔에서 저렇게 뜨는 것으로 보아 제대로 지원이 안되는 것 같은데... ex05 프로젝트는 var로 써야겠다.

* for of 안됨

  for index 방식으로 다시 변경했다.



#### 22.1.1 중복된 이름의 첨부파일 처리

1. 밀리세컨드까지 구분 또는 UUID를 사용하여 파일명 저장
2. 하나의 폴더에 많은 파일이 들어가지 않도록 년/월/일 구조로 폴더를 만들어 저장



#### 22.1.2 년/월/일 폴더의 생성

#### 22.1.3 중복 방지를 위한 UUID적용

난수 만들떄, 블로킹이 생길 수 있으므로, `.nvm/jvm.config` 파일 생성후 아래 내용 기입

```bash
-Djava.security.egd=file:/dev/./urandom
```

윈도우나 Linux나 경로는 같은 것 같다. 윈도우에서는 실제로 저 경로가 없더라도 시스템 적으로 인식이 되는 것 같다. 

* transfer() 로 복사한 대상 파일이 자동삭제되서, 그대로 사용해봤는데, 여전하다..

  https://stackoverflow.com/questions/49849576/springboot-multipart-file-upload-remove-the-local-server-copy

  다른 사람도 동일한 문제가 있던 것 같은데... 자동삭제가 안되도록 transfer()로 복사한 대상 파일을 이름 바꿔두는 로직은 그대로 남겨두었다.

* MultipartResolver 인터페이스

  ```java
  /**
  * Cleanup any resources used for the multipart handling,
  * like a storage for the uploaded files.
  * @param request the request to cleanup resources for
  */
  void cleanupMultipart(MultipartHttpServletRequest request);
  ```

  

### 22.2 섬네일 이미지 생성

섬네일 생성에 라이브러리 사용

```xml
<!-- https://mvnrepository.com/artifact/net.coobird/thumbnailator -->
<dependency>
    <groupId>net.coobird</groupId>
    <artifactId>thumbnailator</artifactId>
    <version>0.4.17</version>
</dependency>
```

최신버전이 2022년 2월 11일자인 것으로 보아 최근에도 버전업이 되고 있다.



#### 22.2.1 이미지 파일의 판단

요청완료시 transfer() 해서 생긴 파일을 자동으로 삭제하므로 이름을 바꿔왔는데, 섬네일 생성할 때, 이름 바뀐 파일로 FileInputStream을 생성해서 섬네일을 만들도록 수정했다.



### 22.3 업로드된 파일의 데이터 반환

업로드된 결과를 피드백으로 보내주기에 대해 나는 간단하게 성공이면 `success`를 전달하게 해두었는데, 책은 좀 더 자세한 도메인을 반환하게 하였다. 따라해보자!

ex05에서는 jackson을 사용하고, jex05는 gson을 사용해보자!



#### 22.3.1 AttachFileDTO 클래스

#### 22.3.2 브라우저에서 Ajax 처리

fetch()를 사용했을 때도 쉽게 json 데이터를 받아올 수 있었다.

```javascript
      /*
      fetch("/uploadAjaxAction", {
        method: "POST",
        body: formData
      }).then(response => { // https://developer.mozilla.org/en-US/docs/Web/API/Response/text
        return response.json().then(data =>
            console.log(data)
        )
      })
      */
      fetch("/uploadAjaxAction", {
        method: "POST",
        body: formData
      }).then(response => response.json())
        .then(json => console.log(json))
```



## 23. 브라우저에서 섬네일 처리

### 23.1 `<input type='file'>`의 초기화

최신자바스크립트를 사용하기로한 jex05 프로젝트에서는 JQuery 사용부분을 아래처럼 변경했다.

```javascript
// uploadDiv 의 전체 복사
var cloneObj = $(".uploadDiv").clone();
// --> uploadDiv 이하의 모든 요소를 복사하기 위해 cloneNode의 인자로 true를 주었다.
const cloneObj = document.querySelector('.uploadDiv').cloneNode(true)

// ...

// 업로드 후, uploadDiv 를 복사해두었던 빈 내용의 uploadDiv로 교체한다.
$(".uploadDiv").html(cloneObj.html());
// --> 
document.querySelector('.uploadDiv').replaceWith(cloneObj);
```

* 참조
  * cloneNode: https://developer.mozilla.org/ko/docs/Web/API/Node/cloneNode
  * replaceWidth: https://developer.mozilla.org/en-US/docs/Web/API/Element/replaceWith



### 23.2 업로드된 이미지 처리

#### 23.2.1 파일 이름 출력

```javascript
    const uploadResult = document.querySelector('.uploadResult ul')

    function showUploadedFile(uploadResultArr) {
      let str = ""
      for(const obj of uploadResultArr) { // JQuery의 $.each() 대체
        str += "<li>" + obj.fileName + "</li>"        
      }
      uploadResult.insertAdjacentHTML('beforeend', str) // JQuery의 $.append() 대체
    }
```

* 참조
  * insertAdjacentHTML: https://developer.mozilla.org/ko/docs/Web/API/Element/insertAdjacentHTML



#### 23.2.2 일반 파일의 파일 처리

* 파일 첨부 아이콘을 구해야하는데, 대충 사이즈(`942 x 720`)만 맞춰서 그려보자!

  ![attach.png](doc-resources/attach.png)

​		이번에는 JQuery 메서드를 사용한 부분은 없어서 ex05와 jex05의 코드는 동일하다.



#### 23.2.3 섬네일 이미지 보여주기

* `ResponseEntity<byte[]>` 을 반환해서 업로드된 이미지를 GET요청으로 바로 보여줄 수 있는 부분 구현이 재미있었다.

* 그런데, 템플릿 리터럴을 쓰려면 대상 변수가 let이나 const로 미리 선언이 되어있어야하는 것인가?

  ```javascript
  str += "<li><img src='/display?fileName=" + fileCallPath + "'><li>" + obj.fileName + "</li>";
  // 아래처럼 바꿔서 되야할 것 같은데... 잘 안된다. 😥
  str += `<li><img src='/display?fileName=${fileCallPath}'><li>${obj.fileName}</li>`
  ```

  



## 24. 첨부파일의 다운로드 혹은 원본 보여주기

첨부 파일의 종류에 따라 동작을 다르게 처리함.

1. 이미지 > 원본이미지를 레이어로 띄워서 보여주기

2. 일반파일 > 다운로드

   

### 24.1 첨부파일 다운로드

* 다운로드 타입 지정: MIME 타입을 `application/octet-stream`으로 지정
* 반환 타입에서 `ResponseEntity<T>`의  타입을 `org.springframework.core.io.Resource` 로 사용

* Content-Disposition 헤더 값 지정

  한글 파일명 깨지는 문제 방지를 위한 처리

  ```java
        headers.add(
            "Content-Disposition",
            "attachment; filename="
                + new String(resource.getFilename().getBytes("UTF-8"), "ISO-8859-1"));
  ```

  한글을 ISO-8859-1로 인코딩 해서 설정하는 것 같은데? 왜 이렇게 해야할까?



#### 24.1.1 IE/Edge 브라우저의 문제

윈도우 10에서는 IE 11을 아직 사용할 수 있어서 여기서 테스트를 하는데,  다음과 같았다.

```
# 파일명을 아래로 보내면 400 응답으로 처리됨
http://192.168.100.40:8080/download?fileName=소스.zip

# URI 인코딩 해서 보내면 문제 없이 받을 수는 있음.
http://192.168.100.40:8080/download?fileName=%EC%86%8C%EC%8A%A4.zip
```

왜이럴까? 책에서는 이런문제는 언급이 안되었었는데...😅 (다음 장에 테스트 할 때 언급이 된다..)

IE 11에서 요청을 보낼 때, URL 파라미터의 한글이 깨진채로 보내서 그런 것 같다.

```
# IE11의 네트워크 탭에 fileName 부분을 보면 깨진체로 전송이 되는 것을 알 수 있다.
http://192.168.100.40:8080/download?fileName=ìì¤.zip
```

Jetty 문제인가 싶어서, cargo를 통해 Tomcat 9로 실행시켰는데...

```
[INFO] [talledLocalContainer] 정보: HTTP 요청 헤더를 파싱하는 중 오류 발생
[INFO] [talledLocalContainer] java.lang.IllegalArgumentException: 요청 타겟에서 유효하지 않은 문자가 발견되었습니다. 유효한 문자들은 RFC 7230과 RFC 3986에 정의되어 있습니다.
[INFO] [talledLocalContainer]   at org.apache.coyote.http11.Http11InputBuffer.parseRequestLine(Http11InputBuffer.java:494)
...
```

Tomcat 9도 동일하게 400오류가 발생한다.

Filefox나 Chrome에서는 문제가 없음.

🎇 **IE 11에서는 페이지에서 다운로드 링크 클릭 시점에 JavaScript 단에서 파라미터 부분의 한글을 URI 인코딩 해서 보내야할 것 같다.** 

🎇 원래는 IE에서 URI인코딩을 자동으로 해줘야하는데,  URL 파라미터 값이 한글문자 뒤에 `.zip` 같은 확장자가 붙은 값이면 제대로 URI인코딩을 안해주는 것 같다.



### Edge 브라우저의 User Agent

예전 Edge는 Edge라는 문자열이 User Agent에 포함되었던 것 같은데, 크로미움으로 바뀌면서 `Edg/버전` 형식으로 바뀐 같다.

```
Mozilla/5.0 (Windows NT 10.0; Win64; x64)  
AppleWebKit/537.36 (KHTML, like Gecko)  
Chrome/90.0.4430.85  
Safari/537.36  
Edg/90.0.818.46
```

안드로이드 버전 Edge의 경우의 User Agent 예시는 아래와 같다.

```
Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N)  
AppleWebKit/537.36 (KHTML, like Gecko)  
Chrome/90.0.4430.85  
Mobile Safari/537.36  
EdgA/90.0.818.46
```

다운로드 할 때도 크롬으로 인식된다.

* https://docs.microsoft.com/en-us/microsoft-edge/web-platform/user-agent-guidance

* IE일 때는 \\ 문자을 전부 한칸 띄어쓰기로 바꾸는 걸까? 이 치환 처리가 없어도 딱히 문제는 없었는데.. 일단 넣었다.

  ```java
  downloadName = URLEncoder.encode(resourceName, "UTF-8").replace("\\+", " ");
  ```

  

#### 24.1.2 업로드된 후 다운로드 처리



### 24.2 원본 이미지 보여주기

#### 24.2.1 원본 이미지를 보여줄 `<div>` 처리

IE 11에서 특이한 현상이 있다.

```javascript
 console.log(originPath);
          str += "<li>"
              + "<a href=\"javascript:showImage(\'" + originPath + "\')\">"
              + "<img src='/display?fileName=" + fileCallPath + "'>"
              + "</a>"
              + "</li>";
```

originPath 의 내용은 URI 인코딩 된 내용이고 이걸 console.log로 찍었을 때는 이상이 없었는데, 이 값이 showImage() 함수로 전달되어 alert로 값을 띄울 때에는 한글 부분이 깨졌다..😅 얼럿에서만 그런 거면 상관은 없는데...

아니다 originPath의 파일명을 미리 encode를 할 필요가 없는 것 같다. 미리 encode하면 showImage에서 한글이 깨져서, 요청시점에 하는게 맞는 것 같다... 저자님은 그렇게 하셨음..



### 최신 자바스크립트 코드로 바꾸기

* html() 으로 img 갱신하는 부분

  * 비우고 img 엘리먼트를 추가 했다.

    * https://stackoverflow.com/questions/3955229/remove-all-child-elements-of-a-dom-node-in-javascript

      ```javascript
          const img = document.createElement('img')
          img.setAttribute('src', 'display?fileName=' + encodeURI(fileCallPath));
          bigPicture.textContent = '';
          bigPicture.insertAdjacentElement('beforeend',img);
      ```

* css 스타일 지정하는 것들.. show(), hide()

  ```javascript
  bigPictureWrapper.style.display = 'flex'
  ...
  document.querySelector(".bigPictureWrapper").style.display = 'none';
  ```

  엘리먼트의 style 속성의 display 값을 변경해줌.

* animate()

  이게 좀 어려운거 같았는데, 엘리먼트에 대해 바로 animate()를 사용할 수 있었다. jQuery와 동일하진 않지만.. 옵션만 다르게 전달하면 되었다.

  * https://developer.mozilla.org/en-US/docs/Web/API/Element/animate

    ```javascript
    // 썸네일 클릭했을 때.. 크기 키우기
    bigPicture.animate([
      {transform: 'scale(0)'},
      {transform: 'scale(1)'}
    ], {
      duration: 1000,
      iterations: 1,
    })
    ...
    
    // 열려진 원본 이미지 클릭 했을 때, 크기 줄이면서 레이어 숨기기
    document.querySelector(".bigPicture").animate([
      {transform: 'scale(1)'},
      {transform: 'scale(0)'}
    ], {
      duration: 1000,
      iterations: 1
    })
    setTimeout(function () {
      document.querySelector(".bigPictureWrapper").style.display = 'none'
    }, 950) // 숨기는 것을 1초로 하면 깜빡임이 눈에 잘띄는 편이라 약간 시간을 줄였다.
    
    ```

    잘 되어서 다행이긴 하다. 😄



### 24.3 첨부파일 삭제

* 고려해야할 점
  * 이미지 파일일 때는 섬네일까지 같이 삭제
  * 파일을 삭제한 후에는 브라우저에서 섬네일과 파일 아이콘이 삭제되도록 처리필요
  * 비정상적으로 브라우저 종료시 업로드된 파일의 처리



#### 24.3.1 일반 파일과 이미지 파일의 삭제

* 첨부 파일 삭제를 위한 `<span>` x가 업로드 후에 생성되기 때문에 이벤트 위임 방식으로 처리해야함.
  * `<span>` 상위에 `.uploadResult` 에 click을 걸지만 하위 span가 span일 경우에만 삭제 함수가 실행되도록 함.
  * jQuery를 사용하지 않고 순수 자바스크립트로 사용할 때는 `event.target.tagName`을 검사해서 span일 때만 삭제함수가 동작하도록 하면 될 것 같다.

* 서버에서 첨부파일의 삭제

  

* 최신 Java에서 URLDecoder 의 선언 중 Charset 타입으로 문자셋정보를 전달하면 [UnsupportedEncodingException](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/UnsupportedEncodingException.html)을 던지지 않는 메서드가 추가되어있다.

  * Java 8
    * https://docs.oracle.com/javase/8/docs/api/java/net/URLDecoder.html
  * Java 17
    * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/net/URLDecoder.html

*  x 누를때... 이미지를 감싸는 `<li>`도 삭제되도록 개선했다.

* 최신 JavaScript 코드로 개선

  ```javascript
  document.querySelector('.uploadResult').addEventListener('click', function (e) {
    const target = e.target
  
    if (target.tagName.toLowerCase() !== 'span') {
      return
    }
  
    const param = new URLSearchParams({"fileName": target.dataset.file, "type": target.dataset.type})
    console.log(param)
  
    fetch("/deleteFile", {
      method: "POST",
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: param
    }).then(response => response.text())
      .then(result => {
      alert(result)
      target.closest("li").remove()
    })
  })
  ```
  
  * data 속성을 사용하는 방법
    * https://developer.mozilla.org/ko/docs/Learn/HTML/Howto/Use_data_attributes
  * ajax form 전송
    * https://developer.mozilla.org/ko/docs/Web/API/Fetch_API/Using_Fetch#%EC%9A%94%EC%B2%AD_%EC%98%B5%EC%85%98_%EC%A0%9C%EA%B3%B5
    * https://developer.mozilla.org/ko/docs/Web/API/URLSearchParams
    * jQuery.ajax에서는 별도 옵션을 지정하지 않으면 알아서 key=value form 데이터로 전송하는데, fetch를 사용할 때는 명시적으로 지정을 해줘야했다.
  * 부모 요소 찾기
    * https://developer.mozilla.org/ko/docs/Web/API/Element/closest



#### 24.3.2 첨부파일의 삭제 고민

* 사용자가 비정상적으로 브라우저를 종료하고 나가는 행위에 대처하는 방법?
  * 실제 최종적인 결과와 서버에 업로드된 파일의 목로글 비교해서 처리하기
  * spring-batch 또는 Quartz 라이브러리를 사용해 스캐줄 배치 처리





## 25. 프로젝트의 첨부파일 - 등록

* uuid와 파일명, 파일 경로등을 저장할 필요가 있으므로, 테이블 생성이 필요하다
  * [DB 스키마 정리](../db-schema.md) 문서의 `6장 진행스키마`를 참고할 것!

* 단순 예제 프로젝트는 jQuery 사용처를 최신 순수 자바스크립트 코드로 바꾸는 작업을 진행했는데, 프로젝트는 그대로 jQuery 유지하는 것이 낫겠다. 그 부분이 이 스터디의 핵심 주제가 아니기도하고 바꾸려면 시간이 많이 걸릴듯 😓...

### 25.1 첨부파일 정보를 위한 준비

* BoardAttachVO의 fileType 필드는 boolean으로 되어있던데, FileType이라는 Enum으로 바꿨음.

  * DB 컬럼에는 이미지일 경우 "I"로 저장하기로 한 것 같은데, mybatis의 EnumTypeHandler를 상속받아 FileTypeEnumHandler를 만들어서 저장할 때는 FileType의 코드 값으로 저장하고 불러올 때는 코드 값으로 FileType Enum이 만들어지도록 처리함.

    ```java
    /**
     * 파일 타입 구분 Enum
     * 일반 파일 또는 이미지 파일 등등..
     */
    public enum FileType {
      NORMAL("N"),
      IMAGE("I");
    
      @Getter private final String code;
    
      FileType(String code) {
        this.code = code;
      }
    
      public static FileType valueByCode(String code) {
        for (FileType fileType : FileType.values()) {
          if (fileType.code.equals(code)) {
            return fileType;
          }
        }
        throw new IllegalArgumentException("잘못된 파일 타입 코드 입니다. 코드:" + code);
      }
    }
    
    
    /**
     * FileType 핸들러
     */
    @MappedTypes(FileType.class)
    public class FileTypeEnumHandler extends EnumTypeHandler<FileType> {
      public FileTypeEnumHandler(Class<FileType> type) {
        super(type);
      }
    
      @Override
      public void setNonNullParameter(
          PreparedStatement ps, int i, FileType parameter, JdbcType jdbcType) throws SQLException {
        if (jdbcType == null) {
          ps.setString(i, parameter.getCode());
        } else {
          ps.setObject(i, parameter.getCode(), jdbcType.TYPE_CODE);
        }
      }
    
      @Override
      public FileType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String s = rs.getString(columnName);
        return s == null ? null : FileType.valueByCode(s);
      }
    
      @Override
      public FileType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String s = rs.getString(columnIndex);
        return s == null ? null : FileType.valueByCode(s);
      }
    
      @Override
      public FileType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String s = cs.getString(columnIndex);
        return s == null ? null : FileType.valueByCode(s);
      }
    }
    ```

    핸들러를 잘 써본적이 없어서 mybatis 내장된 기본 열거형 핸들러 상속해서 약간 수정했는데... 동작에는 문제 없었다. 기본 열거형은 Enum의 name() 기준으로 저장하는데... 위와 같이 Handler를 사용자정의하지 않으면 "IMAGE"라는 문자열을 그대로 저장하려하기 때문에... code로 저장 및 조회되도록 바꿔줘야했다.



### 25.2 등록을 위한 화면 처리

#### 25.2.1 JavaScript 처리

* CSS 확인사항
  아래 부분은 X 아이콘 표시 스타일인데... 나는 fontawesome의 버전 5를 CDN을 통해 사용하므로 아래 URL의 내용대로 사용해보자. 부트 스트랩도 아이콘 사용하기 편하게 되어있는 것 같다.
  
  * fontawesome :  https://fontawesome.com/v5/icons/times?s=solid
  
    ```html
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <!-- ... -->
    <i class='fas fa-times'></i>
    ```
  
  * bootstrap : https://icons.getbootstrap.com/icons/x/
  
    ```html
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css">
    <!-- ... -->
    <i class='bi bi-x'></i>
    ```
    



#### 25.2.2 첨부파일의 변경 처리

* **TODO**: 첨부 파일을 삭제해도, 파일 선택 버튼 옆에  파일 명이나 파일 선택 갯수가 남는데, 이거 어떻게 해야할까?
  * 먼저 예제에서는 Upload 버튼을 따로 둬서, input file을 복사해서 사용하면 됬던 것 같은데, 이부분 어떻해해야할지?



#### 25.2.3 게시물 등록과 첨부파일의 데이터베이스 처리

* 파일 첨부 시점에 파일은 Ajax를 통해 미리 업로드상태이고, 게시물 등록시점에 등록한 파일 정보가 같이 들어가도록 구성
* 파일정보
  1. UUID
  2. 파일 이름
  3. 파일 타입

* 서버 측에서 첨부파일 정보를 List로 받으므로 JavaScript 배열형태로 만들어서 전달해야한다.



### 25.3 BoardController, BoardService의 처리

* TBL_ATTACH의 UPLOADPATH 컬럼에 경로 구분자에 대해서 윈도우 기준으로 할지(\\)  Unix 기준으로 할지(/) 정해야할 것 같다.  일단 나는 DB저장할 때는 Unix 기준으로 저장하기로 했다.
* 이번 장에서 파일 타입 정의한것이 좀 이상한데... CHAR(1)에다가 기본 값 알파벳 대문자 `I` 를 해놓고, 코드 상으로는 boolean으로 되어있어서 0, 1이 들어가게 해두신 것 같은데.... 난 Enum으로 처리하게 했고, 이미지면 대문자 `I`, 그외 일반 파일이면 `N`이 DB에 들어가도록 했다.



## 26. 게시물의 조회와 첨부파일

게시물과 연관된 첨부파일 정보를 가져올 때, JOIN을 해서 한번에 가져오거나,  Ajax로 가져오는 방법이 있는데, 저자님은 Ajax를 이용한 방법을 사용하신다고 한다.

* 기존 코드를 최소한으로 수정해서 사용하는 것이 가능하므로...
* 댓글 조회방법의 일관성을 위함.



### 26.1 BoarService와 BoardController 수정



### 26.2 BoardController의 변경과 화면 처리

* `MediaType.APPLICATION_JSON_UTF8_VALUE`는 Deprecated 되었으니, `MediaType.APPLICATION_JSON_VALUE` 을 사용하자.

#### 26.2.1 게시물 조회 화면의 처리

* fontawesome에서 Icon 찾을 때, Free로된 것을 사용해야 잘 적용되는 것 같다.
  * https://fontawesome.com/v5/icons/paperclip?s=solid



#### 26.2.2 첨부파일 보여주기



#### 26.2.3 첨부파일 클릭 시 이벤트 처리

* **TODO:** 이미지를 animate로 열때 약간 화면 떨림이 나타나는데, 이부분은 잘 모르겠다.

#### 26.2.4 원본 이미지 창 닫기





## 27. 게시물의 삭제와 첨부파일

실제 파일 삭제 작업 추가이전에, 데이터 베이스에서의 첨부파일 정보 삭제 기능을 먼저 진행.



### 27.1 첨부파일 삭제 처리

#### 27.1.1 BoardServiceImpl의 변경



#### 27.1.2 BoardController의 파일 삭제

파일 삭제 작업 순서

* 해당 게시물의 첨부파일 정보를 미리 준비
* DB에서 게시물과 첨부파일 데이터 삭제
* 첨부파일 목록을 이용해서 해당 디렉토리의 일반파일 삭제 (이미지 파일인 경우 썸네일 이미지도 삭제)

##### Criteria 수정

* `getListLink()` 메서드가 소개되어있는데, 이부분은 이미 `getLiink()` 메서드를 앞에서 만들었었다.

##### 파일 삭제 처리

* 나는 DB에 저장하는 Upload 경로는 Unix Path 구분자 기준으로만 저장하기로 했으므로, 삭제시에는 DB에서 불러온 업로드 경로 문자열 속 경로 구분자를 현재 시스템의 경로 구분자로 바뀌도록 처리했다.



## 28. 게시물의 수정과 첨부파일

* 게시물의 첨부파일의 수정은 삭제후 재업로드 하는 것임.



### 28.1 화면에서 첨부파일 수정

* 수정화면에서는 첨부파일 마다 삭제 버튼이 있어야함
* 원본이미지 확대나, 다운로드 기능은 없어도 됨

#### 28.1.1 첨부파일 데이터 보여주기

* **질문?**: 584~585쪽 사이에 있는 첨부파일  데이터 그려주는 부분에서, $(document).ready() 안에 있어서 실행이 흐름대로 될 텐데, `(function() { ... })()`  로 다시 감싼 이유를 잘 모르겠다. 😥

#### 28.1.2 첨부파일의 삭제 이벤트

* 수정 페이지의서의 삭제는 화면에서 `<li>`만 삭제하도록 함.
  * 나중에 등록시 li를 검사해서 없으면 실제로 삭제하는 식으로 할 것 같다.

#### 28.1.3 첨부파일 추가

* 중복코드가 많아지는데, 😥😥😥 약간만 정리했다. 

#### 28.1.4 게시물 수정 이벤트 처리



### 28.2 서버 측 게시물 수정과 첨부파일

28.2.1 BoardService(Impl)의 수정

* 기존 첨부파일 정보를 지우고 다시 첨부파일 데이터를 추가하는 방식 구현.

내 환경에서는 DB에 uploadPath를 Unix 경로 구분자로 저장하므로, JavaScript에서 윈도우 경로구분자를 Unix 경로구분자로 바꾸는등의 코드는 필요가 없어져서 제거했다.



## 29. 잘못 업로드된 파일 삭제

지금 까지 구현에서 더 고려해야할 점.

파일이 업로드만 되어있고, 연관이 없는 상태 ( DB에 첨부파일 정보가 없지만, 업로드 경로에는 파일이 남아있는 상태 )

1. 첨부 파일만 등록하고 게시물 등록을 하지 않았을 때.
2. 게시물 수정시 기존 파일을 삭제하고 등록 할 때, 연관만 해제되고 파일은 서버에 그대로 있음

### 29.1 잘못 업로드된 파일의 정리

이런 파일들을 찾을 때, 어제 기준으로 찾아야함. 오늘 기준으로 찾을 경우 현재 작성 및 수정하고 있는 게시물에 영향을 줄 수 있음.



### 29.2 Quzrtz 라이브러리 설정

* 현재 프로젝트는 `Quartz` 사용해보고, `jex05-board`에서는 `Spring Scheduler` 사용해보자!

#### 29.2.1 Java 설정을 이용하는 경우

* jex05-board에서 진행할 예정.

#### 29.2.2 Task 작업의 처리

🎃 일단 띄어 봤는데... HikariCP-java7 클래스 중복이 일어난다. Quartz가 끌어오는 것 중이 중복이 있는지 살펴보자!

```
[INFO] +- org.quartz-scheduler:quartz:jar:2.3.2:compile
[INFO] |  +- com.mchange:c3p0:jar:0.9.5.4:compile
[INFO] |  +- com.mchange:mchange-commons-java:jar:0.2.15:compile
[INFO] |  \- com.zaxxer:HikariCP-java7:jar:2.4.13:compile
```

quartz가 끌고 오는데, 나는 HikariCP 상위 버전을 사용하고 있으므로 이부분은 제외처리를 하자!

```xml
    <dependency>
      <groupId>org.quartz-scheduler</groupId>
      <artifactId>quartz</artifactId>
      <version>${quartz.version}</version>
      <exclusions>
        <exclusion>
          <groupId>com.zaxxer</groupId>
          <artifactId>HikariCP-java7</artifactId>
        </exclusion>
      </exclusions>
    </dependency>
```





###  29.3 BoardAttachMapper 수정

```sql
SELECT TO_CHAR(SYSDATE - 1, 'yyyy/mm/dd') FROM DUAL
```

어제 날짜를 구할 수 있긴한데... 웹서버 기준 날짜로 Java에서 하루전 날짜 String 을 DB에 전달해주는 것이 테스트가 편할 수 있을 것 같은데... jex05에서는 그렇게 해봅시다 🎈



### 29.4 cron 설정과 삭제 처리

```java
// 매일 새벽 두시 실행
@Scheduled(cron="0 0 2 * * *")
public void checkFiles() throws Exception { ... }
```

* Java 코드에서 하루전 날짜 구하는 코드 개정판에서는 Java 8 날짜 함수로 바꿔주시면 좋을 것 같다.

* `.collect(Collectors.toList());` 를 그냥 `.toList()`로 바꿀 수 있는데 Java 16 부터 메서드가 추가된 것 같다.



---

## jex05-board 프로젝트 진행 특이사항

* 신규 테이블 `TBL_ATTACH`가 추가 되었으므로 mybatis generator로 도메인과 매퍼를 자동생성 하도록 수정해야한다.



### 1. BoardAttachVO 도메인 , 매퍼 자동 생성

* 일반적인 필드는 자동 매핑 되므로, 이름 변경 또는 타임 지정이 명시적으로 필요한 경우만 XML파일에 작성한다.

  * https://mybatis.org/generator/configreference/columnOverride.html

    ```xml
    <!-- 타켓 테이블 - 첨부파일 정보 테이블 -->
    <table tableName="tbl_attach" domainObjectName="BoardAttachVO"
    mapperName="BoardAttachMapper">
      <columnOverride column="uploadpath" property="uploadPath" />
      <columnOverride column="filename" property="fileName" />
      <columnOverride column="filetype" property="fileType" jdbcType="CHAR" javaType="org.fp024.domain.FileType" typeHandler="org.fp024.typehandler.FileTypeEnumHandler" />
      <columnOverride column="bno" property="bno" jdbcType='BIGINT' javaType='java.lang.Long' />
    </table>
    ```

* 생성 커맨드

  ```bash
  $ mvn mybatis-generator:generate
  ```

  ```bash
  === Java 17, Maven 3.8.x Command Prompt ===
  JAVA_HOME=C:\JDK\17
  MAVEN_HOME=C:\Maven\3.8.x
  
  C:\git\learning-spring-web-project-by-code\part06\jex05-board>mvn mybatis-generator:generate
  [INFO] Scanning for projects...
  [INFO]
  [INFO] -----------------------< org.fp024:jex05-board >------------------------
  [INFO] Building jex05-board 1.0.0-BUILD-SNAPSHOT
  [INFO] --------------------------------[ war ]---------------------------------
  [INFO]
  [INFO] --- mybatis-generator-maven-plugin:1.4.1:generate (default-cli) @ jex05-board ---
  [INFO] Connecting to the Database
  [INFO] Introspecting table tbl_board
  [INFO] Introspecting table tbl_reply
  [INFO] Introspecting table tbl_attach
  [INFO] Generating Record class for table TBL_BOARD
  [INFO] Generating Mapper Interface for table TBL_BOARD
  [INFO] Generating Record class for table TBL_REPLY
  [INFO] Generating Mapper Interface for table TBL_REPLY
  [INFO] Generating Record class for table TBL_ATTACH
  [INFO] Generating Mapper Interface for table TBL_ATTACH
  [INFO] Saving file BoardVO.java
  [INFO] Saving file BoardMapper.java
  [INFO] Saving file BoardVODynamicSqlSupport.java
  [INFO] Saving file ReplyVO.java
  [INFO] Saving file ReplyMapper.java
  [INFO] Saving file ReplyVODynamicSqlSupport.java
  [INFO] Saving file BoardAttachVO.java
  [INFO] Saving file BoardAttachMapper.java
  [INFO] Saving file BoardAttachVODynamicSqlSupport.java
  [WARNING] Existing file C:\git\learning-spring-web-project-by-code\part06\jex05-board\src\main\java\org\fp024\domain\BoardVO.java was overwritten
  [WARNING] Existing file C:\git\learning-spring-web-project-by-code\part06\jex05-board\src\main\java\org\fp024\mapper\BoardMapper.java was overwritten
  [WARNING] Existing file C:\git\learning-spring-web-project-by-code\part06\jex05-board\src\main\java\org\fp024\mapper\BoardVODynamicSqlSupport.java was overwritten
  [WARNING] Existing file C:\git\learning-spring-web-project-by-code\part06\jex05-board\src\main\java\org\fp024\domain\ReplyVO.java was overwritten
  [WARNING] Existing file C:\git\learning-spring-web-project-by-code\part06\jex05-board\src\main\java\org\fp024\mapper\ReplyMapper.java was overwritten
  [WARNING] Existing file C:\git\learning-spring-web-project-by-code\part06\jex05-board\src\main\java\org\fp024\mapper\ReplyVODynamicSqlSupport.java was overwritten
  [INFO] ------------------------------------------------------------------------
  [INFO] BUILD SUCCESS
  [INFO] ------------------------------------------------------------------------
  [INFO] Total time:  10.480 s
  [INFO] Finished at: 2022-06-10T03:21:14+09:00
  [INFO] ------------------------------------------------------------------------
  
  C:\git\learning-spring-web-project-by-code\part06\jex05-board>
  ```

* 🎇 여기까진 했는데... 약간 더 봐야할 부분이 있다. BoardVO가 BoardAttachVO의 목록을 가지게 하는 모양으로 자동으로 어떻게 만들도록 처리할지?

  * 1:N 관계의 도메인 모양을 만들어야하는데, mybatis generator로는 만들수 없을 것 같다.

    * https://groups.google.com/g/mybatis-user/c/56fyjJ0yngI
    * https://github.com/mybatis/generator/issues/226

  * 자동으로 만들어진 모델 클래스에 필드를 추가하면 되긴하는데... 

    * 별도 도메인 클래스를 만들자니, 클래스만 늘어나는 것 같고, 결국은 필드를 추가할 수 밖에 없겠다.

      * 그러면 generate 할 때마다 이 모델은 변경사항을 다시 맞춰 줘야한다.

      ```java
      package org.fp024.domain;
      
      import java.time.LocalDateTime;
      import java.util.List;
      import javax.annotation.Generated;
      
      public class BoardVO {
        ...
        /**
         * 첨부파일 목록
         *
         * <p>1:N 관계를 Mybatis Generator 로 만들어낼 수 없어서, 수동으로 기입한 필드
         */
        private List<BoardAttachVO> attachList;
        ...
        public void setAttachList(List<BoardAttachVO> attachList) {
          this.attachList = attachList;
        }
      
        public List<BoardAttachVO> getAttachList() {
          return attachList;
        }
      
        @Override
        @Generated("org.mybatis.generator.api.MyBatisGenerator")
        public String toString() {
          StringBuilder sb = new StringBuilder();
          ...
          sb.append(", attachList=").append(attachList);
          sb.append("]");
          return sb.toString();
        }
      }
      ```

    



---

## 의견

* 




## 정오표

* p546
  * `"<div></li>"` : div가 빠져야할 것 같다.


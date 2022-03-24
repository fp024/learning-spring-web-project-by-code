## 임시 업로드 경로

* servlet-context.xml 에  업로드 임시경로가 다음과 같이 설정되어있을 때, 절대 경로를 지정하지 않으면 `src/main/webapp` 이하에 디렉토리를 만드는 동작이 보였다.

  ```xml
  <beans:property name="uploadTempDir" value="spring-study-upload-tmp" />
  ```


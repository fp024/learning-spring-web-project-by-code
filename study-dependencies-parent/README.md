# 디펜던시 Parent POM



모듈 버전업을 하려면, 각 pom마다 수정사항이 많아져서, 버전관리만 하는 pom을 별도로 분리했다.

* 하위 프로젝트에서는 아래 내용을 상속받아 사용하도록 하자! 😜
    ```xml
      <parent>
        <groupId>org.fp024</groupId>
        <artifactId>study-dependencies-parent</artifactId>
        <version>1.0.0-BUILD-SNAPSHOT</version>
        <relativePath>../../study-dependencies-parent/pom.xml</relativePath>
      </parent>
    ```


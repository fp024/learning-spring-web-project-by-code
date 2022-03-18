# 로그파일 보관 디렉터리

가상머신 실행시 최근 스터디 내용을 바로 실행하게 하기위해서 아래와 같이 크론탭에 넣어보았다.

* `crontab -e`
  ```
  # 코드로 배우는 스프링 웹프로젝트 스터디 게시판 서버 시작
  @reboot ~/git-fp024/learning-spring-web-project-by-code/start-server.sh
  ```

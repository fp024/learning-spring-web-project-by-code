# 데이터베이스 스키마 변경 진행 과정

처음부터 한번에 스키마를 완성하지 않고 진행해나가면서 만들어나가서 약간 정리가 필요했다. 

##### SYS계정으로 로그인하여  book_ex 사용자 생성

```sql
ALTER SESSION SET "_ORACLE_SCRIPT"=true;

CREATE USER book_ex IDENTIFIED BY book_ex
DEFAULT TABLESPACE USERS
TEMPORARY TABLESPACE TEMP;

GRANT CONNECT, RESOURCE TO book_ex;

/*************************************
 * 0. 테이블 스페이스 공간할당 권한 주기 *
 *************************************/
ALTER USER book_ex DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS;
```

##### 이후 아래 내용을 book_ex 계정으로 로그인 후 진행하자!

```sql


/******************
 * 3장 진행 스키마 *
 ******************/
DROP SEQUENCE seq_board;
DROP TABLE tbl_board;

CREATE SEQUENCE seq_board;

CREATE TABLE tbl_board (
    bno         NUMBER(10,0),
    title       VARCHAR2(200)   NOT NULL,
    content	    VARCHAR2(2000)  NOT NULL,
    writer      VARCHAR2(50)    NOT NULL,
    regdate     DATE            DEFAULT SYSDATE,
    updatedate  DATE            DEFAULT SYSDATE
);

ALTER TABLE tbl_board ADD CONSTRAINT pk_board
    PRIMARY KEY (bno);

/*
    3장 스키마 만든이후 기본으로 100건 게시물 입력을 넣어주자
    Hyper-V에서 실행했을 때는 1천만건을 해도 아주 오래 걸리진 않았는데,
    Docker Desktop + WSL2 환경에서는 좀 느린 것 같다. 그래서 일단 100개만 넣었다.
*/
DECLARE
i NUMBER := 1;

BEGIN
    WHILE(i <= 100)
    LOOP
    INSERT INTO tbl_board (bno, title, content, writer) VALUES (SEQ_BOARD.nextval, '제목' || i, '내용' || i ,'작성자' || i);
    i := i + 1;
    END LOOP;
END;


/******************
 * 4장 진행 스키마 *
 ******************/
CREATE TABLE tbl_reply (
    rno         NUMBER(10,0),
    bno         NUMBER(10,0)        NOT NULL,
    reply       VARCHAR2(1000)      NOT NULL,
    replyer     VARCHAR2(50)        NOT NULL,
    replyDate   DATE                DEFAULT SYSDATE,
    updateDate  DATE                DEFAULT SYSDATE
);

CREATE SEQUENCE seq_reply;

ALTER TABLE tbl_reply ADD CONSTRAINT pk_reply PRIMARY KEY (rno);

ALTER TABLE tbl_reply ADD CONSTRAINT fk_reply_board
  FOREIGN KEY (bno) REFERENCES tbl_board (bno);


/******************
 * 5장 진행 스키마 *
 ******************/
ALTER TABLE tbl_board ADD (replycnt NUMBER DEFAULT 0);

/* 트랜젝션 동작 확인용 단순 테이블 */
/* jex04는 HSQLDB를 사용하니, SET DATABASE SQL SYNTAX ORA TRUE 로 실행후에 테이블을 만들어주자! */
CREATE table tbl_sample1 (col1 VARCHAR2(500));
CREATE table tbl_sample2 (col2 VARCHAR2(50));



```



최종장 Java Config + mybatis-dymamic-sql 사용한 프로젝트는 Embedded HSQLDB로 사용하려하는데, 잘될지 모르겠다. 😓😓😓
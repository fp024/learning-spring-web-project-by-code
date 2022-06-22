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


/******************
 * 6장 진행 스키마 *
 ******************/
CREATE TABLE tbl_attach (
  uuid        VARCHAR2(100) NOT NULL,
  uploadpath  VARCHAR2(200) NOT NULL,
  filename    VARCHAR2(100) NOT NULL,
  filetype    CHAR(1)       DEFAULT 'I',
  bno         NUMBER(10,0)
);
/* Oracle 에서는 컬럼의 대소문자를 구분하지 않으므로, 위의 컬럼 명에 CamelCase로 되어있던 것을 전부 소문자로 바꿨다. */

ALTER TABLE tbl_attach ADD CONSTRAINT pk_attach PRIMARY KEY (uuid);

ALTER TABLE tbl_attach ADD CONSTRAINT fk_board_attach FOREIGN KEY (bno) REFERENCES tbl_board(bno);



/******************
 * 7장 진행 스키마 *
 ******************/
 -- 기본 User 스키마
create table users(
	username varchar_ignorecase(50) not null primary key,
	password varchar_ignorecase(500) not null,
	enabled boolean not null
);

create table authorities (
	username varchar_ignorecase(50) not null,
	authority varchar_ignorecase(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);



-- Oracle DB를 위한 기본 User 스키마
CREATE TABLE USERS (
    USERNAME NVARCHAR2(128) PRIMARY KEY,
    PASSWORD NVARCHAR2(128) NOT NULL,
    ENABLED CHAR(1) CHECK (ENABLED IN ('Y','N') ) NOT NULL
);


CREATE TABLE AUTHORITIES (
    USERNAME NVARCHAR2(128) NOT NULL,
    AUTHORITY NVARCHAR2(128) NOT NULL
);
ALTER TABLE AUTHORITIES ADD CONSTRAINT AUTHORITIES_UNIQUE UNIQUE (USERNAME, AUTHORITY);
ALTER TABLE AUTHORITIES ADD CONSTRAINT AUTHORITIES_FK1 FOREIGN KEY (USERNAME) REFERENCES USERS (USERNAME) ENABLE;
 
-- ORACLE 기준 테스트 유저 데이터 입력
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES ('user00', 'pw00', 'Y');
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES ('member00', 'pw00', 'Y');
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES ('admin00', 'pw00', 'Y');

INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('user00', 'ROLE_USER');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('member00', 'ROLE_MANAGER');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('admin00', 'ROLE_MANAGER');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('admin00', 'ROLE_ADMIN');



-- 기존의 테이블을 사용하는 경우의 기존 테이블 예시
CREATE TABLE TBL_MEMBER (
  USERID        VARCHAR2(50)    NOT NULL PRIMARY KEY,
  USERPW        VARCHAR2(100)   NOT NULL,
  USERNAME      VARCHAR2(100)   NOT NULL,
  REGDATE       DATE            DEFAULT SYSDATE,
  UPDATEDATE    DATE            DEFAULT SYSDATE,
  ENABLED       CHAR(1) CHECK (ENABLED IN ('Y','N') ) NOT NULL 
);

CREATE TABLE TBL_MEMBER_AUTH (
  USERID        VARCHAR2(50)    NOT NULL,
  AUTH          VARCHAR2(50)    NOT NULL,
  CONSTRAINT    FK_MEMBER_AUTH  FOREIGN KEY(USERID) REFERENCES TBL_MEMBER(USERID)
);


  
```



최종장 Java Config + mybatis-dymamic-sql 사용한 프로젝트는 Embedded HSQLDB로 사용하려하는데, 잘될지 모르겠다. 😓😓😓
-- ============================================================
-- 01_schema.sql
-- book_ex 스키마 DDL (테이블, 시퀀스 등) - Oracle 23c
-- ============================================================
-- @formatter:off
CONNECT book_ex/book_ex@//localhost:1521/FREEPDB1
-- @formatter:on

-- 시퀀스 (Oracle 23c: IF NOT EXISTS 지원)
CREATE SEQUENCE IF NOT EXISTS seq_board START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE TABLE IF NOT EXISTS tbl_board
(
    bno        NUMBER(10, 0),
    title      VARCHAR2(200)  NOT NULL,
    content    VARCHAR2(2000) NOT NULL,
    writer     VARCHAR2(50)   NOT NULL,
    regdate    DATE       DEFAULT SYSDATE,
    updatedate DATE       DEFAULT SYSDATE,
    replycnt   NUMBER(10) DEFAULT 0,
    CONSTRAINT pk_board PRIMARY KEY (bno)
);

CREATE SEQUENCE IF NOT EXISTS seq_reply START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE TABLE IF NOT EXISTS tbl_reply
(
    rno        NUMBER(19)     NOT NULL,
    bno        NUMBER(19)     NOT NULL,
    reply      VARCHAR2(1000) NOT NULL,
    replyer    VARCHAR2(50)   NOT NULL,
    replydate  TIMESTAMP(6) DEFAULT SYSTIMESTAMP,
    updatedate TIMESTAMP(6) DEFAULT SYSTIMESTAMP,
    CONSTRAINT pk_reply PRIMARY KEY (rno)
);

ALTER TABLE tbl_reply
    ADD CONSTRAINT fk_reply_board
        FOREIGN KEY (bno) REFERENCES tbl_board (bno);

CREATE TABLE IF NOT EXISTS tbl_attach
(
    uuid       VARCHAR2(100) NOT NULL,
    uploadpath VARCHAR2(200) NOT NULL,
    filename   VARCHAR2(100) NOT NULL,
    filetype   CHAR(1) DEFAULT 'I',
    bno        NUMBER(10, 0),
    CONSTRAINT pk_attach PRIMARY KEY (uuid)
);

ALTER TABLE tbl_attach
    ADD CONSTRAINT fk_board_attach FOREIGN KEY (bno) REFERENCES tbl_board (bno);

CREATE TABLE IF NOT EXISTS tbl_member
(
    userid     VARCHAR2(50)                          NOT NULL,
    userpw     VARCHAR2(200)                         NOT NULL,
    username   VARCHAR2(100)                         NOT NULL,
    enabled    CHAR(1) CHECK (enabled IN ('Y', 'N')) NOT NULL,
    regdate    DATE DEFAULT SYSDATE,
    updatedate DATE DEFAULT SYSDATE,
    CONSTRAINT pk_member PRIMARY KEY (userid)
);

CREATE TABLE IF NOT EXISTS tbl_member_auth
(
    userid VARCHAR2(50) NOT NULL,
    auth   VARCHAR2(50) NOT NULL,
    CONSTRAINT pk_member_auth PRIMARY KEY (userid, auth),
    CONSTRAINT fk_member_auth FOREIGN KEY (userid) REFERENCES tbl_member (userid)
);

-- 자동로그인 토큰 저장 테이블
-- Spring Security의 JdbcTokenRepositoryImpl가 사용하는 테이블
CREATE TABLE IF NOT EXISTS persistent_logins
(
    username  VARCHAR2(64) NOT NULL,
    series    VARCHAR2(64) NOT NULL,
    token     VARCHAR2(64) NOT NULL,
    last_used TIMESTAMP    NOT NULL,
    CONSTRAINT pk_persistent_logins PRIMARY KEY (series)
);
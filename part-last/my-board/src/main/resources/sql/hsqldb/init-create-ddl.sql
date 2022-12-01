CREATE TABLE tbl_board
(
  bno        BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
  title      VARCHAR(200)  NOT NULL,
  content    VARCHAR(2000) NOT NULL,
  writer     VARCHAR(50)   NOT NULL,
  regdate    TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
  updatedate TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
  replycnt   INTEGER      DEFAULT 0,
  PRIMARY KEY (bno)
);

CREATE TABLE tbl_reply
(
  rno        BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
  bno        BIGINT        NOT NULL,
  reply      VARCHAR(1000) NOT NULL,
  replyer    VARCHAR(50)   NOT NULL,
  replydate  TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
  updatedate TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (RNO)
);

ALTER TABLE tbl_reply
  ADD CONSTRAINT FKaq7j8vpula8o1qe07a45bcueq
    FOREIGN KEY (bno)
      REFERENCES tbl_board;

CREATE TABLE tbl_attach
(
  uuid       VARCHAR(100) NOT NULL,
  uploadpath VARCHAR(200) NOT NULL,
  filename   VARCHAR(200) NOT NULL,
  filetype   CHAR(1) DEFAULT 'I',
  bno        BIGINT       NOT NULL,
  PRIMARY KEY (uuid)
);

ALTER TABLE tbl_attach
  ADD CONSTRAINT FKjsipp6so0s23y8245u6mekqj1
    FOREIGN KEY (bno)
      REFERENCES tbl_board;


CREATE TABLE tbl_member
(
  userid     VARCHAR(50)  NOT NULL,
  userpw     VARCHAR(200) NOT NULL,
  username   VARCHAR(100) NOT NULL,
  enabled    CHAR(1)      NOT NULL,
  regdate    TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
  updatedate TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (userid)
);

CREATE TABLE tbl_member_auth
(
  auth   VARCHAR(50) NOT NULL,
  userid varchar(50) NOT NULL,
  PRIMARY KEY (auth, userid)
);

ALTER TABLE tbl_member_auth
  ADD CONSTRAINT FKewt4age68dphy59up0jr2q6d9
    FOREIGN KEY (userid)
      REFERENCES tbl_member;


-- 자동로그인 토큰 저장 테이블
-- Spring Security의 JdbcTokenRepositoryImpl가 사용하는 테이블
-- Java 설정에서 초기화를 할수도 있지만, 명확하게 하기위해 여기서 만든다.
CREATE TABLE persistent_logins
(
  series    VARCHAR(64),
  username  VARCHAR(64) NOT NULL,
  token     VARCHAR(64) NOT NULL,
  last_used TIMESTAMP   NOT NULL,
  PRIMARY KEY (series)
);
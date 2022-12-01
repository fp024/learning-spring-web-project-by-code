-- 회원 등록
INSERT INTO tbl_member (userid, userpw, username, enabled)
VALUES ('user00', '{bcrypt}$2a$10$CdNsrwk7cDs7eBNuW7cp8unvLAA8NKXSqA3UfRgysCjvtZyqCLsWm', '일반사용자00', 'Y');
INSERT INTO tbl_member (userid, userpw, username, enabled)
VALUES ('user01', '{bcrypt}$2a$10$7OrtlXQoaVQjnQEMtK12kuxcqA9g58ZfNZJ9nNElnxuBT4HkPDKNO', '일반사용자01', 'Y');
INSERT INTO tbl_member (userid, userpw, username, enabled)
VALUES ('user02', '{bcrypt}$2a$10$szmbgkK4OalTS9t5409tXelO2Xpsrc8fXRudXFZw6f0p2kLOCZpBe', '일반사용자02', 'Y');

INSERT INTO tbl_member (userid, userpw, username, enabled)
VALUES ('manager80', '{bcrypt}$2a$10$3crhdaHx07QIcj.kZ6rwmOPTwI0mGBk7lh9ubFCh2fy4mwwmKGuGW', '운영자80', 'Y');
INSERT INTO tbl_member (userid, userpw, username, enabled)
VALUES ('manager81', '{bcrypt}$2a$10$a0D7O6DzXUlc4UGXrqyGJeVZvXY8RvUWSP4bEflLWtx1c4VOONQBC', '운영자81', 'Y');
INSERT INTO tbl_member (userid, userpw, username, enabled)
VALUES ('manager82', '{bcrypt}$2a$10$J0N5lEnYviHq2RZTlbssIuaC/WMdSZ.KZ0Az.CkzDlvqXbj5.C5Kq', '운영자82', 'Y');

INSERT INTO tbl_member (userid, userpw, username, enabled)
VALUES ('admin90', '{bcrypt}$2a$10$dcB0GXMEhQwW7E0i3yyR/OGdW1N/QQfioIf/OGN60qAf.Gmjt3fNy', '관리자90', 'Y');
INSERT INTO tbl_member (userid, userpw, username, enabled)
VALUES ('admin91', '{bcrypt}$2a$10$kd0PNukIe.v15HghkXUJIO31BB7ZulAPdMi00LH2O//HuAJAFElMC', '관리자91', 'Y');
INSERT INTO tbl_member (userid, userpw, username, enabled)
VALUES ('admin92', '{bcrypt}$2a$10$JqLbWg6o6PfnLr.yObk4v.LZpFHp0/R7cXc0V8gW2AjaXnkImi7WC', '관리자92', 'Y');

-- 회원 권한 등록
INSERT INTO tbl_member_auth (userid, auth)
VALUES ('user00', 'ROLE_USER');
INSERT INTO tbl_member_auth (userid, auth)
VALUES ('user01', 'ROLE_USER');
INSERT INTO tbl_member_auth (userid, auth)
VALUES ('user02', 'ROLE_USER');

INSERT INTO tbl_member_auth (userid, auth)
VALUES ('manager80', 'ROLE_MEMBER');
INSERT INTO tbl_member_auth (userid, auth)
VALUES ('manager81', 'ROLE_MEMBER');
INSERT INTO tbl_member_auth (userid, auth)
VALUES ('manager82', 'ROLE_MEMBER');

INSERT INTO tbl_member_auth (userid, auth)
VALUES ('admin90', 'ROLE_ADMIN');
INSERT INTO tbl_member_auth (userid, auth)
VALUES ('admin91', 'ROLE_ADMIN');
INSERT INTO tbl_member_auth (userid, auth)
VALUES ('admin92', 'ROLE_ADMIN');


-- 게시물 등록
INSERT INTO tbl_board (title, content, writer, regdate, updatedate, replycnt)
VALUES ('게시물 001 제목', '게시물 001 본문', 'admin90', '2022-12-01 00:00:00', '2022-12-01 00:00:00', 0);

INSERT INTO tbl_board (title, content, writer, regdate, updatedate, replycnt)
VALUES ('게시물 002 제목', '게시물 002 본문 - 댓글 달지 마세요!', 'admin90', '2022-12-01 00:00:00', '2022-12-01 00:00:00', 0);

INSERT INTO tbl_board (title, content, writer, regdate, updatedate, replycnt)
VALUES ('게시물 003 제목', '게시물 003 본문', 'admin90', '2022-12-01 00:00:00', '2022-12-01 00:00:00', 0);
INSERT INTO tbl_board (title, content, writer, regdate, updatedate, replycnt)
VALUES ('게시물 004 제목', '게시물 004 본문', 'admin90', '2022-12-01 00:10:00', '2022-12-01 00:10:00', 0);
INSERT INTO tbl_board (title, content, writer, regdate, updatedate, replycnt)
VALUES ('게시물 005 제목', '게시물 005 본문', 'admin90', '2022-12-01 00:20:00', '2022-12-01 00:20:00', 0);
INSERT INTO tbl_board (title, content, writer, regdate, updatedate, replycnt)
VALUES ('게시물 006 제목', '게시물 006 본문', 'admin90', '2022-12-01 00:30:00', '2022-12-01 00:30:00', 0);
INSERT INTO tbl_board (title, content, writer, regdate, updatedate, replycnt)
VALUES ('게시물 007 제목', '게시물 007 본문', 'admin90', '2022-12-01 00:40:00', '2022-12-01 00:40:00', 0);
INSERT INTO tbl_board (title, content, writer, regdate, updatedate, replycnt)
VALUES ('게시물 008 제목', '게시물 008 본문', 'admin90', '2022-12-01 00:50:00', '2022-12-01 00:50:00', 0);
INSERT INTO tbl_board (title, content, writer, regdate, updatedate, replycnt)
VALUES ('게시물 009 제목', '게시물 009 본문', 'admin90', '2022-12-01 01:00:00', '2022-12-01 01:00:00', 0);
INSERT INTO tbl_board (title, content, writer, regdate, updatedate, replycnt)
VALUES ('게시물 010 제목', '게시물 010 본문', 'admin90', '2022-12-01 02:00:00', '2022-12-01 02:00:00', 0);
INSERT INTO tbl_board (title, content, writer, regdate, updatedate, replycnt)
VALUES ('게시물 011 제목', '게시물 011 본문', 'admin90', '2022-12-01 03:00:00', '2022-12-01 03:00:00', 0);
INSERT INTO tbl_board (title, content, writer, regdate, updatedate, replycnt)
VALUES ('게시물 012 제목', '게시물 012 본문', 'admin90', '2022-12-01 04:00:00', '2022-12-01 04:00:00', 0);
INSERT INTO tbl_board (title, content, writer, regdate, updatedate, replycnt)
VALUES ('게시물 013 제목', '게시물 013 본문', 'admin90', '2022-12-01 05:00:00', '2022-12-01 05:00:00', 0);


-- 댓글 등록
INSERT INTO tbl_reply (bno, reply, replyer)
VALUES (1, '댓글 본문 01', 'admin90');
INSERT INTO tbl_reply (bno, reply, replyer)
VALUES (1, '댓글 본문 02', '댓글 작성자 02');
INSERT INTO tbl_reply (bno, reply, replyer)
VALUES (1, '댓글 본문 03', '댓글 작성자 03');
INSERT INTO tbl_reply (bno, reply, replyer)
VALUES (1, '댓글 본문 04', '댓글 작성자 04');
INSERT INTO tbl_reply (bno, reply, replyer)
VALUES (1, '댓글 본문 05', '댓글 작성자 05');
INSERT INTO tbl_reply (bno, reply, replyer)
VALUES (1, '댓글 본문 06', '댓글 작성자 06');
INSERT INTO tbl_reply (bno, reply, replyer)
VALUES (1, '댓글 본문 07', '댓글 작성자 07');
INSERT INTO tbl_reply (bno, reply, replyer)
VALUES (1, '댓글 본문 08', '댓글 작성자 08');
INSERT INTO tbl_reply (bno, reply, replyer)
VALUES (1, '댓글 본문 09', '댓글 작성자 09');
INSERT INTO tbl_reply (bno, reply, replyer)
VALUES (1, '댓글 본문 10', '댓글 작성자 010');
INSERT INTO tbl_reply (bno, reply, replyer)
VALUES (1, '댓글 본문 11', '댓글 작성자 011');
INSERT INTO tbl_reply (bno, reply, replyer)
VALUES (1, '댓글 본문 12', '댓글 작성자 012');


-- 첨부파일 등록
INSERT INTO tbl_attach (uuid, uploadpath, filename, filetype, bno)
VALUES ('5c96644a-fbd3-457c-ab1f-50061153d375', '/2022/12/01', '이미지_파일.jpg', 'I', 1);
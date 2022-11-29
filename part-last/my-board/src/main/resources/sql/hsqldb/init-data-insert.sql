INSERT INTO tbl_member (userid, userpw, username, enabled)
VALUES ('user00', '$2a$10$CdNsrwk7cDs7eBNuW7cp8unvLAA8NKXSqA3UfRgysCjvtZyqCLsWm', '일반사용자00', 'Y');
INSERT INTO tbl_member (userid, userpw, username, enabled)
VALUES ('user01', '$2a$10$7OrtlXQoaVQjnQEMtK12kuxcqA9g58ZfNZJ9nNElnxuBT4HkPDKNO', '일반사용자01', 'Y');
INSERT INTO tbl_member (userid, userpw, username, enabled)
VALUES ('user02', '$2a$10$szmbgkK4OalTS9t5409tXelO2Xpsrc8fXRudXFZw6f0p2kLOCZpBe', '일반사용자02', 'Y');

INSERT INTO tbl_member (userid, userpw, username, enabled)
VALUES ('manager80', '$2a$10$3crhdaHx07QIcj.kZ6rwmOPTwI0mGBk7lh9ubFCh2fy4mwwmKGuGW', '운영자80', 'Y');
INSERT INTO tbl_member (userid, userpw, username, enabled)
VALUES ('manager81', '$2a$10$a0D7O6DzXUlc4UGXrqyGJeVZvXY8RvUWSP4bEflLWtx1c4VOONQBC', '운영자81', 'Y');
INSERT INTO tbl_member (userid, userpw, username, enabled)
VALUES ('manager82', '$2a$10$J0N5lEnYviHq2RZTlbssIuaC/WMdSZ.KZ0Az.CkzDlvqXbj5.C5Kq', '운영자82', 'Y');

INSERT INTO tbl_member (userid, userpw, username, enabled)
VALUES ('admin90', '$2a$10$dcB0GXMEhQwW7E0i3yyR/OGdW1N/QQfioIf/OGN60qAf.Gmjt3fNy', '관리자90', 'Y');
INSERT INTO tbl_member (userid, userpw, username, enabled)
VALUES ('admin91', '$2a$10$kd0PNukIe.v15HghkXUJIO31BB7ZulAPdMi00LH2O//HuAJAFElMC', '관리자91', 'Y');
INSERT INTO tbl_member (userid, userpw, username, enabled)
VALUES ('admin92', '$2a$10$JqLbWg6o6PfnLr.yObk4v.LZpFHp0/R7cXc0V8gW2AjaXnkImi7WC', '관리자92', 'Y');

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

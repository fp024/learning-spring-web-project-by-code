/*
 엔티티 클래스의 metadata 로 해도 되긴하는데,
 테이블 컬럼 순서 정의와 스터디 프로젝트에서는 하이버네이트 동작을 보는것이 좋을 것 같아서
 오직 스크립트로만 동작하게 했다.
 */

DROP TABLE IF EXISTS tbl_attach CASCADE;
DROP TABLE IF EXISTS tbl_reply CASCADE;
DROP TABLE IF EXISTS tbl_board CASCADE;

DROP TABLE IF EXISTS tbl_member_auth CASCADE;
DROP TABLE IF EXISTS tbl_member CASCADE;

DROP TABLE IF EXISTS persistent_logins CASCADE;



package org.fp024;

import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.AuthVO;
import org.fp024.domain.MemberVO;
import org.fp024.helper.CustomBeanPropertySqlParameterSource;
import org.fp024.type.EnabledType;
import org.fp024.type.MemberAuthType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.stream.IntStream;

/** 회원 등록 테스트 */
@Slf4j
@SpringJUnitConfig(
    locations = {
      "file:src/main/webapp/WEB-INF/spring/root-context.xml",
      "file:src/main/webapp/WEB-INF/spring/security-context.xml"
    })
class MemberVOTest {
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private NamedParameterJdbcTemplate jdbcTemplate;

  /** 회원 입력 */
  @Disabled("회원 유저 테스트 할때 풀고 실행하자! 기본은 비활성화해두고...")
  @Test
  void testInsertMember() {
    String sql =
        "INSERT INTO TBL_MEMBER(USERID, USERPW, USERNAME, ENABLED) "
            + "VALUES (:userId, :userPassword, :userName, :enabled)";

    IntStream.range(0, 100)
        .forEach(
            i -> {
              MemberVO memberVO = new MemberVO();

              memberVO.setUserPassword(passwordEncoder.encode(String.format("pw%02d", i)));
              memberVO.setEnabled(EnabledType.YES);

              if (i < 80) {
                memberVO.setUserId(String.format("user%02d", i));
                memberVO.setUserName(String.format("일반사용자%02d", i));
              } else if (i < 90) {
                memberVO.setUserId(String.format("manager%02d", i));
                memberVO.setUserName(String.format("운영자%02d", i));
              } else {
                memberVO.setUserId(String.format("admin%02d", i));
                memberVO.setUserName(String.format("관리자%02d", i));
              }
              jdbcTemplate.update(sql, new CustomBeanPropertySqlParameterSource(memberVO));
            });
  }

  /** 권한 입력 */
  @Disabled("회원 유저 테스트 할때 풀고 실행하자! 기본은 비활성화해두고...")
  @Test
  void testInsertAuth() {
    String sql = "INSERT INTO TBL_MEMBER_AUTH(USERID, AUTH) VALUES (:userId, :auth)";

    IntStream.range(0, 100)
        .forEach(
            i -> {
              AuthVO authVO = new AuthVO();

              if (i < 80) {
                authVO.setUserId(String.format("user%02d", i));
                authVO.setAuth(MemberAuthType.ROLE_USER);
              } else if (i < 90) {
                authVO.setUserId(String.format("manager%02d", i));
                authVO.setAuth(MemberAuthType.ROLE_MEMBER);
              } else {
                authVO.setUserId(String.format("admin%02d", i));
                authVO.setAuth(MemberAuthType.ROLE_ADMIN);
              }
              jdbcTemplate.update(sql, new CustomBeanPropertySqlParameterSource(authVO));
            });
  }
}

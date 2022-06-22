package org.fp024;

import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.Member;
import org.fp024.domain.MemberAuth;
import org.fp024.domain.MemberAuthType;
import org.fp024.helper.CustomBeanPropertySqlParameterSource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/** 회원 등록 테스트 */
@Slf4j
@SpringJUnitConfig(
    locations = {
      "file:src/main/webapp/WEB-INF/spring/root-context.xml",
      "file:src/main/webapp/WEB-INF/spring/security-context.xml"
    })
class MemberTest {
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
              Member member = new Member();

              member.setUserPassword(passwordEncoder.encode(String.format("pw%02d", i)));
              member.setEnabled("Y");

              if (i < 80) {
                member.setUserId(String.format("user%02d", i));
                member.setUserName(String.format("일반사용자%02d", i));
              } else if (i < 90) {
                member.setUserId(String.format("manager%02d", i));
                member.setUserName(String.format("운영자%02d", i));
              } else {
                member.setUserId(String.format("admin%02d", i));
                member.setUserName(String.format("관리자%02d", i));
              }
              jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(member));
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
              MemberAuth memberAuth = new MemberAuth();

              if (i < 80) {
                memberAuth.setUserId(String.format("user%02d", i));
                memberAuth.setAuth(MemberAuthType.ROLE_USER);
              } else if (i < 90) {
                memberAuth.setUserId(String.format("manager%02d", i));
                memberAuth.setAuth(MemberAuthType.ROLE_MEMBER);
              } else {
                memberAuth.setUserId(String.format("admin%02d", i));
                memberAuth.setAuth(MemberAuthType.ROLE_ADMIN);
              }
              jdbcTemplate.update(sql, new CustomBeanPropertySqlParameterSource(memberAuth));
            });
  }
}

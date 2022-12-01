package org.fp024.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.fp024.config.RootConfig;
import org.fp024.domain.MemberVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {RootConfig.class})
@Slf4j
class MemberRepositoryTest {
  @Autowired private MemberRepository repository;

  /* // 회원 - 권한 엔티티는 연관설정을 하고 fetch방식을 EAGER로 설정해서 조인으로 한번에 가져온다.
  Hibernate:
    select
        m1_0.userid,
        a1_0.userid,
        a1_0.auth,
        m1_0.enabled,
        m1_0.regdate,
        m1_0.updatedate,
        m1_0.username,
        m1_0.userpw
    from
        tbl_member m1_0
    left join
        tbl_member_auth a1_0
            on m1_0.userid=a1_0.userid
    where
        m1_0.userid=?

  binding parameter [1] as [VARCHAR] - [admin90]
   */
  @DisplayName("ID로 회원 조회")
  @Test
  void testFindById() {
    Optional<MemberVO> memberVOOptional = repository.findById("admin90");
    assertThat(memberVOOptional.isPresent()).isTrue();

    assertThat(memberVOOptional.get()) //
        .hasFieldOrPropertyWithValue("userId", "admin90")
        .hasFieldOrPropertyWithValue("userName", "관리자90");
  }
}

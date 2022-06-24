package org.fp024.service;

import lombok.RequiredArgsConstructor;
import org.fp024.domain.MemberVO;
import org.fp024.mapper.AuthVODynamicSqlSupport;
import org.fp024.mapper.MemberMapper;
import org.fp024.mapper.MemberVODynamicSqlSupport;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.mybatis.dynamic.sql.SqlBuilder.equalTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.on;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

/** 회원 서비스 */
@RequiredArgsConstructor
@Service
public class MemberService {
  private final MemberMapper memberMapper;

  public Optional<MemberVO> read(String userId) {
    return memberMapper.selectOne(
        select(
                MemberVODynamicSqlSupport.userId,
                MemberVODynamicSqlSupport.userPassword,
                MemberVODynamicSqlSupport.userName,
                MemberVODynamicSqlSupport.enabled,
                MemberVODynamicSqlSupport.registerDate,
                MemberVODynamicSqlSupport.updateDate,
                AuthVODynamicSqlSupport.auth)
            .from(MemberVODynamicSqlSupport.memberVO, "m")
            .leftJoin(
                AuthVODynamicSqlSupport.authVO,
                "a",
                on(MemberVODynamicSqlSupport.userId, equalTo(AuthVODynamicSqlSupport.userId)))
            .where(MemberVODynamicSqlSupport.userId, isEqualTo(userId))
            .build()
            .render(RenderingStrategies.MYBATIS3));
  }
}

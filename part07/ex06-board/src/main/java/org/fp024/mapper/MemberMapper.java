package org.fp024.mapper;

import org.fp024.domain.MemberVO;

/** 회원 매퍼 인터페이스 */
public interface MemberMapper {
  MemberVO read(String userId);
}

package org.fp024.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.fp024.domain.MemberVO;
import org.fp024.repository.MemberRepository;
import org.springframework.stereotype.Service;

/** 회원 서비스 */
@RequiredArgsConstructor
@Service
public class MemberService {
  private final MemberRepository repository;

  public Optional<MemberVO> read(String userId) {
    return repository.findById(userId);
  }
}

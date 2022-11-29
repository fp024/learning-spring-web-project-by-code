package org.fp024.repository;

import org.fp024.domain.MemberVO;
import org.springframework.data.repository.CrudRepository;

/**
 * 회원 리포지토리
 *
 * <p>spring data jpa 인터페이스를 상속할 때, 확실히 사용중인 것만 상속 받는 것이 좋을 것 같다.<br>
 * 회원 리포지토리는 현 시점으로는 findById()만 쓰고 있다.
 */
public interface MemberRepository extends CrudRepository<MemberVO, String> {}

package org.fp024.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * AuthVO의 내부 클래스로 뒀었는데...
 * 내부 클래스로두면 QueryDSL 생성시 오류난다. 그래서 밖으로 분리함.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class AuthId implements Serializable {

  @Column(length = 50, name = "userid")
  private String userId;

  @Column(length = 50, name = "auth")
  @Enumerated(EnumType.STRING)
  private MemberAuthType auth;
}

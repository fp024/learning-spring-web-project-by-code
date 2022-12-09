package org.fp024.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serial;
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
  @Serial private static final long serialVersionUID = -7015651083348796420L;

  @Column(length = 50, name = "userid")
  private String userId;

  @Column(length = 50, name = "auth")
  @Enumerated(EnumType.STRING)
  private MemberAuthType auth;
}

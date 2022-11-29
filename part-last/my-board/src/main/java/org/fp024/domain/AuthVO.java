package org.fp024.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/** 권한 VO */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@Table(name = "tbl_member_auth")
public class AuthVO {
  @EmbeddedId private AuthId authId;

  @JoinColumn(name = "userid", insertable = false, updatable = false)
  @ManyToOne
  private MemberVO memberVO;

  public String getUserId() {
    return authId.getUserId();
  }

  public MemberAuthType getAuth() {
    return authId.getAuth();
  }

}

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
class AuthId implements Serializable {

  @Column(length = 50, name = "userid")
  private String userId;

  @Column(length = 50, name = "auth")
  @Enumerated(EnumType.STRING)
  private MemberAuthType auth;
}

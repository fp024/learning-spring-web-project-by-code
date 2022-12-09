package org.fp024.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
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

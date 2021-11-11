package org.fp024.domain;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("criteria")
public class Criteria {
  public static final int PAGE_SIZE = 10;
  private int pageNum;
  private int amount;

  public Criteria() {
    this(1, PAGE_SIZE);
  }

  public Criteria(int pageNum) {
    this(pageNum, PAGE_SIZE);
  }

  public Criteria(int pageNum, int amount) {
    this.pageNum = pageNum;
    this.amount = amount;
  }
}

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
  private long pageNum;
  private long amount;

  public Criteria() {
    this(1, PAGE_SIZE);
  }

  public Criteria(long pageNum) {
    this(pageNum, PAGE_SIZE);
  }

  public Criteria(long pageNum, long amount) {
    this.pageNum = pageNum;
    this.amount = amount;
  }
}

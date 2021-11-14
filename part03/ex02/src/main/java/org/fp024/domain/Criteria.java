package org.fp024.domain;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Alias("criteria")
public class Criteria {
  private long pageNum;
  private final long amount;

  public Criteria() {
    this(1, PageDTO.PAGE_SIZE);
  }

  public Criteria(long pageNum) {
    this(pageNum, PageDTO.PAGE_SIZE);
  }

  public Criteria(long pageNum, long amount) {
    this.pageNum = pageNum;
    this.amount = amount;
  }

  public void setPageNum(long pageNum) {
    if (pageNum < 1) {
      pageNum = 1;
    }
    this.pageNum = pageNum;
  }
}

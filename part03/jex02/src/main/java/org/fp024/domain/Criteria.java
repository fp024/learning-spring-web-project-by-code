package org.fp024.domain;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Alias("criteria")
public class Criteria {
  @Getter private long pageNum;
  private PageSize pageSize;

  public Criteria() {
    this(1, PageSize.SIZE_10);
  }

  public Criteria(long pageNum) {
    this(pageNum, PageSize.SIZE_10);
  }

  public Criteria(long pageNum, PageSize pageSize) {
    this.pageNum = pageNum;
    this.pageSize = pageSize;
  }

  public void setPageNum(long pageNum) {
    if (pageNum < 1) {
      pageNum = 1;
    }
    this.pageNum = pageNum;
  }

  public void setAmount(int amount) {
    this.pageSize = PageSize.valueOfAmount(amount);
  }

  public int getAmount() {
    return pageSize.getSize();
  }
}

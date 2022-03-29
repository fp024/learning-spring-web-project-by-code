package org.fp024.mapper;

import org.apache.ibatis.annotations.Insert;

public interface Sample1Mapper {
  @Insert("INSERT into tbl_sample1 (col1) VALUES (#{data})")
  int insertCol1(String data);
}

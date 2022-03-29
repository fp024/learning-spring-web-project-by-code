package org.fp024.mapper;

import org.apache.ibatis.annotations.Insert;

public interface Sample2Mapper {
  @Insert("INSERT into tbl_sample2 (col2) VALUES (#{data})")
  int insertCol2(String data);
}

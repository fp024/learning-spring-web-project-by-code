package org.fp024.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
	@Select("SELECT sysdate FROM dual")
	String getTime();
	
	String getTime2();
}

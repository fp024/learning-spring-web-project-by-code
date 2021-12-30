package org.fp024.mapper;

import org.apache.ibatis.annotations.Param;
import org.fp024.domain.Criteria;
import org.fp024.domain.ReplyVO;

import java.util.List;

public interface ReplyMapper {

  void insert(ReplyVO vo);

  ReplyVO read(Long bno);

  int delete(Long rno);

  int update(ReplyVO reply);

  List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno);
}

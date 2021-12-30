package org.fp024.mapper;

import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.BoardVO;
import org.fp024.domain.Criteria;
import org.fp024.domain.ReplyVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringJUnitConfig(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Slf4j
public class ReplyMapperTest {

  @Autowired private ReplyMapper mapper;

  @Autowired private BoardMapper boardMapper;

  @Test
  void testMapper() {
    LOGGER.info("Reply mapper: {}", mapper);
  }

  List<Long> latestBoardNoList() {
    Criteria criteria = new Criteria();
    criteria.setAmount(5);
    criteria.setPageNum(1L);
    List<BoardVO> boardList = boardMapper.getListWithPaging(criteria);
    return boardList.stream().map(BoardVO::getBno).collect(Collectors.toList());
  }

  @Test
  void testCreate() {
    List<Long> latestBnoList = latestBoardNoList();
    LOGGER.info("latestBnoList: {}", latestBnoList);

    IntStream.rangeClosed(1, 10)
        .forEach(
            i -> {
              ReplyVO vo = new ReplyVO();

              // 게시물의 번호
              vo.setBno(latestBnoList.get(i % latestBnoList.size()));
              vo.setReply("댓글 테스트 " + i);
              vo.setReplyer("replayer" + i);

              mapper.insert(vo);
            });
  }

  @Test
  void testRead() {
    long targetRno = 5L;
    ReplyVO vo = mapper.read(targetRno);
    LOGGER.info("{}", vo);
  }

  @Test
  void testDelete() {
    long targetRno = 1L;
    mapper.delete(targetRno);
  }

  @Test
  void testUpdate() {
    long targetRno = 10L;

    ReplyVO vo = mapper.read(targetRno);

    vo.setReply("Update Reply ");

    int count = mapper.update(vo);

    LOGGER.info("UPDATE COUNT: {}", count);
  }

  @Test
  void testList() {
    Criteria cri = new Criteria();

    List<ReplyVO> replies = mapper.getListWithPaging(cri, 10000501L);

    replies.forEach(reply -> LOGGER.info("reply: {}", reply));
  }
}

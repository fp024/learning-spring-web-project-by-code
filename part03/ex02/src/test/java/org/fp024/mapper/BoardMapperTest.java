package org.fp024.mapper;

import org.fp024.domain.BoardVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Slf4j
class BoardMapperTest {
	@Autowired
	private BoardMapper mapper;

	@Test
	void testGetList() {
		mapper.getList().forEach(board -> LOGGER.info(board.toString()));
	}

	@Test
	void testInsert() {
		BoardVO board = new BoardVO();
		board.setTitle("새로 작성하는 글");
		board.setContent("새로 작성하는 내용");
		board.setWriter("newbie");

		mapper.insert(board);

		LOGGER.info(board.toString());
	}

	@Test
	void testInsertSelectKey() {
		BoardVO board = new BoardVO();
		board.setTitle("새로 작성하는 글 select key");
		board.setContent("새로 작성하는 내용 select key");
		board.setWriter("newbie");

		mapper.insertSelectKey(board);

		LOGGER.info(board.toString());
	}

	@Test
	void testRead() {
		// 존재하는 게시물 번호로 테스트
		BoardVO board = mapper.read(5L);

		LOGGER.info(board.toString());
	}

	@Test
	void testDelete() {
		LOGGER.info("DELETE COUNT: {}", mapper.delete(3L));
	}

	@Test
	void testUpdate() {
		BoardVO board = new BoardVO();

		board.setBno(5L);
		board.setTitle("수정된 제목");
		board.setContent("수정된 내용");
		board.setWriter("user00");

		int count = mapper.update(board);
		LOGGER.info("UPDATE COUNT: {}", count);
	}

}

package org.fp024.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.fp024.config.RootConfig;
import org.fp024.domain.BoardVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

/**
 * mybatis-dynamic-sql 으로 만들어진 mapper를 쓸 때, QueryDSL 같이 사용한 부분은
 * 별도 클래스로 분리해서 그것이 mapper클래스를 사용하게 해야할 것 같다.
 * 
 * 참조:
 * 
 * WHERE 절 지원
 *   https://mybatis.org/mybatis-dynamic-sql/docs/whereClauses.html
 * 
 * SELECT 문장
 *   https://mybatis.org/mybatis-dynamic-sql/docs/select.html
 * 
 * @author fp024
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RootConfig.class })
@Slf4j
class BoardMapperTest {

	@Autowired
	private BoardMapper mapper;

	@Test
	void testGetList() {		
		List<BoardVO> boardList = mapper.selectMany(
				SqlBuilder.select(BoardMapper.selectList)
					          .from(BoardVODynamicSqlSupport.boardVO)
					          .where(BoardVODynamicSqlSupport.bno, SqlBuilder.isGreaterThan(0L))
					          .build()
					          .render(RenderingStrategies.MYBATIS3)
				);
		
		boardList.forEach(board -> LOGGER.debug(board.toString()));
	}

	/**
	 * 시퀀스를 generatorConfig.xml에서 정해주면 insert() 함수에 아래 어노테이션을 붙여줘서 
	 * 실행하게되는데, 일부러 해당 값을 반환받지 않게 하기도 애매하고, 굳이 안받는 메서드를 
	 * 따로 만들 필요는 없을 것 같다., 필요시 호출 후 bno를 null로 바꿔주면 될 것 같다.
	 *  
	 * @SelectKey(statement = "SELECT seq_board.nextval FROM DUAL"
	 *          , keyProperty = "record.bno", before = true, resultType = Long.class)
	 */
	@Test
	void testInsertSelectKey() {
		BoardVO board = new BoardVO();
		board.setTitle("새로 작성하는 글 select key");
		board.setContent("새로 작성하는 내용 select key");
		board.setWriter("newbie");
		
		LocalDateTime now = LocalDateTime.now();
		board.setRegdate(now);
		board.setUpdateDate(now);
		
		mapper.insert(board);				
		LOGGER.info(board.toString());
	}
	
	/**
	 * selectByPrimaryKey 가 Optional<T> 로 반환해준다.
	 */
	@Test
	void testRead() {
		// 존재하는 게시물 번호로 테스트
		Optional<BoardVO> board = mapper.selectByPrimaryKey(5L);
		LOGGER.info(board.get().toString());
	}
	
	@Test
	void testDelete() {
		LOGGER.info("DELETE COUNT: {}", mapper.deleteByPrimaryKey(3L));
	}

	@Test
	void testUpdate() {
		BoardVO board = new BoardVO();

		board.setBno(28L);
		board.setTitle("수정된 제목 - jex02");
		board.setContent("수정된 내용 - jex02");
		board.setWriter("user00 - jex02");
		board.setUpdateDate(LocalDateTime.now());
		
		// Selective로 끝나는 메서드를 사용하면, null로 설정된 값은 업데이트하지 않는다.
		int count = mapper.updateByPrimaryKeySelective(board);
		LOGGER.info("UPDATE COUNT: {}", count);
	}

	@Test
	void testUpdateWithDSL() {
		BoardVO board = new BoardVO();

		board.setBno(28L);
		board.setTitle("수정된 제목 - jex02 1");
		board.setContent("수정된 내용 - jex02 1");
		board.setWriter("user00 - jex02 1");
		board.setUpdateDate(LocalDateTime.now());

		UpdateStatementProvider updateStatement = SqlBuilder.update(BoardVODynamicSqlSupport.boardVO)
	            .set(BoardVODynamicSqlSupport.title).equalTo(board.getTitle())
	            .set(BoardVODynamicSqlSupport.content).equalTo(board.getContent())
	            .set(BoardVODynamicSqlSupport.writer).equalTo(board.getWriter())
	            .set(BoardVODynamicSqlSupport.updateDate).equalTo(board.getUpdateDate())
	            .where(BoardVODynamicSqlSupport.bno, SqlBuilder.isEqualTo(board.getBno()))	            
	            .build()
	            .render(RenderingStrategies.MYBATIS3);
		
		// Selective로 끝나는 메서드를 사용하면, null로 설정된 값은 업데이트하지 않는다.
		int count = mapper.update(updateStatement);
		LOGGER.info("UPDATE COUNT: {}", count);
	}
}

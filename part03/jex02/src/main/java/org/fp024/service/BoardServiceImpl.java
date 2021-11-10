package org.fp024.service;

import java.time.LocalDateTime;
import java.util.List;

import org.fp024.domain.BoardVO;
import org.fp024.mapper.BoardMapper;
import org.fp024.mapper.BoardVODynamicSqlSupport;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 쿼리 DSL 작성 참조 링크
 *
 * <p>SELECT https://mybatis.org/mybatis-dynamic-sql/docs/select.html UPDATE
 *
 * <p>Update: https://mybatis.org/mybatis-dynamic-sql/docs/update.html
 *
 * <p>INSERT: https://mybatis.org/mybatis-dynamic-sql/docs/insert.html
 *
 * <p>DELETE: https://mybatis.org/mybatis-dynamic-sql/docs/delete.html
 *
 * <p>예제:https://github.com/mybatis/mybatis-dynamic-sql/tree/master/src/test/java/examples
 *
 * @author fp024
 */
@Slf4j
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {
  // Spring 4.3이상에서 자동처리 (단일 파라미터 생성자에 대해서는 자동 주입)
  // 모든 인자에 대한 생성자를 자동으로 만들도록 lombok에서 정의했음.
  private BoardMapper mapper;

  @Override
  public void register(BoardVO board) {
    LOGGER.info("register..... {}", board);
    // insertSelectKey와 insert를 따로 분리해서 만들지 않았다, 항상 Key 프로퍼티를 설정해서 반환한다.
    // 바로 모델을 insert를 하면 board 모델의 등록/수정일시가 null일 경우 null로 업데이트를 할 수 있으므로 명시적으로 정해준다.
    // 테이블 생성시 SYSDATE 기본값이 동작하도록 명시적으로 지정해줄 필요가 있었다.
    //
    // bno도 시퀀스로 얻은 값이 insert에 포함되도록 지정해줘야한다.
    /*
    mapper.insert(
        SqlBuilder.insert(board)
            .into(BoardVODynamicSqlSupport.boardVO)
            .map(BoardVODynamicSqlSupport.bno)
            .toProperty(BoardVODynamicSqlSupport.bno.name())
            .map(BoardVODynamicSqlSupport.title)
            .toProperty(BoardVODynamicSqlSupport.title.name())
            .map(BoardVODynamicSqlSupport.content)
            .toProperty(BoardVODynamicSqlSupport.content.name())
            .map(BoardVODynamicSqlSupport.writer)
            .toProperty(BoardVODynamicSqlSupport.writer.name())
            .build()
            .render(RenderingStrategies.MYBATIS3));
    */
    // 위의 코드보다 INSERT 직전 입력하지 않을 값을 명확하게하고 선택적 INSERT하는게 더 관리에 나을 수 있어보인다.
    board.setRegdate(null);
    board.setUpdateDate(null);
    mapper.insertSelective(board);
  }

  @Override
  public BoardVO get(Long bno) {
    LOGGER.info("get..... {}", bno);
    return mapper.selectByPrimaryKey(bno).orElse(null);
  }

  @Override
  public boolean modify(BoardVO board) {
    LOGGER.info("modify..... {}", board);

    return mapper.update(
            c ->
                c.set(BoardVODynamicSqlSupport.title)
                    .equalTo(board.getTitle())
                    .set(BoardVODynamicSqlSupport.content)
                    .equalTo(board.getContent())
                    .set(BoardVODynamicSqlSupport.updateDate)
                    .equalTo(LocalDateTime.now())
                    .where(BoardVODynamicSqlSupport.bno, SqlBuilder.isEqualTo(board.getBno())))
        == 1;
  }

  @Override
  public boolean remove(Long bno) {
    LOGGER.info("remove..... {}", bno);
    return mapper.deleteByPrimaryKey(bno) == 1;
  }

  @Override
  public List<BoardVO> getList() {
    LOGGER.info("getList..........");
    return mapper.select(
        c ->
            c.applyWhere(d -> d.where(BoardVODynamicSqlSupport.bno, SqlBuilder.isGreaterThan(0L)))
                .orderBy(BoardVODynamicSqlSupport.bno.descending()));
  }
}
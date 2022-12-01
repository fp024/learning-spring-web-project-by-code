package org.fp024.domain;

import static org.fp024.domain.QBoardVO.boardVO;

import com.querydsl.core.types.dsl.StringPath;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import lombok.Getter;

/** 검색 타입 */
public enum SearchType {
  TITLE("T", boardVO.title, "제목"),
  CONTENT("C", boardVO.content, "내용"),
  WRITER("W", boardVO.writer, "작성자");

  @Getter private final String code;

  @Getter private final StringPath path;
  @Getter private final String description;

  private static final SearchType DEFAULT_SEARCH_TYPE = TITLE;

  SearchType(String code, StringPath path, String description) {
    this.code = code;
    this.path = path;
    this.description = description;
  }

  public static Set<SearchType> allSearchTypeSet() {
    return EnumSet.allOf(SearchType.class);
  }

  public static SearchType searchTypeOf(String code) {
    return Arrays.stream(SearchType.values())
        .filter(s -> s.getCode().equals(code))
        .findAny()
        .orElse(DEFAULT_SEARCH_TYPE);
  }

  public static Set<SearchType> searchTypeSetOf(String... codes) {
    EnumSet<SearchType> searchTypeSet = EnumSet.noneOf(SearchType.class);
    Arrays.stream(codes).forEach(code -> searchTypeSet.add(searchTypeOf(code)));
    return searchTypeSet;
  }

  public String getName() {
    return name();
  }
}

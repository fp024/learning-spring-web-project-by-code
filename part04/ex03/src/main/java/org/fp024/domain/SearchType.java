package org.fp024.domain;

import java.util.EnumSet;
import java.util.Set;

import lombok.Getter;

/** 검색 타입 */
public enum SearchType {
  TITLE("T", "제목"),
  CONTENT("C", "내용"),
  WRITER("W", "작성자");

  @Getter private final String code;
  @Getter private final String description;

  private static final SearchType DEFAULT_SEARCH_TYPE = TITLE;

  SearchType(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static Set<SearchType> allSearchTypeSet() {
    return EnumSet.allOf(SearchType.class);
  }

  public static SearchType searchTypeOf(String code) {
    for (SearchType searchType : SearchType.values()) {
      if (searchType.getCode().equals(code)) {
        return searchType;
      }
    }
    return DEFAULT_SEARCH_TYPE;
  }

  public static Set<SearchType> searchTypeSetOf(String... codes) {
    EnumSet<SearchType> searchTypeSet = EnumSet.noneOf(SearchType.class);
    for (String code : codes) {
      searchTypeSet.add(searchTypeOf(code));
    }
    return searchTypeSet;
  }

  public String getName() {
    return name();
  }
}

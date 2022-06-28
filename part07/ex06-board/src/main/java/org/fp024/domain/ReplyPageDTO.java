package org.fp024.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@Getter
@ToString
public class ReplyPageDTO {
  /** 댓글 페이지 네비게이터에 표시할 페이지 인덱스 수 */
  public static final int PAGE_NAVIGATION_SIZE = 3;

  private final int pageSize;
  private final int replyCount;
  private final List<ReplyVO> list;

  public int getPageNavigationSize() {
    return PAGE_NAVIGATION_SIZE;
  }
}

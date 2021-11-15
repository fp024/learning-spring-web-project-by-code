package org.fp024.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.fp024.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;

import lombok.extern.slf4j.Slf4j;

/**
 * https://www.baeldung.com/spring-5-junit-config<br>
 * @SpringJUnitWebConfig 에 <br>
 * @ExtendWith(SpringExtension.class), @WebAppConfiguration, @ContextConfiguration 을 포함한다.
 */
@SpringJUnitWebConfig(
    locations = {
      "file:src/main/webapp/WEB-INF/spring/root-context.xml",
      "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
    })
@Slf4j
class BoardControllerTest {
  // @Autowired private WebApplicationContext ctx;

  @Autowired private BoardService service;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    // this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    this.mockMvc = MockMvcBuilders.standaloneSetup(new BoardController(service)).build();
  }

  @Test
  void testList() throws Exception {
    ModelMap result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/board/list")
                    .param("pageNum", "1")
                    .param("amount", "10")) // 페이지 사이즈를 전달하게 되면, 범위를 몇가지로 고정해야할 것 같다. => PageSize enum을 적용했다.
            .andReturn()
            .getModelAndView()
            .getModelMap();

    LOGGER.info("result: {}", result);
  }

  @Test
  void testRegister() throws Exception {
    String viewName =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/board/register")
                    .param("title", "테스트 새글 제목")
                    .param("content", "테스트 새글 내용")
                    .param("writer", "useer00"))
            .andReturn()
            .getModelAndView()
            .getViewName();

    LOGGER.info("viewName: {}", viewName);

    assertEquals("redirect:/board/list", viewName);
  }

  @Test
  void testGet() throws Exception {
    ModelMap getResult =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/board/get").param("bno", "2"))
            .andReturn()
            .getModelAndView()
            .getModelMap();

    LOGGER.info("modelMap: {}", getResult);
  }

  @Test
  void testModify() throws Exception {
    String resultPage =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/board/modify")
                    .param("bno", "1")
                    .param("title", "수정된 테스트 새글 제목")
                    .param("content", "수정된 테스트 새글 내용")
                    .param("writer", "user00"))
            .andReturn()
            .getModelAndView()
            .getViewName();

    LOGGER.info("resultPage: {}", resultPage);
    assertEquals("redirect:/board/list", resultPage);
  }

  @Test
  void testRemove() throws Exception {
    // 삭제전 데이터 베이스에서 게시물 번호 확인해보기
    String resultPage =
        mockMvc
            .perform(MockMvcRequestBuilders.post("/board/remove").param("bno", "25"))
            .andReturn()
            .getModelAndView()
            .getViewName();

    LOGGER.info("resultPage: {}", resultPage);
    assertEquals("redirect:/board/list", resultPage);
  }
}

package org.fp024.controller;

import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.ReplyVO;
import org.fp024.service.ReplyService;
import org.fp024.util.ObjectMapperHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(
    locations = {
      "file:src/main/webapp/WEB-INF/spring/root-context.xml",
      "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
    })
@Slf4j
class ReplyControllerTest {
  @Autowired private ReplyService service;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    this.mockMvc =
        MockMvcBuilders.standaloneSetup(new ReplyController(service))
            .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
            .build();
  }

  @Test
  void testCreate() throws Exception {
    ReplyVO reply = new ReplyVO();
    reply.setBno(10000501L);
    reply.setReply("Hello Reply");
    reply.setReplyer("user00");

    String jsonBody = ObjectMapperHelper.objectMapper().writeValueAsString(reply);

    String responseContent =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/replies/new")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    assertThat(responseContent, is("Success"));
  }

  @Test
  void testGetList() throws Exception {
    String responseContent =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/replies/pages/10000501/1"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    LOGGER.info("{}", responseContent);
  }

  @Test
  void testGet() throws Exception {
    String responseContent =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/replies/6.json"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    LOGGER.info("{}", responseContent);
  }

  /** 응답을 Success 평문으로 받으므로.. /replies/43.json으로 요청 시도할 경우 406 응답을 받게된다. */
  @Test
  void testRemove() throws Exception {
    String responseContent =
        mockMvc
            .perform(MockMvcRequestBuilders.delete("/replies/43"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    LOGGER.info("{}", responseContent);
  }

  @Test
  void testModify() throws Exception {
    ReplyVO reply = new ReplyVO();
    reply.setRno(2L);
    reply.setReply(LocalDateTime.now().toString());

    String jsonBody = ObjectMapperHelper.objectMapper().writeValueAsString(reply);

    String responseContent =
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/replies/2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    LOGGER.info("{}", responseContent);
  }
}

package org.fp024.controller;

import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.Ticket;
import org.fp024.util.ObjectMapperHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(
    locations = {
      "file:src/main/webapp/WEB-INF/spring/root-context.xml",
      "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
    })
@Slf4j
class SampleControllerTest {
  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(new SampleController()).build();
  }

  @Test
  void testConvert() throws Exception {
    Ticket ticket = new Ticket();
    ticket.setTno(123);
    ticket.setOwner("Admin");
    ticket.setGrade("AAA");

    String jsonString = ObjectMapperHelper.objectMapper().writeValueAsString(ticket);
    LOGGER.info("ticket jsonString: {}", jsonString);
    assertEquals("{\"tno\":123,\"owner\":\"Admin\",\"grade\":\"AAA\"}", jsonString);

    MvcResult result =
        mockMvc
            .perform(
                post("/sample/ticket.json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonString))
            .andExpect(status().is(200))
            .andExpect(jsonPath("$.tno", equalTo(123)))
            .andExpect(jsonPath("$.owner", equalTo("Admin")))
            .andExpect(jsonPath("$.grade", equalTo("AAA")))
            .andReturn();

    String content = result.getResponse().getContentAsString();
    LOGGER.info("response content: {}", content);
  }
}

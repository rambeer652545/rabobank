package com.rabobank.customer.statement.processor.controllers;

import com.rabobank.customer.statement.processor.models.CustomerStatementsVO;
import com.rabobank.customer.statement.processor.services.CustomerStatementProcessorService;
import com.rabobank.customer.statement.processor.utils.JunitCommonUtilities;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CustomerStatementProcessorControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private CustomerStatementProcessorService customerStatementProcessorService;

  final static String filePath = "src/test/resources/requests/";

  @Test
  public void testValidateAllStatement() throws Exception {
    CustomerStatementsVO customerStatementsVO =
        (CustomerStatementsVO) JunitCommonUtilities.asJsonToObject(filePath+"request.json",
            CustomerStatementsVO.class);
    MvcResult mvcResult = mockMvc.perform(
        MockMvcRequestBuilders.post("/customerstatements/").content(
            JunitCommonUtilities.asJsonString(customerStatementsVO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)).andReturn();
    Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
  }
}

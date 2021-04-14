package com.rabobank.customer.statement.processor.controllers;

import com.rabobank.customer.statement.processor.RabobankCustomerStatementProcessorApplication;
import com.rabobank.customer.statement.processor.exceptions.ErrorMessage;
import com.rabobank.customer.statement.processor.models.CustomerStatementsVO;
import com.rabobank.customer.statement.processor.utils.JunitCommonUtilities;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = RabobankCustomerStatementProcessorApplication.class)
@AutoConfigureMockMvc
public class CustomerStatementProcessorControllerITest {

  @Autowired
  private MockMvc mockMvc;

  final static String filePath = "src/test/resources/requests/";

  @Test
  public void testValidateAllStatementSuccessful() throws Exception {
    CustomerStatementsVO customerStatementsVO =
        (CustomerStatementsVO) JunitCommonUtilities.asJsonToObject(filePath+"request.json",
            CustomerStatementsVO.class);
    mockMvc.perform(
        MockMvcRequestBuilders.post("/customerstatements/").content(
            JunitCommonUtilities.asJsonString(customerStatementsVO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(200))
        .andExpect(MockMvcResultMatchers
            .jsonPath("$.result", Matchers.is(ErrorMessage.SUCCESSFUL)));
  }
  @Test
  public void testValidateAllStatementException() throws Exception {
    CustomerStatementsVO customerStatementsVO =
        (CustomerStatementsVO) JunitCommonUtilities.asJsonToObject(filePath+"request.json",
            CustomerStatementsVO.class);
    customerStatementsVO.getCustomerStatementVOS().add(null);
    mockMvc.perform(
        MockMvcRequestBuilders.post("/customerstatements/").content(
            JunitCommonUtilities.asJsonString(customerStatementsVO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(500))
        .andExpect(MockMvcResultMatchers
            .jsonPath("$.result", Matchers.is(ErrorMessage.INTERNAL_SERVER_ERROR)));
  }

  @Test
  public void testValidateAllStatementInvalid() throws Exception {
    MvcResult mvcResult = mockMvc.perform(
        MockMvcRequestBuilders.post("/customerstatements/").content(
            JunitCommonUtilities.asJsonString(null))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)).andReturn();
    System.out.println(mvcResult.getResponse().getContentAsString());
    Assertions.assertEquals(400, mvcResult.getResponse().getStatus());
  }

  @Test
  public void testValidateAllStatementDuplicate() throws Exception {
    CustomerStatementsVO customerStatementsVO =
        (CustomerStatementsVO) JunitCommonUtilities.asJsonToObject(filePath+"duplicateRequest.json",
            CustomerStatementsVO.class);
    mockMvc.perform(
        MockMvcRequestBuilders.post("/customerstatements/").content(
            JunitCommonUtilities.asJsonString(customerStatementsVO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(400))
        .andExpect(MockMvcResultMatchers
            .jsonPath("$.result", Matchers.is(ErrorMessage.DUPLICATE_REFERENCE)));
  }

  @Test
  public void testValidateAllStatementIncorrectEndBalance() throws Exception {
    CustomerStatementsVO customerStatementsVO =
        (CustomerStatementsVO) JunitCommonUtilities
            .asJsonToObject(filePath+"incorrectEndBalanceRequest.json",
                CustomerStatementsVO.class);
    mockMvc.perform(
        MockMvcRequestBuilders.post("/customerstatements/").content(
            JunitCommonUtilities.asJsonString(customerStatementsVO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(400))
        .andExpect(MockMvcResultMatchers
            .jsonPath("$.result", Matchers.is(ErrorMessage.INCORRECT_END_BALANCE)));
  }

  @Test
  public void testValidateAllStatementDuplicateIncorrectEndBalance() throws Exception {
    CustomerStatementsVO customerStatementsVO =
        (CustomerStatementsVO) JunitCommonUtilities
            .asJsonToObject(filePath+"duplicateIncorrectEndBalanceRequest.json",
                CustomerStatementsVO.class);
    mockMvc.perform(
        MockMvcRequestBuilders.post("/customerstatements/").content(
            JunitCommonUtilities.asJsonString(customerStatementsVO))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.result",
            Matchers.is(ErrorMessage.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE)));
  }
}

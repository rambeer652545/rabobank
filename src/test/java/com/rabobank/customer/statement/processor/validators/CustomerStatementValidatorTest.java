package com.rabobank.customer.statement.processor.validators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabobank.customer.statement.processor.exceptions.ErrorMessage;
import com.rabobank.customer.statement.processor.models.CustomerStatementResponse;
import com.rabobank.customer.statement.processor.models.CustomerStatementsVO;
import com.rabobank.customer.statement.processor.utils.JunitCommonUtilities;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CustomerStatementValidatorTest {
  @InjectMocks
  private CustomerStatementValidator customerStatementValidator;
  final static String filePath = "src/test/resources/requests/";
  @Test
  public void testIsBlankCustomerStatementsVO() throws JsonProcessingException {
    CustomerStatementsVO customerStatementsVO =
        (CustomerStatementsVO) JunitCommonUtilities.asJsonToObject(filePath+"request.json",
            CustomerStatementsVO.class);
    boolean result  = customerStatementValidator.isBlankCustomerStatementsVO(customerStatementsVO);
    Assertions.assertFalse(result);
  }
  @Test
  public void testIsBlankCustomerStatementsVOInValid() {
    boolean result  = customerStatementValidator.isBlankCustomerStatementsVO(null);
    Assertions.assertTrue(result);
  }

  @Test
  public void testValidateCustomerStatements() throws JsonProcessingException {
    CustomerStatementsVO customerStatementsVO =
        (CustomerStatementsVO) JunitCommonUtilities.asJsonToObject(filePath+"request.json",
            CustomerStatementsVO.class);
    Optional<CustomerStatementResponse> customerStatementResponse  = customerStatementValidator.validateCustomerStatements(customerStatementsVO);
    Assertions.assertFalse(customerStatementResponse.isPresent());
  }
  @Test
  public void testValidateCustomerStatementsDuplicate() throws JsonProcessingException {
    CustomerStatementsVO customerStatementsVO =
        (CustomerStatementsVO) JunitCommonUtilities.asJsonToObject(filePath+"duplicateRequest.json",
            CustomerStatementsVO.class);
    Optional<CustomerStatementResponse> customerStatementResponse  = customerStatementValidator.validateCustomerStatements(customerStatementsVO);
    Assertions.assertEquals(ErrorMessage.DUPLICATE_REFERENCE, customerStatementResponse.get().getResult());
  }
  @Test
  public void testValidateCustomerStatementsIncorrectEndBalance() throws JsonProcessingException {
    CustomerStatementsVO customerStatementsVO =
        (CustomerStatementsVO) JunitCommonUtilities.asJsonToObject(filePath+"incorrectEndBalanceRequest.json",
            CustomerStatementsVO.class);
    Optional<CustomerStatementResponse> customerStatementResponse  = customerStatementValidator.validateCustomerStatements(customerStatementsVO);
    Assertions.assertEquals(ErrorMessage.INCORRECT_END_BALANCE, customerStatementResponse.get().getResult());
  }
  @Test
  public void testValidateCustomerStatementsDuplicateIncorrectEndBalance() throws JsonProcessingException {
    CustomerStatementsVO customerStatementsVO =
        (CustomerStatementsVO) JunitCommonUtilities.asJsonToObject(filePath+"duplicateIncorrectEndBalanceRequest.json",
            CustomerStatementsVO.class);
    Optional<CustomerStatementResponse> customerStatementResponse  = customerStatementValidator.validateCustomerStatements(customerStatementsVO);
    Assertions.assertEquals(ErrorMessage.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE, customerStatementResponse.get().getResult());
  }
  @Test
  public void testValidateCustomerStatementsIncorrectEndBalanceWithWrongMutation() throws JsonProcessingException {
    CustomerStatementsVO customerStatementsVO =
        (CustomerStatementsVO) JunitCommonUtilities.asJsonToObject(filePath+"incorrectEndBalanceRequestWithWrongMutation.json",
            CustomerStatementsVO.class);
    Optional<CustomerStatementResponse> customerStatementResponse  = customerStatementValidator.validateCustomerStatements(customerStatementsVO);
    Assertions.assertEquals(ErrorMessage.INCORRECT_END_BALANCE, customerStatementResponse.get().getResult());
  }

}

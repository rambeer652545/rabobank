package com.rabobank.customer.statement.processor.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabobank.customer.statement.processor.exceptions.ErrorMessage;
import com.rabobank.customer.statement.processor.exceptions.RabobankCustomerStatementExceptionHandler;
import com.rabobank.customer.statement.processor.models.CustomerStatementResponse;
import com.rabobank.customer.statement.processor.models.CustomerStatementsVO;
import com.rabobank.customer.statement.processor.utils.JunitCommonUtilities;
import com.rabobank.customer.statement.processor.validators.CustomerStatementValidator;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CustomerStatementProcessorServiceImplTest {


  @Mock
  private CustomerStatementValidator customerStatementValidator;
  @Mock
  private RabobankCustomerStatementExceptionHandler rabobankCustomerStatementExceptionHandler;
  @InjectMocks
  CustomerStatementProcessorServiceImpl customerStatementProcessorService;
  final static String filePath = "src/test/resources/requests/";

  @Test
  public void testValidateAllStatement() throws JsonProcessingException {
    CustomerStatementsVO customerStatementsVO =
        (CustomerStatementsVO) JunitCommonUtilities.asJsonToObject(filePath+"request.json",
            CustomerStatementsVO.class);
    ResponseEntity<CustomerStatementResponse> response = customerStatementProcessorService
        .validateAllStatement(customerStatementsVO);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    Assertions.assertEquals(ErrorMessage.SUCCESSFUL, response.getBody().getResult());
  }

  @Test
  public void testValidateAllStatementBadRequest() {
    Mockito.when(customerStatementValidator.isBlankCustomerStatementsVO(Mockito.any()))
        .thenReturn(true);
    ResponseEntity<CustomerStatementResponse> response = customerStatementProcessorService
        .validateAllStatement(null);
    Assertions.assertEquals(400, response.getStatusCodeValue());
    Assertions.assertEquals(ErrorMessage.BAD_REQUEST, response.getBody().getResult());
  }

  @Test
  public void testValidateAllStatementDuplicate() {

    Mockito.when(customerStatementValidator.isBlankCustomerStatementsVO(Mockito.any()))
        .thenReturn(false);

    CustomerStatementResponse customerStatementResponse = new CustomerStatementResponse();
    customerStatementResponse.setResult(ErrorMessage.DUPLICATE_REFERENCE);
    Mockito.when(customerStatementValidator.validateCustomerStatements(Mockito.any()))
        .thenReturn(Optional.ofNullable(customerStatementResponse));

    ResponseEntity<CustomerStatementResponse> response = customerStatementProcessorService
        .validateAllStatement(null);
    Assertions.assertEquals(400, response.getStatusCodeValue());
    Assertions.assertEquals(ErrorMessage.DUPLICATE_REFERENCE, response.getBody().getResult());
  }

  @Test
  public void testValidateAllStatementIncorrectEndBalance() {

    Mockito.when(customerStatementValidator.isBlankCustomerStatementsVO(Mockito.any()))
        .thenReturn(false);

    CustomerStatementResponse customerStatementResponse = new CustomerStatementResponse();
    customerStatementResponse.setResult(ErrorMessage.INCORRECT_END_BALANCE);
    Mockito.when(customerStatementValidator.validateCustomerStatements(Mockito.any()))
        .thenReturn(Optional.ofNullable(customerStatementResponse));

    ResponseEntity<CustomerStatementResponse> response = customerStatementProcessorService
        .validateAllStatement(null);
    Assertions.assertEquals(400, response.getStatusCodeValue());
    Assertions.assertEquals(ErrorMessage.INCORRECT_END_BALANCE, response.getBody().getResult());
  }

  @Test
  public void testValidateAllStatementDuplicateIncorrectEndBalance() {

    Mockito.when(customerStatementValidator.isBlankCustomerStatementsVO(Mockito.any()))
        .thenReturn(false);

    CustomerStatementResponse customerStatementResponse = new CustomerStatementResponse();
    customerStatementResponse.setResult(ErrorMessage.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE);
    Mockito.when(customerStatementValidator.validateCustomerStatements(Mockito.any()))
        .thenReturn(Optional.ofNullable(customerStatementResponse));

    ResponseEntity<CustomerStatementResponse> response = customerStatementProcessorService
        .validateAllStatement(null);
    Assertions.assertEquals(400, response.getStatusCodeValue());
    Assertions.assertEquals(ErrorMessage.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE,
        response.getBody().getResult());
  }

}

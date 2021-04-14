package com.rabobank.customer.statement.processor.services;

import com.rabobank.customer.statement.processor.exceptions.ErrorMessage;
import com.rabobank.customer.statement.processor.exceptions.RabobankCustomerStatementExceptionHandler;
import com.rabobank.customer.statement.processor.models.CustomerStatementResponse;
import com.rabobank.customer.statement.processor.models.CustomerStatementsVO;
import com.rabobank.customer.statement.processor.validators.CustomerStatementValidator;
import java.util.Collections;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerStatementProcessorServiceImpl implements CustomerStatementProcessorService {

  @Autowired
  private CustomerStatementValidator customerStatementValidator;
  @Autowired
  private RabobankCustomerStatementExceptionHandler rabobankCustomerStatementExceptionHandler;


  @Override
  public ResponseEntity<CustomerStatementResponse> validateAllStatement(
      final CustomerStatementsVO customerStatementVOs) {
    CustomerStatementResponse customerStatementResponse = new CustomerStatementResponse();
    log.info(ErrorMessage.CUSTOMER_STATEMENTS_ARE + "processing");
    log.debug(ErrorMessage.CUSTOMER_STATEMENTS_ARE + "in debug mode : " + customerStatementVOs);
    try {
      if (customerStatementValidator.isBlankCustomerStatementsVO(customerStatementVOs)) {
        customerStatementResponse.setResult(ErrorMessage.BAD_REQUEST);
        customerStatementResponse.setErrorRecords(Collections.emptyList());
        log.error(ErrorMessage.CUSTOMER_STATEMENTS_ARE + "blank");
        return new ResponseEntity<>(customerStatementResponse, HttpStatus.BAD_REQUEST);
      }

      Optional<CustomerStatementResponse> validatorCustomerStatementResponse = customerStatementValidator
          .validateCustomerStatements(customerStatementVOs);
      if (validatorCustomerStatementResponse.isPresent()) {
        log.error(ErrorMessage.CUSTOMER_STATEMENTS_ARE + "duplicates ");
        return new ResponseEntity<>(validatorCustomerStatementResponse.get(),
            HttpStatus.BAD_REQUEST);
      }
      customerStatementResponse.setResult(ErrorMessage.SUCCESSFUL);
      customerStatementResponse.setErrorRecords(Collections.emptyList());
      log.info(ErrorMessage.CUSTOMER_STATEMENTS_ARE + ErrorMessage.SUCCESSFUL);
    } catch (Exception e) {
      return rabobankCustomerStatementExceptionHandler.customerStatementException(e);
    }
    return new ResponseEntity<>(customerStatementResponse, HttpStatus.OK);
  }
}

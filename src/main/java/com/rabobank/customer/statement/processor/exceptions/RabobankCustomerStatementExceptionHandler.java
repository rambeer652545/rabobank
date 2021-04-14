package com.rabobank.customer.statement.processor.exceptions;

import com.rabobank.customer.statement.processor.models.CustomerStatementResponse;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RabobankCustomerStatementExceptionHandler {

  public ResponseEntity<CustomerStatementResponse> customerStatementException(final Exception e) {
    CustomerStatementResponse customerStatementResponse = new CustomerStatementResponse();
    log.error("RabobankCustomerStatementExceptionHandler :" + e);
    customerStatementResponse.setResult(ErrorMessage.INTERNAL_SERVER_ERROR);
    customerStatementResponse.setErrorRecords(Collections.emptyList());
    return new ResponseEntity<>(customerStatementResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
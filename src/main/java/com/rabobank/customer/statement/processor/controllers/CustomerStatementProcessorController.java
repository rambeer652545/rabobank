package com.rabobank.customer.statement.processor.controllers;

import com.rabobank.customer.statement.processor.models.CustomerStatementResponse;
import com.rabobank.customer.statement.processor.models.CustomerStatementsVO;
import com.rabobank.customer.statement.processor.exceptions.ErrorMessage;
import com.rabobank.customer.statement.processor.services.CustomerStatementProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/customerstatements")
public class CustomerStatementProcessorController {

  @Autowired
  private CustomerStatementProcessorService customerStatementProcessorService;

  @PostMapping("/")
  public ResponseEntity<CustomerStatementResponse> validateAllStatement(
      @RequestBody @Validated final CustomerStatementsVO customerStatementVOs) {
    log.info(ErrorMessage.CUSTOMER_STATEMENTS_ARE + "processing initiated");
    return customerStatementProcessorService
        .validateAllStatement(customerStatementVOs);
  }
}

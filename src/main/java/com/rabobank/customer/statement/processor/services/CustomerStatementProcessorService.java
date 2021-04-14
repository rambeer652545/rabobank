package com.rabobank.customer.statement.processor.services;

import com.rabobank.customer.statement.processor.models.CustomerStatementResponse;
import com.rabobank.customer.statement.processor.models.CustomerStatementsVO;
import org.springframework.http.ResponseEntity;

public interface CustomerStatementProcessorService {

  public ResponseEntity<CustomerStatementResponse> validateAllStatement(
      final CustomerStatementsVO customerStatementVOs);
}

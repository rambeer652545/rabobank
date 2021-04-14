package com.rabobank.customer.statement.processor.models;

import com.rabobank.customer.statement.processor.exceptions.ErrorRecord;
import java.util.List;
import lombok.Data;

@Data
public class CustomerStatementResponse {
  private String result;
  private List<ErrorRecord> errorRecords;
}

package com.rabobank.customer.statement.processor.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorRecord {

  private Long reference;
  private String accountNumber;
}

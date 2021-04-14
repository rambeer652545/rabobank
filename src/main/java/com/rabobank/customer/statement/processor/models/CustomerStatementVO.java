package com.rabobank.customer.statement.processor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerStatementVO {

  private Long transactionReference;
  private String accountNumber;
  private Double startBalance;
  private String mutation;
  private String description;
  private Double endBalance;
}

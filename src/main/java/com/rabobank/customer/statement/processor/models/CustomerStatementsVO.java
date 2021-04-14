package com.rabobank.customer.statement.processor.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerStatementsVO {

  private List<CustomerStatementVO> customerStatementVOS;

}

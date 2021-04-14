package com.rabobank.customer.statement.processor.validators;

import com.rabobank.customer.statement.processor.exceptions.ErrorMessage;
import com.rabobank.customer.statement.processor.exceptions.ErrorRecord;
import com.rabobank.customer.statement.processor.models.CustomerStatementResponse;
import com.rabobank.customer.statement.processor.models.CustomerStatementVO;
import com.rabobank.customer.statement.processor.models.CustomerStatementsVO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class CustomerStatementValidator {


  public boolean isBlankCustomerStatementsVO(final CustomerStatementsVO customerStatementVOs) {
    return !(null != customerStatementVOs && null != customerStatementVOs.getCustomerStatementVOS()
        && !customerStatementVOs.getCustomerStatementVOS().isEmpty());
  }
  public boolean isBlankCustomerStatementFields(final CustomerStatementsVO customerStatementVOs) {
    return !(null != customerStatementVOs && null != customerStatementVOs.getCustomerStatementVOS()
        && !customerStatementVOs.getCustomerStatementVOS().isEmpty());
  }

  public Optional<CustomerStatementResponse> validateCustomerStatements(
      final CustomerStatementsVO customerStatementsVO) {

    List<ErrorRecord> inCorrectBalanceErrorRecords = new ArrayList<>();
    List<ErrorRecord> duplicateErrorRecords = new ArrayList<>();
    Set<Long> transactionReferences = new HashSet<>();
    customerStatementsVO.getCustomerStatementVOS().stream().forEach(customerStatementVO -> {
      if (!transactionReferences.add(customerStatementVO.getTransactionReference())) {
        duplicateErrorRecords.add(new ErrorRecord(customerStatementVO.getTransactionReference(),
            customerStatementVO.getAccountNumber()));
      }
      if (!isValidEndBalance(customerStatementVO)) {
        inCorrectBalanceErrorRecords
            .add(new ErrorRecord(customerStatementVO.getTransactionReference(),
                customerStatementVO.getAccountNumber()));
      }
    });

    return customerStatementsValidateResponse(duplicateErrorRecords, inCorrectBalanceErrorRecords);
  }

  private Optional<CustomerStatementResponse> customerStatementsValidateResponse(
      final List<ErrorRecord> duplicateErrorRecords,
      final List<ErrorRecord> inCorrectBalanceErrorRecords) {
    CustomerStatementResponse customerStatementResponse = new CustomerStatementResponse();
    if (!duplicateErrorRecords.isEmpty() && !inCorrectBalanceErrorRecords.isEmpty()) {
      customerStatementResponse.setResult(ErrorMessage.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE);
      duplicateErrorRecords.addAll(inCorrectBalanceErrorRecords);
      customerStatementResponse.setErrorRecords(duplicateErrorRecords);
      return Optional.ofNullable(customerStatementResponse);
    } else if (!duplicateErrorRecords.isEmpty()) {
      customerStatementResponse.setResult(ErrorMessage.DUPLICATE_REFERENCE);
      customerStatementResponse.setErrorRecords(duplicateErrorRecords);
      return Optional.ofNullable(customerStatementResponse);
    } else if (!inCorrectBalanceErrorRecords.isEmpty()) {
      customerStatementResponse.setResult(ErrorMessage.INCORRECT_END_BALANCE);
      customerStatementResponse.setErrorRecords(inCorrectBalanceErrorRecords);
      return Optional.ofNullable(customerStatementResponse);
    } else {
      return Optional.ofNullable(null);
    }
  }

  private boolean isValidEndBalance(final CustomerStatementVO customerStatementVO) {
    double startBalance = customerStatementVO.getStartBalance();
    double endBalance = customerStatementVO.getEndBalance();

    if ("+".equalsIgnoreCase(customerStatementVO.getMutation())
        && (startBalance + endBalance) > 0) {
      return true;
    }
    return ("-".equalsIgnoreCase(customerStatementVO.getMutation())
        && (startBalance - endBalance) > 0);
  }
}

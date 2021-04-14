package com.rabobank.customer.statement.processor.exceptions;

public abstract class ErrorMessage {
  public static final String SUCCESSFUL = "SUCCESSFUL";

  public static final String CUSTOMER_STATEMENTS_ARE = "Customer Statements are ";
  public static final String DUPLICATE_REFERENCE = "DUPLICATE_REFERENCE";
  public static final String DUPLICATE_REFERENCE_INCORRECT_END_BALANCE = "DUPLICATE_REFERENCE_INCORRECT_END_BALANCE";
  public static final String INCORRECT_END_BALANCE = "INCORRECT_END_BALANCE";
  public static final String BAD_REQUEST = "BAD_REQUEST";
  public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

}

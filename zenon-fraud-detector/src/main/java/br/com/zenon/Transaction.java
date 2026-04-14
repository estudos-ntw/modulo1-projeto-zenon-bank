package br.com.zenon;

import java.math.BigDecimal;

public record Transaction(int step, TransactionType type, BigDecimal amount, TransactionCustomer origin,
                          TransactionCustomer destination, Boolean isFraud, Boolean isFlaggedFraud) {


}

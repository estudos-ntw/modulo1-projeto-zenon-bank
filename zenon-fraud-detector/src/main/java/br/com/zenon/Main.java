package br.com.zenon;

import java.math.BigDecimal;

public class Main {

    void main() {
        var t1 = new Transaction(1, TransactionType.PAYMENT, new BigDecimal("9848.64"),
                new TransactionCustomer("THIAGO SILVA", new BigDecimal("170136.0"), new BigDecimal("160296.36")),
                new TransactionCustomer("NATARA LUIZE", new BigDecimal("0.0"), new BigDecimal("0.0")),
                false, false
        );

        var t2 = new Transaction(1, TransactionType.CASH_OUT, new BigDecimal("850002.52"),
                new TransactionCustomer("CLIENTE 3", new BigDecimal("850002.52"), new BigDecimal("0.0")),
                new TransactionCustomer("CLIENTE 4", new BigDecimal("6510099.11"), new BigDecimal("736010.0")),
                true, false
        );

        IO.println(t1);
        IO.println(t2);
    }
}

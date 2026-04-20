package br.com.zenon;

import java.io.IOException;
import java.util.List;

public class Main {

    void main() throws IOException {
        var transactionIngestor = new TransactionIngestor();
        List<Transaction> transactions = transactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");
        List<Transaction> transactionsOldSchool = transactionIngestor.readOldSchool("data/PS_20174392719_1491204439457_log.csv");
        IO.println("---------------------------------------------------------------------------------------");
        IO.println("New Method");

        transactions.stream().limit(10).forEach(transaction -> {
            IO.println(transaction);
        });

        IO.println("---------------------------------------------------------------------------------------");
        IO.println("Old School");

        transactionsOldSchool.stream().limit(10).forEach(transaction -> {
            IO.println(transaction);
        });


    }
}

package br.com.zenon;

import java.io.IOException;
import java.util.List;

public class Main {

    void main() throws IOException {
        var transactionIngestor = new TransactionIngestor();

        List<Transaction> transactions = transactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");
        //List<Transaction> transactionsOldSchool = transactionIngestor.readOldSchool("data/PS_20174392719_1491204439457_log.csv");
        IO.println("---------------------------------------------------------------------------------------");
        IO.println(transactions.size());
        transactions.stream().limit(10).forEach(IO::println);

        IO.println("---------------------------------------------------------------------------------------");
        List<Transaction> transactionsBadData = transactionIngestor.read("data/paysim_with_bad_data.csv");
        IO.println(transactionsBadData.size());
        transactionsBadData.forEach(IO::println);


    }
}

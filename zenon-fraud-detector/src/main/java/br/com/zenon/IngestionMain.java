package br.com.zenon;

import java.util.List;

public class IngestionMain {
    void main() {
        var repository = new TransactionSQLRepository();

        var transactionIngestor = new TransactionIngestor();
        long startTimeSQL = System.nanoTime();
        List<Transaction> transactions = transactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");
        IO.println(transactions.size());



        IO.println("INICIANDO AS ADIÇÕES DAS TRANSAÇÕES NO BANCO DE DADOS...");
                repository.saveAll(transactions);
        long endTimeSQL = System.nanoTime();
        long durationSQL = endTimeSQL - startTimeSQL;
        IO.println("IMPORTAÇÃO FINALIZADA");
        IO.println("Tempo gasto para leitura do CSV: " + (durationSQL/1000) + " segundos");
    }
}

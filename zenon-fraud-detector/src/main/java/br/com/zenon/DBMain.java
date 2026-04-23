package br.com.zenon;

import java.util.List;

public class DBMain {
    void main() {
        ConnectionFactory.getConnection();
        IO.println("Conexão de banco criada");

        var transactionIngestor = new TransactionIngestor();
        List<Transaction> transactions = transactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");

        var repository = new TransactionSQLRepository();
        IO.println("INICIANDO AS ADIÇÕES DAS TRANSAÇÕES NO BANCO DE DADOS...");
        transactions.forEach(repository::save);

        IO.println("IMPORTAÇÃO FINALIZADA");
    }
}

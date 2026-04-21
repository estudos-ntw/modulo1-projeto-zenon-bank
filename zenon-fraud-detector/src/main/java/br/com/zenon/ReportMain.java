package br.com.zenon;

public class ReportMain {
    static void main() {
        var transactionReport = new TransactionReport();
        TransactionReport.Statistics statistics = transactionReport.generateReport("data/PS_20174392719_1491204439457_log.csv");
        IO.println("""
                Total de Linhas: %d
                Total de Fraudes: %d
                Valor total transacionado: %.2f
                """.formatted(statistics.totalTransactions(), statistics.totalFrauds(), statistics.totalAmount()));

    }
}

package br.com.zenon;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.spi.CurrencyNameProvider;

public class ReportMain {
    static void main() {
        var locale = Locale.of("pt", "BR");

        var integerFormatter = NumberFormat.getIntegerInstance(locale);
        var currencyFormatter = DecimalFormat.getCurrencyInstance(locale);
        currencyFormatter.setCurrency(Currency.getInstance("USD"));

        var transactionReport = new TransactionReport();
        TransactionReport.Statistics statistics = transactionReport.generateReport("data/PS_20174392719_1491204439457_log.csv");
        String formattedTotalTransactions = integerFormatter.format(statistics.totalTransactions());
        String formattedTotalFrauds = integerFormatter.format(statistics.totalFrauds());
        String formattedTotalAmount = currencyFormatter.format(statistics.totalAmount());
        IO.println("""
                Total de Linhas: %s
                Total de Fraudes: %s
                Valor total transacionado: %s
                """.formatted(formattedTotalTransactions, formattedTotalFrauds, formattedTotalAmount));

    }
}

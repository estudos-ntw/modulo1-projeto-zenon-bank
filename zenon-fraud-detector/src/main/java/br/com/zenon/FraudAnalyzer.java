package br.com.zenon;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FraudAnalyzer {
    private final List<Transaction> transactions;

    FraudAnalyzer(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactions = transactions;
    }

    public long countFrauds() {
        return fraudStream()
                .count();
    }

    private Stream<Transaction> fraudStream() {
        return this.transactions.stream()
                .filter(Transaction::isFraud);
    }

    public List<BigDecimal> findHighestValueFrauds(int limit) {
        return fraudStream()
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .map(Transaction::amount)
                .limit(limit)
                .toList();
    }

    public List<String> findTopSuspiciousClients(int limit) {
        return fraudStream()
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .map(transaction -> transaction.origin().name())
                .distinct()
                .limit(limit)
                .toList();
    }

    public BigDecimal calculateTotalFraudsLoss() {
        return fraudStream()
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public Map<TransactionType, Long> countFraudsByType() {
        return fraudStream()
                .collect(Collectors.groupingBy(Transaction::type, Collectors.counting()));
    }
}

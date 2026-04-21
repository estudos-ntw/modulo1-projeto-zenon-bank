package br.com.zenon;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Main {

    void main() throws IOException {
        var transactionIngestor = new TransactionIngestor();

        List<Transaction> transactions = transactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");
        //List<Transaction> transactionsOldSchool = transactionIngestor.readOldSchool("data/PS_20174392719_1491204439457_log.csv");
        IO.println("---------------------------------------------------------------------------------------");
        IO.println(transactions.size());
        transactions.stream().limit(10).forEach(IO::println);

        //IO.println("---------------------------------------------------------------------------------------");
        //List<Transaction> transactionsBadData = transactionIngestor.read("data/paysim_with_bad_data.csv");
        //IO.println(transactionsBadData.size());
        //transactionsBadData.forEach(IO::println);

        IO.println("---------------------------------------------------------------------------------------");
        var fraudAnalizyer = new FraudAnalyzer(transactions);
        var fraudCounts = fraudAnalizyer.countFrauds();
        IO.println("1. Total de Fraudes: " + fraudCounts);

        List<BigDecimal> highestFrauds = fraudAnalizyer.findHighestValueFrauds(3);
        IO.println("2. Top 3 fraudes de maior valor:");
        highestFrauds.stream().forEach(amount -> IO.println("- %.2f".formatted(amount)));

        List<String> findTopSuspiciousClients = fraudAnalizyer.findTopSuspiciousClients(5);

        IO.println("3. Clientes Suspeitos:");
        findTopSuspiciousClients.forEach(IO::println);

        BigDecimal calculateTotalFraudsLoss = fraudAnalizyer.calculateTotalFraudsLoss();
        IO.println("Prejuízo Total: " + calculateTotalFraudsLoss);

        Map<TransactionType, Long> countFraudsByType = fraudAnalizyer.countFraudsByType();

        IO.println("5. Fraudes por Tipo:");
        countFraudsByType.forEach((type, count) -> {
            IO.println("    - " + type + ": " + count);
        });

        /*
         *Crie uma branch chamada tarefa/05-streams para trabalhar nesta tarefa. Ao final, não esqueça de fazer o commit e push na sua branch da tarefa.

Carregue 50.000 linhas em TransactionIngestore crie uma nova classe FraudAnalyzer que usa a Stream API para:

Apenas transações onde isFraud == true, imprima o tamanho da lista.

Imprima as 3 fraudes de maior valor (amount).

Obter apenas os nomes dos clientes de origem (nameOrig) dessas fraudes e depois gere uma lista sem repetições (Set ou distinct) com os 5 maiores clientes suspeitos.

Calcule o prejuízo total causado pelas fraudes (soma dos amount).

Conte quantas fraudes ocorreram por tipo de transação (CASH_OUT, TRANSFER, etc...).

Na classe Main, invoque cada um dos métodos e imprima os resultados, que devem ser semelhantes ao seguinte:




2. Top 3 Fraudes de Maior Valor:
10000000.00
10000000.00
5460002.91
3. Clientes Suspeitos:
C7162498
C351297720
C666654362
C1588880909
C2047521920
4. Prejuízo Total: 57393771.84
5. Fraudes por Tipo:
 - CASH_OUT: 51
 - TRANSFER: 49
         */


    }
}

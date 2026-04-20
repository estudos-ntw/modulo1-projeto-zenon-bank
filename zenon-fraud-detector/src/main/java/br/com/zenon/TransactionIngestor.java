package br.com.zenon;


import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionIngestor {
    public List<Transaction> read(String filename) {
        Path path = Path.of(filename);
        try {
            List<String> lines = Files.readAllLines(path);
            return lines.stream()
                    .skip(1)
                    .limit(1000)
                    .map(this::parseTransaction)
                    .toList();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao ler o arquivo: " + filename, ex);
        }

    }

    public List<Transaction> readOldSchool(String filename) {
        List<Transaction> transactions = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filename);
             Scanner scanner = new Scanner(fis)) {
            int lineCount = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineCount++;

                if (lineCount == 1) {
                    continue;
                }

                if (lineCount > 1001) {
                    break;
                }

                var transaction = parseTransaction(line);
                transactions.add(transaction);

            }

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao ler o arquivo: " + filename, ex);
        }
        return transactions;
    }

    private Transaction parseTransaction(String line) {
        String[] chuncks = line.split(",");

        int step = Integer.parseInt(chuncks[0]);
        TransactionType type = TransactionType.valueOf(chuncks[1]);
        BigDecimal amount = new BigDecimal(chuncks[2]);
        var origin = new TransactionCustomer(chuncks[3], new BigDecimal(chuncks[4]), new BigDecimal(chuncks[5]));
        var recipient = new TransactionCustomer(chuncks[6], new BigDecimal(chuncks[7]), new BigDecimal(chuncks[8]));

        boolean isFraud = "1".equals(chuncks[9]);
        boolean isFlaggedFraud = "1".equals(chuncks[10]);

        return new Transaction(step, type, amount, origin, recipient, isFraud, isFlaggedFraud);
    }
}

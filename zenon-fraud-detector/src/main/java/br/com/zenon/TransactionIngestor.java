package br.com.zenon;


import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class TransactionIngestor {
    public static final int FRAUD_LIMITED = 100_000;

    public List<Transaction> read(String filename) {
        Path path = Path.of(filename);
        try {
            List<String> lines = Files.readAllLines(path);
            return lines.stream()
                    .skip(1)
                    .limit(FRAUD_LIMITED)
                    .map(this::parseTransaction)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao ler o arquivo: " + filename, ex);
        }

    }

    private Optional<Transaction> parseTransaction(String line) {
        try {
            String[] chuncks = line.split(",");

            int step = Integer.parseInt(chuncks[0]);
            TransactionType type = TransactionType.valueOf(chuncks[1]);

            if (chuncks[2] == null || chuncks[2].trim().isEmpty())
                throw new IllegalArgumentException("O valor de amount não pode ser nulo ou vazio");
            BigDecimal amount = new BigDecimal(chuncks[2]);
            var origin = new TransactionCustomer(chuncks[3], new BigDecimal(chuncks[4]), new BigDecimal(chuncks[5]));
            var recipient = new TransactionCustomer(chuncks[6], new BigDecimal(chuncks[7]), new BigDecimal(chuncks[8]));

            boolean isFraud = "1".equals(chuncks[9]);
            boolean isFlaggedFraud = "1".equals(chuncks[10]);

            return Optional.of(new Transaction(step, type, amount, origin, recipient, isFraud, isFlaggedFraud));
        } catch (Exception ex) {
            System.err.println("Erro ao ler o arquivo: " + line + ex);

            return Optional.empty();
        }
    }
}

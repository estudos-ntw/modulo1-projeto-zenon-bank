package br.com.zenon;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TransactionSQLRepository implements TransactionRepository {
    @Override
    public void save(Transaction transaction) {
        String sql = """
                insert into transactions 
                (step, `type`, amount, 
                name_origin, old_balance_origin, new_balance_origin, 
                name_recipient,old_balance_recipient, new_balance_recipient, 
                is_fraud, is_flagged_fraud)
                values (?,?,?,?,?,?,?,?,?,?,?);
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, transaction.step());
            ps.setString(2, transaction.type().name());
            ps.setBigDecimal(3, transaction.amount());

            ps.setString(4, transaction.origin().name());
            ps.setBigDecimal(5, transaction.origin().oldBalance());
            ps.setBigDecimal(6, transaction.origin().oldBalance());

            ps.setString(7, transaction.destination().name());
            ps.setBigDecimal(8, transaction.destination().oldBalance());
            ps.setBigDecimal(9, transaction.destination().oldBalance());

            ps.setBoolean(10, transaction.isFraud());
            ps.setBoolean(11, transaction.isFlaggedFraud());
            ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar nova transação", e);
        }
    }

    @Override
    public Optional<Transaction> findByOriginName(String originName) {
        String sql = """
                select id, step, type, amount, name_origin, old_balance_origin,
                new_balance_origin, name_recipient,old_balance_recipient, new_balance_recipient,
                is_fraud, is_flagged_fraud
                FROM  zenon_frauds.transactions
                WHERE name_origin = ?
                LIMIT 1
                """;

        try {
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, originName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Transaction transaction = mapResultSetToTransaction(rs);
                    IO.println(rs.getString("name_origin"));
                } else {
                    IO.println("Transação não encontradao. " + originName);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Transaction mapResultSetToTransaction(ResultSet rs) {
        try {
            int step = rs.getInt("step");
            TransactionType type = TransactionType.valueOf(rs.getString("type"));

            BigDecimal amount = rs.getBigDecimal("amount");
            String nameOrigin = rs.getString("name_origin");
            BigDecimal oldBalanceOrigin = rs.getBigDecimal("old_balance_origin");
            BigDecimal newBalanceOrigin = rs.getBigDecimal("new_balance_origin");
            String nameRecipient = rs.getString("name_recipient");
            BigDecimal oldBalanceRecipient = rs.getBigDecimal("old_balance_recipient");
            BigDecimal newBalanceRecipient = rs.getBigDecimal("new_balance_recipient");
            Boolean isFraud = rs.getBoolean("is_fraud");
            Boolean isFlaggedFraud = rs.getBoolean("is_flagged_fraud");
            TransactionCustomer origin = new TransactionCustomer(nameOrigin, oldBalanceOrigin, newBalanceOrigin);
            TransactionCustomer recipient = new TransactionCustomer(nameRecipient, oldBalanceRecipient, newBalanceRecipient);

            return new Transaction(step, type, amount, origin, recipient, isFraud, isFlaggedFraud);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }


    }
}

package pl.com.itsystems.homeBudget;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepository {

    public boolean insert(Transaction transaction) {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        boolean result = false;
        try {
            String sql = "INSERT INTO transaction (type, description, amount, date) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, transaction.getType().toString());
            preparedStatement.setString(2, transaction.getDescription());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setDate(4, Date.valueOf(transaction.getDate()));
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    public List<Transaction> findByType(TransactionType type) {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        List<Transaction> transactions = new ArrayList<>();
        try {
            String sql = "SELECT * FROM transaction WHERE type = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, type.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                TransactionType transactionType = TransactionType.valueOf(resultSet.getString("type"));
                String description = resultSet.getString("description");
                double amount = resultSet.getDouble("amount");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                transactions.add(new Transaction(id, transactionType, description, amount, date));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return transactions;
    }

    public boolean remove(long id) {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        boolean result = false;
        try {
            String sql = "DELETE FROM transaction WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            int rowsDelete = preparedStatement.executeUpdate();
            if (rowsDelete > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    public List<Transaction> findAll() {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        List<Transaction> transactions = new ArrayList<>();
        try {
            String sql = "SELECT * FROM transaction";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                TransactionType type = TransactionType.valueOf(resultSet.getString("type"));
                String description = resultSet.getString("description");
                double amount = resultSet.getDouble("amount");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                transactions.add(new Transaction(id, type, description, amount, date));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return transactions;
    }

    private Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/home_budget?serverTimezone=UTC&characterEncoding=utf8";
        try {
            return DriverManager.getConnection(url, "admin", "admin");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

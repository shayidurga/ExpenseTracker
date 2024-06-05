package dao;

import model.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {
    private Connection connection;

    public ExpenseDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to get all expenses for a user
    public List<Expense> getAllExpenses(int userID) {
        List<Expense> expenses = new ArrayList<>();
        String query = "SELECT * FROM expenses WHERE userId = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("userId");
                String description = resultSet.getString("description");
                double expenseAmt = resultSet.getDouble("expense");
                String category = resultSet.getString("category");
                Date date = resultSet.getDate("date");

                Expense expense = new Expense(id, userId, date, category, description, expenseAmt);
                expenses.add(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenses;
    }

    // Method to add a new expense
    public void addExpense(Expense expense) {
        String query = "INSERT INTO expenses (userId, description, expense, category, date) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, expense.getUserId());
            statement.setString(2, expense.getDescription());
            statement.setDouble(3, expense.getExpense());
            statement.setString(4, expense.getCategory());
            statement.setDate(5, expense.getDate());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update an existing expense
    public void updateExpense(Expense expense) {
        String query = "UPDATE expenses SET description = ?, expense = ?, category = ?, date = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, expense.getDescription());
            statement.setDouble(2, expense.getExpense());
            statement.setString(3, expense.getCategory());
            statement.setDate(4, expense.getDate());
            statement.setInt(5, expense.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete an expense
    public void deleteExpense(int expenseId) {
        String query = "DELETE FROM expenses WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, expenseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get an expense by its ID
    public Expense getExpenseById(int expenseId) {
        Expense expense = null;
        String query = "SELECT * FROM expenses WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, expenseId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("userId");
                String description = resultSet.getString("description");
                double expenseAmt = resultSet.getDouble("expense");
                String category = resultSet.getString("category");
                Date date = resultSet.getDate("date");

                expense = new Expense(id, userId, date, category, description, expenseAmt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expense;
    }

    // Method to get total expenses by category for a user
    public double getTotalExpensesByCategory(int userID, String category) {
        double total = 0;
        String query = "SELECT SUM(expense) as total FROM expenses WHERE userId = ? AND category = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            statement.setString(2, category);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                total = resultSet.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    // Method to filter expenses by category for a user
    public List<Expense> getExpensesByCategory(int userID, String category) {
        List<Expense> expenses = new ArrayList<>();
        String query = "SELECT * FROM expenses WHERE userId = ? AND category = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            statement.setString(2, category);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("userId");
                String description = resultSet.getString("description");
                double expenseAmt = resultSet.getDouble("expense");
                Date date = resultSet.getDate("date");

                Expense expense = new Expense(id, userId, date, category, description, expenseAmt);
                expenses.add(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenses;
    }

    // Method to sort expenses by date for a user
    public List<Expense> getExpensesSortedByDate(int userID) {
        List<Expense> expenses = new ArrayList<>();
        String query = "SELECT * FROM expenses WHERE userId = ? ORDER BY date";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("userId");
                String description = resultSet.getString("description");
                double expenseAmt = resultSet.getDouble("expense");
                String category = resultSet.getString("category");
                Date date = resultSet.getDate("date");

                Expense expense = new Expense(id, userId, date, category, description, expenseAmt);
                expenses.add(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenses;
    }

    // Method to sort expenses by amount for a user
    public List<Expense> getExpensesSortedByAmount(int userID) {
        List<Expense> expenses = new ArrayList<>();
        String query = "SELECT * FROM expenses WHERE userId = ? ORDER BY expense";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("userId");
                String description = resultSet.getString("description");
                double expenseAmt = resultSet.getDouble("expense");
                String category = resultSet.getString("category");
                Date date = resultSet.getDate("date");

                Expense expense = new Expense(id, userId, date, category, description, expenseAmt);
                expenses.add(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenses;
    }
    
}

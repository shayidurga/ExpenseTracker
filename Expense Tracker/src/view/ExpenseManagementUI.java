package view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;
import dao.ExpenseDAO;
import model.Expense;

public class ExpenseManagementUI extends JFrame {
    private ExpenseDAO expenseDAO;
    private DefaultTableModel tableModel;
    private JTable expenseTable;
    private JLabel totalAmountLabel;
    private int userId;
    private static final long serialVersionUID = 1L;

    public ExpenseManagementUI(ExpenseDAO expenseDAO, int userId) {
        this.expenseDAO = expenseDAO;
        this.userId = userId;

        setTitle("Expense Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel expensePanel = createExpensePanel();
        JPanel buttonPanel = createButtonPanel();

        add(expensePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createExpensePanel() {
        JPanel expensePanel = new JPanel();
        expensePanel.setLayout(new BorderLayout());

        totalAmountLabel = new JLabel("Total Amount: $0.00");
        totalAmountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        expensePanel.add(totalAmountLabel, BorderLayout.NORTH);

        expenseTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(expenseTable);
        expensePanel.add(scrollPane, BorderLayout.CENTER);

        String[] columnNames = {"ID", "Description", "Category", "Expense", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        expenseTable.setModel(tableModel);

        displayAllExpenses();

        return expensePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton addButton = new JButton("Add Expense");
        JButton updateButton = new JButton("Update Expense");
        JButton deleteButton = new JButton("Delete Expense");
        JButton sortButton = new JButton("Sort by Date");
        JButton filterButton = new JButton("Filter by Category");

        addButton.addActionListener(e -> addExpense());
        updateButton.addActionListener(e -> updateExpense());
        deleteButton.addActionListener(e -> deleteExpense());
        sortButton.addActionListener(e -> sortExpensesByDate());
        filterButton.addActionListener(e -> filterExpensesByCategory());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(filterButton);

        return buttonPanel;
    }

    private void displayAllExpenses() {
        tableModel.setRowCount(0);
        List<Expense> expenses = expenseDAO.getAllExpenses(userId);
        addExpensesToTable(expenses);
    }

    private void addExpensesToTable(List<Expense> expenses) {
        for (Expense expense : expenses) {
            Object[] rowData = {
                expense.getId(),
                expense.getDescription(),
                expense.getCategory(),
                expense.getExpense(),
                expense.getDate()
            };
            tableModel.addRow(rowData);
        }
        double totalAmount = calculateTotalAmount(expenses);
        totalAmountLabel.setText("Total Amount: $" + String.format("%.2f", totalAmount));
    }

    private double calculateTotalAmount(List<Expense> expenses) {
        double total = 0.0;
        for (Expense expense : expenses) {
            total += expense.getExpense();
        }
        return total;
    }

    private void addExpense() {
        // Code for adding a new expense
    	String description = JOptionPane.showInputDialog(this, "Enter Description:");
        String category = JOptionPane.showInputDialog(this, "Enter Category:");
        double expense = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Expense:"));
        String dateStr = JOptionPane.showInputDialog(this, "Enter Date (YYYY-MM-DD):");

        Date date = Date.valueOf(dateStr);

        Expense newExpense = new Expense(0, userId, date, category, description, expense);

        expenseDAO.addExpense(newExpense);

        displayAllExpenses();
    }

    private void updateExpense() {
        // Code for updating an existing expense
    	 int selectedRow = expenseTable.getSelectedRow();
         if (selectedRow == -1) {
             JOptionPane.showMessageDialog(this, "Please select an expense to update.");
             return;
         }

         int expenseId = (int) expenseTable.getValueAt(selectedRow, 0);
         Expense selectedExpense = expenseDAO.getExpenseById(expenseId);

         String updatedDescription = JOptionPane.showInputDialog(this, "Enter Updated Description:", selectedExpense.getDescription());
         String updatedCategory = JOptionPane.showInputDialog(this, "Enter Updated Category:", selectedExpense.getCategory());
         double updatedExpense = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Updated Expense:", selectedExpense.getExpense()));
         String updatedDateStr = JOptionPane.showInputDialog(this, "Enter Updated Date (YYYY-MM-DD):", selectedExpense.getDate());

         Date updatedDate = Date.valueOf(updatedDateStr);

         Expense updatedExpenseObj = new Expense(expenseId, userId, updatedDate, updatedCategory, updatedDescription, updatedExpense);

         expenseDAO.updateExpense(updatedExpenseObj);

         displayAllExpenses();
    }

    private void deleteExpense() {
        // Code for deleting an expense
    	int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an expense to delete.");
            return;
        }

        int expenseId = (int) expenseTable.getValueAt(selectedRow, 0);

        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this expense?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            expenseDAO.deleteExpense(expenseId);
        displayAllExpenses();
        }
    }

    private void sortExpensesByDate() {
        List<Expense> sortedExpenses = expenseDAO.getExpensesSortedByDate(userId);
        tableModel.setRowCount(0);
        addExpensesToTable(sortedExpenses);
    }

    private void filterExpensesByCategory() {
        String category = JOptionPane.showInputDialog(this, "Enter Category to Filter:");
        List<Expense> filteredExpenses = expenseDAO.getExpensesByCategory(userId, category);
        tableModel.setRowCount(0);
        addExpensesToTable(filteredExpenses);
    }

    public void displayUI() {
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
        });
    }
}

package model;

import java.sql.Date;

public class Expense {
    private int id;
    private int userId;
    private String description;
    private double expense;
    private String category; // New attribute for category
    private Date date;

    public Expense(int id, int userId, Date date, String category, String description, double expense) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.expense = expense;
        this.category = category; // Initialize category
        this.date = date;
    }

    // Getters and setters for all attributes, including the new category
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

package com.mytracker;

import java.time.LocalDate;

public class Transaction {
    private LocalDate date;
    private String type; // "Income" or "Expense"
    private String category;
    private double amount;

    public Transaction(LocalDate date, String type, String category, double amount) {
        this.date = date;
        this.type = type;
        this.category = category;
        this.amount = amount;
    }

    public String toString() {
        return date + "," + type + "," + category + "," + amount;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }
}

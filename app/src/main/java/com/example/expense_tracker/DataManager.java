package com.example.expense_tracker;

import com.example.expense_tracker.model.Expense;

import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static final List<Expense> expenses = new ArrayList<>();
    private static int nextId = 1;

    public static void addExpense(Expense expense) {
        if (expense == null) {
            return;
        }

        if (expense.getId() == 0) {
            expense.setId(nextId++);
        }

        expenses.add(expense);
    }

    public static List<Expense> getAllExpenses() {
        return expenses;
    }

    public static Expense getExpenseById(int id) {
        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                return expense;
            }
        }
        return null;
    }

    public static void deleteExpense(int id) {
        Expense expenseToDelete = null;

        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                expenseToDelete = expense;
                break;
            }
        }

        if (expenseToDelete != null) {
            expenses.remove(expenseToDelete);
        }
    }

    public static void updateExpense(Expense updatedExpense) {
        if (updatedExpense == null) {
            return;
        }

        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId() == updatedExpense.getId()) {
                expenses.set(i, updatedExpense);
                return;
            }
        }
    }

    public static void loadDummyExpenses() {
        if (!expenses.isEmpty()) {
            return;
        }

        addExpense(new Expense("Lunch", "Food", 12.50, "2026-03-23"));
        addExpense(new Expense("Bus Ticket", "Transport", 2.40, "2026-03-23"));
        addExpense(new Expense("Coffee", "Food", 4.20, "2026-03-22"));
        addExpense(new Expense("Notebook", "Study", 6.99, "2026-03-21"));
    }
}
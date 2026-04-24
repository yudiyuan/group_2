package com.example.expense_tracker;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.expense_tracker.model.Expense;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static final String PREF_NAME = "expense_tracker_prefs";
    private static final String KEY_EXPENSES = "expenses";

    private static final List<Expense> expenses = new ArrayList<>();
    private static int nextId = 1;

    public static void addExpense(Context context, Expense expense) {
        if (expense == null) {
            return;
        }

        if (expense.getId() == 0) {
            expense.setId(nextId++);
        }

        expenses.add(expense);
        saveExpenses(context);
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

    public static void deleteExpense(Context context, int id) {
        Expense expenseToDelete = null;

        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                expenseToDelete = expense;
                break;
            }
        }

        if (expenseToDelete != null) {
            expenses.remove(expenseToDelete);
            saveExpenses(context);
        }
    }

    public static void updateExpense(Context context, Expense updatedExpense) {
        if (updatedExpense == null) {
            return;
        }

        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId() == updatedExpense.getId()) {
                expenses.set(i, updatedExpense);
                saveExpenses(context);
                return;
            }
        }
    }

    public static void loadExpenses(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_EXPENSES, null);

        expenses.clear();

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Expense>>() {}.getType();
            List<Expense> savedExpenses = gson.fromJson(json, type);

            if (savedExpenses != null) {
                expenses.addAll(savedExpenses);
            }
        }

        updateNextId();
    }

    public static void saveExpenses(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(expenses);

        editor.putString(KEY_EXPENSES, json);
        editor.apply();
    }

    private static void updateNextId() {
        int maxId = 0;

        for (Expense expense : expenses) {
            if (expense.getId() > maxId) {
                maxId = expense.getId();
            }
        }

        nextId = maxId + 1;
    }

    public static void loadDummyExpenses(Context context) {
        if (!expenses.isEmpty()) {
            return;
        }

        addExpense(context, new Expense("Lunch", "Food", 12.50, "2026-03-23"));
        addExpense(context, new Expense("Bus Ticket", "Transport", 2.40, "2026-03-23"));
        addExpense(context, new Expense("Coffee", "Food", 4.20, "2026-03-22"));
        addExpense(context, new Expense("Notebook", "Study", 6.99, "2026-03-21"));
    }
}
package com.example.expense_tracker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.expense_tracker.R;
import com.example.expense_tracker.DataManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StatisticsActivity extends AppCompatActivity {

    TextView tvTotalExpense, tvTodayExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tvTotalExpense = findViewById(R.id.tvTotalExpense);
        tvTodayExpense = findViewById(R.id.tvTodayExpense);

        calculateExpenses();
    }

    private void calculateExpenses() {
        List<String> expenses = DataManager.expenses;

        double total = 0;
        double todayTotal = 0;

        String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(new Date());

        for (String expense : expenses) {
            String[] parts = expense.split(" - ");

            if (parts.length == 4) {
                double amount = Double.parseDouble(parts[2]);
                String date = parts[3];

                total += amount;

                if (date.equals(todayDate)) {
                    todayTotal += amount;
                }
            }
        }

        tvTotalExpense.setText("Total Expense: €" + total);
        tvTodayExpense.setText("Today's Expense: €" + todayTotal);
    }
}
package com.example.expense_tracker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.example.expense_tracker.R;
import com.example.expense_tracker.DataManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;

public class StatisticsActivity extends AppCompatActivity {

    TextView tvTotalExpense, tvTodayExpense, tvCategorySummary;
    Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tvTotalExpense = findViewById(R.id.tvTotalExpense);
        tvTodayExpense = findViewById(R.id.tvTodayExpense);
        tvCategorySummary = findViewById(R.id.tvCategorySummary);
        btnHome = findViewById(R.id.btnHome);


        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(StatisticsActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        calculateExpenses();
    }

    private void calculateExpenses() {
        List<String> expenses = DataManager.expenses;

        double total = 0;
        double todayTotal = 0;

        Map<String, Double> categoryTotals = new HashMap<>();

        String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(new Date());

        for (String expense : expenses) {
            String[] parts = expense.split(" - ");

            if (parts.length == 4) {
                try {
                    String category = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    String date = parts[3];

                    total += amount;

                    if (date.equals(todayDate)) {
                        todayTotal += amount;
                    }

                    categoryTotals.put(category,
                            categoryTotals.getOrDefault(category, 0.0) + amount);

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        tvTotalExpense.setText("Total Expense: €" + total);
        tvTodayExpense.setText("Today's Expense: €" + todayTotal);

        displayCategorySummary(categoryTotals);
    }

    private void displayCategorySummary(Map<String, Double> categoryTotals) {
        StringBuilder result = new StringBuilder();

        for (String category : categoryTotals.keySet()) {
            double amount = categoryTotals.get(category);
            result.append(category)
                    .append(": €")
                    .append(amount)
                    .append("\n");
        }

        tvCategorySummary.setText(result.toString());
    }
}

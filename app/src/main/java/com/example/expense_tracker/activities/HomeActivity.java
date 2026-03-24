package com.example.expense_tracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expense_tracker.R;

public class HomeActivity extends AppCompatActivity {

    Button btnViewExpenses, btnAddExpense, btnStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnViewExpenses = findViewById(R.id.btnViewExpenses);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnStatistics = findViewById(R.id.btnStatistics);

        btnViewExpenses.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ExpenseListActivity.class);
            startActivity(intent);
        });

        btnAddExpense.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });

        btnStatistics.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, StatisticsActivity.class);
            startActivity(intent);
        });
    }
}
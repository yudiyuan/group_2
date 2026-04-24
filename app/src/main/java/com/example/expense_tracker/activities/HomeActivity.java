package com.example.expense_tracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expense_tracker.R;

public class HomeActivity extends AppCompatActivity {

    Button btnViewExpenses, btnAddExpense, btnStatistics, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnViewExpenses = findViewById(R.id.btnViewExpenses);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnStatistics = findViewById(R.id.btnStatistics);
        btnLogout = findViewById(R.id.btnLogout);

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


        btnLogout.setOnClickListener(v -> {
            getSharedPreferences("UserPrefs", MODE_PRIVATE)
                    .edit()
                    .putBoolean("is_logged_in", false)
                    .remove("logged_in_user")
                    .apply();

            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}
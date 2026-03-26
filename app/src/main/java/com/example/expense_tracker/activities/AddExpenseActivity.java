package com.example.expense_tracker.activities;


import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expense_tracker.R;

public class AddExpenseActivity extends AppCompatActivity {

    EditText etTitle, etCategory, etAmount, etDate;
    Button btnSaveExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        etTitle = findViewById(R.id.etTitle);
        etCategory = findViewById(R.id.etCategory);
        etAmount = findViewById(R.id.etAmount);
        etDate = findViewById(R.id.etDate);
        btnSaveExpense = findViewById(R.id.btnSaveExpense);

        btnSaveExpense.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String category = etCategory.getText().toString().trim();
            String amount = etAmount.getText().toString().trim();
            String date = etDate.getText().toString().trim();

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(category)
                    || TextUtils.isEmpty(amount) || TextUtils.isEmpty(date)) {
                Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            } else {

                String expense = title + " - " + category + " - " + amount + " - " + date;

                com.example.expense_tracker.DataManager.expenses.add(expense);

                Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddExpenseActivity.this, ExpenseListActivity.class);
                startActivity(intent);

            }
        });
        Button btnHome = findViewById(R.id.btnHome);

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(AddExpenseActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

    }
}
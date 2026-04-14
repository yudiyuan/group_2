package com.example.expense_tracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expense_tracker.DataManager;
import com.example.expense_tracker.R;
import com.example.expense_tracker.model.Expense;

public class AddExpenseActivity extends AppCompatActivity {

    EditText etTitle, etAmount, etDate;
    Spinner spCategory;
    Button btnSaveExpense;

    String[] categories = {"Food", "Transport", "Shopping", "Utilities", "Study", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        etTitle = findViewById(R.id.etTitle);
        spCategory = findViewById(R.id.spCategory);
        etAmount = findViewById(R.id.etAmount);
        etDate = findViewById(R.id.etDate);
        btnSaveExpense = findViewById(R.id.btnSaveExpense);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        btnSaveExpense.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String category = spCategory.getSelectedItem().toString();
            String amountText = etAmount.getText().toString().trim();
            String date = etDate.getText().toString().trim();

            if (TextUtils.isEmpty(title)
                    || TextUtils.isEmpty(amountText)
                    || TextUtils.isEmpty(date)) {
                Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountText);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                return;
            }

            Expense expense = new Expense(title, category, amount, date);
            DataManager.addExpense(this, expense);

            Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AddExpenseActivity.this, ExpenseListActivity.class);
            startActivity(intent);
            finish();
        });

        Button btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(AddExpenseActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}
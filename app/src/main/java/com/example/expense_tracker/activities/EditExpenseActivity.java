package com.example.expense_tracker.activities;

import android.app.DatePickerDialog;
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

import java.util.Calendar;
import java.util.Locale;

public class EditExpenseActivity extends AppCompatActivity {

    EditText etTitle, etAmount, etDate;
    Spinner spCategory;
    Button btnUpdateExpense;

    Expense expense;

    String[] categories = {"Food", "Transport", "Shopping", "Utilities", "Study", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        etTitle = findViewById(R.id.etTitle);
        spCategory = findViewById(R.id.spCategory);
        etAmount = findViewById(R.id.etAmount);
        etDate = findViewById(R.id.etDate);
        btnUpdateExpense = findViewById(R.id.btnUpdateExpense);

        etDate.setFocusable(false);
        etDate.setClickable(true);
        etDate.setOnClickListener(v -> showDatePicker());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        int expenseId = getIntent().getIntExtra("expense_id", -1);
        expense = DataManager.getExpenseById(expenseId);

        if (expense != null) {
            etTitle.setText(expense.getTitle());
            etAmount.setText(String.valueOf(expense.getAmount()));
            etDate.setText(expense.getDate());

            for (int i = 0; i < categories.length; i++) {
                if (categories[i].equals(expense.getCategory())) {
                    spCategory.setSelection(i);
                    break;
                }
            }
        }

        btnUpdateExpense.setOnClickListener(v -> {
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

            if (expense != null) {
                Expense updatedExpense = new Expense(
                        expense.getId(), title, category, amount, date
                );
                DataManager.updateExpense(this, updatedExpense);

                Toast.makeText(this, "Expense updated", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EditExpenseActivity.this, ExpenseListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String currentDateText = etDate.getText().toString();
        if (!TextUtils.isEmpty(currentDateText)) {
            try {
                String[] parts = currentDateText.split("-");
                if (parts.length == 3) {
                    year = Integer.parseInt(parts[0]);
                    month = Integer.parseInt(parts[1]) - 1;
                    day = Integer.parseInt(parts[2]);
                }
            } catch (Exception ignored) {
            }
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    etDate.setText(formattedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}
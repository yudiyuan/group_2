package com.example.expense_tracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expense_tracker.DataManager;
import com.example.expense_tracker.R;
import com.example.expense_tracker.model.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseListActivity extends AppCompatActivity {

    ListView listViewExpenses;
    Button btnBackHome, btnAddExpense, btnSort;
    TextView tvNoExpenses;
    Spinner categorySpinner;

    ArrayAdapter<String> adapter;
    List<Expense> displayedExpenses = new ArrayList<>();
    String currentCategory = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        listViewExpenses = findViewById(R.id.listViewExpenses);
        btnBackHome = findViewById(R.id.btnBackHome);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnSort = findViewById(R.id.btnSort);
        tvNoExpenses = findViewById(R.id.tvNoExpenses);
        categorySpinner = findViewById(R.id.categorySpinner);

        setupSpinner();

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(ExpenseListActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        btnAddExpense.setOnClickListener(v -> {
            startActivity(new Intent(ExpenseListActivity.this, AddExpenseActivity.class));
        });

        btnSort.setOnClickListener(v -> {
            List<Expense> allExpenses = DataManager.getAllExpenses();
            allExpenses.sort((e1, e2) -> Double.compare(e1.getAmount(), e2.getAmount()));
            applyFilter(currentCategory);
            Toast.makeText(this, "Sorted by amount", Toast.LENGTH_SHORT).show();
        });

        listViewExpenses.setOnItemClickListener((parent, view, position, id) -> {
            Expense selectedExpense = displayedExpenses.get(position);

            Intent intent = new Intent(ExpenseListActivity.this, EditExpenseActivity.class);
            intent.putExtra("expense_id", selectedExpense.getId());
            startActivity(intent);
        });

        listViewExpenses.setOnItemLongClickListener((parent, view, position, id) -> {
            Expense selectedExpense = displayedExpenses.get(position);

            new android.app.AlertDialog.Builder(this)
                    .setTitle("Delete Expense")
                    .setMessage("Are you sure you want to delete this expense?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        DataManager.deleteExpense(this, selectedExpense.getId());
                        applyFilter(currentCategory);
                        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

            return true;
        });

        applyFilter(currentCategory);
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyFilter(currentCategory);
    }

    private void setupSpinner() {
        String[] categories = {"All", "Food", "Transport", "Shopping", "Utilities", "Study"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        categorySpinner.setSelection(0);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentCategory = categories[position];
                applyFilter(currentCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void applyFilter(String category) {
        List<Expense> allExpenses = DataManager.getAllExpenses();
        displayedExpenses.clear();

        if ("All".equals(category)) {
            displayedExpenses.addAll(allExpenses);
        } else {
            for (Expense expense : allExpenses) {
                if (expense.getCategory() != null &&
                        expense.getCategory().equalsIgnoreCase(category)) {
                    displayedExpenses.add(expense);
                }
            }
        }

        updateListView();
    }

    private void updateListView() {
        List<String> displayList = new ArrayList<>();

        for (Expense e : displayedExpenses) {
            String item = e.getTitle() + " | "
                    + e.getCategory() + " | €"
                    + e.getAmount() + " | "
                    + e.getDate();
            displayList.add(item);
        }

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                displayList
        );

        listViewExpenses.setAdapter(adapter);

        if (displayedExpenses.isEmpty()) {
            tvNoExpenses.setVisibility(View.VISIBLE);
            listViewExpenses.setVisibility(View.GONE);
        } else {
            tvNoExpenses.setVisibility(View.GONE);
            listViewExpenses.setVisibility(View.VISIBLE);
        }
    }
}
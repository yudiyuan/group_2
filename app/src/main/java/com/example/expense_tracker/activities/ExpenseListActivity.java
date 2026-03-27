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

import androidx.appcompat.app.AppCompatActivity;

import com.example.expense_tracker.DataManager;
import com.example.expense_tracker.R;

import java.util.ArrayList;
import java.util.List;

public class ExpenseListActivity extends AppCompatActivity {

    ListView listViewExpenses;
    Button btnBackHome;
    Spinner categorySpinner;
    TextView tvNoExpenses;

    List<String> displayedExpenses = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        listViewExpenses = findViewById(R.id.listViewExpenses);
        btnBackHome = findViewById(R.id.btnBackHome);
        categorySpinner = findViewById(R.id.categorySpinner);
        tvNoExpenses = findViewById(R.id.tvNoExpenses);

        displayedExpenses.clear();
        if (DataManager.expenses != null) {
            displayedExpenses.addAll(DataManager.expenses);
        }

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                displayedExpenses
        );
        listViewExpenses.setAdapter(adapter);

        setupSpinner();

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(ExpenseListActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void setupSpinner() {
        String[] categories = {"All", "Food", "Transport", "Shopping", "Utilities"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories
        );
        categorySpinner.setAdapter(spinnerAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterList(categories[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void filterList(String category) {
        displayedExpenses.clear();

        if (DataManager.expenses != null) {
            if ("All".equals(category)) {
                displayedExpenses.addAll(DataManager.expenses);
            } else {
                for (String expense : DataManager.expenses) {
                    if (expense.contains(category)) {
                        displayedExpenses.add(expense);
                    }
                }
            }
        }

        adapter.notifyDataSetChanged();

        if (displayedExpenses.isEmpty()) {
            tvNoExpenses.setVisibility(View.VISIBLE);
            listViewExpenses.setVisibility(View.GONE);
        } else {
            tvNoExpenses.setVisibility(View.GONE);
            listViewExpenses.setVisibility(View.VISIBLE);
        }
    }
}
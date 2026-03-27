package com.example.expense_tracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
import java.util.Collections;
import java.util.List;

public class ExpenseListActivity extends AppCompatActivity {

    ListView listViewExpenses;
    Button btnBackHome, btnSort, btnAddExpense;
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
        btnSort = findViewById(R.id.btnSort);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        categorySpinner = findViewById(R.id.categorySpinner);
        tvNoExpenses = findViewById(R.id.tvNoExpenses);

        if (DataManager.expenses == null) {
            DataManager.expenses = new ArrayList<>();
        }
        displayedExpenses.clear();
        displayedExpenses.addAll(DataManager.expenses);

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

        btnAddExpense.setOnClickListener(v -> {
            startActivity(new Intent(ExpenseListActivity.this, AddExpenseActivity.class));
        });

        btnSort.setOnClickListener(v -> {
            Collections.sort(DataManager.expenses, (e1, e2) -> {
                String[] p1 = e1.split(" - ");
                String[] p2 = e2.split(" - ");
                if (p1.length >= 3 && p2.length >= 3) {
                    double a1 = Double.parseDouble(p1[2].replace("€", "").trim());
                    double a2 = Double.parseDouble(p2[2].replace("€", "").trim());
                    return Double.compare(a1, a2);
                }
                return 0;
            });
            String currentCategory = categorySpinner.getSelectedItem().toString();
            filterList(currentCategory);
        });

        setupSwipeToDelete();
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
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void filterList(String category) {
        displayedExpenses.clear();

        if ("All".equals(category)) {
            displayedExpenses.addAll(DataManager.expenses);
        } else {
            for (String expense : DataManager.expenses) {
                if (expense.contains(category)) {
                    displayedExpenses.add(expense);
                }
            }
        }

        adapter.notifyDataSetChanged();
        updateEmptyState();
    }

    private void updateEmptyState() {
        if (displayedExpenses.isEmpty()) {
            tvNoExpenses.setVisibility(View.VISIBLE);
            listViewExpenses.setVisibility(View.GONE);
        } else {
            tvNoExpenses.setVisibility(View.GONE);
            listViewExpenses.setVisibility(View.VISIBLE);
        }
    }

    private void setupSwipeToDelete() {
        GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    int position = listViewExpenses.pointToPosition((int) e1.getX(), (int) e1.getY());
                    if (position != ListView.INVALID_POSITION) {
                        String itemToDelete = displayedExpenses.get(position);
                        DataManager.expenses.remove(itemToDelete);
                        displayedExpenses.remove(position);

                        adapter.notifyDataSetChanged();
                        updateEmptyState();
                    }
                    return true;
                }
                return false;
            }
        });

        listViewExpenses.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }
}
package com.example.expense_tracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expense_tracker.DataManager;
import com.example.expense_tracker.R;
import android.view.View;

public class ExpenseListActivity extends AppCompatActivity {

    ListView listViewExpenses;
    Button btnBackHome, btnSort, btnAddExpense;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        listViewExpenses = findViewById(R.id.listViewExpenses);
        btnBackHome = findViewById(R.id.btnBackHome);
        btnSort = findViewById(R.id.btnSort);
        btnAddExpense = findViewById(R.id.addExpense);

        TextView tvNoExpenses = findViewById(R.id.tvNoExpenses);

        if (DataManager.expenses == null) {
            DataManager.expenses = new java.util.ArrayList<>();
        }

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                DataManager.expenses
        );

        listViewExpenses.setAdapter(adapter);

        if (DataManager.expenses.isEmpty()) {
            tvNoExpenses.setVisibility(View.VISIBLE);
            listViewExpenses.setVisibility(View.GONE);
        } else {
            tvNoExpenses.setVisibility(View.GONE);
            listViewExpenses.setVisibility(View.VISIBLE);
        }

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(ExpenseListActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        btnSort.setOnClickListener(v -> {
            java.util.Collections.sort(DataManager.expenses, (e1, e2) -> {
                String[] p1 = e1.split(" - ");
                String[] p2 = e2.split(" - ");
                double a1 = Double.parseDouble(p1[2]);
                double a2 = Double.parseDouble(p2[2]);
                return Double.compare(a1, a2);
            });
            adapter.notifyDataSetChanged();
        });

        btnAddExpense.setOnClickListener(v -> {
            Intent intent = new Intent(ExpenseListActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });

        android.view.GestureDetector gestureDetector = new android.view.GestureDetector(
                this,
                new android.view.GestureDetector.SimpleOnGestureListener() {

                    private static final int SWIPE_THRESHOLD = 100;
                    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

                    @Override
                    public boolean onFling(android.view.MotionEvent e1, android.view.MotionEvent e2,
                                           float velocityX, float velocityY) {

                        float diffX = e2.getX() - e1.getX();

                        if (Math.abs(diffX) > SWIPE_THRESHOLD &&
                                Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {

                            int position = listViewExpenses.pointToPosition(
                                    (int) e1.getX(),
                                    (int) e1.getY()
                            );

                            if (position != ListView.INVALID_POSITION) {
                                DataManager.expenses.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                            return true;
                        }
                        return false;
                    }
                }
        );

        listViewExpenses.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }
}

package com.example.expense_tracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
    Button btnBackHome, btnAddExpense;
    TextView tvNoExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        listViewExpenses = findViewById(R.id.listViewExpenses);
        btnBackHome = findViewById(R.id.btnBackHome);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        tvNoExpenses = findViewById(R.id.tvNoExpenses);

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(ExpenseListActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        btnAddExpense.setOnClickListener(v -> {
            startActivity(new Intent(ExpenseListActivity.this, AddExpenseActivity.class));
        });

        loadExpenses();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadExpenses();
    }

    private void loadExpenses() {
        List<Expense> expenseList = DataManager.getAllExpenses();
        List<String> displayList = new ArrayList<>();

        for (Expense e : expenseList) {
            String item = e.getTitle() + " | "
                    + e.getCategory() + " | €"
                    + e.getAmount() + " | "
                    + e.getDate();
            displayList.add(item);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                displayList
        );

        listViewExpenses.setAdapter(adapter);

        listViewExpenses.setOnItemClickListener((parent, view, position, id) -> {
            Expense selectedExpense = expenseList.get(position);

            Intent intent = new Intent(ExpenseListActivity.this, EditExpenseActivity.class);
            intent.putExtra("expense_id", selectedExpense.getId());
            startActivity(intent);
        });

        listViewExpenses.setOnItemLongClickListener((parent, view, position, id) -> {
            Expense selectedExpense = expenseList.get(position);

            DataManager.deleteExpense(selectedExpense.getId());
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();

            loadExpenses();
            return true;
        });

        if (expenseList.isEmpty()) {
            tvNoExpenses.setVisibility(View.VISIBLE);
            listViewExpenses.setVisibility(View.GONE);
        } else {
            tvNoExpenses.setVisibility(View.GONE);
            listViewExpenses.setVisibility(View.VISIBLE);
        }
    }
}
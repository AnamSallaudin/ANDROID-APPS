package com.example.foodorderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CheckBox checkboxBurger, checkboxPizza, checkboxFries;
    private Spinner spinnerBurgerQuantity, spinnerPizzaQuantity, spinnerFriesQuantity;
    private Button buttonViewCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize checkboxes
        checkboxBurger = findViewById(R.id.checkbox_burger);
        checkboxPizza = findViewById(R.id.checkbox_pizza);
        checkboxFries = findViewById(R.id.checkbox_fries);

        // Initialize spinners
        spinnerBurgerQuantity = findViewById(R.id.spinner_burger_quantity);
        spinnerPizzaQuantity = findViewById(R.id.spinner_pizza_quantity);
        spinnerFriesQuantity = findViewById(R.id.spinner_fries_quantity);

        // Populate spinners with quantity options (1-5)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.quantity_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerBurgerQuantity.setAdapter(adapter);
        spinnerPizzaQuantity.setAdapter(adapter);
        spinnerFriesQuantity.setAdapter(adapter);

        // Initialize "View Cart" button
        buttonViewCart = findViewById(R.id.button_view_cart);

        // Set click listener for "View Cart" button
        buttonViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> selectedItems = new ArrayList<>();
                int totalCost = 0;

                if (checkboxBurger.isChecked()) {
                    int quantity = Integer.parseInt(spinnerBurgerQuantity.getSelectedItem().toString());
                    selectedItems.add("Burger x " + quantity + " - $" + (10 * quantity));
                    totalCost += 10 * quantity;
                }
                if (checkboxPizza.isChecked()) {
                    int quantity = Integer.parseInt(spinnerPizzaQuantity.getSelectedItem().toString());
                    selectedItems.add("Pizza x " + quantity + " - $" + (15 * quantity));
                    totalCost += 15 * quantity;
                }
                if (checkboxFries.isChecked()) {
                    int quantity = Integer.parseInt(spinnerFriesQuantity.getSelectedItem().toString());
                    selectedItems.add("Fries x " + quantity + " - $" + (5 * quantity));
                    totalCost += 5 * quantity;
                }

                // Pass the selected items and total cost to the Cart Activity
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                intent.putStringArrayListExtra("selectedItems", selectedItems);
                intent.putExtra("totalCost", totalCost);
                startActivity(intent);
            }
        });
    }
}

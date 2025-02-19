package com.example.foodorderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private TextView cartItemsTextView, totalCostTextView;
    private Button buttonBackToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartItemsTextView = findViewById(R.id.cart_items);
        totalCostTextView = findViewById(R.id.total_cost);
        buttonBackToMenu = findViewById(R.id.button_back_to_menu);

        // Get the data from the intent
        Intent intent = getIntent();
        ArrayList<String> selectedItems = intent.getStringArrayListExtra("selectedItems");
        int totalCost = intent.getIntExtra("totalCost", 0);

        // Display the selected items
        if (selectedItems != null && !selectedItems.isEmpty()) {
            StringBuilder itemsText = new StringBuilder();
            for (String item : selectedItems) {
                itemsText.append(item).append("\n");
            }
            cartItemsTextView.setText(itemsText.toString());
        } else {
            cartItemsTextView.setText("No items selected");
        }

        // Display the total cost
        totalCostTextView.setText("Total Cost: $" + totalCost);

        // Handle the "Back to Menu" button
        buttonBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Return to the previous activity
            }
        });
    }
}

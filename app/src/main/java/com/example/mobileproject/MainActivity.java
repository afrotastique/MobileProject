// MainActivity.java
package com.example.mobileproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements RestaurantDialogFragment.OnRestaurantSaveListener {

    private RecyclerView recyclerView;
    private RestaurantAdapter restaurantAdapter;
    private RestaurantManager restaurantManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RestaurantManager singleton
        restaurantManager = RestaurantManager.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the RestaurantAdapter with the OnItemClickListener and MainActivity as OnRestaurantSaveListener
        restaurantAdapter = new RestaurantAdapter(restaurantManager.getRestaurantList(), new RestaurantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Restaurant restaurant) {
                // Handle item click, navigate to the details screen
                Intent intent = new Intent(MainActivity.this, RestaurantDetailsActivity.class);
                intent.putExtra("restaurant", restaurant);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(restaurantAdapter);

        //Add, edit, and remove buttons
        Button btnAddRestaurant = findViewById(R.id.btnAddRestaurant);
        Button btnEditRestaurant = findViewById(R.id.btnEditRestaurant);
        Button btnRemoveRestaurant = findViewById(R.id.btnRemoveRestaurant);

        btnAddRestaurant.setOnClickListener(v -> addRestaurant());
        btnEditRestaurant.setOnClickListener(v -> editRestaurant());
        btnRemoveRestaurant.setOnClickListener(v -> removeRestaurant());

        // About button screen
        Button btnAbout = findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }

    private void addRestaurant() {
        // Show the dialog for adding a new restaurant
        showAddEditDialog(null);
    }

    private void editRestaurant() {
        // Check if a restaurant is selected
        int selectedPosition = restaurantAdapter.getSelectedPosition();
        if (selectedPosition != RecyclerView.NO_POSITION) {
            // Show the dialog for editing the selected restaurant
            showAddEditDialog(restaurantManager.getRestaurantList().get(selectedPosition));
        }
    }

    private void removeRestaurant() {
        // Check if a restaurant is selected
        int selectedPosition = restaurantAdapter.getSelectedPosition();
        if (selectedPosition != RecyclerView.NO_POSITION) {
            // Remove the selected restaurant
            restaurantManager.removeRestaurant(selectedPosition);
            // Notify the adapter about the change
            restaurantAdapter.notifyDataSetChanged();
        }
    }

    private void showAddEditDialog(Restaurant restaurant) {
        // Implement the logic to show a dialog for adding/editing a restaurant
        // You may use AlertDialog, BottomSheetDialog, or start a new activity
        // In this example, we will use a simple AlertDialog
        RestaurantDialogFragment dialogFragment = RestaurantDialogFragment.newInstance(restaurant);
        dialogFragment.setOnRestaurantSaveListener(this);
        dialogFragment.show(getSupportFragmentManager(), "RestaurantDialog");
    }

    @Override
    public void onSave(Restaurant newRestaurant) {
        // Callback method from the dialog to handle the saved restaurant
        if (restaurantAdapter.getSelectedPosition() != RecyclerView.NO_POSITION) {
            // Edit existing restaurant
            restaurantManager.editRestaurant(restaurantAdapter.getSelectedPosition(), newRestaurant);
        } else {
            // Add new restaurant
            restaurantManager.addRestaurant(newRestaurant);
        }
        // Notify the adapter about the change
        restaurantAdapter.notifyDataSetChanged();
    }
}




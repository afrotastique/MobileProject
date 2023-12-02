// MainActivity.java
package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RestaurantAdapter restaurantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dummy data for testing
        List<Restaurant> dummyData = createDummyData();

        // Set up the RestaurantAdapter with the OnItemClickListener
        restaurantAdapter = new RestaurantAdapter(dummyData, new RestaurantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Restaurant restaurant) {
                // Handle item click, navigate to the details screen
                Intent intent = new Intent(MainActivity.this, RestaurantDetailsActivity.class);
                intent.putExtra("restaurant", restaurant);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(restaurantAdapter);
    }

    // Dummy data for testing
    private List<Restaurant> createDummyData() {
        List<Restaurant> dummyData = new ArrayList<>();
        dummyData.add(new Restaurant("Restaurant 1", "Address 1", 4.0f));
        dummyData.add(new Restaurant("Restaurant 2", "Address 2", 3.5f));
        // Add more dummy data as needed
        return dummyData;
    }
}


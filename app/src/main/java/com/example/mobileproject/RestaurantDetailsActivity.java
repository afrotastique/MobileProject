// RestaurantDetailsActivity.java
package com.example.mobileproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RestaurantDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        // Retrieve restaurant details from Intent
        Restaurant restaurant = getIntent().getParcelableExtra("restaurant");

        // Set up views
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewAddress = findViewById(R.id.textViewAddress);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button btnViewMap = findViewById(R.id.btnViewMap);
        Button btnShareEmail = findViewById(R.id.btnShareEmail);
        Button btnShareFacebook = findViewById(R.id.btnShareFacebook);
        Button btnShareTwitter = findViewById(R.id.btnShareTwitter);

        // Populate views with restaurant details
        if (restaurant != null) {
            textViewName.setText(restaurant.getName());
            textViewAddress.setText(restaurant.getAddress());
            ratingBar.setRating(restaurant.getRating());
        }

        // Implement the click listener for the "View Map" button
        btnViewMap.setOnClickListener(v -> {
            // Implement the logic to view the map
            // For example, launch a map application with the restaurant's location
            String mapUri = "geo:0,0?q=" + Uri.encode(restaurant.getAddress());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUri));
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        // Implement the click listener for the "Share via Email" button
        btnShareEmail.setOnClickListener(v -> {
            // Implement the logic to share via email
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Restaurant Recommendation");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Check out this restaurant:\n\n" +
                    "Name: " + restaurant.getName() + "\n" +
                    "Address: " + restaurant.getAddress() + "\n" +
                    "Rating: " + restaurant.getRating());
            startActivity(Intent.createChooser(emailIntent, "Send email"));
        });

        // Implement the click listener for the "Share on Facebook" button
        btnShareFacebook.setOnClickListener(v -> {
            // Implement the logic to share on Facebook
            // You can use the Facebook SDK or open the Facebook app
            // with pre-filled content (if the app is installed)
        });

        // Implement the click listener for the "Share on Twitter" button
        btnShareTwitter.setOnClickListener(v -> {
            // Implement the logic to share on Twitter
            // You can use the Twitter SDK or open the Twitter app
            // with pre-filled content (if the app is installed)
        });
    }
}


// RestaurantDetailsActivity.java
package com.example.mobileproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageButton;
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
        TextView textViewPhone = findViewById(R.id.textViewPhone);
        TextView textViewDescription = findViewById(R.id.textViewDescription);
        TextView textViewTags = findViewById(R.id.textViewTags);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button btnViewMap = findViewById(R.id.btnViewMap);
        ImageButton btnShareEmail = findViewById(R.id.btnShareEmail);
        ImageButton btnShareFacebook = findViewById(R.id.btnShareFacebook);
        ImageButton btnShareTwitter = findViewById(R.id.btnShareTwitter);

        // Populate views with restaurant details
        if (restaurant != null) {
            final String phoneNumber = restaurant.getPhone();

            textViewName.setText(restaurant.getName());
            textViewAddress.setText(restaurant.getAddress());
            textViewPhone.setText("Phone: " + restaurant.getPhone());
            textViewDescription.setText("Description: " + restaurant.getDescription());
            textViewTags.setText("Tags: " + TextUtils.join(", ", restaurant.getTags()));
            ratingBar.setRating((float) restaurant.getRating());

            textViewPhone.setOnClickListener(v -> dialPhoneNumber(phoneNumber));
        }




        // "View Map" button
        btnViewMap.setOnClickListener(v -> {
            String mapUri = "geo:0,0?q=" + Uri.encode(restaurant.getAddress());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUri));
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        // "Share via Email" button
        btnShareEmail.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Restaurant Recommendation");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Check out this restaurant:\n\n" +
                    "Name: " + restaurant.getName() + "\n" +
                    "Address: " + restaurant.getAddress() + "\n" +
                    "Rating: " + restaurant.getRating());
            startActivity(Intent.createChooser(emailIntent, "Send email"));
        });


        // "Share on Facebook" button
        btnShareFacebook.setOnClickListener(v -> {

        });

        //"Share on Twitter" button
        btnShareTwitter.setOnClickListener(v -> {

        });
    }

    // dialPhoneNumber method
    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}


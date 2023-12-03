// RestaurantDialogFragment.java
package com.example.mobileproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Arrays;

public class RestaurantDialogFragment extends DialogFragment {

    private static final String ARG_RESTAURANT = "restaurant";

    private Restaurant restaurant;
    private EditText etName, etAddress, etPhone, etDescription, etTags;
    private RatingBar ratingBar;
    private OnRestaurantSaveListener onRestaurantSaveListener;

    public interface OnRestaurantSaveListener {
        void onSave(Restaurant newRestaurant);
    }

    public void setOnRestaurantSaveListener(OnRestaurantSaveListener listener) {
        this.onRestaurantSaveListener = listener;
    }

    public static RestaurantDialogFragment newInstance(Restaurant restaurant) {
        RestaurantDialogFragment fragment = new RestaurantDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RESTAURANT, restaurant);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurant = getArguments().getParcelable(ARG_RESTAURANT);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_edit_restaurant, null);

        etName = view.findViewById(R.id.etName);
        etAddress = view.findViewById(R.id.etAddress);
        etPhone = view.findViewById(R.id.etPhone);
        etDescription = view.findViewById(R.id.etDescription);
        etTags = view.findViewById(R.id.etTags);
        ratingBar = view.findViewById(R.id.ratingBar);

        // Populate fields if editing an existing restaurant
        if (restaurant != null) {
            etName.setText(restaurant.getName());
            etAddress.setText(restaurant.getAddress());
            etPhone.setText(restaurant.getPhone());
            etDescription.setText(restaurant.getDescription());
            etTags.setText(TextUtils.join(", ", restaurant.getTags()));
            ratingBar.setRating(restaurant.getRating());
        }

        builder.setView(view)
                .setTitle(restaurant == null ? "Add Restaurant" : "Edit Restaurant")
                .setPositiveButton("Save", (dialog, which) -> saveRestaurant())
                .setNegativeButton("Cancel", (dialog, which) -> dismiss());

        return builder.create();
    }

    private void saveRestaurant() {
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String tags = etTags.getText().toString().trim();
        float rating = ratingBar.getRating();

        // Validation
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(address)) {
            // Show an error message if required fields are empty
            etName.setError("Required");
            etAddress.setError("Required");
        } else {
            // Create a new restaurant with the entered details
            Restaurant newRestaurant = new Restaurant(name, address, phone, description, Arrays.asList(tags.split("\\s*,\\s*")), rating);

            // Notify the listener
            if (onRestaurantSaveListener != null) {
                onRestaurantSaveListener.onSave(newRestaurant);
            }

            // Dismiss the dialog
            dismiss();
        }
    }
}


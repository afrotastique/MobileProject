// RestaurantAdapter.java
package com.example.mobileproject;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurantList;
    private OnItemClickListener onItemClickListener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public interface OnItemClickListener {
        void onItemClick(Restaurant restaurant);
    }

    public RestaurantAdapter(List<Restaurant> restaurantList, OnItemClickListener onItemClickListener) {
        this.restaurantList = restaurantList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.bind(restaurant);

        // Set the background color based on the selected position
        holder.itemView.setBackgroundResource(selectedPosition == position ? R.color.colorSelected : android.R.color.transparent);

        holder.itemView.setOnClickListener(v -> {
            // Update the selected position
            selectedPosition = position;
            onItemClickListener.onItemClick(restaurant);
            notifyDataSetChanged(); // Notify the adapter about the change
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewAddress;
        private TextView textViewPhone;

        RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
        }

        void bind(Restaurant restaurant) {
            textViewName.setText(restaurant.getName());
            textViewAddress.setText(restaurant.getAddress());
            textViewPhone.setText(restaurant.getPhone());

            textViewPhone.setOnClickListener(v -> {
                // Create an Intent to dial the phone number
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + restaurant.getPhone()));
                if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                    v.getContext().startActivity(intent);
                }
            });

        }

    }
}

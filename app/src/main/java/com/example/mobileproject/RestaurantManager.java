package com.example.mobileproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestaurantManager {
    private List<Restaurant> restaurantList;
    private static RestaurantManager instance;

    private RestaurantManager() {
        // Initialize the list of restaurants
        restaurantList = new ArrayList<>();
        // Add some dummy data for testing
        restaurantList.add(new Restaurant("Restaurant 1", "Address 1", "555-555-5555",
                "test description", Arrays.asList("Vegetarian", "Italian"), 4.0f));
        restaurantList.add(new Restaurant("Restaurant 2", "Address 2","555-555-5555",
                "test description", Arrays.asList("Vegetarian", "Italian"), 3.5f));
        // Add more initialization logic if needed
    }

    public static RestaurantManager getInstance() {
        if (instance == null) {
            instance = new RestaurantManager();
        }
        return instance;
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurantList.add(restaurant);
    }

    public void editRestaurant(int position, Restaurant restaurant) {
        if (position >= 0 && position < restaurantList.size()) {
            restaurantList.set(position, restaurant);
        }
    }

    public void removeRestaurant(int position) {
        if (position >= 0 && position < restaurantList.size()) {
            restaurantList.remove(position);
        }
    }
}
package ca.ece.ubc.cpen221.mp5;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

// TODO: Use this class to represent a restaurant.
// State the rep invariant and abs

public class Restaurant {
    private int price;
    private String name;
    private String businessID;
    private ArrayList<String> categories;
    private double rating;
    
    /**
     * Create a restaurant object.
     * @param price
     * @param name
     * @param businessID
     * @param categories
     * @param rating
     */
    public Restaurant(int price, String name, String businessID, JSONArray categories, double rating){
        this.price = price;
        this.name = name;
        this.businessID = businessID;
        this.rating = rating;
        
        for (int i = 0; i < categories.size(); i++){
            this.categories.add(categories.get(i).toString());
        }
    }

}

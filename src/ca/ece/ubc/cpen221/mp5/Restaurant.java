package ca.ece.ubc.cpen221.mp5;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

// TODO: Use this class to represent a restaurant.
// State the rep invariant and abs

public class Restaurant {
    final private JSONObject restoJSON;
   
    
    final private double[] location = { 0, 0 };
    final private Set<String> neighbourhoods = new HashSet<String>();
    final private String businessID;
    
    final private String name;
    final private Set<String> categories = new HashSet<String>();
    final private String type;
    
    private double rating;
    final private long price;
    
    JSONObject errorMsg = new JSONObject(); //for invalid query

    
    /**
     * Create a restaurant object.
     * 
     */
    public Restaurant(JSONObject obj){
        this.restoJSON = (JSONObject) obj.clone();
        
        this.businessID=this.restoJSON.get("business_id").toString();
        
        JSONArray neighbourhoods=(JSONArray) this.restoJSON.get("neighborhoods");
        for(Object object:neighbourhoods){
            this.neighbourhoods.add(object.toString());
        }
        
        this.name=this.restoJSON.get("name").toString();
        this.type=this.restoJSON.get("type").toString();
        
        JSONArray categories=(JSONArray) this.restoJSON.get("neighborhoods");
        for(Object object:categories){
            this.categories.add(object.toString());
        }

        
        this.rating= (Double) this.restoJSON.get("stars");
        this.price=(long) this.restoJSON.get("price");
        
        
    }
    
    /**
     * Create an error restaurant restaurant object.
     * 
     */
    public Restaurant(){
        errorMsg.put("Error Message", "Invalid Query");
        
        this.restoJSON =null;
        this.businessID="";
        this.name="";
        this.type="";
        this.rating=0.0;
        this.price=0;
    }
    
    /**
     * Returns the restaurant details in JSON format
     * 
     * @return the restaurant details in JSON format
     */
    public String getJSONDetails() {
        return this.restoJSON.toJSONString();
    }
    
    /**
     * Returns the restaurant's business ID in JSON format
     * 
     * @return the restaurant's business ID  in JSON format
     */
    public String getBusinessID() {
        return this.businessID;
    }
    
    /**
     * Returns the restaurant's name in JSON format
     * 
     * @return the restaurant's name in JSON format
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the restaurant's rating 
     * 
     * @return the restaurant's rating
     */
    public double getRating() {
        return this.rating;
    }
    
    /**
     * Returns the restaurant's price 
     * 
     * @return the restaurant's price
     */
    public long getPrice() {
        return this.price;
    }
    
    /**
     * Returns a set restaurant's neighbourhoods
     * 
     * @return a set restaurant's neighbourhoods
     */
    public Set<String> getNeighbourhoods() {
        Set<String> neighbourhoods=new HashSet<String>();
        
        Iterator<String> itr = this.neighbourhoods.iterator();
        while (itr.hasNext()) {
            String currentNeighbourhood = itr.next();
            
            neighbourhoods.add(currentNeighbourhood);
        }
        
        return neighbourhoods;
    }
    
    /**
     * Returns a set restaurant's categories
     * 
     * @return a set restaurant's categories
     */
    public Set<String> getCategories() {
        Set<String> categories=new HashSet<String>();
        
        Iterator<String> itr = this.categories.iterator();
        while (itr.hasNext()) {
            String currentCategory = itr.next();
            
            categories.add(currentCategory);
        }
        
        return categories;
    }
    
    /**
     * Compares this restaurant to another restaurant. Restaurants considered the same if they have the same
     * business id 
     * 
     * @return true if the restaurants are equal
     */
    @Override
    public boolean equals(Object obj){
        return this.businessID.equals(((Restaurant) obj).getBusinessID());
    }
    
    /**
     * Overrides hashcode method as result of overriding equals
     * 
     * @return restaurant's hashcode
     */
    @Override
    public int hashCode(){
        return this.businessID.hashCode();
    }
    
    
    /**
     * Returns a copy of this restaurant
     * 
     * @return Restaurant that is a copy of this restaurant
     */
    @Override
    public Restaurant clone(){
        
        Restaurant newResto=new Restaurant(this.restoJSON);
        return newResto;
        
    }
    

}

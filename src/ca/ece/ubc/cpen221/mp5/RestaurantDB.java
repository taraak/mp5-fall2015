package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import formula.FormulaFactory;


// TODO: This class represents the Restaurant Database.
// Define the internal representation and 
// state the rep invariant and the abstraction function.

public class RestaurantDB {
    
    private Set<Restaurant> restaurantDB = new HashSet<Restaurant>();
    private Set<Review> reviewDB = new HashSet<Review>();
    private Set<User> userDB = new HashSet<User>();

	/**
	 * Create a database from the Yelp dataset given the names of three files:
	 * <ul>
	 * <li>One that contains data about the restaurants;</li>
	 * <li>One that contains reviews of the restaurants;</li>
	 * <li>One that contains information about the users that submitted reviews.
	 * </li>
	 * </ul>
	 * The files contain data in JSON format.
	 * 
	 * @param restaurantJSONfilename
	 *            the filename for the restaurant data
	 * @param reviewsJSONfilename
	 *            the filename for the reviews
	 * @param usersJSONfilename
	 *            the filename for the users
	 */
	public RestaurantDB(String restaurantJSONfilename, String reviewsJSONfilename, String usersJSONfilename) {
	    try {
            JSONParser parser = new JSONParser();

            BufferedReader restoReader=new BufferedReader(new FileReader (restaurantJSONfilename));
            String currentLine;
            
            while((currentLine=restoReader.readLine())!=null){
                JSONObject JSONResto = (JSONObject) parser.parse(currentLine); 
                this.restaurantDB.add(new Restaurant(JSONResto));
                
            }
            
            
            BufferedReader reviewReader=new BufferedReader(new FileReader (reviewsJSONfilename));
            
            while((currentLine=reviewReader.readLine())!=null){
                JSONObject JSONReview = (JSONObject) parser.parse(currentLine); 
                this.reviewDB.add(new Review(JSONReview));
                
            }
            
            
            BufferedReader userReader=new BufferedReader(new FileReader (usersJSONfilename));
            
            while((currentLine=userReader.readLine())!=null){
                JSONObject JSONReview = (JSONObject) parser.parse(currentLine); 
                this.userDB.add(new User(JSONReview));
                
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
		
	}
	
	/**
	 *  This method random review provides a random review (in JSON format)
	 *  for the restaurant that matches the provided name. If more than one restaurant
	 *  matches the name then any restaurant that satisfies the match can be selected.
	 * @param restoName restaurant name for which to find a review.
	 */
	public String randomReview(String restoName) {
	    
        JSONObject message=new JSONObject();
        
        ArrayList<String> matchingReviews= new ArrayList<String>();
        
        Iterator<Restaurant> restoIterator= this.restaurantDB.iterator();
        
        while(restoIterator.hasNext()){
            Restaurant currentResto=restoIterator.next();
            
            if(currentResto.getName().equals(restoName)){
                
                String restoBusinessID=currentResto.getBusinessID();
                for(Review review: reviewDB){
                    
                    if(review.getBusinessID().equals(restoBusinessID))
                        matchingReviews.add(review.getText());
                }
                    
            }
                
        }
        
        if(matchingReviews.isEmpty()){
            message.put("Error:", "No reviews found");
            return message.toJSONString();
        }
            
        
        int index = (int)Math.random()*matchingReviews.size();
        return matchingReviews.get(index);
	}
	
	/**
	 * This method provides the restaurant details in JSON format for the restaurant
	 * that has the provided business identifier.
	 * @param businessID unique business identifier for which to find the associated restaurant.
	 */
	public String getRestaurant(String businessID){
	    JSONObject message=new JSONObject();
	    
	    Iterator<Restaurant> restoIterator= this.restaurantDB.iterator();
	    
	    while(restoIterator.hasNext()){
	        Restaurant currentResto=restoIterator.next();
	        
	        if(currentResto.getBusinessID().equals(businessID))
	            return currentResto.getJSONDetails();
	    }
	    message.put("Error", "Invalid Request");
	    return message.toJSONString();
	}
	
	
	
	/**
	 * This method adds a new restaurant to the database with suitable checking.
	 * @param restoDetails restaurant details in JSON format to add to the database.
	 */
	public String addRestaurant(String restoDetails){
	    boolean restoAlreadyThere=false;
	    JSONObject message=new JSONObject();
	    
	       try {
	            JSONParser parser = new JSONParser();
	            Restaurant newRestaurant = new Restaurant((JSONObject) parser.parse((restoDetails)));

	            Iterator<Restaurant> restaurantItr = this.restaurantDB.iterator();
	            while (restaurantItr.hasNext()) {
	                if (restaurantItr.next().equals(newRestaurant)) {
	                    restoAlreadyThere = true;
	                    break;
	                }
	            }

	            if (!restoAlreadyThere) {
	                this.restaurantDB.add(newRestaurant);	                
	                message.put("Added?", true);

	                return message.toJSONString();
	            }
	            
	               
                message.put("Added?", false);
                return message.toJSONString();

	        } catch (ParseException e) {
	            e.printStackTrace();
	            throw new IllegalArgumentException();
	        }  
	}

    /**
     * This method adds a new user to the database with suitable checking.
     * @param userDetails user details in JSON format to add to the database.
     */
    public String addUser(JSONObject userDetails){
        return null;
    }
    
    /**
     * This method adds a new review to the database with suitable checking.
     * @param reviewDetails review details in JSON format to add to the database.
     */
    public String addReview(String reviewDetails){
        boolean reviewoAlreadyThere=false;
        JSONObject message=new JSONObject();
        
        try {
             JSONParser parser = new JSONParser();
             Review newReview = new Review((JSONObject) parser.parse((reviewDetails)));

             Iterator<Review> reviewItr = this.reviewDB.iterator();
             while (reviewItr.hasNext()) {
                 if (reviewItr.next().equals(newReview)) {
                     reviewoAlreadyThere = true;
                     break;
                 }
             }

             if (!reviewoAlreadyThere) {
                 this.reviewDB.add(newReview);
                 message.put("Added?", true);
                 return message.toJSONString();
             }
             
             message.put("Added?", false);
             return message.toJSONString();

         } catch (ParseException e) {
             e.printStackTrace();
             throw new IllegalArgumentException();
         }  
        
    }
    
	public Set<Restaurant> query(String queryString) {
	       FormulaFactory queryParser = new FormulaFactory();
	        return queryParser.parse(queryString, this);
	}
	
	
	/*
	 * Helper method that reads a single line
	 */
	private JSONObject JSONReader(String fileName){
	       try{
	            JSONParser parser = new JSONParser();

	            BufferedReader reader=new BufferedReader(new FileReader (fileName));
	            String currentLine=reader.readLine();
	            
	            if(currentLine==null){
	                throw new IllegalArgumentException();
	            }
	            
	            JSONObject object = (JSONObject) parser.parse(currentLine);
	            return object;
	            
	           }catch (FileNotFoundException e) {
	               e.printStackTrace();
	               throw new IllegalArgumentException();
	           } catch (IOException e) {
	               e.printStackTrace();
	               throw new IllegalArgumentException();
	           } catch (ParseException e) {
	               e.printStackTrace();
	               throw new IllegalArgumentException();
	           }
	    
	}
	
	/**
	 * Returns a set of all of the restaurants in the database
	 * @return Set of all of the restaurant in the database
	 */
    public  Set<Restaurant> getAllRestaurants(){
        Set<Restaurant> restaurants = new HashSet<Restaurant>();
        Iterator<Restaurant> restoIterator= this.restaurantDB.iterator();
         
         while(restoIterator.hasNext()){
             restaurants.add(restoIterator.next());
         }
         
         return restaurants;
     } 
	
}

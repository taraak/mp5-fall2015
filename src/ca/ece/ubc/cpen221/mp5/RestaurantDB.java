package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import formula.FormulaFactory;

/**
 * This class is the data type representing Yelp restaurant database
 * and contains a restaurant database, containing all restaurants and their details,
 * a user database and a review database
 * This class is thread safe as it uses CopyOnWriteArrayList, and synchronized blocks and final
 * fields
 */


public class RestaurantDB {
    
//    private Set<Restaurant> restaurantDB = new HashSet<Restaurant>();
//    private Set<Review> reviewDB = new HashSet<Review>();
//    private Set<User> userDB = new HashSet<User>();
//    
    private final List<Restaurant> restaurantDB = new CopyOnWriteArrayList<Restaurant>();
    private final List<Review> reviewDB = new CopyOnWriteArrayList<Review>();
    private final List<User> userDB = new CopyOnWriteArrayList<User>();

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
	@SuppressWarnings("unchecked")
    public String randomReview(String restoName) {
	    
        JSONObject message=new JSONObject();
        
        ArrayList<JSONObject> matchingReviews= new ArrayList<JSONObject>();
        
        Iterator<Restaurant> restoIterator= this.restaurantDB.iterator();
        
        while(restoIterator.hasNext()){
            Restaurant currentResto=restoIterator.next();
            
            if(currentResto.getName().equals(restoName)){
                
                String restoBusinessID=currentResto.getBusinessID();
                for(Review review: reviewDB){
                    
                    if(review.getBusinessID().equals(restoBusinessID))
                        matchingReviews.add(review.getJSONDetails());
                }
                    
            }
                
        }
        
        if(matchingReviews.isEmpty()){
            message.put("Error:", "No reviews found");
            return message.toJSONString();
        }
            
        
        int index = (int)Math.random()*matchingReviews.size();
        return matchingReviews.get(index).toJSONString();
	}
	
	/**
	 * This method provides the restaurant details in JSON format for the restaurant
	 * that has the provided business identifier.
	 * @param businessID unique business identifier for which to find the associated restaurant.
	 * @throws Exception 
	 */
	public Restaurant getRestaurant(String businessID) throws Exception{
	    Iterator<Restaurant> restoIterator= this.restaurantDB.iterator();
	    
	    
	    while(restoIterator.hasNext()){
	        Restaurant currentResto=restoIterator.next();
	        
	        if(currentResto.getBusinessID().equals(businessID) 
	                && !"Error".equals(currentResto.getName()))
	                
	            return new Restaurant(currentResto);
	    }
	    //TODO make this more descriptive
	     throw new Exception();
	}
	
	
	
	/**
	 * This method adds a new restaurant to the database with suitable checking.
	 * @param restoDetails restaurant details in JSON format to add to the database.
	 */
	@SuppressWarnings("unchecked")
    public synchronized String addRestaurant(String restoDetails){
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
	            
	            if(restoAlreadyThere){
	                message.put("Message:", "This restaurant already exist in the database");
	                return message.toJSONString();
	            }
	                

	            if (!restoAlreadyThere) {
	                this.restaurantDB.add(newRestaurant);	                
	                message.put("Message:", "Restaurant was successfully added to the databse");

	                return message.toJSONString();
	            }
	            
	               
                message.put("Added?", false);
                return message.toJSONString();

	        } catch (ParseException e) {
	            message.put("Error:", "Your request had syntax error");
	            return message.toJSONString();
	        }  
	}

    /**
     * This method adds a new user to the database with suitable checking.
     * @param userDetails user details in JSON format to add to the database.
     */
    public synchronized String addUser(String userDetails){
        boolean userAlreadyThere=false;
        JSONObject message=new JSONObject();
        
           try {
                JSONParser parser = new JSONParser();
                User newUser = new User((JSONObject) parser.parse((userDetails)));

                Iterator<User> userItr = this.userDB.iterator();
                while (userItr.hasNext()) {
                    if (userItr.next().equals(newUser)) {
                        userAlreadyThere = true;
                        break;
                    }
                }

                if (!userAlreadyThere) {
                    this.userDB.add(newUser);                   
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
     * This method adds a new review to the database with suitable checking.
     * @param reviewDetails review details in JSON format to add to the database.
     */
    public synchronized String addReview(String reviewDetails){
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
    
    /**
     * Parsers a query using query parser from FormulaFactory class
     * @param queryString the query from client 
     * @return teh parsed query
     */
	public Set<Restaurant> query(String queryString) {
	       FormulaFactory queryParser = new FormulaFactory();
	        return queryParser.parse(queryString, this);
	}
	
	

	
	/**
	 * Helper method that returns a set of all of the restaurants in the database
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
    
    /**
     * Helper method that returns a set of all of the reviews in the database
     * @return Set of all of the reviews in the database
     */
    public  Set<Review> getAllReviews(){
        Set<Review> reviews = new HashSet<Review>();
        Iterator<Review> reviewIterator= this.reviewDB.iterator();
         
         while(reviewIterator.hasNext()){
             reviews.add(reviewIterator.next());
         }
         
         return reviews;
     } 
    
	
}

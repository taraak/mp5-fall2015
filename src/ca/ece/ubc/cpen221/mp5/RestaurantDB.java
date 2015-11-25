package ca.ece.ubc.cpen221.mp5;

import java.util.Set;

import org.json.simple.JSONObject;

// TODO: This class represents the Restaurant Database.
// Define the internal representation and 
// state the rep invariant and the abstraction function.

public class RestaurantDB {

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
		// TODO: Implement this method
	}
	
	/**
	 *  This method random review provides a random review (in JSON format)
	 *  for the restaurant that matches the provided name. If more than one restaurant
	 *  matches the name then any restaurant that satisfies the match can be selected.
	 * @param restoName restaurant name for which to find a review.
	 */
	public JSONObject randomReview(String restoName) {
        return null;	    
	}
	
	/**
	 * This method provides the restaurant details in JSON format for the restaurant
	 * that has the provided business identifier.
	 * @param businessID unique business identifier for which to find the associated restaurant.
	 */
	public JSONObject getRestaurant(String businessID){
	    return null;
	}
	
	/**
	 * This method adds a new restaurant to the database with suitable checking.
	 * @param restoDetails restaurant details in JSON format to add to the database.
	 */
	public void addRestaurant(JSONObject restoDetails){
	    
	}

    /**
     * This method adds a new user to the database with suitable checking.
     * @param userDetails user details in JSON format to add to the database.
     */
    public void addUser(JSONObject userDetails){
        
    }
    
    /**
     * This method adds a new review to the database with suitable checking.
     * @param reviewDetails review details in JSON format to add to the database.
     */
    public void addReview(JSONObject reviewDetails){
        
    }
    
	public Set<Restaurant> query(String queryString) {
		// TODO: Implement this method
		// Write specs, etc.
		return null;
	}

	//fuck git
}

package ca.ece.ubc.cpen221.mp5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;

// TODO: Use this class to represent a Yelp user.

public class User {
    final private JSONObject userJSON;

    final private String userID;
    final private String name;
    final private String url;
    final private String type;
    final private Map<String, Long> votes = new HashMap<String, Long>();
    private int reviewCount;
    private long avgRating;
    
    /**
     * Create a user object.
     * 
     */
    public User(JSONObject obj){
        
        this.userJSON = (JSONObject) obj.clone();

        this.userID = this.userJSON.get("user_id").toString();
        this.name = this.userJSON.get("name").toString();
        this.url = this.userJSON.get("url").toString();       
        this.type = this.userJSON.get("type").toString();       
        this.avgRating = (Long) this.userJSON.get("average_stars");
        
        JSONObject allVotes = (JSONObject) this.userJSON.get("votes");
        this.votes.put("cool", (Long) allVotes.get("cool"));
        this.votes.put("useful", (Long) allVotes.get("useful"));
        this.votes.put("funny", (Long) allVotes.get("funny"));
    }
    
    /**
     * Returns the user details in JSON format
     * 
     * @return the user details in JSON format
     */
    public JSONObject getJSONDetails() {
        return Util.jsonCopier(userJSON);
    }
    
    /**
     * Returns the user's votes in JSON format
     * 
     * @return the user's votes in JSON format
     */
    public Long getVotes(String voteType) {
        return votes.get(voteType);
    }
    
    
    /**
     * Returns the user ID
     * 
     * @return the user ID
     */
    public String getUserID() {
        return this.userID;
    }
    
    /**
     * Returns the user's name
     * 
     * @return the user's name
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the user's url
     * 
     * @return the user's url
     */
    public String getURL() {
        return this.url;
    }
    
    /**
     * Returns the user's review count
     * 
     * @return the user's review count
     */
    public int getReviewCount() {
        return this.reviewCount;
    }
    
    /**
     * 
     * @param db
     * @return
     */
    public Set<Review> getUserReviews(RestaurantDB db){
        Set<Review> allReviews = db.getAllReviews();
        Set<Review> userReviews = new HashSet<Review>();
        
        for(Review review : allReviews){
            if(review.getUserID().equals(userID)){
                userReviews.add(review);
            }
        }
        
        return userReviews;       
    }
  
    /**
     * Returns the user's average rating
     * 
     * @return the user's average rating
     */
    public Long getAverageRating() {
        return this.avgRating;
    }
    
    /**
     * Compares this user to another user. Users considered the same if they have the same
     * user id 
     * 
     * @return true if the users are equal
     */
    @Override
    public boolean equals(Object obj){
        return this.userID.equals(((Review) obj).getUserID());
    }
    
    /**
     * Overrides hashcode method as result of overriding equals
     * 
     * @return user's hashcode
     */
    @Override
    public int hashCode(){
        return this.userID.hashCode();
    }

}

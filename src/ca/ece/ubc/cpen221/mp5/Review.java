package ca.ece.ubc.cpen221.mp5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

// TODO: Use this class to represent a Yelp review.

public class Review {
    final private JSONObject reviewJSON;

    final private String businessID;
    final private String reviewID;
    final private String userID;
    final private String text;
    final private String date;
    
    final private String type;
    final private Map<String, Long> votes = new HashMap<String, Long>();
    private long rating;
    
    /**
     * Create a review object.
     * 
     */
    public Review(JSONObject obj) {
        
        this.reviewJSON = (JSONObject) obj.clone();

        this.businessID = this.reviewJSON.get("business_id").toString();
        this.userID = this.reviewJSON.get("user_id").toString();
        this.reviewID = this.reviewJSON.get("review_id").toString();
        
        this.text = this.reviewJSON.get("text").toString();
        
        this.date = this.reviewJSON.get("date").toString();
        
        this.type = this.reviewJSON.get("type").toString();
        
        this.rating = (Long) this.reviewJSON.get("stars");
        
        JSONObject allVotes = (JSONObject) this.reviewJSON.get("votes");
        this.votes.put("cool", (Long) allVotes.get("cool"));
        this.votes.put("useful", (Long) allVotes.get("useful"));
        this.votes.put("funny", (Long) allVotes.get("funny"));
    }
    /**
     * Returns the review details in JSON format
     * 
     * @return the review details in JSON format
     */
    public JSONObject getJSONDetails() {
        return (JSONObject) this.reviewJSON.clone();
    }
    
    /**
     * Returns the business ID that corresponds to this review
     * 
     * @return the the business ID
     */
    public String getBusinessID() {
        return this.businessID;
    }
    
    /**
     * Returns the review ID for this review
     * 
     * @return the review ID
     */
    public String getReviewID() {
        return this.reviewID;
    }
    
    /**
     * Returns the user ID that corresponds to this review
     * 
     * @return the user ID
     */
    public String getUserID() {
        return this.userID;
    }
    
    /**
     * Returns the review text
     * 
     * @return the review text
     */
    public String getText() {
        return this.text;
    }
    
    /**
     * Returns the review date as a string formated as "YYYY-MM-DD"
     * 
     * @return the review date
     */
    public String getDate() {
        return this.date;
    }
    
    /**
     * Compares this review to another review. Reviews considered the same if they have the same
     * review id 
     * 
     * @return true if the reviews are equal
     */
    @Override
    public boolean equals(Object obj){
        return this.businessID.equals(((Review) obj).getReviewID());
    }
    
    /**
     * Overrides hashcode method as result of overriding equals
     * 
     * @return review's hashcode
     */
    @Override
    public int hashCode(){
        return this.reviewID.hashCode();
    }
}

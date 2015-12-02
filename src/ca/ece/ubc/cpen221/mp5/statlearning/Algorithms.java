package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ca.ece.ubc.cpen221.mp5.*;

public class Algorithms {

	/**
	 * Use k-means clustering to compute k clusters for the restaurants in the
	 * database.
	 * 
	 * @param db
	 * @return
	 */
    //SUPER unfinished, going to read up on using map filter reduce in java, it would be really helpful here
	public static List<Set<Restaurant>> kMeansClustering(int k, RestaurantDB db) {
		// TODO: Implement this method
	    //choose k random start centroids
	    //for each restaurant in the database, assign it to the set for which that restaurant is the closest
	    //for each centroid, find a new centroid
	    //repeat until stable
	    Set<Restaurant> restaurants = db.getAllRestaurants();
	    List<Map<Location, Set<Restaurant>>> cToResto = new LinkedList<Map<Location, Set<Restaurant>>>();
	    List<Location> centroids = new LinkedList<Location>;
	    
	    for(int i = 0; i < k; i++){
	        Location cLoc = Location.getRandomLocation();
	        Map centroid = new HashMap<Location, Set<Restaurant>>();
	        centroid.put(cLoc, new HashSet<Restaurant>());
	        cToResto.add(centroid);
	        centroids.add(cLoc);
	    }
	    
	    for (Restaurant resto : db.getAllRestaurants()){
	       
	    }
		return null;
	}

	public static String convertClustersToJSON(List<Set<Restaurant>> clusters) {
		// TODO: Implement this method
		return null;
	}

	public static MP5Function getPredictor(User u, RestaurantDB db, MP5Function featureFunction) {
		// TODO: Implement this method
		return null;
	}

	public static MP5Function getBestPredictor(User u, RestaurantDB db, List<MP5Function> featureFunctionList) {
		// TODO: Implement this method
		return null;
	}
}
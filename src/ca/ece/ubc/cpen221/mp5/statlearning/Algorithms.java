package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ca.ece.ubc.cpen221.mp5.*;

public class Algorithms {

	/**
	 * Use k-means clustering to compute k clusters for the restaurants in the
	 * database.
	 * 
	 * @param k
	 * @param db the restaurant database
	 * @return
	 */
	public static List<Set<Restaurant>> kMeansClustering(int k, RestaurantDB db) {
	    
	  //get a set of all restaurants in the database
	    Set<Restaurant> restaurants = db.getAllRestaurants();
	  //originally initialize random location as centroids
	    Set<Location> centroids=Location.getRandomLocation(k);
	    
	    
	    Map<Location, Set<Restaurant>> clusters=makeClusters(restaurants,centroids);
	    
	    //need to finish this
	  return null;
	}
	
	/*
	 * Helper method that make clusters of restaurants so that the restaurant's distance to its own clustre's
	 * centroid is smaller than its distance to all the other centroids
	 * 
	 * @param set of all restaurants in the database
	 * @param set of k centroids
     * @return a map that maps each set of restaurants that represent one cluster to their respective centroid
	 */
	public static Map<Location, Set<Restaurant>> makeClusters(Set<Restaurant> restuarants, Set<Location> centroids){
	    Map<Location, Set<Restaurant>> clusters= new ConcurrentHashMap<Location, Set<Restaurant>>();
	    
	    Iterator<Restaurant> restoItr=restuarants.iterator();
        while(restoItr.hasNext()){
            Double distance=Double.MAX_VALUE;
            
            Restaurant currentResto=restoItr.next();
            Location restoLocation=new Location(currentResto.getLocation());
            
            Iterator<Location> centroidItr=centroids.iterator();
            while(centroidItr.hasNext()){
                
                Location currentCentroid=centroidItr.next();
                Double newDistance=Location.getDistance(restoLocation, currentCentroid);
                
                if(newDistance<distance){
                    
                    if(clusters.get(currentCentroid)==null){
                        Set<Restaurant> oneCluster= new HashSet<Restaurant>();
                        oneCluster.add(currentResto);
                        clusters.put(currentCentroid, oneCluster);
                    }
                    
                    else
                        clusters.get(currentCentroid).add(currentResto);
                    
                    distance=newDistance;
                    }
                }
            }
        
        return clusters;
	}
	
	/**
     * Helper method to make a set of new centroids for the cluster
     * New centoid for each cluster is the average fo sum of longitudes and latitudes for each restaurant
     *      in that cluster
     * @param a mapping of centoids to a set of restaurtants representing a cluster
     * @return a set of new centroids
     */
    public Set<Location> getNewCentroids(Map<Location, Set<Restaurant>> clusters){
        
        Set<Location> newCentroids = new HashSet<Location>();
        
        for (Location centroid : clusters.keySet()) {
            Double longitudeSum=0.0;
            Double latitudeSum=0.0;
            
            for(Restaurant resto:clusters.get(centroid)){
                Location restoLocation=new Location(resto.getLocation());
                
                longitudeSum+=restoLocation.getLongitude();
                latitudeSum+=restoLocation.getLatitude();
            }
            
            Double newLongitude=longitudeSum/clusters.get(centroid).size();
            Double newLatitude=latitudeSum/clusters.get(centroid).size();
            
            Location newCentroid=new Location(newLongitude, newLatitude);
            newCentroids.add(newCentroid);
        }

        return Collections.unmodifiableSet(newCentroids);
    }

	
	
	public static JSONObject convertClustersToJSON(List<Set<Restaurant>> clusters) {
	    JSONObject jsonClusters = new JSONObject();
	    for(int i = 0; i < clusters.size(); i++){
	        for (Restaurant resto : clusters.get(i)){
	            //this is not giving the same info as the voronoi file has
	            //but it has more
	            //and its less work for mee
	            //missing "weight" also wuuut is that
	            JSONObject jsonResto = resto.getJSONDetails();
	            jsonResto.put("Cluster", i);
	            jsonClusters.putAll(jsonResto);
	        }
	    }
		return jsonClusters;
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
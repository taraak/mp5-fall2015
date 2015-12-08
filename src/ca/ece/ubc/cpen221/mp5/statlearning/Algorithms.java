package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ca.ece.ubc.cpen221.mp5.*;

public class Algorithms {

    /**
     * Use k-means clustering to compute k clusters for the restaurants in the
     * database.
     * 
     * @param k the number of clusters to be made
     * @param db the restaurant database
     * @return a list of clusters of restaurants; return empty set if k is negative
     */
    public static List<Set<Restaurant>> kMeansClustering(int k, RestaurantDB db) {

        // get a set of all restaurants in the database
        Set<Restaurant> restaurants = db.getAllRestaurants();
        
        Map<Location, Set<Restaurant>> clusters= new ConcurrentHashMap<Location, Set<Restaurant>>();
        List<Set<Restaurant>> clustersList = Collections.synchronizedList(new ArrayList<Set<Restaurant>>());
        
        boolean isSteady=false; //shows if clusters have achieved their steady state yet or not
        
        //if k is zero add all the restaurant in database to the list and retun it
        if(k==0){
            clustersList.add(restaurants);
            return clustersList;
        }
        
        // k can't be negative, return empty list if it is
        else if (k < 0) {
            return clustersList;
        }
        // originally initialize random location as centroids
        Set<Location> centroids = Location.getRandomLocation(k);
        
        //assign these random locations as centroids for the clusters
        for(Location currentCentroid: centroids)
            clusters.put(currentCentroid,  new HashSet<Restaurant>());
        
        //make initial clusters of restaurants based on random locations for centroids
        
        while(!isSteady){
            // group into clusters around centroids
            
            //first clear the set of restaurants around a centroid in order to make a new cluster
            for (Location loc : clusters.keySet()) {
                clusters.get(loc).clear();
            }
            
            for (Restaurant currentResto : restaurants) {
                double minDistance = Double.MAX_VALUE;
                Location closestCentroid = null;

                //go through all of the centroids and calculate each restaurant's distance from those centroids.
                //assign each restaurant to the centroid that it is closest to
                for (Location currentCentroid : clusters.keySet()) {
                    Location restoLocation=new Location(currentResto.getLocation());
                    
                    double thisDistance = Location.getDistance(currentCentroid, restoLocation);
                    
                    if (thisDistance < minDistance) {
                        closestCentroid = currentCentroid;
                        minDistance = thisDistance;

                    }
                }

                clusters.get(closestCentroid).add(currentResto);
            }
            
            // find new centroids
            Map<Location, Set<Restaurant>> newClusters = new HashMap<Location, Set<Restaurant>>();
            for(Location centroid: clusters.keySet()){
                 
                Set<Restaurant> thisCluster= clusters.get(centroid);
                
                //if there are any restaurants in that cluster
                if(thisCluster.size()>0){
                    //get a new centroid for each cluster
                    Location newCentroid=getNewCentroid(thisCluster);
                    //put the restaurants of this cluster around it
                    newClusters.put(newCentroid,thisCluster);
                }
                
                // if the clustres aren't changing any more it means that a steady state has been achieved a
                //and we are done
                if (newClusters.equals(clusters)) {
                    isSteady = true;
                }

                //update the current clusters
                clusters = newClusters;
                }
          //continue doing this until a steady state has been achieved
        }
        // put clusters into the list to return
        clustersList.addAll(clusters.values());
        
        return Collections.synchronizedList(clustersList);
        }

    /**
     * Helper method to make a new centroid for the cluster New centoid
     * for each cluster is the average of sum of longitudes and latitudes for
     * each restaurant in that cluster
     * 
     * @param a set of restaurants that represents one cluster
     * 
     * @return Loaction of teh new calculated centroid for taht cluster
     */
    public static Location getNewCentroid(Set<Restaurant> cluster) {
        Double longitudeSum = 0.0;
        Double latitudeSum = 0.0;

        for (Restaurant resto : cluster) {
            Location restoLocation = new Location(resto.getLocation());

            longitudeSum += restoLocation.getLongitude();
            latitudeSum += restoLocation.getLatitude();
        }

        Double newLongitude = longitudeSum / cluster.size();
        Double newLatitude = latitudeSum / cluster.size();

        Location newCentroid = new Location(newLongitude, newLatitude);

        return newCentroid;
    }


    /**
     *Convert the clusters from K-Means into
     * a JSON formatted string.
     * 
     * @param clusters
     *            the clusters to be converted
     * @return a JSON formatted string
     */
    @SuppressWarnings("unchecked")
    public static String convertClustersToJSON(List<Set<Restaurant>> clusters) {
        JSONArray clustersJSON = new JSONArray();

        int clusterNumber = 0;
        for (Set<Restaurant> cluster : clusters) {
            
            for (Restaurant currentResto : cluster) {
                JSONObject jsonObj = new JSONObject();
                
                jsonObj.put("x", currentResto.getLocation()[1]);
                jsonObj.put("y", currentResto.getLocation()[0]);
                jsonObj.put("name", currentResto.getName());
                jsonObj.put("cluster", clusterNumber);
                
                //I don't really know what weight is!
                jsonObj.put("weight", 1);
                clustersJSON.add(jsonObj);
            }
            clusterNumber++;
        }

        return clustersJSON.toJSONString();
    }

    /**
     * 
     * @param u
     * @param db
     * @param FF
     * @return
     * @throws Exception
     */
    public static MP5Function getPredictor(User u, RestaurantDB db, MP5Function FF) throws Exception {
        Set<Review> userReviews = u.getUserReviews(db);
        Set<DataPoint> points = new HashSet<DataPoint>();

        for (Review review : userReviews) {
            Double rating = (double) review.getRating();
            Double feature = FF.f(new Restaurant(db.getRestaurant(review.getBusinessID())), db);
            points.add(new DataPoint(feature, rating));
        }
        
        if (points.isEmpty()) throw new IllegalArgumentException("User has not submitted any reviews!");

        Double sumXX = DataPoint.sumXX(points);
        Double sumYY = DataPoint.sumYY(points);
        Double sumXY = DataPoint.sumXY(points);
        DataPoint avgPoint = DataPoint.avgPointVal(points);
        
        Double b = sumXY / sumXX;
        Double a = avgPoint.getFeature()- b*avgPoint.getRating();
        Double r_squared = Math.pow(sumXY, 2) / (sumXX*sumYY);
        
        return new Predictor(a, b, r_squared, FF);
    }

    /**
     * 
     * @param u
     * @param db
     * @param FFList
     * @return
     * @throws Exception
     */
    public static MP5Function getBestPredictor(User u, RestaurantDB db, List<MP5Function> FFList) throws Exception {
        List<Predictor> predictors = new LinkedList<Predictor>();
        for (int i = 0; i < FFList.size(); i++){
           predictors.add((Predictor) getPredictor(u, db, FFList.get(i)));
        }
        
        Double minR = predictors.get(0).getR();
        int index = 0;
        
        for (int i = 0; i < predictors.size(); i++){
            if (predictors.get(0).getR() < minR) {
                minR = predictors.get(0).getR();
                index = i;
            }
        }
        return predictors.get(index);
    }

}
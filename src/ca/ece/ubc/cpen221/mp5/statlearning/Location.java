package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Represents a location with longitude and latitude. Helper for K means clustering.
 * 
 * @author annabelleharvey
 *
 */
public class Location {
    
    final static Double MAX_LONG=-122.251;
    final static Double MIN_LONG=-122.266;
    
    final static Double MAX_LAT=37.88;
    final static Double MIN_LAT=37.86;
   
    private Double latitude;
    private Double longitude;
    
    public Location (Double[] location){
        this.longitude = location[0];
        this.latitude = location[1];
    }
    
    public Location (Double longitude, Double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }
    
    public double getLongitude() {
        return this.longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    /**
     * Returns the distance between two locations
     * @param loc1 first location
     * @param loc2 second location
     * @return distance between two locations
     */
    public static Double getDistance(Location loc1, Location loc2){
        Double lat1=loc1.getLatitude();
        Double long1=loc1.getLongitude();
        
        Double lat2=loc1.getLatitude();
        Double long2=loc1.getLongitude();
        
        Double dLat=lat1-lat2;
        Double dLong=long1-long2;
        
        return Math.sqrt(dLat*dLat+dLong*dLong);
    }
    
    /**
     *Finds a set of random locations of given size
     * @param number of location that we want in the set
     * @return a set of random locations
     */
    public static Set<Location> getRandomLocation(int num){
        Set<Location> randomLocations=new HashSet<Location>();
        Location randomLoc=null;
             
        for(int i=0; i<num-1; i++){
            
            do{
                Double longitude = Location.MIN_LONG+Math.random()*(Location.MAX_LONG-Location.MIN_LONG);
                Double latitude = Location.MIN_LAT+Math.random()*(Location.MAX_LAT-Location.MIN_LAT);
                
                randomLoc.latitude=latitude;
                randomLoc.longitude=longitude;

            }while(randomLocations.contains(randomLoc));
            
            randomLocations.add(randomLoc);
        }
        return randomLocations;
    }
    
    /**
     * Overrides equality method for location; they are equal if their longitude and 
     * latitude are very close (to the order of 10^-14)
     */
    @Override 
    public boolean equals (Object obj){
        
        Location objLocation=(Location) obj;
        
        double longThis=this.getLongitude();
        double latThis=this.getLatitude();
        
        double longThat=objLocation.getLongitude();
        double latThat=objLocation.getLatitude();
        
        if(Math.abs(longThat-longThis)<=  0.00000005
                && Math.abs(latThis - latThat) <= 0.00000000000005)
            return true;
        
        return false;
    }
    
    /**
     * Overrides hashcode as a result of overriding equality method for location
     */
    @Override
    public int hashCode() {

        return Double.toString(this.latitude).hashCode() 
                + Double.toString(this.longitude).hashCode();
    }
    
}

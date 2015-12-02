package ca.ece.ubc.cpen221.mp5.statlearning;
/**
 * 
 * Represents a location with longitude and latitude. Helper for K means clustering.
 * 
 * @author annabelleharvey
 *
 */
public class Location {
    private Double latitude;
    private Double longitude;
    
    public Location (Double latitude, Double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public Location (Double[] location){
        //this is bull, i dont know which one is which in the array
        //indices might need to switch
        this.latitude = location[0];
        this.longitude = location[1];
    }
    
    public static Double getDistance(Location loc1, Location loc2){
        return Math.sqrt(Math.pow((loc1.latitude - loc2.latitude),2) + Math.pow((loc1.longitude - loc2.longitude),2));
    }
    
    /**
     * Finds a new random location near a given location by looking in the box upward and to the left.
     * ------Could probably make this better, but the point is to give a location in range of the data
     * so it doesn't really matter that much.
     * @param loc
     * @return
     */
    public static Location getRandomLocation(){
        //HARDCODED IN HERE BECAUSE ILL THINK ABOUT THIS LATER
        Double longitude = -122.2519943 - Math.random()*(122.2666688-122.2519943);
        Double latitude = 37.863919 + Math.random()*(37.8759615-37.863919);
        return new Location(latitude, longitude);
    }
}

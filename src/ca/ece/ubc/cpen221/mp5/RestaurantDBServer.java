package ca.ece.ubc.cpen221.mp5;

// TODO: Implement a server that will instantiate a database, 
// process queries concurrently, etc.

public class RestaurantDBServer {
    
    private String port;
    private String reviewsJSONfilename; 
    private String restaurantJSONfilename; 
    private String usersJSONfilename; 

	/**
	 * Constructor
	 * 
	 * @param port
	 * @param restaurantJSONfilename
	 * @param reviewsJSONfilename
	 * @param usersJSONfilename
	 */
	public RestaurantDBServer(int port, String restoFile, String reviewsFile, String usersFile) {
		// TODO: See the problem statement for what the arguments are.
		// TODO: Rename the arguments suitably.

	}
	
	public void main(String[] args){
	    this.port=args[0];
	    this.restaurantJSONfilename=args[1];
	    this.reviewsJSONfilename=args[2];
	    this.usersJSONfilename=args[3];
	    
	    
	}

}

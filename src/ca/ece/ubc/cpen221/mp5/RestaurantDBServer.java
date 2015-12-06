package ca.ece.ubc.cpen221.mp5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// TODO: Implement a server that will instantiate a database, 
// process queries concurrently, etc.

public class RestaurantDBServer implements Runnable {
    
    private final int port;
    private String reviewsFile; 
    private String restoFile; 
    private String usersFile; 
    private ServerSocket serverSocket;
    private Boolean isStopped = false;
    
    private final RestaurantDB database;

	/**
	 * Constructor
	 * 
	 * @param port
	 * @param restaurantJSONfilename
	 * @param reviewsJSONfilename
	 * @param usersJSONfilename
	 */
	public RestaurantDBServer(int port, String restaurantDetails, String userReviews, String userDetails) {
	    this.port = port;
	    
        this.database = new RestaurantDB(restaurantDetails, userReviews, userDetails);
        System.out.println("Database created");

        openServerSocket();
	}

    public void run(){
        
        openServerSocket();
        
        while(!isStopped()){
            
            Socket clientSocket = null;
            
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                
                if(isStopped()) {
                    System.out.println("Server Not  On.") ;
                }  
                else{
                    e.printStackTrace();
                    throw new IllegalArgumentException( "Error accepting client connection", e);
                }
            }
            new Thread(new RestaurantDBWorkerThread(clientSocket,this.database)).start();
        }
    }
    
    

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    /**
     * Helper method to stop the server socket.
     */
    public synchronized void stop(){
       
        try {
            this.serverSocket.close();
            this.isStopped = true;
            
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error closing server", e);
        }
    }

    /**
     * Helper method to to open  server socket.
     */
    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            isStopped=false;
            System.out.println("Server Socket Opened");
            
        } catch (IOException e) {
            
            isStopped=true;
            throw new RuntimeException("Cannot open port" + port, e);
        }
        
    }

	public void main(String[] args){
	    RestaurantDBServer server = new RestaurantDBServer(Integer.parseInt(args[0]), args[1], args[2], args[3]);
	    server.run();

	}

}

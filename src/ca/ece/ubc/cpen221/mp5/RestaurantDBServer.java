package ca.ece.ubc.cpen221.mp5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Multi-threaded server that instantiates a database and processes the queries
 * concurrently
 *
 */

public class RestaurantDBServer implements Runnable {
    
    private final int port;
    private String reviewsFile; 
    private String restoFile; 
    private String usersFile; 
    private ServerSocket serverSocket;
    private Boolean isStopped = false;
    
    private final RestaurantDB database;

    /**
     * Constructor for RestaurantDBServer. Listens for connections on port and creates a restaurant database.
     * 
     * @param port port number, must be greater than or equal to zero, port M = 65535
     * @param restaurantJSONfilename name of a file that contains restaurant details in JSON format
     * @param reviewsJSONfilename name of a file that contains review details in JSON format
     * @param usersJSONfilename name of a file that contains user details in JSON format
     */
    public RestaurantDBServer(int port, String restaurantDetails, String userReviews, String userDetails) {
        this.port = port;
        
        this.database = new RestaurantDB(restaurantDetails, userReviews, userDetails);
        System.out.println("Database created");

        this.run();
	}

    public void run(){
        
        openServerSocket();
        
        while(!isStopped()){      
            Socket clientSocket = null;
            
            try {
                //listen until connection is made
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                //print error messages if there is an issue
                if(isStopped()) {
                    System.out.println("Server not online.");
                }  
                else{
                    e.printStackTrace();
                    throw new IllegalArgumentException( "Error accepting connection");
                }
            }
            //when connection is made, create a new thread to handle queries and go back to listening
            new Thread(new RestaurantDBWorkerThread(clientSocket,this.database)).start();
        }
    }
    
    
    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    /**
     * Helper method to stop the server socket.
     */
    public synchronized void stopServerSocket(){
       
        try {
            this.serverSocket.close();
            this.isStopped = true;
            
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error closing server");
        }
    }

    /**
     * Helper method to to open server socket.
     */
    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            isStopped=false;
            System.out.println("Server socket opened.");
            
        } catch (IOException e) {
            isStopped=true;
            throw new RuntimeException("Cannot open port" + port);
        }
        
    }
    
    public void main(String[] args){
        RestaurantDBServer server = new RestaurantDBServer(Integer.parseInt(args[0]), args[1], args[2], args[3]);
        
        server.stopServerSocket();
        return;

    }

}

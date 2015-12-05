package ca.ece.ubc.cpen221.mp5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// TODO: Implement a server that will instantiate a database, 
// process queries concurrently, etc.

public class RestaurantDBServer implements Runnable {
    
    private int port;
    private String reviewsFile; 
    private String restoFile; 
    private String usersFile; 
    private ServerSocket serverSocket;
    private Boolean isStopped = false;

	/**
	 * Constructor
	 * 
	 * @param port
	 * @param restaurantJSONfilename
	 * @param reviewsJSONfilename
	 * @param usersJSONfilename
	 */
	public RestaurantDBServer(int port, String restoFile, String reviewsFile, String usersFile) {
	    this.port = port;
        this.restoFile = restoFile;
        this.reviewsFile = reviewsFile;
        this.usersFile = usersFile;

	}

    public void run(){
        RestaurantDB database = new RestaurantDB(restoFile, reviewsFile, usersFile);
        
        openServerSocket();
        while(!isStopped()){
            
            Socket clientSocket = null;
            
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }  
                throw new RuntimeException( "Error accepting client connection", e);
            }
            new Thread(new RestaurantDBWorkerThread(clientSocket)).start();
        }
        System.out.println("Server Stopped.") ;
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port" + port, e);
        }
    }

	public void main(String[] args){
	    RestaurantDBServer server = new RestaurantDBServer(Integer.parseInt(args[0]), args[1], args[2], args[3]);
	    server.run();

	}

}

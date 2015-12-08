package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RestaurantDBClient {
    
    public RestaurantDBClient(String hostname, int port){
       try (
            Socket socket = new Socket(hostname, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
               ) {
           BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
           
            String fromServer = in.readLine();
            String fromUser = stdIn.readLine();
 
            if(fromServer != null) {
                System.out.println("Server: " + fromServer);
                fromServer = null;
            }
            
            if (fromUser != null) {
                System.out.println("Client: " + fromUser);
                out.println(fromUser);
                fromUser = null;
                }
        } catch (IOException e) {
            System.err.println("Connection Failed");
            System.exit(1);
        }
    }
        
    public static void main(String[] args) throws IOException {   
        RestaurantDBClient client = new RestaurantDBClient(args[0], Integer.parseInt(args[1]));
    }
}

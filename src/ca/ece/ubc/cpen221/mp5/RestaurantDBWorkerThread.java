package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.simple.JSONObject;

public class RestaurantDBWorkerThread implements Runnable {
    private Socket socket;
    private RestaurantDB database;
    
    public RestaurantDBWorkerThread(Socket socket, RestaurantDB database){
        this.socket = socket;
        this.database = database;
    }

    @Override
    public void run() {
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            ) {
                String inputLine;
                String output;
                
                
                output = processInput(null);
                out.println(output);

                while ((inputLine = in.readLine()) != null) {
                    
                    output = processInput(inputLine);
                    out.println(output);
                    
                    if (output.equals("poisonPill"))
                        break;
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        
    }
    
    /**
     * Parse a query 
     * @param input
     * @return answer to that query
     */
    private String processInput(String input){
        JSONObject message = new JSONObject();

        String answer = message.toJSONString();
        String type = input.substring(0, input.indexOf("("));

        if ('\"' == input.charAt(input.indexOf("(") + 1) && '\"' == input.charAt(input.length() - 2)) {

            if ("getRestaurant".equals(type)) {
                String businessID = input.substring(15, input.length() - 2);
                answer = database.getRestaurant(businessID);
            }

            else if ("addRestaurant".equals(type)) {
                String restaurantDetails = input.substring(15, input.length() - 2);
                answer = database.addRestaurant(restaurantDetails);
            }

            else if ("addUser".equals(type)) {
                String userDetails = input.substring(9, input.length() - 2);
                answer = database.addUser(userDetails);

            }

            else if ("addReview".equals(type)) {
                String reviewDetails = input.substring(11, input.length() - 2);
                answer = database.addReview(reviewDetails);
            }

            else if ("randomReview".equals(type)) {
                String restaurantName = input.substring(14, input.length() - 2);
                answer = database.randomReview(restaurantName);
            }
        }

        return answer;
    }
    
    /**
     * Checks to see if a query is acceptable
     * An acceptable query is one of "randomReview"/ "getRestaurant"/
     *      "addRestaurant"/"addUser"/"addReview"
     * @param request
     * @return true if acceptable and false otherwise
     */
    public static boolean isAcceptableQuery(String request) {

        if (request.indexOf("(") != -1) {

            String requestType = request.substring(0, request.indexOf("("));
            if ("randomReview".equals(requestType) || "getRestaurant".equals(requestType)
                    || "addRestaurant".equals(requestType) || "addUser".equals(requestType)
                    || "addReview".equals(requestType)) {
                return true;
            }
        }
        return false;
    }


}

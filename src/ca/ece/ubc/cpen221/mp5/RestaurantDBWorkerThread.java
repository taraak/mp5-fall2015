package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

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
        String POISON_PILL="Done";
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            ) {
                String inputLine;
                String output;
                

                while ((inputLine = in.readLine()) != null) {
                    
                    
                    if (inputLine.equals(POISON_PILL))
                        break;
                    
                    else if(isAcceptableQuery(inputLine))
                        output=processInput(inputLine);
                    
                    else
                        output=processSearchQuery(inputLine);
                    
                    out.println(output);
                }
                
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                System.err.println("Error running the server on that port");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
    
    /**
     * Parse a query that is one of the type:  "randomReview"/ "getRestaurant"/
     *      "addRestaurant"/"addUser"/"addReview"
     * @param input query from the client
     * @return answer to that query
     * @throws Exception 
     */
    private String processInput(String input) throws Exception{
        JSONObject message = new JSONObject();

        String answer = message.toJSONString();
        String type = input.substring(0, input.indexOf("("));

        if ('\"' == input.charAt(input.indexOf("(") + 1) &&
                '\"' == input.charAt(input.length() - 2)
                && (input.indexOf("(")+1) != (input.length() - 2)) {

            if ("getRestaurant".equals(type)) {
                String businessID = input.substring(15, input.length() - 2);
                answer = database.getRestaurant(businessID).toString();
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
    
    
    /**
     * Responds to the type of query that is searching for specific restaurants
     * of specified details
     * @param input from the client
     * @return
     */
    private String processSearchQuery(String input) {
        Restaurant errorRestaurant = new Restaurant();
        JSONObject message = new JSONObject();

        Set<Restaurant> answer = database.query(input);
        
        if (answer.isEmpty()) {

            message.put("Answer:", "No results found for your query");
            return message.toJSONString();
        }

        else if (answer.contains(errorRestaurant)) {
            message.put("Answer:", "Invalid input query");
            return message.toJSONString();
        }

        return answer.toString();
    }
}

package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.simple.JSONObject;

public class RestaurantDBWorkerThread implements Runnable {
    private Socket socket;
    
    public RestaurantDBWorkerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            ) {
                String inputLine;
                JSONObject output;
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
    
    private JSONObject processInput(String input){
        //TODO parse the query, call the method on the database, return the output
        return new JSONObject();
    }

}

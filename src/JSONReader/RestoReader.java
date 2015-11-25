package JSONReader;

import java.util.Iterator;
import java.io.FileReader;
import java.io.BufferedReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RestoReader {

    public static void restaurantReader(String fileName){
        @SuppressWarnings("unchecked")
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser
                    .parse(new BufferedReader(new FileReader(fileName)).readLine());

            JSONObject jsonObject = (JSONObject) obj; 

            String name = jsonObject.get("name").toString();
            String city = jsonObject.get("city").toString();
            String businessID= jsonObject.get("business_id").toString();
            JSONArray categories = (JSONArray) jsonObject.get("categories");
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}
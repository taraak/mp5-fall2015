import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonReader {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser
                    .parse(new BufferedReader(new FileReader("data/restaurants.json")).readLine());

            JSONObject jsonObject = (JSONObject) obj; 

            String name = (String) jsonObject.get("name");
            String city = (String) jsonObject.get("city");
            String businessID= (String) jsonObject.get("business_id");
            JSONArray categories = (JSONArray) jsonObject.get("categories");

            System.out.println("Name: " + name);
            System.out.println("City: " + city);
            System.out.println("Business Id: " + businessID);
            System.out.print("Categories: ");        
            Iterator<String> iterator = categories.iterator();
            while (iterator.hasNext()) {
                System.out.print(iterator.next()+" ");
            }
            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
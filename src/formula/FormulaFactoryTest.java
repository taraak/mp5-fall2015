package formula;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;
public class FormulaFactoryTest {
    RestaurantDB testDB = new RestaurantDB("data/restaurants.json", "data/reviews.json", "data/users.json");

        @Test
        public void Test1() {
            FormulaFactory parser = new FormulaFactory();

            String query = "(category(\"Chinese\") || category(\"Italian\") + && price(1..2) ";

            Set<Restaurant> result = parser.parse(query, testDB);

            for (Restaurant current : result) {
                System.out.println(current.getName());
            }
            if (result.isEmpty()) {
                System.out.println("no results");
            }
        }

}

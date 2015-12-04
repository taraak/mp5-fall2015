package formula;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;

public class Atom implements Formula {

    String requestType;
    String toFind;
    Set<Restaurant> restoDB=new HashSet<Restaurant>();
    
    public Atom(String type, String toFind, RestaurantDB restoDB){
        this.requestType=type;
        this.toFind=toFind;
        
        Iterator<Restaurant> restoIterator= restoDB.getAllRestaurants().iterator();
        
        while(restoIterator.hasNext()){
            this.restoDB.add(restoIterator.next());
        }
    }
    
    @Override
    public Set<Restaurant> evaluate() {
        Set<Restaurant> results = Collections.synchronizedSet(new HashSet<Restaurant>());
        
        if (requestType.equals("name")) {
            
            Iterator<Restaurant> restaurantItr = this.restoDB.iterator();
            while (restaurantItr.hasNext()) {
                Restaurant resto = restaurantItr.next();
                
                if(resto.getName().equals(requestType))
                    results.add(resto);
            }
        }

        else if (requestType.equals("in")) {
            Iterator<Restaurant> restaurantItr = this.restoDB.iterator();
            while (restaurantItr.hasNext()) {
                Restaurant resto = restaurantItr.next();

                Set<String> neighbourhoods = resto.getNeighbourhoods();

                if (neighbourhoods.contains(toFind)) {
                    results.add(resto);
                }
            }
        }
        
        else if (requestType.equals("category")) {
            
            Iterator<Restaurant> itr = this.restoDB.iterator();
            while (itr.hasNext()) {
                Restaurant resto = itr.next();

                Set<String> categories = resto.getCategories();

                if (categories.contains(toFind)) {
                    results.add(resto);
                }
            }
        }
        
        else if (requestType.equals("rating")) {
            
            Iterator<Restaurant> restaurantItr = this.restoDB.iterator();
            while (restaurantItr.hasNext()) {
                Restaurant resto = restaurantItr.next();
                
                double lowerBound=toFind.charAt(0);
                double upperBound=toFind.charAt(3);
                
                if(resto.getRating()>=lowerBound && resto.getRating()<=upperBound)
                    results.add(resto);
            }
        }
        
        else if (requestType.equals("price")) {
            
            Iterator<Restaurant> restaurantItr = this.restoDB.iterator();
            while (restaurantItr.hasNext()) {
                Restaurant resto = restaurantItr.next();
                
                double lowerBound=toFind.charAt(0);
                double upperBound=toFind.charAt(3);
                
                if(resto.getPrice()>=lowerBound && resto.getPrice()<=upperBound)
                    results.add(resto);
            }
        }
        
        return results;
    }

}

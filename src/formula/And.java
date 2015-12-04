package formula;

import java.util.HashSet;
import java.util.Set;

import ca.ece.ubc.cpen221.mp5.Restaurant;

public class And implements Formula {

    Set<Restaurant> result1=new HashSet<>();
    Set<Restaurant> result2=new HashSet<>();
    
    public And(Set<Restaurant> result1, Set<Restaurant> result2){
        
        this.result1=result1;
        this.result2=result2;
    }
    

    
    /**
     * Gets two sets of restaurants as input and returns the intersection of those sets
     *  as the result of or expression, thus returning the restaurants that are in both sets
     * 
     * @param result1
     * @param result2
     * @return a set of restaurants that are in both of the input sets
     */
    @Override
    public Set<Restaurant> evaluate(){
        Set<Restaurant> answer=new HashSet<Restaurant>();
        
        Set<Restaurant> toAdd=new HashSet<Restaurant>();
        toAdd.addAll(result1);
        toAdd.retainAll(result2);
        
        answer.addAll(toAdd);
        

        return answer;
        
    }
}

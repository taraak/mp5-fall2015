package formula;

import java.util.HashSet;
import java.util.Set;

import ca.ece.ubc.cpen221.mp5.Restaurant;

public class Or implements Formula {
    
    Set<Restaurant>result1=new HashSet<Restaurant>();
    Set<Restaurant>result2=new HashSet<Restaurant>();
    
    public Or(Set<Restaurant> result1, Set<Restaurant> result2){
        
        this.result1=result1;
        this.result1=result2;
        
    }
    
    
    /**
     * Gets two sets of restaurants as input and returns the union of those sets
     *  as the result of or expression
     * 
     * @param result1
     * @param result2
     * @return a set of restaurants that are in either one of the input sets
     */
    @Override
    public Set<Restaurant> evaluate(){
        Set<Restaurant> answer=new HashSet<Restaurant>();
        
        answer.addAll(result1);
        answer.addAll(result2);
        
        return answer;
        
    }

}

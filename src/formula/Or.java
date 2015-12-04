package formula;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import ca.ece.ubc.cpen221.mp5.Restaurant;

public class Or implements Formula {
    
    ArrayList<And> andExps=new ArrayList<And>();
    
    public Or(ArrayList<And> andExps){
        
        for(And andExp: andExps){
            this.andExps.add(andExp);
        }
        
    }
    
    @Override
    public Set<Restaurant> evaluate() {
        
        Stack<Set<Restaurant>> result=new Stack<>();
        
        for(And andExpr:andExps){
            result.push(andExpr.evaluate());
        }
        
        Set<Restaurant> result1=new HashSet<>();
        Set<Restaurant> result2=new HashSet<>();
        
        while(!result.isEmpty()){
            
            result1=result.pop();
            if(result.isEmpty()){
                break;
            }
            else{
                result2=result.pop();
                result.push(or(result1,result2));
            }
        }
        
        return result1;
    }
    
    /**
     * Gets two sets of restaurants as input and returns the union of those sets
     *  as the result of or expression
     * 
     * @param result1
     * @param result2
     * @return a set of restaurants that are in either one of the input sets
     */
    public Set<Restaurant> or(Set<Restaurant>result1,Set<Restaurant>result2){
        Set<Restaurant> answer=new HashSet<Restaurant>();
        
        answer.addAll(result1);
        answer.addAll(result2);
        
        return answer;
        
    }

}

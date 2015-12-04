package formula;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ca.ece.ubc.cpen221.mp5.Restaurant;

public class Atom implements Formula {

    String type;
    String toFind;
    
    public Atom(String type, String toFind){
        this.type=type;
        this.toFind=toFind;
    }
    
    @Override
    public Set<Restaurant> evaluate() {
        return null;
    }

}

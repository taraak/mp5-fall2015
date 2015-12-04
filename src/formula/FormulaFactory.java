package formula;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;


public class FormulaFactory {
    private RestaurantDB database;
    
    
    /**
     * @param string must contain a well-formed formula string of boolean literals and operators..
     * @return Formula corresponding to the string
     */
    public Set<Restaurant> parse(String query, RestaurantDB restoDB) {
        this.database=restoDB;
        
        try{
            // Create a stream of tokens using the lexer.
            CharStream stream = new ANTLRInputStream(query);
            FormulaLexer lexer = new FormulaLexer(stream);
            lexer.reportErrorsAsExceptions();
            TokenStream tokens = new CommonTokenStream(lexer);
            
            // Feed the tokens into the parser.
            FormulaParser parser = new FormulaParser(tokens);
            parser.reportErrorsAsExceptions();
            
            // Generate the parse tree using the starter rule.
            ParseTree tree = parser.orExpr(); // "root" is the starter rule.
            System.out.println("FUUUCK");
            
            // debugging option #1: print the tree to the console
            //System.err.println(tree.toStringTree(parser));

            // debugging option #2: show the tree in a window
            //((RuleContext)tree).inspect(parser);

            // debugging option #3: walk the tree with a listener
            new ParseTreeWalker().walk(new FormulaListener_PrintEverything(), tree);
            
            // Finally, construct a Document value by walking over the parse tree.
            ParseTreeWalker walker = new ParseTreeWalker();
            FormulaListener_FormulaCreator listener = new FormulaListener_FormulaCreator();
            walker.walk(listener, tree);
            
            // return the Document value that the listener created
            return listener.answer();
            
        }
        catch (RuntimeException e) {

        }
        
        Restaurant errorRestaurant = new Restaurant();
        Set<Restaurant> errorSet = new HashSet<Restaurant>();
        errorSet.add(errorRestaurant);
        return errorSet;

    }
    
    
    private class FormulaListener_FormulaCreator extends FormulaBaseListener {
        
        //stack stores the restaurant that matches each of the atomic expressions
        private Stack<Set<Restaurant>> stack = new Stack<Set<Restaurant>>();
        
        // this represents the set of restaurants that corresponds to the  query
        private final Set<Restaurant> answerRestaurants = Collections.synchronizedSet(new HashSet<Restaurant>());
        
        public Set<Restaurant> answer() {

            // only the final set should be left on the stack
            assert stack.size() == 1;
            answerRestaurants.addAll(stack.get(0));

            // clone the set from the stack and add it to the restaurantSet
            Set<Restaurant> clonedAnswer = Collections.synchronizedSet(Collections.unmodifiableSet(answerRestaurants));

            return clonedAnswer;
        }
        
        
        @Override
        public void exitOrExpr(@NotNull FormulaParser.OrExprContext ctx) { 
            //or expression is the union of the restaurants given by each of its two expressions
            if (ctx.OR() != null) {
                // we matched the OR rule
                int count=0;
                
                while(count < (ctx.getChildCount()-1) /2 && stack.size()>1){
                    Set<Restaurant> result1 = stack.pop();
                    Set<Restaurant> result2 = stack.pop();
                    
                    Or or = new Or(result1, result2);
                    stack.push(or.evaluate()); 
                    
                    count++;
                }
                
            } 
            else {
                //do nothing
            }
        }
        
        @Override
        public void exitAndExpr(@NotNull FormulaParser.AndExprContext ctx) { 
            //and expression is the intersection of the restaurants found by evaluating each of its two expressions
            if (ctx.AND() != null) {
             // we matched the AND rule 
                int count=0;
                
                while(count < (ctx.getChildCount()-1) /2 && stack.size()>1){
                    
                    
                    Set<Restaurant> result1 = stack.pop();
                    Set<Restaurant> result2 = stack.pop();
                    
                    And and = new And(result1, result2);
                    stack.push(and.evaluate());  
                    
                    count++;
                }
                
            }
            
            else {
                //do nothing
            }
        }
        
        @Override
        public void exitAtom(@NotNull FormulaParser.AtomContext ctx) { 
            String text = ctx.getText();
            
            if(ctx.start.getType()==FormulaParser.IN){
                
                // request type should be in
                String requestType = text.substring(0, 2);
                // we want neighbourhood toFind
                String toFind = text.substring(4, text.length() - 2);
                
                Atom atom = new Atom(requestType,toFind,database);
                
                Set<Restaurant> results = Collections.synchronizedSet(atom.evaluate());
                stack.push(results);
            }
            
            else if(ctx.start.getType()==FormulaParser.CATEGORY){
                
                // request type should be in
                String requestType = text.substring(0, 8);
                // we want category toFind
                String toFind = text.substring(10, text.length() - 2);
                
                Atom atom = new Atom(requestType,toFind,database);
                
                Set<Restaurant> results = Collections.synchronizedSet(atom.evaluate());
                stack.push(results);
            }
            
            else if(ctx.start.getType()==FormulaParser.PRICE){
                
                // request type should be in
                String requestType = text.substring(0, 5);
                // we want category toFind
                String toFind = text.substring(6, text.length() - 1);
                
                Atom atom = new Atom(requestType,toFind,database);
                
                Set<Restaurant> results = Collections.synchronizedSet(atom.evaluate());
                stack.push(results);
            }
            
            else if(ctx.start.getType()==FormulaParser.RATING){
                
                // request type should be in
                String requestType = text.substring(0, 6);
                // we want category toFind
                String toFind = text.substring(7, text.length() - 1);
                
                Atom atom = new Atom(requestType,toFind,database);
                
                Set<Restaurant> results = Collections.synchronizedSet(atom.evaluate());
                stack.push(results);
            }
            
            else if(ctx.start.getType()==FormulaParser.NAME){
                
                // request type should be in
                String requestType = text.substring(0, 4);
                // we want category toFind
                String toFind = text.substring(6, text.length() - 2);
                
                Atom atom = new Atom(requestType,toFind,database);
                
                Set<Restaurant> results = Collections.synchronizedSet(atom.evaluate());
                stack.push(results);
            }
            
            
        }
        
        
       
    }
    
    private static class FormulaListener_PrintEverything extends FormulaBaseListener {
        public void enterOrExpr(@NotNull FormulaParser.OrExprContext ctx) { 
            System.err.println("entering or expression " + ctx.getText());
        }
        
        public void exitOrExpr(@NotNull FormulaParser.OrExprContext ctx) { 
            System.err.println("exiting or expression " + ctx.getText());
        }
        
        public void enterAtom(@NotNull FormulaParser.AtomContext ctx) { 
            System.err.println("entering atom " + ctx.getText());
        }
        
        public void exitAtom(@NotNull FormulaParser.AtomContext ctx) { 
            System.err.println("exiting atom " + ctx.getText());
        }
        public void enterAndExpr(@NotNull FormulaParser.AndExprContext ctx) { 
            System.err.println("entering and expression " + ctx.getText());
        }
        public void exitAndExpr(@NotNull FormulaParser.AndExprContext ctx) { 
            System.err.println("entering exiting and expression " + ctx.getText());
        }

    }
}
